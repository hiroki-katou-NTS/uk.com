package nts.uk.ctx.at.record.infra.repository.workrule.specific;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.record.dom.workrule.specific.TimeOffVacationPriorityOrder;
import nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstConstraintTimeCal;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstConstraintTimeCalPK;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstHolidayPriorOrder;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstHolidayPriorOrderPK;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstWkHourLimitCtrl;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstWkHourLimitCtrlPK;

/**
 * The Class JpaSpecificWorkRuleRepository.
 */
@Stateless
public class JpaSpecificWorkRuleRepository extends JpaRepository implements SpecificWorkRuleRepository {

	/** The Constant SEL_TOTAL_CONSTRAINT. */
	private static final String SEL_TOTAL_CONSTRAINT = 
			"SELECT a "
			+ "FROM KrcstConstraintTimeCal a "
			+ "WHERE a.id.cid = :companyId ";
	
	/** The Constant SEL_TIME_OFF_ORDER. */
	private static final String SEL_TIME_OFF_ORDER = 
			"SELECT a "
			+ "FROM KrcstHolidayPriorOrder a "
			+ "WHERE a.id.cid = :companyId ";
	
	/** The Constant SEL_WORK_HOUR_LIMIT. */
	private static final String SEL_WORK_HOUR_LIMIT = 
			"SELECT a "
			+ "FROM KrcstWkHourLimitCtrl a "
			+ "WHERE a.id.cid = :companyId ";
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the calculate of total constraint time
	 */
	public CalculateOfTotalConstraintTime toDomain(KrcstConstraintTimeCal entity) {
		return CalculateOfTotalConstraintTime.createFromJavaType(entity.getId().getCid(), entity.getCalMethod());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the time off vacation priority order
	 */
	public TimeOffVacationPriorityOrder toDomain(KrcstHolidayPriorOrder entity) {
		return TimeOffVacationPriorityOrder.createFromJavaType(entity.getId().getCid(), entity.getSubstitute(),
				entity.getSixtyHour(), entity.getSpecial(), entity.getAnnual());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the upper limit total working hour
	 */
	public UpperLimitTotalWorkingHour toDomain(KrcstWkHourLimitCtrl entity) {
		return UpperLimitTotalWorkingHour.createFromJavaType(entity.getId().getCid(), entity.getWorkLimitCtrl());
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst constraint time cal
	 */
	public KrcstConstraintTimeCal toDbType(CalculateOfTotalConstraintTime setting) {
		KrcstConstraintTimeCal entity = new KrcstConstraintTimeCal();
		KrcstConstraintTimeCalPK primaryKey = new KrcstConstraintTimeCalPK();
		primaryKey.setCid(setting.getCompanyId().v());
		entity.setCalMethod(setting.getCalcMethod().value);
		entity.setId(primaryKey);
		return entity;
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst holiday prior order
	 */
	public KrcstHolidayPriorOrder toDbType(TimeOffVacationPriorityOrder setting) {
		KrcstHolidayPriorOrder entity = new KrcstHolidayPriorOrder();
		KrcstHolidayPriorOrderPK primaryKey = new KrcstHolidayPriorOrderPK();
		primaryKey.setCid(setting.getCompanyId().v());
		entity.setSubstitute(setting.getSubstituteHoliday());
		entity.setSixtyHour(setting.getSixtyHourVacation());
		entity.setSpecial(setting.getSpecialHoliday());
		entity.setAnnual(setting.getAnnualHoliday());
		entity.setId(primaryKey);
		return entity;
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst wk hour limit ctrl
	 */
	public KrcstWkHourLimitCtrl toDbType(UpperLimitTotalWorkingHour setting) {
		KrcstWkHourLimitCtrl entity = new KrcstWkHourLimitCtrl();
		KrcstWkHourLimitCtrlPK primaryKey = new KrcstWkHourLimitCtrlPK();
		primaryKey.setCid(setting.getCompanyId().v());
		entity.setWorkLimitCtrl(setting.getLimitSet().value);
		entity.setId(primaryKey);
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#findCalcMethodByCid(java.lang.String)
	 */
	@Override
	public Optional<CalculateOfTotalConstraintTime> findCalcMethodByCid(String companyId) {
		Optional<KrcstConstraintTimeCal> opt = this.queryProxy().query(SEL_TOTAL_CONSTRAINT, KrcstConstraintTimeCal.class).
				setParameter("companyId", companyId).getSingle();
		if (opt.isPresent()) {
			return Optional.of(toDomain(opt.get()));
		}
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#findTimeOffVacationOrderByCid(java.lang.String)
	 */
	@Override
	public Optional<TimeOffVacationPriorityOrder> findTimeOffVacationOrderByCid(String companyId) {
		Optional<KrcstHolidayPriorOrder> opt = this.queryProxy().query(SEL_TIME_OFF_ORDER, KrcstHolidayPriorOrder.class).
				setParameter("companyId", companyId).getSingle();
		if (opt.isPresent()) {
			return Optional.of(toDomain(opt.get()));
		}
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#findUpperLimitWkHourByCid(java.lang.String)
	 */
	@Override
	public Optional<UpperLimitTotalWorkingHour> findUpperLimitWkHourByCid(String companyId) {
		try (PreparedStatement statement = this.connection().prepareStatement("select * FROM KRCST_WK_HOUR_LIMIT_CTRL where CID = ?")) {
			statement.setString(1, companyId);
			Optional<KrcstWkHourLimitCtrl> opt = new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				val entity = new KrcstWkHourLimitCtrl();
				KrcstWkHourLimitCtrlPK id = new KrcstWkHourLimitCtrlPK();
				id.setCid(companyId);
				entity.setWorkLimitCtrl(rec.getInt("WORK_LIMIT_CTRL"));
				entity.setId(id);
				return entity;
			});
//			Optional<KrcstWkHourLimitCtrl> opt = this.queryProxy().query(SEL_WORK_HOUR_LIMIT, KrcstWkHourLimitCtrl.class).
//					setParameter("companyId", companyId).getSingle();
			if (opt.isPresent()) {
				return Optional.of(toDomain(opt.get()));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return Optional.empty();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#insertCalcMethod(nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime)
	 */
	@Override
	public void insertCalcMethod(CalculateOfTotalConstraintTime setting) {
		KrcstConstraintTimeCal entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#insertTimeOffVacationOrder(nts.uk.ctx.at.record.dom.workrule.specific.TimeOffVacationPriorityOrder)
	 */
	@Override
	public void insertTimeOffVacationOrder(TimeOffVacationPriorityOrder setting) {
		KrcstHolidayPriorOrder entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#insertUpperLimitWkHour(nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour)
	 */
	@Override
	public void insertUpperLimitWkHour(UpperLimitTotalWorkingHour setting) {
		KrcstWkHourLimitCtrl entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateCalcMethod(nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime)
	 */
	@Override
	public void updateCalcMethod(CalculateOfTotalConstraintTime setting) {
		KrcstConstraintTimeCal entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateTimeOffVacationOrder(nts.uk.ctx.at.record.dom.workrule.specific.TimeOffVacationPriorityOrder)
	 */
	@Override
	public void updateTimeOffVacationOrder(TimeOffVacationPriorityOrder setting) {
		KrcstHolidayPriorOrder entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateUpperLimitWkHour(nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour)
	 */
	@Override
	public void updateUpperLimitWkHour(UpperLimitTotalWorkingHour setting) {
		KrcstWkHourLimitCtrl entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

}
