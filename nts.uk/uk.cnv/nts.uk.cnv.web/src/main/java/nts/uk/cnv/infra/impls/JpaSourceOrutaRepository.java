package nts.uk.cnv.infra.impls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;

import nts.arc.layer.infra.data.EntityManagerLoader;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.intercept.RepositoryInterceptor;
import nts.arc.layer.infra.data.query.QueryHint;
import nts.arc.system.ServerSystemProperties;
import nts.gul.util.value.MutableValue;

@Interceptors(RepositoryInterceptor.class)
public abstract class JpaSourceOrutaRepository extends JpaRepository {

	private Map<String, Object> defaultProperties;

	@Inject
    private DefaultEntityManagerLoader defaultLoader;
	@Inject
    private EntityManagerLoaderFromSourceOruta sourceOrutaLoader;

    /**
     * EntityManagerLoader
     */
	private EntityManagerLoader entityManagerLoader;

	private boolean fromSourceOruta = false;


	@Override
	@PostConstruct
	public void init() {
		this.fromSourceOruta = fromSourceOruta();
		if(fromSourceOruta) {
			entityManagerLoader = sourceOrutaLoader;
		}
		else {
			entityManagerLoader = defaultLoader;
		}

		entityManagerLoader.forDefaultDatasource(em ->{
			this.defaultProperties = new HashMap<>(em.getProperties());
		});
	}

	private static boolean fromSourceOruta() {
    	return "oruta".equalsIgnoreCase(
    			ServerSystemProperties.get("nts.uk.cnv.referencesource"));
	}

	@Override
	public <T> T forDefaultDataSource(Function<EntityManager, T> process) {
		MutableValue<T> value = new MutableValue<T>();
		entityManagerLoader.forDefaultDatasource(em ->{
			value.set(process.apply(em));
		});
		return value.get();
	}

	@Override
	public <T> List<T> forAllDataSources(Function<EntityManager, List<T>> process) {
		List<T> results = new ArrayList<>();
		entityManagerLoader.forAllDataSources(em -> {
			results.addAll(process.apply(em));
		});
		return results;
	}

	@Override
	public <T> T forTenantDatasource(String tenantCode, Function<EntityManager, T> process) {
		MutableValue<T> value = new MutableValue<T>();
		entityManagerLoader.forTenantDatasource(tenantCode, (em -> {
			value.set(process.apply(em));
		}));
		return value.get();
	}

	@Override
    public EntityManager getEntityManager() {
        return QueryHint.setDefaultHint(this.entityManagerLoader.getEntityManager(), defaultProperties);
    }

}
