/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrecord.monthcal.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex.wkp.WkpFlexMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace.KrcstWkpFlexMCalSetPK;

/**
 * The Class JpaWkpFlexMonthActCalSetRepository.
 */
@Stateless
public class JpaWkpFlexMonthActCalSetRepository extends JpaRepository implements WkpFlexMonthActCalSetRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KrcstWkpFlexMCalSet c"
			+ " WHERE c.krcstWkpFlexMCalSetPK.cid = :cid";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void add(WkpFlexMonthActCalSet domain) {
		// Create new entity
		KrcstWkpFlexMCalSet entity = new KrcstWkpFlexMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstWkpFlexMCalSetPK(
				new KrcstWkpFlexMCalSetPK(domain.getComId(), domain.getWorkplaceId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.workplace.WkpFlexMonthActCalSet)
	 */
	@Override
	public void update(WkpFlexMonthActCalSet domain) {
		// Get info
		KrcstWkpFlexMCalSetPK pk = new KrcstWkpFlexMCalSetPK(domain.getComId().toString(),
				domain.getWorkplaceId().toString());
		
		this.queryProxy().find(pk, KrcstWkpFlexMCalSet.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpFlexMonthActCalSet> find(String cid, String wkpId) {
		// Get info
		KrcstWkpFlexMCalSetPK pk = new KrcstWkpFlexMCalSetPK(cid, wkpId);
		
		return this.queryProxy().find(pk, KrcstWkpFlexMCalSet.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.workplace.
	 * WkpFlexMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.queryProxy().find(new KrcstWkpFlexMCalSetPK(cid, wkpId),
				KrcstWkpFlexMCalSet.class)
		.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private WkpFlexMonthActCalSet toDomain (KrcstWkpFlexMCalSet e) {
		
		return WkpFlexMonthActCalSet.of(e.getKrcstWkpFlexMCalSetPK().getCid(),
										e.flexAggregateMethod(),
										e.shortageFlexSetting(), 
										e.aggregateTimeSetting(), 
										e.flexTimeHandle(), 
										e.getKrcstWkpFlexMCalSetPK().getWkpId());
	}

	@Override
	public List<WkpFlexMonthActCalSet> findByCid(String cid) {
		List<KrcstWkpFlexMCalSet> entitys = this.queryProxy().query(SELECT_BY_CID, KrcstWkpFlexMCalSet.class)
				.setParameter("cid", cid).getList();
		
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}
}
