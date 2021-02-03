package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.TargetItem;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeStandard;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantTime;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedDays;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.PeriodGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.RegularGrantDays;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.AvailabilityPeriod;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationDeadline;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.TimeLimitSpecification;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHoliday;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHolidayPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdAbsence;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdAbsencePK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdSpecLeave;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdSpecLeavePK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecCls;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecClsPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecEmp;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecEmpPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecialLeaveRestriction;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition.KshstSpecialLeaveRestrictionPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantRegularPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspGrant;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshmtHdspGrantPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation.KshmtHdspGrantDeadline;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation.KshmtHdspGrantDeadlinePK;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * Jpa Special Holiday Repository
 *
 * @author tanlv
 *
 */
@Stateless
public class JpaSpecialHolidayRepository extends JpaRepository implements SpecialHolidayRepository {
	private final static String SELECT_SPHD_BY_COMPANY_ID_QUERY = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.autoGrant, e.memo FROM KshstSpecialHoliday e "
			+ "WHERE e.pk.companyId = :companyId "
			+ "ORDER BY e.pk.specialHolidayCode ASC";

	private final static String SELECT_SPHD_BY_CODE_QUERY = "SELECT sphd.CID, sphd.SPHD_CD, sphd.SPHD_NAME, sphd.SPHD_AUTO_GRANT, sphd.MEMO,"
//			+ " sphd.GRANT_TIMING, gra.GRANT_MD, gra.GRANTED_DAYS,"
			+ " sphd.CONTINUOUS_ACQUISITION, sphd.GRANT_TIMING, sphd.GRANT_DATE, gra.GRANTED_DAYS,"
			+ " pe.TIME_CSL_METHOD, gpe.PERIOD_START, gpe.PERIOD_END, pe.DEADLINE_MONTHS, pe.DEADLINE_YEARS, pe.LIMIT_CARRYOVER_DAYS,"
			+ " re.RESTRICTION_CLS, re.AGE_LIMIT, re.GENDER_REST, re.REST_EMP, re.AGE_CRITERIA_CLS, re.AGE_BASE_DATE, re.AGE_LOWER_LIMIT, re.AGE_HIGHER_LIMIT, re.GENDER"
			+ " FROM KSHST_SPECIAL_HOLIDAY sphd"
			+ " LEFT JOIN KSHST_GRANT_REGULAR gra"
			+ " ON sphd.CID = gra.CID AND sphd.SPHD_CD = gra.SPHD_CD"
			+ " LEFT JOIN KSHMT_HDSP_GRANT_DEADLINE pe"
			+ " ON sphd.CID = pe.CID AND sphd.SPHD_CD = pe.SPHD_CD"
			+ " LEFT JOIN KSHST_SPEC_LEAVE_REST re"
			+ " ON sphd.CID = re.CID AND sphd.SPHD_CD = re.SPHD_CD"
			+ " LEFT JOIN KSHMT_HDSP_GRANT_PERIOD gpe"
			+ " ON sphd.CID = gpe.CID AND sphd.SPHD_CD = gpe.SPHD_CD "
			+ " WHERE sphd.CID = ? AND sphd.SPHD_CD = ?"
			+ " ORDER BY sphd.SPHD_CD";

	private final static String SELECT_SPHD_BY_LIST_CODE = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.autoGrant, e.memo FROM KshstSpecialHoliday e "
			+ "WHERE e.pk.companyId = :companyId "
			+ " AND e.pk.specialHolidayCode IN :specialHolidayCodes"
			+ " ORDER BY e.pk.specialHolidayCode";

	private final static String SELECT_SPHD_ABSENCE_BY_CODE = "SELECT a FROM KshstSphdAbsence a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD ORDER BY a.pk.absFameNo";

	private final static String SELECT_SPHD_SPEC_LEAVE = "SELECT a FROM KshstSphdSpecLeave a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD ORDER BY a.pk.sphdNo";

	private final static String SELECT_SPEC_CLS = "SELECT a FROM KshstSpecCls a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String SELECT_SPEC_EMP = "SELECT a FROM KshstSpecEmp a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

//	private final static String FIND_GRANT_REGULAR = "SELECT e,f FROM KshstGrantRegular e "
//			+ " LEFT JOIN KshstGrantDateTbl f "
//			+ " ON e.pk.companyId = f.pk.companyId AND e.pk.specialHolidayCode = f.pk.specialHolidayCode";

//	private final static String FIND_GRANT_REGULAR_TEST = FIND_GRANT_REGULAR
//			+ " WHERE e.pk.companyId = 'hung-test'";

//	private final static String FIND_SPECICAL_LEAVE =  "SELECT g,h,i FROM KshstSpecialLeaveRestriction g"
//			+ " LEFT JOIN KshstSpecEmp h"
//			+ " ON g.pk.companyId = h.pk.companyId AND g.pk.specialHolidayCode = h.pk.specialHolidayCode"
//			+ "	LEFT JOIN KshstSpecCls i"
//			+ " ON i.pk.companyId = i.pk.companyId AND i.pk.specialHolidayCode = i.pk.specialHolidayCode";

//	private final static String FIND_BY_CID = "SELECT a, b, c, d, k, l "
//			+ " FROM KshstSpecialHoliday a"
//			+ " LEFT JOIN ("
//			+ FIND_GRANT_REGULAR
//			+ " ) b"
//			+ " ON a.pk.companyId = b.pk.companyId AND a.pk.specialHolidayCode = b.pk.specialHolidayCode"
//			+ " LEFT JOIN KshstGrantPeriodic c"
//			+ " ON a.pk.companyId = c.pk.companyId AND a.pk.specialHolidayCode = c.pk.specialHolidayCode"
//			+ " LEFT JOIN ("
//			+ FIND_SPECICAL_LEAVE
//			+ " ) d"
//			+ " ON a.pk.companyId = d.pk.companyId AND a.pk.specialHolidayCode = d.pk.specialHolidayCode"
//			+ " LEFT JOIN KshstSphdSpecLeave k"
//			+ " ON a.pk.companyId = k.pk.companyId AND a.pk.specialHolidayCode = k.pk.specialHolidayCode"
//			+ " LEFT JOIN KshstSphdAbsence l"
//			+ " ON a.pk.companyId = l.pk.companyId AND a.pk.specialHolidayCode = l.pk.specialHolidayCode"
//			+ " WHERE a.pk.companyId = :companyId ORDER BY e.pk.specialHolidayCode ASC";

	private final static String DELETE_SPECICAL_LEAVE_RESTRICTION = "DELETE FROM KshstSpecialLeaveRestriction a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_SPHD_ABSENCE = "DELETE FROM KshstSphdAbsence a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_SPHD_SPEC_LEAVE = "DELETE FROM KshstSphdSpecLeave a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_SPEC_CLS = "DELETE FROM KshstSpecCls a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_SPEC_EMP = "DELETE FROM KshstSpecEmp a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_GRANT_DATE = "DELETE FROM KshstGrantDateTbl a "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";

	private final static String DELETE_All_ELAPSE = "DELETE FROM KshstElapseYears e "
			+ "WHERE e.pk.companyId =:companyID "
			+ "AND e.pk.specialHolidayCode =:specialHolidayCD ";


	/**
	 * For delete releated domain of KDR001 (team G)
	 */
	private final static String DELETE_SPEC_HD = "DELETE FROM KfnmtSpecialHoliday a "
			+ "WHERE a.kfnmtSpecialHolidayPk.cid = :companyID "
			+ "AND a.kfnmtSpecialHolidayPk.specialCd = :specialHolidayCD";

	private String QUEYRY_BY_ABSFRAMENO = "SELECT c FROM KshstSphdAbsence c"
			+ " WHERE c.pk.companyId = :companyId"
			+ " AND c.pk.absFameNo = :absFameNo";

	private String QUERY_BY_SPECLEAVE = "SELECT c FROM KshstSphdSpecLeave c"
			+ " WHERE c.pk.companyId = :companyId"
			+ " AND c.pk.sphdNo = :sphdNo";

	private final static String SELECT_SPHD_BY_COMPANY_AND_NO = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.autoGrant, e.memo FROM KshstSpecialHoliday e "
			+ "WHERE e.pk.companyId = :companyId "
			+ "AND e.pk.specialHolidayCode IN :specialHolidayCode";

	private SpecialHoliday createDomainFromEntity(NtsResultRecord c) {

		String companyId = c.getString("CID");
		int specialHolidayCode = c.getInt("SPHD_CD");
		String specialHolidayName = c.getString("SPHD_NAME");
		int autoGrant = c.getInt("SPHD_AUTO_GRANT") != null ? c.getInt("SPHD_AUTO_GRANT") : 0;
		String memo = c.getString("MEMO");
		int typeTime = c.getInt("GRANT_TIMING")!= null ? c.getInt("GRANT_TIMING") : 0;
		int grantDate = c.getInt("GRANT_DATE") != null ? c.getInt("GRANT_DATE") : 0;
//		boolean allowDisappear = c.getInt("ALLOW_DISAPPEAR") == 1 ? true : false;
//		int interval = c.getInt("INTERVAL") != null ? c.getInt("INTERVAL") : 0;
		int grantedDays = c.getInt("GRANTED_DAYS") != null ? c.getInt("GRANTED_DAYS") : 0;
		int timeMethod = c.getInt("TIME_CSL_METHOD") != null ? c.getInt("TIME_CSL_METHOD") : 0;
		GeneralDate startDate = c.getGeneralDate("PERIOD_START");
		GeneralDate endDate = c.getGeneralDate("PERIOD_END");
		int deadlineMonths = c.getInt("DEADLINE_MONTHS") != null ? c.getInt("DEADLINE_MONTHS") : 0;
		int deadlineYears = c.getInt("DEADLINE_YEARS") != null ? c.getInt("DEADLINE_YEARS") : 0;
		int limitCarryoverDays = c.getInt("LIMIT_CARRYOVER_DAYS") != null ? c.getInt("LIMIT_CARRYOVER_DAYS") : 0;
		int restrictionCls = c.getInt("RESTRICTION_CLS") != null ? c.getInt("RESTRICTION_CLS") : 0;
		int ageLimit = c.getInt("AGE_LIMIT") != null ? c.getInt("AGE_LIMIT") : 0;
		int genderRest = c.getInt("GENDER_REST") != null ? c.getInt("GENDER_REST") : 0;
		int restEmp = c.getInt("REST_EMP") != null ? c.getInt("REST_EMP") : 0;
		int ageCriteriaCls = c.getInt("AGE_CRITERIA_CLS") != null ? c.getInt("AGE_CRITERIA_CLS") : 0;
		Integer ageBaseDateValue = c.getInt("AGE_BASE_DATE") != null ? c.getInt("AGE_BASE_DATE") : null;
		MonthDay ageBaseDate = null;
		if(ageBaseDateValue!=null){
			ageBaseDate = new MonthDay(ageBaseDateValue/100, ageBaseDateValue%100);
		}
		int ageLowerLimit = c.getInt("AGE_LOWER_LIMIT") != null ? c.getInt("AGE_LOWER_LIMIT") : 0;
		int ageHigherLimit = c.getInt("AGE_HIGHER_LIMIT") != null ? c.getInt("AGE_HIGHER_LIMIT") : 0;
		int gender = c.getInt("GENDER") != null ? c.getInt("GENDER") : 1;
		int continuousAcquisition = c.getInt("CONTINUOUS_ACQUISITION");

		FixGrantDate fixGrantDate = FixGrantDate.createFromJavaType(
				companyId,
				specialHolidayCode,
				grantedDays,
				timeMethod,
				limitCarryoverDays,
				GeneralDate.max(), // 要修正　expirationDate
				1, // 要修正　grantMonth
				1); // 要修正　grantDay
//		GrantTime grantTime = GrantTime.createFromJavaType(fixGrantDate, null);

		/** 期間 */
		DatePeriod period = new DatePeriod(startDate, endDate); // 要確認 初期値

		/** 期間付与 */
		PeriodGrantDate periodGrantDate =
			PeriodGrantDate.of(
				/** 期間 */
				period
				/** 付与日数 */
				,RegularGrantDays.of(
					/** 付与日数.付与日数 */
					 new GrantedDays(grantedDays)
				)
			);

		//AvailabilityPeriod availabilityPeriod = AvailabilityPeriod.createFromJavaType(startDate, endDate);
		SpecialVacationDeadline expirationDate = SpecialVacationDeadline.createFromJavaType(deadlineMonths, deadlineYears);
		GrantDeadline grantPeriodic = GrantDeadline.createFromJavaType(
				timeMethod, limitCarryoverDays,
				expirationDate.getMonths().v(), expirationDate.getYears().v());

		GrantRegular grantRegular = GrantRegular.of(
				EnumAdaptor.valueOf(typeTime, TypeTime.class),
				Optional.of(EnumAdaptor.valueOf(grantDate, GrantDate.class)),
				Optional.of(fixGrantDate),
				Optional.of(grantPeriodic),
				Optional.of(periodGrantDate));

		AgeStandard ageStandard = AgeStandard.createFromJavaType(ageCriteriaCls, ageBaseDate);
		AgeRange ageRange = AgeRange.createFromJavaType(ageLowerLimit, ageHigherLimit);
		SpecialLeaveRestriction specialLeaveRestriction = SpecialLeaveRestriction.createFromJavaType(companyId, specialHolidayCode, restrictionCls,
				ageLimit, genderRest, restEmp, ageStandard, ageRange, gender);

		return SpecialHoliday.of(
				companyId, specialHolidayCode, specialHolidayName, grantRegular,
				specialLeaveRestriction, new TargetItem(), autoGrant, memo,
				continuousAcquisition == 0 ? NotUseAtr.NOT_USE : NotUseAtr.USE);
	}

	private SpecialHoliday createSphdDomainFromEntity(Object[] c) {
		String companyId = String.valueOf(c[0]);
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[1]));
		String specialHolidayName = String.valueOf(c[2]);
		int autoGrant = Integer.parseInt(String.valueOf(c[3]));
		String memo = String.valueOf(c[4]);

		return SpecialHoliday.createFromJavaType(companyId, specialHolidayCode, specialHolidayName,autoGrant, memo);
	}

	private KshstSpecialHoliday createSpecialHolidayFromDomain(SpecialHoliday domain) {

		int grantDate = 0;
		if (domain.getGrantRegular().getGrantDate().isPresent()){
			/** 特別休暇.付与・期限情報.付与基準日 */
			grantDate = domain.getGrantRegular().getGrantDate().get().value;
		}

		KshstSpecialHolidayPK pk = new KshstSpecialHolidayPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		return new KshstSpecialHoliday(
				pk, domain.getSpecialHolidayName().v(),
				domain.getAutoGrant().value, /*自動付与区分*/
				0, /* 連続で取得する */
				grantDate, /* 付与基準日 */
				domain.getMemo().v(),
				domain.getGrantRegular().getTypeTime().value);
	}

	private List<KshstSphdAbsence> createKshstSphdAbsenceLst(SpecialHoliday domain){
		String companyID = domain.getCompanyId();
		int specialHolidayCD = domain.getSpecialHolidayCode().v();
		List<KshstSphdAbsence> kshstSphdAbsenceLst = domain.getTargetItem().getAbsenceFrameNo()
				.stream().map(x -> new KshstSphdAbsence(new KshstSphdAbsencePK(companyID, specialHolidayCD, x)))
				.collect(Collectors.toList());
		return kshstSphdAbsenceLst;
	}

	private List<KshstSphdSpecLeave> createKshstSphdSpecLeaveLst(SpecialHoliday domain){
		String companyID = domain.getCompanyId();
		int specialHolidayCD = domain.getSpecialHolidayCode().v();
		List<KshstSphdSpecLeave> kshstSphdSpecLeaveLst = domain.getTargetItem().getFrameNo()
				.stream().map(x -> new KshstSphdSpecLeave(new KshstSphdSpecLeavePK(companyID, specialHolidayCD, x)))
				.collect(Collectors.toList());
		return kshstSphdSpecLeaveLst;
	}

//	private KshstGrantRegular createKshstGrantRegular(SpecialHoliday domain) {
//
//		boolean isAutoGrant = domain.getAutoGrant().value == 0 ? false : true;
//		int typeTime = TypeTime.REFER_GRANT_DATE_TBL.value, grantDate = GrantDate.EMP_GRANT_DATE.value,
//				interval = 0, grantedDays = 0;
//		KshstGrantRegular entity = new KshstGrantRegular();
//		entity.pk = new KshstGrantRegularPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
//		// update document ver 32
//		if (isAutoGrant) {
//			typeTime = domain.getGrantRegular().getTypeTime().value;
//			grantDate = domain.getGrantRegular().getGrantDate().get().value;
////			interval = domain.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v();
//			if ( domain.getGrantRegular().getFixGrantDate().isPresent() ) {
//				grantedDays = domain.getGrantRegular().getFixGrantDate().get().getGrantDays().getGrantDays().v();
//			}
//		}
//		entity.typeTime = typeTime;
//		entity.grantDate = grantDate;
//		entity.interval = interval;
//		entity.grantedDays = grantedDays;
//
//		return entity;
//	}

	// KshmtHdspGrant
	private KshmtHdspGrant createKshmtHdspGrant(SpecialHoliday domain) {

		boolean isAutoGrant = domain.getAutoGrant().value == 0 ? false : true;
		int typeTime = TypeTime.REFER_GRANT_DATE_TBL.value, grantDate = GrantDate.EMP_GRANT_DATE.value,
				interval = 0, grantedDays = 0;
		KshmtHdspGrant entity = new KshmtHdspGrant();
		entity.pk = new KshmtHdspGrantPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		// update document ver 32
		if (isAutoGrant) {
			typeTime = domain.getGrantRegular().getTypeTime().value;
			grantDate = domain.getGrantRegular().getGrantDate().get().value;
//			interval = domain.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v();
			if (domain.getGrantRegular().getFixGrantDate().get().getGrantMonthDay().isPresent()){
				/** 特別休暇.付与・期限情報.指定日付与.付与月日. */
				int month = domain.getGrantRegular().getFixGrantDate().get().getGrantMonthDay().get().getMonth();

				/** 特別休暇.付与・期限情報.指定日付与.付与月日. */
				int day = domain.getGrantRegular().getFixGrantDate().get().getGrantMonthDay().get().getDay();
				entity.grantMd = month * 100 + day;
			}

			if ( domain.getGrantRegular().getFixGrantDate().isPresent() ) {
				grantedDays = domain.getGrantRegular().getFixGrantDate().get().getGrantDays().getGrantDays().v();
			}
		}

		entity.grantedDays = grantedDays;

		return entity;
	}

	private KshmtHdspGrantDeadline createKshmtHdspGrantDeadline(SpecialHoliday domain){

		KshmtHdspGrantDeadline entity = new KshmtHdspGrantDeadline();
		boolean isAutoGrant = domain.getAutoGrant().value == 0 ? false : true;
		int timeSpecifyMethod = TimeLimitSpecification.INDEFINITE_PERIOD.value, limitCarryoverDays = 999;
		entity.pk = new KshmtHdspGrantDeadlinePK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());

		if (isAutoGrant) {
			if ( domain.getGrantRegular().getGrantPeriodic().isPresent() ) {
				timeSpecifyMethod = domain.getGrantRegular().getGrantPeriodic().get().getTimeSpecifyMethod().value;
			}
			limitCarryoverDays = 0;

			if (domain.getGrantRegular().getFixGrantDate().isPresent()){
				if (domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().isPresent()){
					if (domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().get().getLimitCarryoverDays().isPresent()){
						/** 特別休暇.付与・期限情報.指定日付与.期限.蓄積上限.繰越上限日数 */
						limitCarryoverDays = domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getLimitAccumulationDays().get().getLimitCarryoverDays().get().v();
					}
				}
			}
		}

		entity.timeMethod = timeSpecifyMethod;
//		if (domain.getGrantRegular().getPeriodGrantDate().isPresent()){
//			/** 特別休暇.付与・期限情報.期間付与.期間 */
//			entity.startDate = domain.getGrantRegular().getPeriodGrantDate().get().getPeriod().start().month() * 100
//					+ domain.getGrantRegular().getPeriodGrantDate().get().getPeriod().start().day();
//			entity.endDate = domain.getGrantRegular().getPeriodGrantDate().get().getPeriod().end().month() * 100
//					+ domain.getGrantRegular().getPeriodGrantDate().get().getPeriod().end().day();
//		}

		if (domain.getGrantRegular().getFixGrantDate().isPresent()){
			/** 特別休暇.付与・期限情報.指定日付与.期限.期限指定方法 */
			domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getTimeSpecifyMethod();
			if (domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().isPresent()){
				/** 特別休暇.付与・期限情報.指定日付与.期限.有効期限.月数 */
				entity.deadlineMonths = domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getMonths().v();
				/** 特別休暇.付与・期限情報.指定日付与.期限.有効期限.年数 */
				entity.deadlineYears = domain.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getYears().v();
			}
		}
		entity.limitCarryoverDays = limitCarryoverDays;

		return entity;
	}

	private KshstSpecialLeaveRestriction createKshstSpecialLeaveRestriction(SpecialHoliday domain){
		MonthDay ageBaseDate = domain.getSpecialLeaveRestriction().getAgeStandard().getAgeBaseDate();
		return new KshstSpecialLeaveRestriction(
				new KshstSpecialLeaveRestrictionPK(
						domain.getCompanyId(),
						domain.getSpecialHolidayCode().v()),
				domain.getSpecialLeaveRestriction().getRestrictionCls().value,
				domain.getSpecialLeaveRestriction().getAgeLimit().value,
				domain.getSpecialLeaveRestriction().getGenderRest().value,
				domain.getSpecialLeaveRestriction().getRestEmp().value,
				domain.getSpecialLeaveRestriction().getAgeStandard().getAgeCriteriaCls().value,
				ageBaseDate.getMonth()*100+ageBaseDate.getDay(),
				domain.getSpecialLeaveRestriction().getAgeRange() != null
						? (domain.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit() != null ? domain.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit().v() : 0)
						: 0,
				domain.getSpecialLeaveRestriction().getAgeRange() != null
						? (domain.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit() != null ? domain.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit().v() : 0)
						: 0,
				domain.getSpecialLeaveRestriction().getGender().value);
	}

	private List<KshstSpecCls> createKshstSpecClsLst(SpecialHoliday domain){
		String companyID = domain.getCompanyId();
		int specialHolidayCD = domain.getSpecialHolidayCode().v();
		return domain.getSpecialLeaveRestriction().getListCls().stream()
			.map(x -> new KshstSpecCls(new KshstSpecClsPK(companyID, specialHolidayCD, x)))
			.collect(Collectors.toList());
	}

	private List<KshstSpecEmp> createKshstSpecEmpLst(SpecialHoliday domain){
		String companyID = domain.getCompanyId();
		int specialHolidayCD = domain.getSpecialHolidayCode().v();
		return domain.getSpecialLeaveRestriction().getListEmp().stream()
			.map(x -> new KshstSpecEmp(new KshstSpecEmpPK(companyID, specialHolidayCD, x)))
			.collect(Collectors.toList());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_SPHD_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.getList(c -> {
					return createSphdDomainFromEntity(c);
				});
	}

	@Override
	@SneakyThrows
	public Optional<SpecialHoliday> findByCode(String companyId, int specialHolidayCode) {

		try (PreparedStatement stmt = this.connection().prepareStatement(
				SELECT_SPHD_BY_CODE_QUERY)) {

			stmt.setString(1, companyId);
			stmt.setInt(2, specialHolidayCode);

			return new NtsResultSet(stmt.executeQuery()).getSingle(x->{
				return createDomainFromEntity(x);
			});
		}
	}

	@Override
	public void add(SpecialHoliday specialHoliday) {
		this.commandProxy().insert(createSpecialHolidayFromDomain(specialHoliday));
		this.commandProxy().insertAll(createKshstSphdAbsenceLst(specialHoliday));
		this.commandProxy().insertAll(createKshstSphdSpecLeaveLst(specialHoliday));
		this.commandProxy().insert(createKshmtHdspGrant(specialHoliday));
		this.commandProxy().insert(createKshmtHdspGrantDeadline(specialHoliday));
		this.commandProxy().insert(createKshstSpecialLeaveRestriction(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecClsLst(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecEmpLst(specialHoliday));
	}

	@Override
	public void update(SpecialHoliday specialHoliday) {

		// ooooo 要修正 jinno
		boolean isAutoGrant = specialHoliday.getAutoGrant().value == 0 ? false : true;
		int typeTime = TypeTime.REFER_GRANT_DATE_TBL.value, grantDate = GrantDate.EMP_GRANT_DATE.value,
				interval = 0, grantedDays = 0, timeSpecifyMethod = TimeLimitSpecification.INDEFINITE_PERIOD.value,
				limitCarryoverDays = 999;
		KshstSpecialHolidayPK pk = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHoliday old = this.queryProxy().find(pk, KshstSpecialHoliday.class).orElse(null);
		old.autoGrant = specialHoliday.getAutoGrant().value;
		old.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		old.memo = specialHoliday.getMemo().v();
		this.commandProxy().update(old);

		KshmtHdspGrantDeadlinePK grantPeriodicPK = new KshmtHdspGrantDeadlinePK(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		KshmtHdspGrantDeadline oldGrantPeriodic = this.queryProxy().find(grantPeriodicPK, KshmtHdspGrantDeadline.class).orElse(null);

		GrantDeadline grantPeriodic = specialHoliday.getGrantRegular().getGrantPeriodic().get();
		if (specialHoliday.getGrantRegular().getFixGrantDate().isPresent()){
			/** 特別休暇.付与・期限情報.指定日付与.期限.期限指定方法 */
			specialHoliday.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getTimeSpecifyMethod();
			if (specialHoliday.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().isPresent()){
				/** 特別休暇.付与・期限情報.指定日付与.期限.有効期限.月数 */
				oldGrantPeriodic.deadlineMonths = specialHoliday.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getMonths().v();
				/** 特別休暇.付与・期限情報.指定日付与.期限.有効期限.年数 */
				oldGrantPeriodic.deadlineYears = specialHoliday.getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getYears().v();
			}
		}

		// 要修正　date →　int ?
		if (specialHoliday.getGrantRegular().getPeriodGrantDate().isPresent()){
			/** 特別休暇.付与・期限情報.期間付与.期間 */
////			oldGrantPeriodic.startDate = grantPeriodic.getAvailabilityPeriod().getStartDateValue();
////			oldGrantPeriodic.endDate = grantPeriodic.getAvailabilityPeriod().getEndDateValue();
//			oldGrantPeriodic.startDate = specialHoliday.getGrantRegular().getPeriodGrantDate().get().getPeriod().start().toString("MMdd");
//			oldGrantPeriodic.endDate = specialHoliday.getGrantRegular().getPeriodGrantDate().get().getPeriod().end();
		}

		oldGrantPeriodic.timeMethod = timeSpecifyMethod;

		oldGrantPeriodic.limitCarryoverDays = limitCarryoverDays;
		this.commandProxy().update(oldGrantPeriodic);

		KshmtHdspGrantPK grantPK = new KshmtHdspGrantPK(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		KshmtHdspGrant oldGrant = this.queryProxy().find(grantPK, KshmtHdspGrant.class).orElse(null);
		GrantRegular grantRegular = specialHoliday.getGrantRegular();

		if (isAutoGrant) {
			typeTime = grantRegular.getTypeTime().value;
			if ( grantRegular.getGrantDate().isPresent() ) {
				grantDate = grantRegular.getGrantDate().get().value;
			}
//			interval = grantRegular.getGrantTime().getFixGrantDate().getInterval().v();
			if (specialHoliday.getGrantRegular().getFixGrantDate().isPresent()){
				/** 特別休暇.付与・期限情報.指定日付与.付与日数.付与日数 */
				grantedDays = specialHoliday.getGrantRegular().getFixGrantDate().get().getGrantDays().getGrantDays().v();
			}
		}

//		oldGrant.typeTime = typeTime;
//		oldGrant.grantDate = grantDate;
//		oldGrantRegular.allowDisappear = grantRegular.isAllowDisappear() ? 1 : 0;
//		oldGrant.interval = interval;
		oldGrant.grantedDays = grantedDays;
		this.commandProxy().update(oldGrant);

		KshstSpecialLeaveRestrictionPK specialLeaveRestrictionPK = new KshstSpecialLeaveRestrictionPK(
				specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialLeaveRestriction oldSpecialLeaveRestriction = this.queryProxy()
				.find(specialLeaveRestrictionPK, KshstSpecialLeaveRestriction.class).orElse(null);
		oldSpecialLeaveRestriction.restrictionCls = specialHoliday.getSpecialLeaveRestriction().getRestrictionCls().value;
		oldSpecialLeaveRestriction.ageLimit = specialHoliday.getSpecialLeaveRestriction().getAgeLimit().value;
		oldSpecialLeaveRestriction.genderRest = specialHoliday.getSpecialLeaveRestriction().getGenderRest().value;
		oldSpecialLeaveRestriction.restEmp = specialHoliday.getSpecialLeaveRestriction().getRestEmp().value;
		oldSpecialLeaveRestriction.ageCriteriaCls = specialHoliday.getSpecialLeaveRestriction().getAgeStandard().getAgeCriteriaCls().value;
		MonthDay ageBaseDate = specialHoliday.getSpecialLeaveRestriction().getAgeStandard().getAgeBaseDate();
		oldSpecialLeaveRestriction.ageBaseDate = ageBaseDate.getMonth()*100+ageBaseDate.getDay();
		oldSpecialLeaveRestriction.ageLowerLimit = specialHoliday.getSpecialLeaveRestriction().getAgeRange() != null
				? (specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit() != null
				? specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit().v() : 0)
			: null;
		oldSpecialLeaveRestriction.ageHigherLimit = specialHoliday.getSpecialLeaveRestriction().getAgeRange() != null
				? (specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit() != null
				? specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit().v() : 0)
			: null;
		oldSpecialLeaveRestriction.gender = specialHoliday.getSpecialLeaveRestriction().getGender().value;
		this.commandProxy().update(oldSpecialLeaveRestriction);

		this.getEntityManager().createQuery(DELETE_SPHD_ABSENCE)
			.setParameter("companyID", specialHoliday.getCompanyId())
			.setParameter("specialHolidayCD", specialHoliday.getSpecialHolidayCode().v())
			.executeUpdate();
		this.getEntityManager().flush();
		this.commandProxy().insertAll(createKshstSphdAbsenceLst(specialHoliday));

		this.getEntityManager().createQuery(DELETE_SPHD_SPEC_LEAVE)
			.setParameter("companyID", specialHoliday.getCompanyId())
			.setParameter("specialHolidayCD", specialHoliday.getSpecialHolidayCode().v())
			.executeUpdate();
		this.getEntityManager().flush();
		this.commandProxy().insertAll(createKshstSphdSpecLeaveLst(specialHoliday));

		this.getEntityManager().createQuery(DELETE_SPEC_CLS)
			.setParameter("companyID", specialHoliday.getCompanyId())
			.setParameter("specialHolidayCD", specialHoliday.getSpecialHolidayCode().v())
			.executeUpdate();
		this.getEntityManager().flush();
		this.commandProxy().insertAll(createKshstSpecClsLst(specialHoliday));

		this.getEntityManager().createQuery(DELETE_SPEC_EMP)
			.setParameter("companyID", specialHoliday.getCompanyId())
			.setParameter("specialHolidayCD", specialHoliday.getSpecialHolidayCode().v())
			.executeUpdate();
		this.getEntityManager().flush();
		this.commandProxy().insertAll(createKshstSpecEmpLst(specialHoliday));

	}

	@Override
	public void delete(String companyId, int specialHolidayCode) {
		KshstSpecialHolidayPK pk = new KshstSpecialHolidayPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstSpecialHoliday.class, pk);

		KshmtHdspGrantPK grantPK = new KshmtHdspGrantPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshmtHdspGrant.class, grantPK);

		KshmtHdspGrantDeadlinePK grantPeriodicPK = new KshmtHdspGrantDeadlinePK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshmtHdspGrantDeadline.class, grantPeriodicPK);

		this.getEntityManager().createQuery(DELETE_SPECICAL_LEAVE_RESTRICTION)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_SPHD_ABSENCE)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_SPHD_SPEC_LEAVE)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_SPEC_CLS)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_SPEC_EMP)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_GRANT_DATE)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		this.getEntityManager().createQuery(DELETE_All_ELAPSE)
			.setParameter("companyID", companyId)
			.setParameter("specialHolidayCD", specialHolidayCode)
			.executeUpdate();

		/**
		 * For delete releated domain of KDR001 (team G)
		 */
//		this.getEntityManager().createQuery(DELETE_SPEC_HD)
//			.setParameter("companyID", companyId)
//			.setParameter("specialHolidayCD", specialHolidayCode)
//			.executeUpdate();
	}

	/*@Override
	public void findByCID(String companyID) {
		List<Object[]> result1 = this.queryProxy().query(FIND_GRANT_REGULAR_TEST, Object[].class).getList();
		Map<KshstGrantRegular, List<KshstGrantDateTbl>> gratnRegularMap = result1
						.stream().collect(Collectors.groupingBy(item -> {
							return (KshstGrantRegular)item[0];
						}, Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream().filter(c -> c[1] != null)
								.map(c -> (KshstGrantDateTbl)c[1]))));
		List<Object[]> result2 = this.queryProxy().query(FIND_SPECICAL_LEAVE, Object[].class).getList();
		List<Object[]> result = this.queryProxy().query(FIND_BY_CID, Object[].class).getList();
		System.out.println(result);
	}*/

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<SpecialHoliday> findBySingleCD(String companyID, int specialHolidayCD) {

		return this.findByCode(companyID, specialHolidayCD)
			.map(x -> {
				List<Integer> absenceFrameNoLst = this.queryProxy().query(SELECT_SPHD_ABSENCE_BY_CODE, KshstSphdAbsence.class)
						.setParameter("companyID", companyID)
						.setParameter("specialHolidayCD", specialHolidayCD)
						.getList(c -> Integer.valueOf(c.pk.absFameNo));
				List<Integer> frameNoLst = this.queryProxy().query(SELECT_SPHD_SPEC_LEAVE, KshstSphdSpecLeave.class)
						.setParameter("companyID", companyID)
						.setParameter("specialHolidayCD", specialHolidayCD)
						.getList(c -> c.pk.sphdNo);
				List<String> listCls = this.queryProxy().query(SELECT_SPEC_CLS, KshstSpecCls.class)
						.setParameter("companyID", companyID)
						.setParameter("specialHolidayCD", specialHolidayCD)
						.getList(c -> c.pk.clsCode);
				List<String> listEmp = this.queryProxy().query(SELECT_SPEC_EMP, KshstSpecEmp.class)
						.setParameter("companyID", companyID)
						.setParameter("specialHolidayCD", specialHolidayCD)
						.getList(c -> c.pk.empCode);
				if(!absenceFrameNoLst.isEmpty()) {
					x.getTargetItem().setAbsenceFrameNo(absenceFrameNoLst);
				}
				if(!frameNoLst.isEmpty()) {
					x.getTargetItem().setFrameNo(frameNoLst);
				}
				if(!listCls.isEmpty()) {
					x.getSpecialLeaveRestriction().setListCls(listCls);
				}
				if(!listEmp.isEmpty()) {
					x.getSpecialLeaveRestriction().setListEmp(listEmp);
				}
				return x;
			});

	}

	@Override
	public List<Integer> findByAbsframeNo(String cid, int absFrameNo) {
		return this.queryProxy().query(QUEYRY_BY_ABSFRAMENO, KshstSphdAbsence.class)
				.setParameter("companyId", cid)
				.setParameter("absFameNo", absFrameNo)
				.getList().stream()
				.map(c -> {
					return c.pk.specialHolidayCode;
				}).collect(Collectors.toList());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecialHoliday> findSimpleByListCode(String companyId, List<Integer> specialHolidayCodes) {
		if(specialHolidayCodes.isEmpty())
			return Collections.emptyList();
		List<SpecialHoliday> resultList = new ArrayList<>();
		CollectionUtil.split(specialHolidayCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_SPHD_BY_LIST_CODE, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCodes", subList)
				.getList(c -> { return createSphdDomainFromEntity(c); }));
		});
		resultList.sort(Comparator.comparing(SpecialHoliday::getSpecialHolidayCode));
		return resultList;
	}

	@Override
	public List<SpecialHoliday> findByCompanyIdWithTargetItem(String companyId) {
		return this.queryProxy().query(SELECT_SPHD_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.getList(c -> {
					 return createSphdDomainFromEntityWithTargetItem(c);
				});
	}

	private SpecialHoliday 	createSphdDomainFromEntityWithTargetItem(Object[] c){
		String companyId = String.valueOf(c[0]);
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[1]));
		 return this.findBySingleCD(companyId, specialHolidayCode).get();
	}

	@Override
	public List<Integer> findBySphdSpecLeave(String cid, int sphdSpecLeaveNo) {
		return this.queryProxy().query(QUERY_BY_SPECLEAVE, KshstSphdSpecLeave.class)
				.setParameter("companyId", cid)
				.setParameter("sphdNo", sphdSpecLeaveNo)
				.getList().stream()
				.map(c -> {
					return c.pk.specialHolidayCode;
				}).collect(Collectors.toList());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<SpecialHoliday> findSimpleByCompanyIdNoMaster(String companyId, List<Integer> specialHolidayCodes) {
		if(specialHolidayCodes.isEmpty()) return Collections.emptyList();
		List<SpecialHoliday> resultList = new ArrayList<>();
		CollectionUtil.split(specialHolidayCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(SELECT_SPHD_BY_COMPANY_AND_NO, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", subList)
				.getList(c -> { return createSphdDomainFromEntity(c); }));
		});
		return resultList;
	}
}
