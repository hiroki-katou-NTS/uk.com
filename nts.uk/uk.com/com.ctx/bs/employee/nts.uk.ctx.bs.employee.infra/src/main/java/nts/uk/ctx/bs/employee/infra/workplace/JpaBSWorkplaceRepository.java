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
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist_;

@Stateless
public class JpaBSWorkplaceRepository extends JpaRepository implements WorkplaceRepository {

	@Override
	public List<Workplace> findByStartDate(String companyId, GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(Workplace wkp) {
		// TODO Auto-generated method stub
		// return historyId
		return null;
	}

	@Override
	public void updateLatestHistory(Workplace wkp) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLatestHistory(String companyId, String workplaceId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Workplace> findByWkpIds(List<String> workplaceIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Workplace> findLatestByWorkplaceId(String workplaceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Workplace> findByWorkplaceId(String companyId, String workplaceId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWorkplaceHist> cq = criteriaBuilder.createQuery(BsymtWorkplaceHist.class);
		Root<BsymtWorkplaceHist> root = cq.from(BsymtWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWorkplaceHist_.bsymtWorkplaceHistPK).get(BsymtWorkplaceHistPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWorkplaceHist_.bsymtWorkplaceHistPK).get(BsymtWorkplaceHistPK_.wkpid), workplaceId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		List<BsymtWorkplaceHist> lstBsymtWorkplaceHist = em.createQuery(cq).getResultList();
		return Optional.of(new Workplace(new JpaWorkplaceGetMemento(companyId,workplaceId,lstBsymtWorkplaceHist)));
	}
}
