package nts.uk.ctx.at.record.infra.repository.monthly.vtotalmethod;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
//import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.WorkTypeClassification;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcmtCalcMAgg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.AggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.DefoAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.FlexAggregateMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecCountNotCalcSubject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.SpecTotalCountMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;

/**
 * The Class JpaVerticalTotalMethodOfMonthly.
 */
@Stateless
public class JpaVerticalTotalMethodOfMonthly extends JpaRepository implements VerticalTotalMethodOfMonthlyRepository {

	/** The Constant FIND_BY_CID. */
	private static final String FIND_BY_CID =
			"SELECT a FROM KrcmtCalcMAgg a "
			+ "WHERE a.cid = :companyId ";
	
	private static final String REMOVE_BY_CID =
			"DELETE FROM KrcmtCalcMAgg a "
			+ "WHERE a.cid = :companyId ";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#findByCid(java.lang.String)
	 */
	@Override
	public Optional<AggregateMethodOfMonthly> findByCid(String companyId) {
		
		val vertDaysList = this.queryProxy()
				.query(FIND_BY_CID, KrcmtCalcMAgg.class)
				.setParameter("companyId", companyId)
				.getSingle();
		
		return vertDaysList.map(c -> toDomain(c));
	}
	
	
	/**
	 * To domain.
	 *
	 * @param lstVertical the lst vertical
	 * @return the vertical total method of monthly
	 */
	public AggregateMethodOfMonthly toDomain(KrcmtCalcMAgg entity) {
		
		return AggregateMethodOfMonthly.of(entity.cid, 
				TADaysCountOfMonthlyAggr.of(
						EnumAdaptor.valueOf(entity.transAttendDay, TADaysCountCondOfMonthlyAggr.class)),
				SpecTotalCountMonthly.of(
						entity.countContiousWorkDay, 
						entity.countNoWorkDay, 
						EnumAdaptor.valueOf(entity.calcTargetOutCountCondition, SpecCountNotCalcSubject.class)), 
				entity.weekPremiumCalcWithPrevMonthLastWeek,
				DefoAggregateMethodOfMonthly.of(entity.methodEnterInMonthDeforLabor),
				new FlexAggregateMethodOfMonthly(entity.methodEnterInMonthFlex));
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst vert mon method
	 */
	public KrcmtCalcMAgg toDbType(AggregateMethodOfMonthly setting) {
		KrcmtCalcMAgg entity = new KrcmtCalcMAgg();
		entity.cid = setting.getCompanyId();
		entity.transAttendDay = setting.getTransferAttendanceDays().getTADaysCountCondition().value;
		entity.calcTargetOutCountCondition = setting.getSpecTotalCountMonthly().getSpecCount().value;
		entity.countContiousWorkDay = setting.getSpecTotalCountMonthly().isContinuousCount();
		entity.countNoWorkDay = setting.getSpecTotalCountMonthly().isNotWorkCount();
		entity.weekPremiumCalcWithPrevMonthLastWeek = setting.isCalcWithPreviousMonthLastWeek();
		entity.methodEnterInMonthDeforLabor = setting.getDefoAggregateMethod().getPremiumTimeCalcMethod().value;
		entity.methodEnterInMonthFlex = setting.getFlexAggregateMethod().isDivisionOnEntryRetire();
		return entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#insert(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void insert(AggregateMethodOfMonthly setting) {
		//List<KrcmtCalcMAgg> lstEntity = toDbType(setting);
		this.commandProxy().insert(toDbType(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#update(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void update(AggregateMethodOfMonthly setting) {
		// Remove all old records
		this.getEntityManager().createQuery(REMOVE_BY_CID)
			.setParameter("companyId", setting.getCompanyId())
			.executeUpdate();
		
		// Then add all new records
		this.insert(setting);
	}

}
