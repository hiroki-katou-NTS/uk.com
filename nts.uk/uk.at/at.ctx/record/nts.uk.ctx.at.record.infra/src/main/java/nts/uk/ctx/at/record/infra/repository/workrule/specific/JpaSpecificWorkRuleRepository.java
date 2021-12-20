package nts.uk.ctx.at.record.infra.repository.workrule.specific;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcmtCalcDRestTime;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcmtCalcDTotaltime;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcmtCalcMHdOffset;
import nts.uk.ctx.at.record.infra.entity.workrule.specific.KrcstWkHourLimitCtrlPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;

/**
 * The Class JpaSpecificWorkRuleRepository.
 */
@Stateless
public class JpaSpecificWorkRuleRepository extends JpaRepository implements SpecificWorkRuleRepository {

	/** The Constant SEL_TOTAL_CONSTRAINT. */
	private static final String SEL_TOTAL_CONSTRAINT = 
			"SELECT a "
			+ "FROM KrcmtCalcDRestTime a "
			+ "WHERE a.cid = :companyId ";
	
	/** The Constant SEL_TIME_OFF_ORDER. */
	private static final String SEL_TIME_OFF_ORDER = 
			"SELECT a "
			+ "FROM KrcmtCalcMHdOffset a "
			+ "WHERE a.cid = :companyId ";
	
	/** The Constant SEL_WORK_HOUR_LIMIT. */
//	private static final String SEL_WORK_HOUR_LIMIT = 
//			"SELECT a "
//			+ "FROM KrcmtCalcDTotaltime a "
//			+ "WHERE a.id.cid = :companyId ";
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the calculate of total constraint time
	 */
	public CalculateOfTotalConstraintTime toDomain(KrcmtCalcDRestTime entity) {
		return CalculateOfTotalConstraintTime.createFromJavaType(entity.getCid(), entity.getCalMethod());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the upper limit total working hour
	 */
	public UpperLimitTotalWorkingHour toDomain(KrcmtCalcDTotaltime entity) {
		return UpperLimitTotalWorkingHour.createFromJavaType(entity.getId().getCid(), entity.getWorkLimitCtrl());
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst constraint time cal
	 */
	public KrcmtCalcDRestTime toDbType(CalculateOfTotalConstraintTime setting) {
		KrcmtCalcDRestTime entity = new KrcmtCalcDRestTime();
		entity.setCalMethod(setting.getCalcMethod().value);
		entity.setCid(setting.getCompanyId().v());
		return entity;
	}
	
	/**
	 * To db type.
	 *
	 * @param setting the setting
	 * @return the krcst wk hour limit ctrl
	 */
	public KrcmtCalcDTotaltime toDbType(UpperLimitTotalWorkingHour setting) {
		KrcmtCalcDTotaltime entity = new KrcmtCalcDTotaltime();
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
		Optional<KrcmtCalcDRestTime> opt = this.queryProxy().query(SEL_TOTAL_CONSTRAINT, KrcmtCalcDRestTime.class).
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
	public Optional<CompanyHolidayPriorityOrder> findTimeOffVacationOrderByCid(String companyId) {
		return this.queryProxy()
				.query(SEL_TIME_OFF_ORDER, KrcmtCalcMHdOffset.class)
				.setParameter("companyId", companyId)
				.getSingle().map(c -> c.domain());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#findUpperLimitWkHourByCid(java.lang.String)
	 */
	@Override
	public Optional<UpperLimitTotalWorkingHour> findUpperLimitWkHourByCid(String companyId) {
		try (PreparedStatement statement = this.connection().prepareStatement("select * FROM KRCMT_CALC_D_TOTALTIME where CID = ?")) {
			statement.setString(1, companyId);
			Optional<KrcmtCalcDTotaltime> opt = new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				val entity = new KrcmtCalcDTotaltime();
				KrcstWkHourLimitCtrlPK id = new KrcstWkHourLimitCtrlPK();
				id.setCid(companyId);
				entity.setWorkLimitCtrl(rec.getInt("WORK_LIMIT_CTRL"));
				entity.setId(id);
				return entity;
			});
//			Optional<KrcmtCalcDTotaltime> opt = this.queryProxy().query(SEL_WORK_HOUR_LIMIT, KrcmtCalcDTotaltime.class).
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
		KrcmtCalcDRestTime entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#insertTimeOffVacationOrder(nts.uk.ctx.at.record.dom.workrule.specific.TimeOffVacationPriorityOrder)
	 */
	@Override
	public void insertTimeOffVacationOrder(CompanyHolidayPriorityOrder setting) {
		this.commandProxy().insert(KrcmtCalcMHdOffset.from(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#insertUpperLimitWkHour(nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour)
	 */
	@Override
	public void insertUpperLimitWkHour(UpperLimitTotalWorkingHour setting) {
		KrcmtCalcDTotaltime entity = toDbType(setting);
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateCalcMethod(nts.uk.ctx.at.record.dom.workrule.specific.CalculateOfTotalConstraintTime)
	 */
	@Override
	public void updateCalcMethod(CalculateOfTotalConstraintTime setting) {
		KrcmtCalcDRestTime entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateTimeOffVacationOrder(nts.uk.ctx.at.record.dom.workrule.specific.TimeOffVacationPriorityOrder)
	 */
	@Override
	public void updateTimeOffVacationOrder(CompanyHolidayPriorityOrder setting) {
		this.commandProxy().update(KrcmtCalcMHdOffset.from(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrule.specific.SpecificWorkRuleRepository#updateUpperLimitWkHour(nts.uk.ctx.at.record.dom.workrule.specific.UpperLimitTotalWorkingHour)
	 */
	@Override
	public void updateUpperLimitWkHour(UpperLimitTotalWorkingHour setting) {
		KrcmtCalcDTotaltime entity = toDbType(setting);
		this.commandProxy().update(entity);
	}

}
