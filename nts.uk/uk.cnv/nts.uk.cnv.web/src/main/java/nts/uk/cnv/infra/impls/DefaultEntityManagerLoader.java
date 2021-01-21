package nts.uk.cnv.infra.impls;

import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nts.arc.layer.infra.data.EntityManagerLoader;

public class DefaultEntityManagerLoader implements EntityManagerLoader{

    @PersistenceContext(unitName = "UK_CNV")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

	@Override
	public void forAllDataSources(Consumer<EntityManager> process) {
		// TODO 自動生成されたメソッド・スタブ
		throw new RuntimeException("未実装");
	}

	@Override
	public void forDefaultDatasource(Consumer<EntityManager> process) {
		// TODO 自動生成されたメソッド・スタブ
		throw new RuntimeException("未実装");
	}

	@Override
	public void forTenantDatasource(String tenantCode, Consumer<EntityManager> process) {
		// TODO 自動生成されたメソッド・スタブ
		throw new RuntimeException("未実装");
	}
}
