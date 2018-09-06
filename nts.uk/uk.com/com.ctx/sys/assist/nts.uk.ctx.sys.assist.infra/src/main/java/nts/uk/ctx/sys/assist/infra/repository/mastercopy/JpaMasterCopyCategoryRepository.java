package nts.uk.ctx.sys.assist.infra.repository.mastercopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryGetMemento;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategorySetMemento;
import nts.uk.ctx.sys.assist.infra.entity.mastercopy.SspmtMastercopyCategory;

/**
 * The Class JpaMasterCopyCategoryRepository.
 */
@Stateless
public class JpaMasterCopyCategoryRepository extends JpaRepository implements MasterCopyCategoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository#
	 * findAllMasterCopyCategory(java.lang.String)
	 */
	@Override
	public List<MasterCopyCategory> findAllMasterCopyCategory() {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<SspmtMastercopyCategory> cq = criteriaBuilder.createQuery(SspmtMastercopyCategory.class);
		Root<SspmtMastercopyCategory> root = cq.from(SspmtMastercopyCategory.class);

		// Build query
		cq.select(root);

		// query data
		List<SspmtMastercopyCategory> sspmtMastercopyCategories = em.createQuery(cq).getResultList();

		// return
		if (sspmtMastercopyCategories != null)
			return sspmtMastercopyCategories.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
		return new ArrayList<MasterCopyCategory>();

	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategoryRepository#findByMasterCopyId(java.lang.String)
	 */
	@Override
	public Optional<MasterCopyCategory> findByMasterCopyId(String masterCopyId){
		return this.queryProxy().find(masterCopyId, SspmtMastercopyCategory.class).map(item -> this.toDomain(item));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the master copy category
	 */
	private MasterCopyCategory toDomain(SspmtMastercopyCategory entity) {
		MasterCopyCategoryGetMemento memento = new JpaMasterCopyCategoryGetMemento(entity);
		return new MasterCopyCategory(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the sspmt mastercopy category
	 */
	private SspmtMastercopyCategory toEntity(MasterCopyCategory domain) {
		SspmtMastercopyCategory entity = this.queryProxy().find(domain.getCategoryNo(), SspmtMastercopyCategory.class)
				.orElse(new SspmtMastercopyCategory());

		MasterCopyCategorySetMemento memento = new JpaMasterCopyCategorySetMemento(entity);
		domain.saveToMemento(memento);

		return entity;
	}

}
