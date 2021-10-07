package nts.uk.cnv.infra.impls;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nts.arc.layer.infra.data.EntityManagerLoader;

public class EntityManagerLoaderFromSourceOruta implements EntityManagerLoader{

    @PersistenceContext(unitName = "SOURCE_ORUTA")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

	@Override
	public void forAllDataSources(Consumer<EntityManager> process) {
		process.accept(this.entityManager);
	}

	@Override
	public void forDefaultDatasource(Consumer<EntityManager> process) {
		process.accept(this.entityManager);
	}

	@Override
	public void forTenantDatasource(String tenantCode, Consumer<EntityManager> process) {
		process.accept(this.entityManager);
	}
}
