import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.io.*;


@ManagedBean
@ViewScoped
public class UploadBean {

	private Part file;
	private String[] bolts;
	private String[] nuts;
	private int damage;
	private String message;

	public void upload() {
		try {
			bolts = Damage.getItem(file,"bolts");
			nuts = Damage.getItem(file,"nuts");
			damage = Damage.damageSum(bolts, nuts);
			message = file.getSubmittedFileName();
		} catch (Exception e1) {
			e1.printStackTrace();
			message = "It's impossible to read the file";
		}
	}

	public void downloadFile(){
		OutputStream outputStream = null;
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.responseReset();
			externalContext.setResponseContentType("text/plain");
			externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"output.txt\"");
			outputStream = externalContext.getResponseOutputStream();
			outputStream.write(String.valueOf(damage).getBytes("UTF-8"));
			context.responseComplete();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}finally {
			try {
				if(outputStream != null)
					outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void validate(FacesContext context, UIComponent component, Object value) {
		Part file = (Part) value;

		if (!(file.getSubmittedFileName()).contains(".txt")){
			throw new ValidatorException(new FacesMessage("File format should be '.txt'"));
		}
		if (file.getSize() == 0) {
			throw new ValidatorException(new FacesMessage("File is empty"));
		}
		if (file.getSize() > 50) {
			throw new ValidatorException(new FacesMessage("File is to large"));
		}
		if (!file.getContentType().equals("text/plain"))
			throw new ValidatorException(new FacesMessage("File is not a text file"));
	}
	
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void setBolts(String[] bolts) {
		this.bolts = bolts;
	}

	public String[] getBolts() {
		return bolts;
	}

	public String[] getNuts() {
		return nuts;
	}

	public void setNuts(String[] nuts) {
		this.nuts = nuts;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
