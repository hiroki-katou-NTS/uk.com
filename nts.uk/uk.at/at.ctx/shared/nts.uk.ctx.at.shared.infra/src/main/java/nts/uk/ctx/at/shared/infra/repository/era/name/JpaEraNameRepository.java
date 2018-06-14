package nts.uk.ctx.at.shared.infra.repository.era.name;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
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
	
	@Override
	public List<EraNameDom> getAllEraName(){
		EntityManager em = this.getEntityManager();

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
	};
	
	@Override
	public void deleteEraName(String eraNameId){
		
		Optional<CisdtEraName> entity = this.queryProxy().find(eraNameId, CisdtEraName.class);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Invalid CisdtEraName");
		}
		
		this.commandProxy().remove(entity);
		
	};
	
	@Override
	public void updateEraName(EraNameDom domain){
		
		Optional<CisdtEraName> entity = this.queryProxy().find(domain.getEraNameId(), CisdtEraName.class);
		
		if(!entity.isPresent()) {
			throw new RuntimeException("Invalid CisdtEraName");
		}
		this.commandProxy().update(entity);
		
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
		CisdtEraName entity = this.queryProxy().find(domain.getEraNameId(), CisdtEraName.class)
				.orElse(new CisdtEraName());
		
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
}
