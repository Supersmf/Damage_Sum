package validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 * Created by Oleg on 20.02.2017.
 */
@FacesValidator("fileValidator")
public class FileValidator implements Validator{
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
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
}
