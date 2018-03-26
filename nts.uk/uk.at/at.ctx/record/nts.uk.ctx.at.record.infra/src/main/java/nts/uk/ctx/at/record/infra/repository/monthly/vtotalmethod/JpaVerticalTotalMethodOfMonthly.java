package nts.uk.ctx.at.record.infra.repository.monthly.vtotalmethod;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.SpecCountNotCalcSubject;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.SpecDayMonthCountCon;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.SpecTotalCountMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.TADaysCountCondOfMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.TADaysCountOfMonthlyAggr;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.WorkTypeClassification;
import nts.uk.ctx.at.record.infra.entity.monthly.vtotalmethod.KrcstVertMonMethod;

/**
 * The Class JpaVerticalTotalMethodOfMonthly.
 */
public class JpaVerticalTotalMethodOfMonthly extends JpaRepository implements VerticalTotalMethodOfMonthlyRepository {

	/** The Constant FIND_BY_CID. */
	private static final String FIND_BY_CID =
			"SELECT a FROM KrcstVertMonMethod a "
			+ "WHERE a.companyId = :companyId ";
	
	private static final String REMOVE_BY_CID =
			"DELETE FROM KrcstVertMonMethod a "
			+ "WHERE a.companyId = :companyId ";
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#findByCid(java.lang.String)
	 */
	@Override
	public VerticalTotalMethodOfMonthly findByCid(String companyId) {
		
		val vertDaysList = this.queryProxy()
				.query(FIND_BY_CID, KrcstVertMonMethod.class)
				.setParameter("companyId", companyId)
				.getList();
		
		return toDomain(vertDaysList);
	}
	
	
	/**
	 * To domain.
	 *
	 * @param lstVertical the lst vertical
	 * @return the vertical total method of monthly
	 */
	public VerticalTotalMethodOfMonthly toDomain(List<KrcstVertMonMethod> lstVertical) {
		KrcstVertMonMethod entity = lstVertical.get(0);
		VerticalTotalMethodOfMonthly setting = new VerticalTotalMethodOfMonthly(entity.getCid());
		setting.setTransferAttendanceDays(TADaysCountOfMonthlyAggr.of(
				EnumAdaptor.valueOf(entity.getTransAttendDay(), TADaysCountCondOfMonthlyAggr.class)));
		SpecTotalCountMonthly specTotalCountMonthly = new SpecTotalCountMonthly();
		specTotalCountMonthly.setSpecCount(EnumAdaptor.valueOf(entity.getSpecDayNotCal(), SpecCountNotCalcSubject.class));
		for (val verticalSetting: lstVertical) {
			SpecDayMonthCountCon condition = new SpecDayMonthCountCon(
					EnumAdaptor.valueOf(verticalSetting.getDutyType(), WorkTypeClassification.class), verticalSetting.isUseCountSpec());
			specTotalCountMonthly.getSpecDayOfTotalMonCon().add(condition);
		}
		return setting;
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst vert mon method
	 */
	public List<KrcstVertMonMethod> toDbType(VerticalTotalMethodOfMonthly setting) {
		List<KrcstVertMonMethod> lstEntity = new ArrayList<>();
		List<SpecDayMonthCountCon> lstCondition = setting.getSpecTotalCountMonthly().getSpecDayOfTotalMonCon();
		for (val condition: lstCondition) {
			KrcstVertMonMethod entity = new KrcstVertMonMethod();
			entity.setCid(setting.getCompanyId());
			entity.setDutyType(condition.getWorkType().value);
			entity.setSpecDayNotCal(setting.getSpecTotalCountMonthly().getSpecCount().value);
			entity.setTransAttendDay(setting.getTransferAttendanceDays().getTADaysCountCondition().value);
			entity.setUseCountSpec(condition.isUseCountSpecDay());
			lstEntity.add(entity);
		}
		return lstEntity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#insert(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void insert(VerticalTotalMethodOfMonthly setting) {
		List<KrcstVertMonMethod> lstEntity = toDbType(setting);
		this.commandProxy().insertAll(lstEntity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyRepository#update(nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly)
	 */
	@Override
	public void update(VerticalTotalMethodOfMonthly setting) {
		// Remove all old records
		this.getEntityManager().createQuery(REMOVE_BY_CID)
			.setParameter("companyId", setting.getCompanyId())
			.executeUpdate();
		
		// Then add all new records
		this.insert(setting);
	}

}
