package nts.sample.impls;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nts.arc.layer.infra.data.EntityManagerLoader;

public class DefaultEntityManagerLoader implements EntityManagerLoader{

    @PersistenceContext(unitName = "UK")
    private EntityManager entityManager;
    
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
