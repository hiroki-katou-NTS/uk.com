package nts.uk.ctx.at.shared.infra.repository.specialholidaynew;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeLimit;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.AgeStandard;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantTime;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantedDays;
import nts.uk.ctx.at.shared.dom.specialholidaynew.grantinformation.GrantedYears;
import nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation.SpecialVacationDeadline;
import nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation.SpecialVacationMonths;
import nts.uk.ctx.at.shared.dom.specialholidaynew.periodinformation.SpecialVacationYears;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSpecialHolidayNew;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSpecialHolidayPKNew;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSphdAbsence;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSphdAbsencePK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSphdSpecLeave;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.KshstSphdSpecLeavePK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecCls;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecClsPK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecEmp;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecEmpPK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecialLeaveRestriction;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition.KshstSpecialLeaveRestrictionPK;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstGrantRegularNew;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantinformation.KshstGrantRegularPKNew;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.periodinformation.KshstGrantPeriodicNew;
import nts.uk.ctx.at.shared.infra.entity.specialholidaynew.periodinformation.KshstGrantPeriodicPKNew;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * Jpa Special Holiday Repository
 * 
 * @author tanlv
 *
 */
@Stateless
public class JpaSpecialHolidayRepositoryNew extends JpaRepository implements SpecialHolidayRepository {
	private final static String SELECT_SPHD_BY_COMPANY_ID_QUERY = "SELECT e.pk.companyId, e.pk.specialHolidayCode, e.specialHolidayName, e.memo FROM KshstSpecialHolidayNew e "
			+ "WHERE e.pk.companyId = :companyId "
			+ "ORDER BY e.pk.specialHolidayCode ASC";
	
	private final static String SELECT_SPHD_BY_CODE_QUERY = "SELECT sphd.pk.companyId, sphd.pk.specialHolidayCode, sphd.specialHolidayName, sphd.memo,"
			+ " gra.typeTime, gra.grantDate, gra.allowDisappear, gra.interval, gra.grantedDays,"
			+ " pe.timeMethod, pe.startDate, pe.endDate, pe.deadlineMonths, pe.deadlineYears, pe.limitCarryoverDays,"
			+ " re.pk.specialHolidayCode, re.restrictionCls, re.ageLimit, re.genderRest, re.restEmp, re.ageCriteriaCls, re.ageBaseDate, re.ageLowerLimit, re.ageHigherLimit, re.gender"
			+ " FROM KshstSpecialHolidayNew sphd"
			+ " LEFT JOIN KshstGrantRegularNew gra"
			+ " ON sphd.pk.companyId = gra.pk.companyId AND sphd.pk.specialHolidayCode = gra.pk.specialHolidayCode"
			+ " LEFT JOIN KshstGrantPeriodicNew pe"
			+ " ON sphd.pk.companyId = pe.pk.companyId AND sphd.pk.specialHolidayCode = pe.pk.specialHolidayCode"
			+ " LEFT JOIN KshstSpecialLeaveRestriction re"
			+ " ON sphd.pk.companyId = re.pk.companyId AND sphd.pk.specialHolidayCode = re.pk.specialHolidayCode"
			+ " WHERE sphd.pk.companyId = :companyId AND sphd.pk.specialHolidayCode = :specialHolidayCode"
			+ " ORDER BY sphd.pk.specialHolidayCode";
	
	private final static String SELECT_SPHD_ABSENCE_BY_CODE = "SELECT a FROM KshstSphdAbsence "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";
	
	private final static String SELECT_SPHD_SPEC_LEAVE = "SELECT a FROM KshstSphdSpecLeave "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";
	
	private final static String SELECT_SPEC_CLS = "SELECT a FROM KshstSpecCls "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";
	
	private final static String SELECT_SPEC_EMP = "SELECT a FROM KshstSpecEmp "
			+ "WHERE a.pk.companyId = :companyID "
			+ "AND a.pk.specialHolidayCode = :specialHolidayCD";
	
	private final static String FIND_GRANT_REGULAR = "SELECT e,f FROM KshstGrantRegularNew e "
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
			+ " FROM KshstSpecialHolidayNew a"
			+ " LEFT JOIN ("
			+ FIND_GRANT_REGULAR
			+ " ) b"
			+ " ON a.pk.companyId = b.pk.companyId AND a.pk.specialHolidayCode = b.pk.specialHolidayCode"
			+ " LEFT JOIN KshstGrantPeriodicNew c"
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
	
	private String QUEYRY_BY_ABSFRAMENO = "SELECT c FROM KshstSphdAbsence c"
			+ " WHERE c.pk.companyId = :companyId"
			+ " AND c.pk.absFameNo = :absFameNo";
	
	private SpecialHoliday createDomainFromEntity(Object[] c) {
		String companyId = String.valueOf(c[0]);
		int specialHolidayCode = Integer.parseInt(String.valueOf(c[1]));
		String specialHolidayName = String.valueOf(c[2]);
		String memo = String.valueOf(c[3]);
		int typeTime = Integer.parseInt(String.valueOf(c[4]));
		int grantDate = Integer.parseInt(String.valueOf(c[5]));
		boolean allowDisappear = Integer.parseInt(String.valueOf(c[6])) == 1 ? true : false;
		int interval = Integer.parseInt(String.valueOf(c[7]));
		int grantedDays = c[8] != null ? Integer.parseInt(String.valueOf(c[8])) : 0;
		int timeMethod = Integer.parseInt(String.valueOf(c[9]));
		GeneralDate startDate = c[10] != null ? (GeneralDate)c[10] : null;
		GeneralDate endDate = c[11] != null ? (GeneralDate)c[11] : null;
		int deadlineMonths = c[12] != null ? Integer.parseInt(String.valueOf(c[12])) : 0;
		int deadlineYears = c[13] != null ? Integer.parseInt(String.valueOf(c[13])) : 0;
		int limitCarryoverDays = c[14] != null ? Integer.parseInt(String.valueOf(c[14])) : 0;
		int specialLeaveCode = c[15] != null ? Integer.parseInt(String.valueOf(c[15])) : 0;
		int restrictionCls = Integer.parseInt(String.valueOf(c[16]));
		int ageLimit = Integer.parseInt(String.valueOf(c[17]));
		int genderRest = Integer.parseInt(String.valueOf(c[18]));
		int restEmp = Integer.parseInt(String.valueOf(c[19]));
		int ageCriteriaCls = c[20] != null ? Integer.parseInt(String.valueOf(c[20])) : 0;
		Integer ageBaseDateValue = c[21] != null ? Integer.parseInt(String.valueOf(c[21])) : null;
		MonthDay ageBaseDate = null;
		if(ageBaseDateValue!=null){
			ageBaseDate = new MonthDay(ageBaseDateValue/100, ageBaseDateValue%100);
		}
		int ageLowerLimit = c[22] != null ? Integer.parseInt(String.valueOf(c[22])) : 0;
		int ageHigherLimit = c[23] != null ? Integer.parseInt(String.valueOf(c[23])) : 0;
		int gender = c[24] != null ? Integer.parseInt(String.valueOf(c[24])) : 0;
		
		FixGrantDate fixGrantDate = FixGrantDate.createFromJavaType(new GrantedYears(interval), new GrantedDays(grantedDays));
		GrantTime grantTime = GrantTime.createFromJavaType(fixGrantDate, null);
		GrantRegular grantRegular = GrantRegular.createFromJavaType(companyId, specialHolidayCode, typeTime, grantDate, allowDisappear, grantTime);
		
		DatePeriod availabilityPeriod = new DatePeriod(startDate != null ? startDate : GeneralDate.min(), endDate != null ? endDate : GeneralDate.min());
		SpecialVacationDeadline expirationDate = SpecialVacationDeadline.createFromJavaType(new SpecialVacationMonths(deadlineMonths), new SpecialVacationYears(deadlineYears));
		GrantPeriodic grantPeriodic = GrantPeriodic.createFromJavaType(companyId, specialHolidayCode, timeMethod, availabilityPeriod, expirationDate, limitCarryoverDays);
		
		AgeStandard ageStandard = AgeStandard.createFromJavaType(ageCriteriaCls, ageBaseDate);
		AgeRange ageRange = AgeRange.createFromJavaType(new AgeLimit(ageLowerLimit), new AgeLimit(ageHigherLimit));
		SpecialLeaveRestriction specialLeaveRestriction = SpecialLeaveRestriction.createFromJavaType(companyId, specialHolidayCode, specialLeaveCode, restrictionCls, 
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
	
	private KshstSpecialHolidayNew createSpecialHolidayFromDomain(SpecialHoliday domain) {
		KshstSpecialHolidayPKNew pk = new KshstSpecialHolidayPKNew(domain.getCompanyId(), domain.getSpecialHolidayCode().v());
		return new KshstSpecialHolidayNew(pk, domain.getSpecialHolidayName().v(), domain.getMemo().v());
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
	
	private KshstGrantRegularNew createKshstGrantRegular(SpecialHoliday domain) {
		return new KshstGrantRegularNew(
				new KshstGrantRegularPKNew(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), 
				domain.getGrantRegular().getTypeTime().value, 
				domain.getGrantRegular().getGrantDate().value, 
				domain.getGrantRegular().isAllowDisappear()?1:0, 
				domain.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v(), 
				domain.getGrantRegular().getGrantTime().getFixGrantDate().getGrantDays().v());
	}
	
	private KshstGrantPeriodicNew createKshstGrantPeriodicNew(SpecialHoliday domain){
		return new KshstGrantPeriodicNew(
				new KshstGrantPeriodicPKNew(domain.getCompanyId(), domain.getSpecialHolidayCode().v()), 
				domain.getGrantPeriodic().getTimeSpecifyMethod().value, 
				domain.getGrantPeriodic().getAvailabilityPeriod().start(), 
				domain.getGrantPeriodic().getAvailabilityPeriod().end(), 
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
				domain.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit().v(), 
				domain.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit().v(), 
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
		this.commandProxy().insert(createKshstGrantPeriodicNew(specialHoliday));
		this.commandProxy().insert(createKshstSpecialLeaveRestriction(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecClsLst(specialHoliday));
		this.commandProxy().insertAll(createKshstSpecEmpLst(specialHoliday));
	}

	@Override
	public void update(SpecialHoliday specialHoliday) {
		KshstSpecialHolidayPKNew pk = new KshstSpecialHolidayPKNew(specialHoliday.getCompanyId(), specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHolidayNew old = this.queryProxy().find(pk, KshstSpecialHolidayNew.class).orElse(null);
		old.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		old.memo = specialHoliday.getMemo().v();
		this.commandProxy().update(old);
		
		KshstGrantPeriodicPKNew grantPeriodicPK = new KshstGrantPeriodicPKNew(
				specialHoliday.getCompanyId(), 
				specialHoliday.getSpecialHolidayCode().v());
		KshstGrantPeriodicNew oldGrantPeriodic = this.queryProxy().find(grantPeriodicPK, KshstGrantPeriodicNew.class).orElse(null);
		oldGrantPeriodic.timeMethod = specialHoliday.getGrantPeriodic().getTimeSpecifyMethod().value;
		oldGrantPeriodic.startDate = specialHoliday.getGrantPeriodic().getAvailabilityPeriod().start();
		oldGrantPeriodic.endDate = specialHoliday.getGrantPeriodic().getAvailabilityPeriod().end();
		oldGrantPeriodic.deadlineMonths = specialHoliday.getGrantPeriodic().getExpirationDate().getMonths().v();
		oldGrantPeriodic.deadlineYears = specialHoliday.getGrantPeriodic().getExpirationDate().getYears().v();
		oldGrantPeriodic.limitCarryoverDays = specialHoliday.getGrantPeriodic().getLimitCarryoverDays().v();
		this.commandProxy().update(oldGrantPeriodic);
		
		KshstGrantRegularPKNew grantRegularPK = new KshstGrantRegularPKNew(
				specialHoliday.getCompanyId(), 
				specialHoliday.getSpecialHolidayCode().v());
		KshstGrantRegularNew oldGrantRegular = this.queryProxy().find(grantRegularPK, KshstGrantRegularNew.class).orElse(null);
		oldGrantRegular.typeTime = specialHoliday.getGrantRegular().getTypeTime().value;
		oldGrantRegular.grantDate = specialHoliday.getGrantRegular().getGrantDate().value;
		oldGrantRegular.allowDisappear = specialHoliday.getGrantRegular().isAllowDisappear() ? 1 : 0;
		oldGrantRegular.interval = specialHoliday.getGrantRegular().getGrantTime().getFixGrantDate().getInterval().v();
		oldGrantRegular.grantedDays = specialHoliday.getGrantRegular().getGrantTime().getFixGrantDate().getGrantDays().v();
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
		oldSpecialLeaveRestriction.ageLowerLimit = specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeLowerLimit().v();
		oldSpecialLeaveRestriction.ageHigherLimit = specialHoliday.getSpecialLeaveRestriction().getAgeRange().getAgeHigherLimit().v();
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
		KshstSpecialHolidayPKNew pk = new KshstSpecialHolidayPKNew(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstSpecialHolidayNew.class, pk);
		
		KshstGrantRegularPKNew grantRegularPK = new KshstGrantRegularPKNew(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstGrantRegularNew.class, grantRegularPK);
		
		KshstGrantPeriodicPKNew grantPeriodicPK = new KshstGrantPeriodicPKNew(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstGrantPeriodicNew.class, grantPeriodicPK);
		
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
	}

	/*@Override
	public void findByCID(String companyID) {
		List<Object[]> result1 = this.queryProxy().query(FIND_GRANT_REGULAR_TEST, Object[].class).getList();
		Map<KshstGrantRegularNew, List<KshstGrantDateTbl>> gratnRegularMap = result1
						.stream().collect(Collectors.groupingBy(item -> {
							return (KshstGrantRegularNew)item[0];
						}, Collectors.collectingAndThen(Collectors.toList(), 
								list -> list.stream().filter(c -> c[1] != null)
								.map(c -> (KshstGrantDateTbl)c[1]))));
		List<Object[]> result2 = this.queryProxy().query(FIND_SPECICAL_LEAVE, Object[].class).getList();
		List<Object[]> result = this.queryProxy().query(FIND_BY_CID, Object[].class).getList();
		System.out.println(result);
	}*/

	@Override
	public Optional<SpecialHoliday> findBySingleCD(String companyID, int specialHolidayCD) {
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
		return this.findByCode(companyID, specialHolidayCD)
			.map(x -> {
				x.getTargetItem().setAbsenceFrameNo(absenceFrameNoLst);
				x.getTargetItem().setFrameNo(frameNoLst);
				x.getSpecialLeaveRestriction().setListCls(listCls);
				x.getSpecialLeaveRestriction().setListEmp(listEmp);
				return x;
			});
		
	}

	@Override
	public Optional<Integer> findByAbsframeNo(String cid, int absFrameNo) {
		return this.queryProxy().query(QUEYRY_BY_ABSFRAMENO, KshstSphdAbsence.class)
				.setParameter("companyId", cid)
				.setParameter("absFameNo", absFrameNo)
				.getSingle(c -> {
					return c.pk.specialHolidayCode;
				});
	}
}
