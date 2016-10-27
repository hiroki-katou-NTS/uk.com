package nts.uk.shr.com.data;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nts.arc.layer.infra.data.EntityManagerLoader;

/**
 * DefaultEntityManagerLoader
 */
@RequestScoped
public class DefaultEntityManagerLoader implements EntityManagerLoader {

    @PersistenceContext(unitName = "HEALTHCARE")
    private EntityManager entityManager;
    
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
    
}
