/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

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
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp_;

@Stateless
public class JpaCompensLeaveEmSetRepository extends JpaRepository implements CompensLeaveEmSetRepository {

	@Override
	public void insert(CompensatoryLeaveEmSetting setting) {
		this.commandProxy().insert(this.toEntity(setting));
		this.getEntityManager().flush();
	}

	private KmfmtCompensLeaveEmp toEntity(CompensatoryLeaveEmSetting setting) {
		KmfmtCompensLeaveEmp entity = new KmfmtCompensLeaveEmp();
		setting.saveToMemento(new JpaCompensatoryLeaveEmSettingSetMemento(entity));
		return entity;
	}

	@Override
	public void update(CompensatoryLeaveEmSetting setting) {
		this.commandProxy().update(this.toEntity(setting));
		this.getEntityManager().flush();
	}

	@Override
	public CompensatoryLeaveEmSetting find(String companyId, String employmentCode) {
		
		Optional<KmfmtCompensLeaveEmp> findItem = this.queryProxy().find(new KmfmtCompensLeaveEmpPK(companyId, employmentCode), KmfmtCompensLeaveEmp.class);
		/*EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KmfmtCompensLeaveEmp> query = builder.createQuery(KmfmtCompensLeaveEmp.class);
		Root<KmfmtCompensLeaveEmp> root = query.from(KmfmtCompensLeaveEmp.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();

		predicateList.add(builder.equal(root.get(KmfmtCompensLeaveEmp_.kmfmtCompensLeaveEmpPK),
				));
		em.createQuery(query).getResultList();

		KmfmtCompensLeaveEmp result = em.createQuery(query).getSingleResult();
		if (result == null) {
			return null;
		} else {
			return new CompensatoryLeaveEmSetting(new JpaCompensatoryLeaveEmSettingGetMemento(result));
		}
		*/
		if (findItem.isPresent()) {
			return new CompensatoryLeaveEmSetting(new JpaCompensatoryLeaveEmSettingGetMemento(findItem.get()));
		} else {
			return null;
		}
	}

}
