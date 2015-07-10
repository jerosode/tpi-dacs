package mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;

import model.Cliente;
import model.Direccion;
import model.Estado;
import model.Orden;
import facade.ClienteFacade;
import facade.DireccionFacade;
import facade.EstadoFacade;
import facade.OrdenFacade;
import facade.ServicioFacade;
import facade.SucursalFacade;

@ManagedBean
@RequestScoped
public class ClienteMB implements Serializable {

	private static final String LIST_ALL_ClienteS = "listAllClientes";
	private static final String STAY_IN_THE_SAME_PAGE = null;
	private static final long serialVersionUID = 1L;

	@EJB
	private ClienteFacade ClienteFacade;

	@EJB
	private DireccionFacade direccionFacade;

	@EJB
	private SucursalFacade sucursalFacade;

	@EJB
	private ServicioFacade servicioFacade;
	
	@EJB
	private EstadoFacade estadoFacade;
	
	@EJB
	private OrdenFacade ordenFacade;
	@Resource
	UserTransaction tx;

	private Cliente mCliente;

	private List<Cliente> mClientes;

	private Direccion mDireccion;

	public String listarClientes() {
		return "/pages/protected/employee/listCliente.jsp?faces-redirect=true";
	}
	
	public String altaCliente() {
		return "/pages/protected/employee/altaCliente.jsp?faces-redirect=true";
	}
	
	public String createClienteEnd() {
		try {
			tx.begin();
			direccionFacade.save(mDireccion);
			mCliente.setDireccion(mDireccion);
			mCliente.setDNI();
			mCliente.setPass();
			mCliente.setRole();
			ClienteFacade.save(mCliente);
			
			
			Estado e1 = new Estado();
			e1.setFecha(new Date());
			e1.setHora(new Date());
			e1.setLatitud(30.f);
			e1.setLongitud(40.f);
			e1.setSucursal(sucursalFacade.findSucursalById(1L));
			
			Estado e2 = new Estado();
			e2.setFecha(new Date());
			e2.setHora(new Date());
			e2.setLatitud(3.f);
			e2.setLongitud(4.f);
			e2.setSucursal(sucursalFacade.findSucursalById(2L));
			
			estadoFacade.save(e1);
			estadoFacade.save(e2);
			
			
			
			Orden orden = new Orden();
			orden.setFecha(new Date());
			orden.setMonto(300.f);
			orden.setPagado("No");
			orden.setServicio(servicioFacade.findAll().get(0));
			
			List<Estado> estados = new ArrayList<>();
			estados.add(e1);
			estados.add(e2);
			
			
			
			orden.setEstado(estados);
			ordenFacade.save(orden);
			
			
			tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (Exception e1) {
				sendErrorMessageToUser("Error del servidor.");
				return STAY_IN_THE_SAME_PAGE;
			}
			sendErrorMessageToUser("Error datos invalidos.");
			return STAY_IN_THE_SAME_PAGE;
		}
		sendInfoMessageToUser("Operacion completada.");
		return LIST_ALL_ClienteS;
	}

	public List<Cliente> findAll() {
		return ClienteFacade.findAll();
	}

	@PostConstruct
	public void init() {
		mClientes = findAll();
	}

	// Views errors

	private void sendInfoMessageToUser(String message) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				message, message));
	}

	private void sendErrorMessageToUser(String message) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				message, message));
	}

	private FacesContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}

	// Setters and getters

	public Cliente getCliente() {
		if (mCliente == null) {
			mCliente = new Cliente();
		}
		return mCliente;
	}

	public void setCliente(Cliente Cliente) {
		mCliente = Cliente;
	}

	public Direccion getDireccion() {
		if (mDireccion == null) {
			mDireccion = new Direccion();
		}
		return mDireccion;
	}

	public void setDireccion(Direccion direccion) {
		mDireccion = direccion;
	}

	public List<Cliente> getClientes() {
		return mClientes;
	}
}
