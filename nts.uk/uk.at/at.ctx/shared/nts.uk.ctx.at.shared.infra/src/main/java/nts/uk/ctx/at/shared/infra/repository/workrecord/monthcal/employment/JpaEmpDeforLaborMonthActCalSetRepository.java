/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpDeforMCalSetPK;

/**
 * The Class JpaEmpDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpDeforLaborMonthActCalSetRepository extends JpaRepository
		implements EmpDeforLaborMonthActCalSetRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KrcstEmpDeforMCalSet c"
			+ " WHERE c.krcstEmpDeforMCalSetPK.cid = :cid";
	
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
		KrcstEmpDeforMCalSet entity = new KrcstEmpDeforMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstEmpDeforMCalSetPK(
				new KrcstEmpDeforMCalSetPK(domain.getComId(), domain.getEmploymentCode().v()));

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
		KrcstEmpDeforMCalSetPK pk = new KrcstEmpDeforMCalSetPK(domain.getComId(),
				domain.getEmploymentCode().toString());
		
		this.queryProxy().find(pk, KrcstEmpDeforMCalSet.class).ifPresent(e -> {
			
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
		KrcstEmpDeforMCalSetPK pk = new KrcstEmpDeforMCalSetPK(cid, empCode);
		
		return this.queryProxy().find(pk, KrcstEmpDeforMCalSet.class).map(c -> toDomain(c));

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
		
		this.queryProxy().find(new KrcstEmpDeforMCalSetPK(cid, empCode),
				KrcstEmpDeforMCalSet.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private EmpDeforLaborMonthActCalSet toDomain (KrcstEmpDeforMCalSet e) {
		
		return EmpDeforLaborMonthActCalSet.of(new EmploymentCode(e.getKrcstEmpDeforMCalSetPK().getEmpCd()),
				e.getKrcstEmpDeforMCalSetPK().getCid(), 
				e.getAggregateTimeSet(), e.getExcessOutsideTimeSet(),
				e.deforLaborCalSetting(),
				e.deforLaborSettlementPeriod());
	}

	@Override
	public List<EmpDeforLaborMonthActCalSet> findEmpDeforLabor(String cid) {
		List<KrcstEmpDeforMCalSet> entitys = this.queryProxy().query(SELECT_BY_CID, KrcstEmpDeforMCalSet.class)
				.setParameter("cid", cid).getList();
		
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

}
