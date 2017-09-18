/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist_;
@Stateless
public class JpaWorkplaceConfigRepository extends JpaRepository implements WorkplaceConfigRepository {

	@Override
	public List<WorkplaceConfig> findAllByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkplaceConfig> findLastestByCompanyId(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWorkplaceHist> cq = criteriaBuilder.createQuery(BsymtWorkplaceHist.class);
		Root<BsymtWorkplaceHist> root = cq.from(BsymtWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(BsymtWorkplaceHist_.bsymtWorkplaceHistPK).get(BsymtWorkplaceHistPK_.cid), companyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// exclude select
		return null;
//		return em.createQuery(cq).getResultList().stream().map(item -> this.histToDomain(item))
//				.collect(Collectors.toList());
	}

}
