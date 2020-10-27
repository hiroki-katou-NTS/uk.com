/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcmtCalcMSetRegEmp;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcmtCalcMSetRegEmpPK;

/**
 * The Class JpaEmpRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpRegulaMonthActCalSetRepository extends JpaRepository implements EmpRegulaMonthActCalSetRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.employment.EmpRegulaMonthActCalSet)
	 */
	@Override
	public void add(EmpRegulaMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetRegEmp entity = new KrcmtCalcMSetRegEmp();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcmtCalcMSetRegEmpPK(
				new KrcmtCalcMSetRegEmpPK(domain.getComId(), domain.getEmploymentCode().v()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpRegulaMonthActCalSet)
	 */
	@Override
	public void update(EmpRegulaMonthActCalSet domain) {
		// Get info
		KrcmtCalcMSetRegEmpPK pk = new KrcmtCalcMSetRegEmpPK(domain.getComId(),
				domain.getEmploymentCode().v());
		
		this.queryProxy().find(pk, KrcmtCalcMSetRegEmp.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<EmpRegulaMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcmtCalcMSetRegEmpPK pk = new KrcmtCalcMSetRegEmpPK(cid, empCode);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetRegEmp.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		Optional<KrcmtCalcMSetRegEmp> optEntity = this.queryProxy().find(new KrcmtCalcMSetRegEmpPK(cid, empCode),
				KrcmtCalcMSetRegEmp.class);
		KrcmtCalcMSetRegEmp entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

	private EmpRegulaMonthActCalSet toDomain (KrcmtCalcMSetRegEmp e) {
		
		return EmpRegulaMonthActCalSet.of(new EmploymentCode(e.getKrcmtCalcMSetRegEmpPK().getEmpCd()), 
				e.getKrcmtCalcMSetRegEmpPK().getCid(), 
				e.getAggregateTimeSet(), 
				e.getExcessOutsideTimeSet());
	}
}
