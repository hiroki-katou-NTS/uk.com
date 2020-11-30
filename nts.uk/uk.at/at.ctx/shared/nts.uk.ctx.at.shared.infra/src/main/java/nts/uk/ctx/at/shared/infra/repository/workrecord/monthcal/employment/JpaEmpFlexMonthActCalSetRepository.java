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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.emp.EmpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpFlexMCalSetPK;

/**
 * The Class JpaEmpFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpFlexMonthActCalSetRepository extends JpaRepository implements EmpFlexMonthActCalSetRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KrcstEmpFlexMCalSet c"
			+ " WHERE c.krcstEmpFlexMCalSetPK.cid = :cid";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.employment.EmpFlexMonthActCalSet)
	 */
	@Override
	public void add(EmpFlexMonthActCalSet domain) {
		// Create new entity
		KrcstEmpFlexMCalSet entity = new KrcstEmpFlexMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstEmpFlexMCalSetPK(
				new KrcstEmpFlexMCalSetPK(domain.getComId(), domain.getEmploymentCode().v()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employment.EmpFlexMonthActCalSet)
	 */
	@Override
	public void update(EmpFlexMonthActCalSet domain) {
		// Get info
		KrcstEmpFlexMCalSetPK pk = new KrcstEmpFlexMCalSetPK(domain.getComId().toString(),
				domain.getEmploymentCode().toString());
		
		this.queryProxy().find(pk, KrcstEmpFlexMCalSet.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmpFlexMonthActCalSet> find(String cid, String empCode) {
		// Get info
		KrcstEmpFlexMCalSetPK pk = new KrcstEmpFlexMCalSetPK(cid, empCode);
		
		return this.queryProxy().find(pk, KrcstEmpFlexMCalSet.class).map(c -> toDomain(c));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employment.
	 * EmpFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String empCode) {
		this.queryProxy().find(new KrcstEmpFlexMCalSetPK(cid, empCode),
				KrcstEmpFlexMCalSet.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private EmpFlexMonthActCalSet toDomain (KrcstEmpFlexMCalSet e) {
		
		return EmpFlexMonthActCalSet.of(e.getKrcstEmpFlexMCalSetPK().getCid(),
										e.flexAggregateMethod(),
										e.shortageFlexSetting(), 
										e.aggregateTimeSetting(), 
										e.flexTimeHandle(), 
										new EmploymentCode(e.getKrcstEmpFlexMCalSetPK().getEmpCd()));
	}

	@Override
	public List<EmpFlexMonthActCalSet> findEmpFlexMonthByCid(String cid) {
		List<KrcstEmpFlexMCalSet> entitys = this.queryProxy().query(SELECT_BY_CID, KrcstEmpFlexMCalSet.class)
				.setParameter("cid", cid).getList();
		
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

}
