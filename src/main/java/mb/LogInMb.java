package mb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Cliente;
import facade.ClienteFacade;

@ManagedBean
@ViewScoped
public class LogInMb implements Serializable{

	private static final String LOG_SUCCESS = "logSuccess";
	private static final String STAY_IN_THE_SAME_PAGE = null;
	private static final long serialVersionUID = 1L;
	
	private Long mCUIL;
	
	private String mPassword;

	@EJB
	private ClienteFacade clienteFacade;
	
	private Cliente mCliente;

	public String logIn(){
		mCliente = clienteFacade.findClienteByCUIL(mCUIL);
		if (mCliente != null && mCliente.getPass().equals(mPassword)){
			return LOG_SUCCESS;
		}
		return STAY_IN_THE_SAME_PAGE;
	}

	// Setters and getters
	
	public Cliente getCliente() {
		if (mCliente == null) {
			mCliente = new Cliente();
		}
		return mCliente;
	}

	public void setCliente(Cliente cliente) {
		mCliente = cliente;
	}

	public Long getCUIL() {
		return mCUIL;
	}

	public void setCUIL(Long cUIL) {
		mCUIL = cUIL;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}
	
}