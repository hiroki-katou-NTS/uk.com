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
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other.sha.ShaDeforLaborMonthActCalSetRepo;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSet;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee.KrcstShaDeforMCalSetPK;

/**
 * The Class JpaShaDeforLaborMonthActCalSetRepository.
 */
@Stateless
public class JpaShaDeforLaborMonthActCalSetRepository extends JpaRepository
		implements ShaDeforLaborMonthActCalSetRepo {

	
	private static final String SELECT_BY_CID = "SELECT c FROM KrcstShaDeforMCalSet c"
			+ " WHERE c.krcstShaDeforMCalSetPK.cid = :cid";
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#add(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet)
	 */
	@Override
	public void add(ShaDeforLaborMonthActCalSet domain) {
		// Create new entity
		KrcstShaDeforMCalSet entity = new KrcstShaDeforMCalSet();

		// Transfer data
		entity.transfer(domain);
		entity.setKrcstShaDeforMCalSetPK(
				new KrcstShaDeforMCalSetPK(domain.getComId(), domain.getEmployeeId()));

		// Insert into DB
		this.commandProxy().insert(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#update(nts.uk.ctx.at.record.dom.
	 * workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet)
	 */
	@Override
	public void update(ShaDeforLaborMonthActCalSet domain) {
		// Get info
		KrcstShaDeforMCalSetPK pk = new KrcstShaDeforMCalSetPK(domain.getComId(),
				domain.getEmployeeId());
		
		this.queryProxy().find(pk, KrcstShaDeforMCalSet.class).ifPresent(e -> {
			
			e.transfer(domain);
			
			this.commandProxy().update(e);
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#find(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<ShaDeforLaborMonthActCalSet> find(String cid, String empId) {
		// Get info
		KrcstShaDeforMCalSetPK pk = new KrcstShaDeforMCalSetPK(cid, empId);
		
		return this.queryProxy().find(pk, KrcstShaDeforMCalSet.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.
	 * ShaDeforLaborMonthActCalSetRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String cId, String sId) {
		
		this.queryProxy().find(new KrcstShaDeforMCalSetPK(cId, sId),
				KrcstShaDeforMCalSet.class)
			.ifPresent(entity -> this.commandProxy().remove(entity));

	}

	private ShaDeforLaborMonthActCalSet toDomain (KrcstShaDeforMCalSet e) {
		
		return ShaDeforLaborMonthActCalSet.of(e.getKrcstShaDeforMCalSetPK().getSid(),
				e.getKrcstShaDeforMCalSetPK().getCid(), 
				e.getAggregateTimeSet(), e.getExcessOutsideTimeSet(),
				e.deforLaborCalSetting(),
				e.deforLaborSettlementPeriod());
	}

	@Override
	public List<ShaDeforLaborMonthActCalSet> findByCid(String cid) {
		List<KrcstShaDeforMCalSet> entitys = this.queryProxy().query(SELECT_BY_CID, KrcstShaDeforMCalSet.class)
				.setParameter("cid", cid).getList();
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}

}
