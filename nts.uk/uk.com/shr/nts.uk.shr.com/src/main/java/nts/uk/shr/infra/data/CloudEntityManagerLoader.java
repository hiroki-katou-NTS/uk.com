package nts.uk.shr.infra.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.val;
import nts.arc.layer.infra.data.EntityManagerLoader;
import nts.arc.scoped.session.SessionContextProvider;

@ApplicationScoped
public class CloudEntityManagerLoader implements EntityManagerLoader{
	
	/**
	 * DefaultEntityManager of cloud
	 */
    @PersistenceContext(unitName = "UK")
    private EntityManager entityManager;

    @PersistenceContext(unitName = "UK1")
    private EntityManager entityManager1;
    
    private Map<String, EntityManager> entityManagersMap;
    
    @PostConstruct
    public void init() {
//    	//refrection使って、このクラスの@PersistenceContextアノが付いたフィールド集める。
//    	val annoFields = ReflectionUtil.getStreamOfFieldsAnnotated(
//    														CloudEntityManagerLoader.class,
//    														ReflectionUtil.Condition.ALL,
//    														PersistenceContext.class);
//    	//集めたやつらのUnitNameを穿り出す。
//    	annoFields.forEach(field ->{
//    		val value = ReflectionUtil.getFieldValue(field, this);
//			entityManagersMap.put("a", (EntityManager)value);
//    	});
//    	//穿り出したやつらの unitName を Key, EntityManagerをValueへ入れる.

    	
    	//借
    	val map = new HashMap<String, EntityManager>();
    	map.put("UK", entityManager);
    	map.put("UK1", entityManager1);
    	this.entityManagersMap = map;
    }
    
    @Override
    public EntityManager getEntityManager() {
    	String datasourceName = SessionContextProvider.get().get("datasource");
//    	EntityManager em = entityManagersMap.get(datasourceName);
//    	if (em == null) {
//    		throw new RuntimeException("データソースに一致するマネージャが見つかりませんでした。");
//    	}
//    	return em;
    	switch(datasourceName) {
    	case "UK" : return entityManager;
    	case "UK1" : return entityManager1;
    	default : 
    		throw new RuntimeException("データソースに一致するマネージャが見つかりませんでした。");
    	}
    }

	@Override
	public String get() {
		return SessionContextProvider.get().get("datasource");
	}
	
	@Override
	public void forAllDataSources(Consumer<EntityManager> process) {
		entityManagersMap.values().forEach(em ->{
			process.accept(em);
		});
	}

	@Override
	public void forDefaultDatasource(Consumer<EntityManager> process) {
		process.accept(entityManager);
	}
}

