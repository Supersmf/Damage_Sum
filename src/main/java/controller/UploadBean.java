package controller;

import service.Damage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
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
		} catch (Exception e) {
			e.printStackTrace();
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
