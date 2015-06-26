package mb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Seguro;
import facade.SeguroFacade;

@ManagedBean
@ViewScoped
public class SeguroMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private SeguroFacade seguroFacade;
	
	private Seguro mSeguro;
	
	public Seguro getSeguro() {
		if(mSeguro == null){
			mSeguro = new Seguro();
		}
		return mSeguro;
	}

	public void setSeguro(Seguro seguro) {
		mSeguro = seguro;
	}

	public String create(){
		try {
			seguroFacade.save(mSeguro);
		} catch (EJBException e) {
			sendErrorMessageToUser("Error");
			return "Error";
		}
		sendInfoMessageToUser("Operacion completada.");
		return "OK";
	}
	
	private void sendInfoMessageToUser(String message) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,message,message));		
	}

	private void sendErrorMessageToUser(String message){
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,message,message));
	}
	
	private FacesContext getContext(){
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}
	
}