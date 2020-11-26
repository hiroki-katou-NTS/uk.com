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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.emp.EmpRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment.KrcstEmpRegMCalSetPK;

/**
 * The Class JpaEmpRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaEmpRegulaMonthActCalSetRepository extends JpaRepository implements EmpRegulaMonthActCalSetRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KwkdtStampCard c"
			+ " WHERE c.krcstEmpRegMCalSetPK.cid = :cid";
	
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
		KrcstEmpRegMCalSet entity = new KrcstEmpRegMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstEmpRegMCalSetPK(
				new KrcstEmpRegMCalSetPK(domain.getComId(), domain.getEmploymentCode().v()));

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
		KrcstEmpRegMCalSetPK pk = new KrcstEmpRegMCalSetPK(domain.getComId(),
				domain.getEmploymentCode().v());
		
		this.queryProxy().find(pk, KrcstEmpRegMCalSet.class).ifPresent(e -> {
			
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
		KrcstEmpRegMCalSetPK pk = new KrcstEmpRegMCalSetPK(cid, empCode);
		
		return this.queryProxy().find(pk, KrcstEmpRegMCalSet.class).map(c -> toDomain(c));
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
		Optional<KrcstEmpRegMCalSet> optEntity = this.queryProxy().find(new KrcstEmpRegMCalSetPK(cid, empCode),
				KrcstEmpRegMCalSet.class);
		KrcstEmpRegMCalSet entity = optEntity.get();
		this.commandProxy().remove(entity);

	}

	private EmpRegulaMonthActCalSet toDomain (KrcstEmpRegMCalSet e) {
		
		return EmpRegulaMonthActCalSet.of(new EmploymentCode(e.getKrcstEmpRegMCalSetPK().getEmpCd()), 
				e.getKrcstEmpRegMCalSetPK().getCid(), 
				e.getAggregateTimeSet(), 
				e.getExcessOutsideTimeSet());
	}

	@Override
	public List<EmpRegulaMonthActCalSet> findByCid(String cid) {
		
		List<KrcstEmpRegMCalSet> entities = this.queryProxy()
				.query(SELECT_BY_CID , KrcstEmpRegMCalSet.class).setParameter("cid", cid).getList();
		
		return entities.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}
}
