/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KmfmtCompensLeaveEmp;

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
	public CompensatoryLeaveEmSetting find(String companyId) {
		EntityManager em = this.getEntityManager();
		
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtCompensLeaveEmp> query = builder.createQuery(KmfmtCompensLeaveEmp.class);
        Root<KmfmtCompensLeaveEmp> root = query.from(KmfmtCompensLeaveEmp.class);

//        List<Predicate> predicateList = new ArrayList<Predicate>();
//
//        predicateList.add(builder.equal(root.get(KmfmtCompensLeaveEmp_.kmfmtCompensLeaveEmpPK.), companyId));
//
//        List<KmfmtCompensLeaveEmp> result = em.createQuery(query).getResultList();
//        if (result.isEmpty()) {
//            return null;
//        }
//        KmfmtCompensLeaveEmp entity = result.get(0);
//        KmfmtNormalVacationSet entityNormal = findNormal(companyId);
//        List<KmfmtOccurVacationSet> lstEntityOccur = findOccurVacation(companyId);
//        
//        return new CompensatoryLeaveComSetting(
//                new JpaCompensatoryLeaveComGetMemento(entity, entityNormal, lstEntityOccur));
        return null;
	}

}
