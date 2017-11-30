/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.worktimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtspWorkTimeSetPKNew_;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew_;
public class JpaWorkTimeSettingRepository extends JpaRepository implements WorkTimeSettingRepository {

	@Override
	public List<WorkTimeSetting> findByCompanyID(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkTimeSetting> findAll(String companyID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkTimeSetting> findByCodes(String companyID, List<String> codes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WorkTimeSetting> findByCode(String companyId, String siftCD) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KwtstWorkTimeSetNew> cq = criteriaBuilder.createQuery(KwtstWorkTimeSetNew.class);
		Root<KwtstWorkTimeSetNew> root = cq.from(KwtstWorkTimeSetNew.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KwtstWorkTimeSetNew_.kwtspWorkTimeSetPK).get(KwtspWorkTimeSetPKNew_.companyId), companyId));

		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KwtstWorkTimeSetNew_.kwtspWorkTimeSetPK).get(KwtspWorkTimeSetPKNew_.siftCD), siftCD));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		KwtstWorkTimeSetNew kwtstWorkTimeSet = em.createQuery(cq).getSingleResult();

		// check empty
		if (kwtstWorkTimeSet == null) {
			return null;
		}
		return Optional.of(new WorkTimeSetting(new JpaWorkTimeSettingGetMemento(kwtstWorkTimeSet)));
	}

	@Override
	public List<WorkTimeSetting> findByCodeList(String companyID, List<String> siftCDs) {
		// TODO Auto-generated method stub
		return null;
	}

}
