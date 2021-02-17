/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaRegulaMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetRegSya;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcstShaRegMCalSetPK;

/**
 * The Class JpaShaRegulaMonthActCalSetRepository.
 */
@Stateless
public class JpaShaRegulaMonthActCalSetRepository extends JpaRepository implements ShaRegulaMonthActCalSetRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KrcmtCalcMSetRegSya c"
			+ " WHERE c.krcstShaRegMCalSetPK.cid = :cid";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord
	 * .monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void add(ShaRegulaMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetRegSya entity = new KrcmtCalcMSetRegSya();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstShaRegMCalSetPK(
				new KrcstShaRegMCalSetPK(domain.getComId(), domain.getEmployeeId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaRegulaMonthActCalSet)
	 */
	@Override
	public void update(ShaRegulaMonthActCalSet domain) {
		// Get info
		KrcstShaRegMCalSetPK pk = new KrcstShaRegMCalSetPK(domain.getComId(),
				domain.getEmployeeId());
		
		this.queryProxy().find(pk, KrcmtCalcMSetRegSya.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<ShaRegulaMonthActCalSet> find(String cid, String sid) {
		// Get info
		KrcstShaRegMCalSetPK pk = new KrcstShaRegMCalSetPK(cid, sid);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetRegSya.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaRegulaMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String sId) {
		Optional<KrcmtCalcMSetRegSya> optEntity = this.queryProxy().find(new KrcstShaRegMCalSetPK(cid, sId),
				KrcmtCalcMSetRegSya.class);
		KrcmtCalcMSetRegSya entity = optEntity.get();
		this.commandProxy().remove(entity);
	}

	private ShaRegulaMonthActCalSet toDomain (KrcmtCalcMSetRegSya e) {
		
		return ShaRegulaMonthActCalSet.of(e.getKrcstShaRegMCalSetPK().getSid(), 
				e.getKrcstShaRegMCalSetPK().getCid(), 
				e.getAggregateTimeSet(), 
				e.getExcessOutsideTimeSet());
	}

	@Override
	public List<ShaRegulaMonthActCalSet> findRegulaMonthActCalSetByCid(String cid) {
		List<KrcmtCalcMSetRegSya> entitys = this.queryProxy().query(SELECT_BY_CID, KrcmtCalcMSetRegSya.class)
				.setParameter("cid", cid).getList();
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

}
