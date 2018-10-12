package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeStandard;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantTime;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.AvailabilityPeriod;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationDeadline;
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
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantRegular;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantinformation.KshstGrantRegularPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation.KshstGrantPeriodic;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.periodinformation.KshstGrantPeriodicPK;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * Jpa Special Holiday Repository
 * 
 * @author tanlv
 *
 */
@Stateless
public class JpaSpecialHolidayRepository extends JpaRepository implements SpecialHolidayRepository {
	private final static String SELECT_SPHD_BY_COMPANY_ID_QUERY = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.memo FROM KshstSpecialHoliday e "
			+ "WHERE e.pk.companyId = :companyId "
			+ "ORDER BY e.pk.specialHolidayCode ASC";
	
	private final static String SELECT_SPHD_BY_CODE_QUERY = "SELECT sphd.pk.companyId, sphd.pk.specialHolidayCode, sphd.specialHolidayName, sphd.memo,"
			+ " gra.typeTime, gra.grantDate, gra.allowDisappear, gra.interval, gra.grantedDays,"
			+ " pe.timeMethod, pe.startDate, pe.endDate, pe.deadlineMonths, pe.deadlineYears, pe.limitCarryoverDays,"
			+ " re.restrictionCls, re.ageLimit, re.genderRest, re.restEmp, re.ageCriteriaCls, re.ageBaseDate, re.ageLowerLimit, re.ageHigherLimit, re.gender"
			+ " FROM KshstSpecialHoliday sphd"
			+ " LEFT JOIN KshstGrantRegular gra"
			+ " ON sphd.pk.companyId = gra.pk.companyId AND sphd.pk.specialHolidayCode = gra.pk.specialHolidayCode"
			+ " LEFT JOIN KshstGrantPeriodic pe"
			+ " ON sphd.pk.companyId = pe.pk.companyId AND sphd.pk.specialHolidayCode = pe.pk.specialHolidayCode"
			+ " LEFT JOIN KshstSpecialLeaveRestriction re"
			+ " ON sphd.pk.companyId = re.pk.companyId AND sphd.pk.specialHolidayCode = re.pk.specialHolidayCode"
			+ " WHERE sphd.pk.companyId = :companyId AND sphd.pk.specialHolidayCode = :specialHolidayCode"
			+ " ORDER BY sphd.pk.specialHolidayCode";
	
	private final static String SELECT_SPHD_BY_LIST_CODE = "SELECT sphd.pk.companyId, sphd.pk.specialHolidayCode, sphd.specialHolidayName, sphd.memo,"
			+ " gra.typeTime, gra.grantDate, gra.allowDisappear, gra.interval, gra.grantedDays,"
			+ " pe.timeMethod, pe.startDate, pe.endDate, pe.deadlineMonths, pe.deadlineYears, pe.limitCarryoverDays,"
			+ " re.restrictionCls, re.ageLimit, re.genderRest, re.restEmp, re.ageCriteriaCls, re.ageBaseDate, re.ageLowerLimit, re.ageHigherLimit, re.gender"
			+ " FROM KshstSpecialHoliday sphd"
			+ " LEFT JOIN KshstGrantRegular gra"
			+ " ON sphd.pk.companyId = gra.pk.companyId AND sphd.pk.specialHolidayCode = gra.pk.specialHolidayCode"
			+ " LEFT JOIN KshstGrantPeriodic pe"
			+ " ON sphd.pk.companyId = pe.pk.companyId AND sphd.pk.specialHolidayCode = pe.pk.specialHolidayCode"
			+ " LEFT JOIN KshstSpecialLeaveRestriction re"
			+ " ON sphd.pk.companyId = re.pk.companyId AND sphd.pk.specialHolidayCode = re.pk.specialHolidayCode"
			+ " WHERE sphd.pk.companyId = :companyId "
			+ " AND sphd.pk.specialHolidayCode IN :specialHolidayCodes"
			+ " ORDER BY sphd.pk.specialHolidayCode";
	
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
	
	private final static String FIND_GRANT_REGULAR = "SELECT e,f FROM KshstGrantRegular e "
			+ " LEFT JOIN KshstGrantDateTbl f "
			+ " ON e.pk.companyId = f.pk.companyId AND e.pk.specialHolidayCode = f.pk.specialHolidayCode";
	
	private final static String FIND_GRANT_REGULAR_TEST = FIND_GRANT_REGULAR
			+ " WHERE e.pk.companyId = 'hung-test'";
	
	private final static String FIND_SPECICAL_LEAVE =  "SELECT g,h,i FROM KshstSpecialLeaveRestriction g"
			+ " LEFT JOIN KshstSpecEmp h"
			+ " ON g.pk.companyId = h.pk.companyId AND g.pk.specialHolidayCode = h.pk.specialHolidayCode"
			+ "	LEFT JOIN KshstSpecCls i"
			+ " ON i.pk.companyId = i.pk.companyId AND i.pk.specialHolidayCode = i.pk.specialHolidayCode";
	
	private final static String FIND_BY_CID = "SELECT a, b, c, d, k, l "
			+ " FROM KshstSpecialHoliday a"
			+ " LEFT JOIN ("
			+ FIND_GRANT_REGULAR
			+ " ) b"
			+ " ON a.pk.companyId = b.pk.companyId AND a.pk.specialHolidayCode = b.pk.specialHolidayCode"
			+ " LEFT JOIN KshstGrantPeriodic c"
			+ " ON a.pk.companyId = c.pk.companyId AND a.pk.specialHolidayCode = c.pk.specialHolidayCode"
			+ " LEFT JOIN ("
			+ FIND_SPECICAL_LEAVE
			+ " ) d"
			+ " ON a.pk.companyId = d.pk.companyId AND a.pk.specialHolidayCode = d.pk.specialHolidayCode"
			+ " LEFT JOIN KshstSphdSpecLeave k"
			+ " ON a.pk.companyId = k.pk.companyId AND a.pk.specialHolidayCode = k.pk.specialHolidayCode"
			+ " LEFT JOIN KshstSphdAbsence l"
			+ " ON a.pk.companyId = l.pk.companyId AND a.pk.specialHolidayCode = l.pk.specialHolidayCode"
			+ " WHERE a.pk.companyId = :companyId ORDER BY e.pk.specialHolidayCode ASC";
	
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
//	private final static String DELETE_SPEC_HD = "DELETE FROM KfnmtSpecialHoliday a "
//			+ "WHERE a.kfnmtSpecialHolidayPk.cid = :companyID "
//			+ "AND a.kfnmtSpecialHolidayPk.specialCd = :specialHolidayCD"; 
	
	private String QUEYRY_BY_ABSFRAMENO = "SELECT c FROM KshstSphdAbsence c"
			+ " WHERE c.pk.companyId = :companyId"
			+ " AND c.pk.absFameNo = :absFameNo";
	
	private String QUERY_BY_SPECLEAVE = "SELECT c FROM KshstSphdSpecLeave c"
			+ " WHERE c.pk.companyId = :companyId"
			+ " AND c.pk.sphdNo = :sphdNo";
	
	private final static String SELECT_SPHD_BY_COMPANY_AND_NO = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.memo FROM KshstSpecialHoliday e "
			+ "WHERE e.pk.companyId = :companyId "
			+ "AND e.pk.specialHolidayCode IN :specialHolidayCode";
	
	private SpecialHoliday createDomainFromEntity(Object[] c) {
		String companyId = String.valueOf(c[0]);
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[1]));
		String specialHolidayName = String.valueOf(c[2]);
		String memo = String.valueOf(c[3]);
		int typeTime = Integer.parseInt(String.valueOf(c[4]));
		int grantDate = Integer.parseInt(String.valueOf(c[5]));
		boolean allowDisappear = Integer.parseInt(String.valueOf(c[6])) == 1 ? true : false;
		int interval = c[7] != null ? Integer.parseInt(String.valueOf(c[7])) : 0;
		int grantedDays = c[8] != null ? Integer.parseInt(String.valueOf(c[8])) : 0;
		int timeMethod = Integer.parseInt(String.valueOf(c[9]));
		Integer startDate = c[10] != null ? (Integer)c[10] : null;
		Integer endDate = c[11] != null ? (Integer)c[11] : null;
		int deadlineMonths = c[12] != null ? Integer.parseInt(String.valueOf(c[12])) : 0;
		int deadlineYears = c[13] != null ? Integer.parseInt(String.valueOf(c[13])) : 0;
		int limitCarryoverDays = c[14] != null ? Integer.parseInt(String.valueOf(c[14])) : 0;
		int restrictionCls = Integer.parseInt(String.valueOf(c[15]));
		int ageLimit = Integer.parseInt(String.valueOf(c[16]));
		int genderRest = Integer.parseInt(String.valueOf(c[17]));
		int restEmp = Integer.parseInt(String.valueOf(c[18]));
		int ageCriteriaCls = c[20] != null ? Integer.parseInt(String.valueOf(c[19])) : 0;
		Integer ageBaseDateValue = c[21] != null ? Integer.parseInt(String.valueOf(c[20])) : null;
		MonthDay ageBaseDate = null;
		if(ageBaseDateValue!=null){
			ageBaseDate = new MonthDay(ageBaseDateValue/100, ageBaseDateValue%100);
		}
		int ageLowerLimit = c[21] != null ? Integer.parseInt(String.valueOf(c[21])) : 0;
		int ageHigherLimit = c[22] != null ? Integer.parseInt(String.valueOf(c[22])) : 0;
		int gender = c[23] != null ? Integer.parseInt(String.valueOf(c[23])) : 0;
		
		FixGrantDate fixGrantDate = FixGrantDate.createFromJavaType(interval, grantedDays);
		GrantTime grantTime = GrantTime.createFromJavaType(fixGrantDate, null);
		GrantRegular grantRegular = GrantRegular.createFromJavaType(companyId, specialHolidayCode, typeTime, grantDate, allowDisappear, grantTime);
		
		AvailabilityPeriod availabilityPeriod = AvailabilityPeriod.createFromJavaType(startDate, endDate);
		SpecialVacationDeadline expirationDate = SpecialVacationDeadline.createFromJavaType(deadlineMonths, deadlineYears);
		GrantPeriodic grantPeriodic = GrantPeriodic.createFromJavaType(companyId, specialHolidayCode, timeMethod, availabilityPeriod, expirationDate, limitCarryoverDays);
		
		AgeStandard ageStandard = AgeStandard.createFromJavaType(ageCriteriaCls, ageBaseDate);
		AgeRange ageRange = AgeRange.createFromJavaType(ageLowerLimit, ageHigherLimit);
		SpecialLeaveRestriction specialLeaveRestriction = SpecialLeaveRestriction.createFromJavaType(companyId, specialHolidayCode, restrictionCls, 
				ageLimit, genderRest, restEmp, ageStandard, ageRange, gender);
		
		return SpecialHoliday.createFromJavaType(companyId, specialHolidayCode, specialHolidayName, grantRegular, grantPeriodic, specialLeaveRestriction, memo);
	}
	
	private SpecialHoliday createSphdDomainFromEntity(Object[] c) {
		String companyId = String.valueOf(c[0]);
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[1]));
		String specialHolidayName = String.valueOf(c[2]);
		String memo = String.valueOf(c[3]);
		
		return SpecialHoliday.createFromJavaType(companyId, specialHolidayCode, specialHolidayName, memo);
	}
	
	private KshstSpecialHoliday createSpecialHolidayFromDomain(SpecialHoliday domain) {
		KshstSpecialHolidayPK pk = new KshstSpecialHolidayPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		return new KshstSpecialHoliday(pk, domain.getSpecialHolidayName().v(), domain.getMemo().v());
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
	
	private KshstGrantRegular createKshstGrantRegular(SpecialHoliday domain) {
		return new KshstGrantRegular(
				new KshstGrantRegularPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), 
				domain.getGrantRegular().getTypeTime().value, 
				domain.getGrantRegular().getGrantDate().value, 
				domain.getGrantRegular().isAllowDisappear()?1:0, 
				domain.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v(), 
				domain.getGrantRegular().getGrantTime().getFixGrantDate().getGrantDays().v());
	}
	
	private KshstGrantPeriodic createKshstGrantPeriodic(SpecialHoliday domain){
		return new KshstGrantPeriodic(
				new KshstGrantPeriodicPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), 
				domain.getGrantPeriodic().getTimeSpecifyMethod().value, 
				domain.getGrantPeriodic().getAvailabilityPeriod().getStartDateValue(), 
				domain.getGrantPeriodic().getAvailabilityPeriod().getEndDateValue(), 
				domain.getGrantPeriodic().getExpirationDate().getMonths().v(), 
				domain.getGrantPeriodic().getExpirationDate().getYears().v(), 
				domain.getGrantPeriodic().getLimitCarryoverDays().v());
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
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_SPHD_BY_COMPANY_ID_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.getList(c -> {
					return createSphdDomainFromEntity(c);
				});
	}
	
	@Override
	public Optional<SpecialHoliday> findByCode(String companyId, int specialHolidayCode) {
		return this.queryProxy().query(SELECT_SPHD_BY_CODE_QUERY, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("specialHolidayCode", specialHolidayCode)
				.getSingle(c -> {
					return createDomainFromEntity(c);
				});
	}

	@Override
	public void add(SpecialHoliday specialHoliday) {
		this.commandProxy().insert(createSpecialHolidayFromDomain(specialHoliday));
		this.commandProxy().insertAll(createKshstSphdAbsenceLst(specialHoliday));
		this.commandProxy().insertAll(createKshstSphdSpecLeaveLst(specialHoliday));
		this.commandProxy().insert(createKshstGrantRegular(specialHoliday));
		this.commandProxy().insert(createKshstGrantPeriodic(specialHoliday));
		this.commandProxy().insert(createKshstSpecialLeaveRestriction(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecClsLst(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecEmpLst(specialHoliday));
	}

	@Override
	public void update(SpecialHoliday specialHoliday) {
		KshstSpecialHolidayPK pk = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHoliday old = this.queryProxy().find(pk, KshstSpecialHoliday.class).orElse(null);
		old.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		old.memo = specialHoliday.getMemo().v();
		this.commandProxy().update(old);
		
		KshstGrantPeriodicPK grantPeriodicPK = new KshstGrantPeriodicPK(
				specialHoliday.getCompanyId(), 
				specialHoliday.getSpecialHolidayCode().v());
		KshstGrantPeriodic oldGrantPeriodic = this.queryProxy().find(grantPeriodicPK, KshstGrantPeriodic.class).orElse(null);
		GrantPeriodic grantPeriodic = specialHoliday.getGrantPeriodic();
		oldGrantPeriodic.timeMethod = grantPeriodic.getTimeSpecifyMethod().value;
		oldGrantPeriodic.startDate = grantPeriodic.getAvailabilityPeriod().getStartDateValue();
		oldGrantPeriodic.endDate = grantPeriodic.getAvailabilityPeriod().getEndDateValue();
		oldGrantPeriodic.deadlineMonths = grantPeriodic.getExpirationDate().getMonths().v();
		oldGrantPeriodic.deadlineYears = grantPeriodic.getExpirationDate().getYears().v();
		oldGrantPeriodic.limitCarryoverDays = grantPeriodic.getLimitCarryoverDays().v();
		this.commandProxy().update(oldGrantPeriodic);
		
		KshstGrantRegularPK grantRegularPK = new KshstGrantRegularPK(
				specialHoliday.getCompanyId(), 
				specialHoliday.getSpecialHolidayCode().v());
		KshstGrantRegular oldGrantRegular = this.queryProxy().find(grantRegularPK, KshstGrantRegular.class).orElse(null);
		GrantRegular grantRegular = specialHoliday.getGrantRegular();
		oldGrantRegular.typeTime = grantRegular.getTypeTime().value;
		oldGrantRegular.grantDate = grantRegular.getGrantDate().value;
		oldGrantRegular.allowDisappear = grantRegular.isAllowDisappear() ? 1 : 0;
		oldGrantRegular.interval = grantRegular.getGrantTime().getFixGrantDate().getInterval().v();
		oldGrantRegular.grantedDays = grantRegular.getGrantTime().getFixGrantDate().getGrantDays().v();
		this.commandProxy().update(oldGrantRegular);
		
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
		
		KshstGrantRegularPK grantRegularPK = new KshstGrantRegularPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstGrantRegular.class, grantRegularPK);
		
		KshstGrantPeriodicPK grantPeriodicPK = new KshstGrantPeriodicPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstGrantPeriodic.class, grantPeriodicPK);
		
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
	
	@Override
	public List<SpecialHoliday> findByListCode(String companyId, List<Integer> specialHolidayCodes) {
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

	@Override
	public List<SpecialHoliday> findByCompanyIdNoMaster(String companyId, List<Integer> specialHolidayCodes) {
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
