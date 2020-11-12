package nts.uk.ctx.at.shared.infra.repository.era.name;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomGetMemento;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomRepository;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomSetMemento;
import nts.uk.ctx.at.shared.infra.entity.era.name.CisdtEraName;

/**
 * The Class JpaEraNameRepository.
 */
@Stateless
public class JpaEraNameRepository extends JpaRepository implements EraNameDomRepository{
	
	private static final String GET_BY_STR_DATE = "select c from CisdtEraName c where c.startDate = :startDate";
	private static final String GET_BY_END_DATE = "select c from CisdtEraName c where c.endDate = :endDate";
	
	@Override
	public List<EraNameDom> getAllEraName(){
		return this.forDefaultDataSources(em ->{
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<CisdtEraName> cq = criteriaBuilder.createQuery(CisdtEraName.class);
			Root<CisdtEraName> root = cq.from(CisdtEraName.class);

			// Build query
			cq.select(root);

			// query data
			List<CisdtEraName> cisdtEraNames = em.createQuery(cq).getResultList();		
			// return
			if (cisdtEraNames != null) {
				List<EraNameDom> eraNameDoms = cisdtEraNames.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
				return eraNameDoms.stream().sorted(Comparator.comparing(EraNameDom :: getStartDate)).collect(Collectors.toList());
			}
			return new ArrayList<EraNameDom>();
		});
	};
	
	@Override
	public EraNameDom getEraNameById(String eraNameId) {
		
		Optional<CisdtEraName> entity = this.queryProxy().find(eraNameId, CisdtEraName.class);
		
		if(!entity.isPresent()) {
			return null;
		}
		
		EraNameDomGetMemento memento = new JpaEraNameGetMemento(entity.get());
		
		return new EraNameDom(memento);
	}
	
	@Override
	public void deleteEraName(String eraNameId){
		
		Optional<CisdtEraName> entity = this.queryProxy().find(eraNameId, CisdtEraName.class);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Invalid CisdtEraName");
		}
		
		this.commandProxy().remove(entity.get());
		
	};
	
	@Override
	public void updateEraName(EraNameDom domain){
		
		Optional<CisdtEraName> entity = this.queryProxy().find(domain.getEraNameId(), CisdtEraName.class);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Invalid CisdtEraName");
		}
		// update details
		entity.get().setEraName(domain.getEraName().toString());
		entity.get().setSymbol(domain.getSymbol().toString());
		entity.get().setStartDate(domain.getStartDate());
		entity.get().setEndDate(domain.getEndDate());
		this.commandProxy().update(entity.get());
		
	};
	
	@Override
	public void addNewEraName(EraNameDom domain) {
		this.commandProxy().insert(this.toEntity(domain));
	};
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the cisdt era name
	 */
	private CisdtEraName toEntity(EraNameDom domain) {
		CisdtEraName entity = new CisdtEraName();
		if(domain.getEraNameId() == null || domain.getEraNameId().isEmpty()) {
			String uuid = UUID.randomUUID().toString();
			domain.setEraNameId(uuid);
		}
		EraNameDomSetMemento memento = new JpaEraNameSetMemento(entity);
		domain.saveToMemento(memento);
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the era name dom
	 */
	private EraNameDom toDomain(CisdtEraName entity) {
		EraNameDomGetMemento memento = new JpaEraNameGetMemento(entity);
		return new EraNameDom(memento);
	}

	@Override
	public EraNameDom getEraNameByStartDate(GeneralDate strDate) {
		Optional<CisdtEraName> entity = this.queryProxy()
				.query(GET_BY_STR_DATE, CisdtEraName.class)
				.setParameter("startDate", strDate).getSingle();
		if (entity.isPresent()) {
			return this.toDomain(entity.get());
		}
		return null;
	}

	@Override
	public EraNameDom getEraNameByEndDate(GeneralDate endDate) {
		Optional<CisdtEraName> entity = this.queryProxy()
				.query(GET_BY_END_DATE, CisdtEraName.class)
				.setParameter("endDate", endDate).getSingle();
		if (entity.isPresent()) {
			return this.toDomain(entity.get());
		}
		return null;
	}
}
