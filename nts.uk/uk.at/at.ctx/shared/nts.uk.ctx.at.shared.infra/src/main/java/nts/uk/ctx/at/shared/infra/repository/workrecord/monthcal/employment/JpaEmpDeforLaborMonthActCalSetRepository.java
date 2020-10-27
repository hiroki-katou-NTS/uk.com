/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcmtCalcMSetDefEmp;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcmtCalcMSetDefEmpPK;

/**
 * The Class JpaEmpDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements EmpDeforLaborMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(EmpDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetDefEmp entity = new KrcmtCalcMSetDefEmp();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetDefEmpPK(
				new KrcmtCalcMSetDefEmpPK(domain.getComId(), domain.getEmploymentCode().v()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(EmpDeforLaborMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetDefEmpPK pk = new KrcmtCalcMSetDefEmpPK(domain.getComId(),
				domain.getEmploymentCode().toString());
		
		this.queryProxy().find(pk, KrcmtCalcMSetDefEmp.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<EmpDeforLaborMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcmtCalcMSetDefEmpPK pk = new KrcmtCalcMSetDefEmpPK(cid, empCode);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetDefEmp.class).map(c -> toDomain(c));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		
		this.queryProxy().find(new KrcmtCalcMSetDefEmpPK(cid, empCode),
				KrcmtCalcMSetDefEmp.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private EmpDeforLaborMonthActCalSet toDomain (KrcmtCalcMSetDefEmp e) {
		
		return EmpDeforLaborMonthActCalSet.of(new EmploymentCode(e.getKrcmtCalcMSetDefEmpPK().getEmpCd()),
				e.getKrcmtCalcMSetDefEmpPK().getCid(), 
				e.getAggregateTimeSet(), e.getExcessOutsideTimeSet(),
				e.deforLaborCalSetting(),
				e.deforLaborSettlementPeriod());
	}

}
