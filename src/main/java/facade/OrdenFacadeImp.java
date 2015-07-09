package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Orden;
import dao.OrdenDao;

@Stateless
public class OrdenFacadeImp implements OrdenFacade {
	
	@EJB
	private OrdenDao ordenDao;

	public OrdenFacadeImp() {
	}

	@Override
	public void save(Orden orden) {
		ordenDao.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		return ordenDao.findAll();
	}

}