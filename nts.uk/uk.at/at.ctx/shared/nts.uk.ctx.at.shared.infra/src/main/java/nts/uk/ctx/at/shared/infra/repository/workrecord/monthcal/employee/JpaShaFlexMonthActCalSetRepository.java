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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.sha.ShaFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcmtCalcMSetFleSya;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcstShaFlexMCalSetPK;

/**
 * The Class JpaShaFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaShaFlexMonthActCalSetRepository extends JpaRepository implements ShaFlexMonthActCalSetRepo {
	
	private static final String SELECT_BY_CID = "SELECT c FROM KrcmtCalcMSetFleSya c"
			+ " WHERE c.krcstShaFlexMCalSetPK.cid = :cid";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.employee.ShaFlexMonthActCalSet)
	 */
	@Override
	public void add(ShaFlexMonthActCalSet domain) {
		// Create new entity
		KrcmtCalcMSetFleSya entity = new KrcmtCalcMSetFleSya();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstShaFlexMCalSetPK(
				new KrcstShaFlexMCalSetPK(domain.getComId(), domain.getEmpId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaFlexMonthActCalSet)
	 */
	@Override
	public void update(ShaFlexMonthActCalSet domain) {
		// Get info
		KrcstShaFlexMCalSetPK pk = new KrcstShaFlexMCalSetPK(domain.getComId().toString(),
				domain.getEmpId());
		
		this.queryProxy().find(pk, KrcmtCalcMSetFleSya.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShaFlexMonthActCalSet> find(String cid, String sid) {
		// Get info
		KrcstShaFlexMCalSetPK pk = new KrcstShaFlexMCalSetPK(cid, sid);
		
		return this.queryProxy().find(pk, KrcmtCalcMSetFleSya.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String sid) {
		this.queryProxy().find(new KrcstShaFlexMCalSetPK(cid, sid),
				KrcmtCalcMSetFleSya.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private ShaFlexMonthActCalSet toDomain (KrcmtCalcMSetFleSya e) {
		
		return ShaFlexMonthActCalSet.of(e.getKrcstShaFlexMCalSetPK().getCid(),
										e.flexAggregateMethod(),
										e.shortageFlexSetting(), 
										e.aggregateTimeSetting(), 
										e.flexTimeHandle(), 
										e.getKrcstShaFlexMCalSetPK().getSid());
	}

	@Override
	public List<ShaFlexMonthActCalSet> findAllShaByCid(String cid) {
		List<KrcmtCalcMSetFleSya> entitys = this.queryProxy().query(SELECT_BY_CID, KrcmtCalcMSetFleSya.class)
				.setParameter("cid", cid).getList();
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

}
