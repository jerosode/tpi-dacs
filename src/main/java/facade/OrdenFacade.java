package facade;

import java.util.List;

import javax.ejb.Local;

import model.Orden;

@Local
public interface OrdenFacade {
	public abstract void save (Orden orden);
	public abstract List<Orden> findAll();
}