package nts.uk.ctx.at.shared.infra.repository.specialholiday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialVacationMethod;
import nts.uk.ctx.at.shared.dom.specialholiday.SphdLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.ctx.at.shared.dom.specialholiday.UseAge;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantDaySingleType;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantPeriodic;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantRegularMethod;
import nts.uk.ctx.at.shared.dom.specialholiday.grantday.GrantSingle;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantPeriodic;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantPeriodicPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantRegular;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantRegularPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantSingle;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantSinglePK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHoliday;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSpecialHolidayPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdClassfication;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdClassficationPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdEmployment;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdEmploymentPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdLimit;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdLimitPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdSubCondition;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdSubConditionPK;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdWorkType;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstSphdWorkTypePK;

/**
 * The class JpaSpecialHolidayRepository
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaSpecialHolidayRepository extends JpaRepository implements SpecialHolidayRepository {

	private static final String SELECT_BY_CID;
	private static final String CHECK_BY_CID;
	static {

		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstSpecialHoliday e");
		builderString.append(" WHERE e.kshstSpecialHolidayPK.companyId = :companyId");
		builderString.append(" ORDER BY e.kshstSpecialHolidayPK.specialHolidayCode ASC");
		SELECT_BY_CID = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstSpecialHoliday e");
		builderString.append(" WHERE e.kshstSpecialHolidayPK.companyId = :companyId");
		builderString.append(" AND e.kshstSpecialHolidayPK.specialHolidayCode = :specialHolidayCode");
		CHECK_BY_CID = builderString.toString();
	}

	/**
	 * Convert to Domain Special Holiday
	 * 
	 * @param kshstSpecialHoliday
	 * @return
	 */
	private SpecialHoliday convertToDomain(KshstSpecialHoliday kshstSpecialHoliday) {
		List<String> workTypeList = kshstSpecialHoliday.sphdWorkTypes.stream()
				.map(x -> x.kshstSphdWorkTypePK.workTypeCode).collect(Collectors.toList());

		GrantRegular grantRegular = convertToDomainRegular(kshstSpecialHoliday.grantRegular);
		GrantPeriodic grantPeriodic = convertToDomainPeriodic(kshstSpecialHoliday.grantPeriodic);
		SphdLimit sphdLimit = convertToDomainLimit(kshstSpecialHoliday.sphdLimit);
		SubCondition subCondition = convertToDomainSubCondition(kshstSpecialHoliday.subCondition);
		GrantSingle grantSingle = convertToDomainGrantSingle(kshstSpecialHoliday.grantSingle);

		SpecialHoliday specialHoliday = SpecialHoliday.createFromJavaType(
				kshstSpecialHoliday.kshstSpecialHolidayPK.companyId,
				kshstSpecialHoliday.kshstSpecialHolidayPK.specialHolidayCode, kshstSpecialHoliday.specialHolidayName,
				kshstSpecialHoliday.grantMethod, kshstSpecialHoliday.memo, workTypeList, grantRegular, grantPeriodic,
				sphdLimit, subCondition, grantSingle);
		return specialHoliday;
	}

	/**
	 * 
	 * Convert to Database Type Special Holiday
	 * 
	 * @param specialHoliday
	 * @return
	 */
	private KshstSpecialHoliday convertToDbType(SpecialHoliday specialHoliday) {
		KshstSpecialHoliday kshstSpecialHoliday = new KshstSpecialHoliday();
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		kshstSpecialHoliday.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		kshstSpecialHoliday.grantMethod = specialHoliday.getGrantMethod().value;
		kshstSpecialHoliday.memo = specialHoliday.getMemo().v();
		// Add list KDL 002
		List<KshstSphdWorkType> workTypes = specialHoliday.getWorkTypeList().stream().map(x -> {
			KshstSphdWorkTypePK key = new KshstSphdWorkTypePK(specialHoliday.getCompanyId(),
					specialHoliday.getSpecialHolidayCode().v(), x);
			return new KshstSphdWorkType(key);
		}).collect(Collectors.toList());

		kshstSpecialHoliday.sphdWorkTypes = workTypes;
		kshstSpecialHoliday.kshstSpecialHolidayPK = kshstSpecialHolidayPK;

		kshstSpecialHoliday.grantRegular = convertToDbTypeRegular(specialHoliday.getGrantRegular());
		kshstSpecialHoliday.grantPeriodic = convertToDbTypePeriodic(specialHoliday.getGrantPeriodic());
		kshstSpecialHoliday.sphdLimit = convertToDbTypeSphdLimit(specialHoliday.getSphdLimit());
		kshstSpecialHoliday.subCondition = convertToDbTypeSubCondition(specialHoliday.getSubCondition());
		kshstSpecialHoliday.grantSingle = convertToDbTypeGrantSingle(specialHoliday.getGrantSingle());
		return kshstSpecialHoliday;
	}

	/**
	 * Convert to Database Type Grant Regular
	 * 
	 * @param grantRegular
	 * @return
	 */
	private KshstGrantRegular convertToDbTypeRegular(GrantRegular grantRegular) {
		if (grantRegular == null) {
			return null;
		}
		KshstGrantRegular kshstGrantRegular = new KshstGrantRegular();
		KshstGrantRegularPK kshstGrantRegularPK = new KshstGrantRegularPK(grantRegular.getCompanyId(),
				grantRegular.getSpecialHolidayCode().v());
		kshstGrantRegular.grantStartDate = grantRegular.getGrantStartDate();
		if (GrantRegularMethod.GrantStartDateSpecify.equals(grantRegular.getGrantRegularMethod())) {
			kshstGrantRegular.months = grantRegular.getMonths().v();
			kshstGrantRegular.years = grantRegular.getYears().v();
		}
		kshstGrantRegular.grantRegularMethod = grantRegular.getGrantRegularMethod().value;
		kshstGrantRegular.kshstGrantRegularPK = kshstGrantRegularPK;
		return kshstGrantRegular;
	}

	/**
	 * Convert to Database Type Grant Periodic
	 * 
	 * @param grantPeriodic
	 * @return
	 */
	private KshstGrantPeriodic convertToDbTypePeriodic(GrantPeriodic grantPeriodic) {
		if (grantPeriodic == null) {
			return null;
		}
		KshstGrantPeriodic kshstGrantPeriodic = new KshstGrantPeriodic();
		KshstGrantPeriodicPK kshstGrantPeriodicPK = new KshstGrantPeriodicPK(grantPeriodic.getCompanyId(),
				grantPeriodic.getSpecialHolidayCode().v());
		kshstGrantPeriodic.grantDay = grantPeriodic.getGrantDay().v();
		kshstGrantPeriodic.splitAcquisition = grantPeriodic.getSplitAcquisition().value;
		kshstGrantPeriodic.grantPerioricMethod = grantPeriodic.getGrantPeriodicMethod().value;
		kshstGrantPeriodic.kshstGrantPeriodicPK = kshstGrantPeriodicPK;

		return kshstGrantPeriodic;
	}

	/**
	 * Convert to Database Type Sphd Limit
	 * 
	 * @param sphdLimit
	 * @return
	 */
	private KshstSphdLimit convertToDbTypeSphdLimit(SphdLimit sphdLimit) {
		if (sphdLimit == null) {
			return null;
		}
		KshstSphdLimit kshstSphdLimit = new KshstSphdLimit();
		KshstSphdLimitPK kshstSphdLimitPK = new KshstSphdLimitPK(sphdLimit.getCompanyId(),
				sphdLimit.getSpecialHolidayCode().v());
		if (SpecialVacationMethod.AvailableGrantDateDesignate.equals(sphdLimit.getSpecialVacationMethod())) {
			kshstSphdLimit.specialVacationMonths = sphdLimit.getSpecialVacationMonths().v();
			kshstSphdLimit.specialVacationYears = sphdLimit.getSpecialVacationYears().v();
		}
		kshstSphdLimit.grantCarryForward = sphdLimit.getGrantCarryForward().value;
		kshstSphdLimit.limitCarryoverDays = sphdLimit.getLimitCarryoverDays().v();
		kshstSphdLimit.specialVacationMethod = sphdLimit.getSpecialVacationMethod().value;
		kshstSphdLimit.kshstSphdLimitPK = kshstSphdLimitPK;
		return kshstSphdLimit;
	}

	/**
	 * Convert to Database Type Sub Condition
	 * 
	 * @param subCondition
	 * @return
	 */
	private KshstSphdSubCondition convertToDbTypeSubCondition(SubCondition subCondition) {
		if (subCondition == null) {
			return null;
		}
		
		List<KshstSphdEmployment> employments = subCondition.getEmploymentList().stream().map(x -> {
			KshstSphdEmploymentPK key = new KshstSphdEmploymentPK(subCondition.getCompanyId(),
					subCondition.getSpecialHolidayCode().v(), x);
			return new KshstSphdEmployment(key);
		}).collect(Collectors.toList());
		
		List<KshstSphdClassfication> classfications = subCondition.getClassificationList().stream().map(x -> {
			KshstSphdClassficationPK key = new KshstSphdClassficationPK(subCondition.getCompanyId(),
					subCondition.getSpecialHolidayCode().v(), x);
			return new KshstSphdClassfication(key);
		}).collect(Collectors.toList());
		
		KshstSphdSubCondition kshstSphdSubCondition = new KshstSphdSubCondition();
		KshstSphdSubConditionPK kshstSphdSubConditionPK = new KshstSphdSubConditionPK(subCondition.getCompanyId(),
				subCondition.getSpecialHolidayCode().v());
		kshstSphdSubCondition.useGender = subCondition.getUseGender().value;
		kshstSphdSubCondition.useEmployee = subCondition.getUseEmployee().value;
		kshstSphdSubCondition.useCls = subCondition.getUseCls().value;
		kshstSphdSubCondition.useAge = subCondition.getUseAge().value;
		kshstSphdSubCondition.genderAtr = subCondition.getGenderAtr().value;
		if (UseAge.Allow.equals(subCondition.getUseAge())) {
			kshstSphdSubCondition.limitAgeFrom = subCondition.getLimitAgeFrom().v();
			kshstSphdSubCondition.limitAgeTo = subCondition.getLimitAgeTo().v();
		}
		kshstSphdSubCondition.ageCriteriaAtr = subCondition.getAgeCriteriaAtr().value;
		kshstSphdSubCondition.ageBaseYearAtr = subCondition.getAgeBaseYearAtr().value;
		kshstSphdSubCondition.ageBaseDates = subCondition.getAgeBaseDates().v();
		kshstSphdSubCondition.sphdEmployments = employments;
		kshstSphdSubCondition.sphdClassfications = classfications;
		kshstSphdSubCondition.kshstSphdSubConditionPK = kshstSphdSubConditionPK;
		return kshstSphdSubCondition;
	}

	/**
	 * Convert to Database Type Grant Single
	 * 
	 * @param grantSingle
	 * @return
	 */
	private KshstGrantSingle convertToDbTypeGrantSingle(GrantSingle grantSingle) {
		if (grantSingle == null) {
			return null;
		}
		KshstGrantSingle kshstGrantSingle = new KshstGrantSingle();
		KshstGrantSinglePK kshstGrantSinglePK = new KshstGrantSinglePK(grantSingle.getCompanyId(),
				grantSingle.getSpecialHolidayCode().v());
		kshstGrantSingle.grantDaySingleType = grantSingle.getGrantDaySingleType().value;
		if (GrantDaySingleType.FixDay.equals(grantSingle.getGrantDaySingleType())) {
			kshstGrantSingle.fixNumberDays = grantSingle.getFixNumberDays().v();
		}
		kshstGrantSingle.makeInvitation = grantSingle.getMakeInvitation().value;
		kshstGrantSingle.holidayExcusionAtr = grantSingle.getHolidayExclusionAtr().value;
		kshstGrantSingle.kshstGrantSinglePK = kshstGrantSinglePK;
		return kshstGrantSingle;
	}

	/**
	 * 
	 * @param kshstGrantRegular
	 * @return
	 */
	private GrantRegular convertToDomainRegular(KshstGrantRegular kshstGrantRegular) {
		if (kshstGrantRegular == null) {
			return null;
		}
		GrantRegular grantRegular = GrantRegular.createFromJavaType(kshstGrantRegular.kshstGrantRegularPK.companyId,
				kshstGrantRegular.kshstGrantRegularPK.specialHolidayCode, kshstGrantRegular.grantStartDate,
				kshstGrantRegular.months, kshstGrantRegular.years, kshstGrantRegular.grantRegularMethod);

		return grantRegular;
	}

	/**
	 * 
	 * @param kshstgrantPeriodic
	 * @return
	 */
	private GrantPeriodic convertToDomainPeriodic(KshstGrantPeriodic kshstgrantPeriodic) {
		if (kshstgrantPeriodic == null) {
			return null;
		}
		GrantPeriodic grantPeriodic = GrantPeriodic.createFromJavaType(
				kshstgrantPeriodic.kshstGrantPeriodicPK.companyId,
				kshstgrantPeriodic.kshstGrantPeriodicPK.specialHolidayCode, kshstgrantPeriodic.grantDay,
				kshstgrantPeriodic.splitAcquisition, kshstgrantPeriodic.grantPerioricMethod);

		return grantPeriodic;
	}

	/**
	 * 
	 * @param kshstLimitSphd
	 * @return
	 */
	private SphdLimit convertToDomainLimit(KshstSphdLimit kshstLimitSphd) {
		if (kshstLimitSphd == null) {
			return null;
		}
		SphdLimit sphdLimit = SphdLimit.createFromJavaType(kshstLimitSphd.kshstSphdLimitPK.companyId,
				kshstLimitSphd.kshstSphdLimitPK.specialHolidayCode, kshstLimitSphd.specialVacationMonths,
				kshstLimitSphd.specialVacationYears, kshstLimitSphd.grantCarryForward,
				kshstLimitSphd.limitCarryoverDays, kshstLimitSphd.specialVacationMethod);

		return sphdLimit;
	}

	/**
	 * 
	 * @param kshstSphdSubCondition
	 * @return
	 */
	private SubCondition convertToDomainSubCondition(KshstSphdSubCondition kshstSphdSubCondition) {
		if (kshstSphdSubCondition == null) {
			return null;
		}
		List<String> classficationsList = kshstSphdSubCondition.sphdClassfications.stream()
				.map(x -> x.kshstSphdClassficationPK.classficationCode).collect(Collectors.toList());
		
		List<String> employmentsList = kshstSphdSubCondition.sphdEmployments.stream()
				.map(x -> x.kshstSphdEmploymentPK.employmentCode).collect(Collectors.toList());
				
		SubCondition subCondition = SubCondition.createFromJavaType(
				
				kshstSphdSubCondition.kshstSphdSubConditionPK.companyId,
				kshstSphdSubCondition.kshstSphdSubConditionPK.specialHolidayCode, kshstSphdSubCondition.useGender,
				kshstSphdSubCondition.useEmployee, kshstSphdSubCondition.useCls, kshstSphdSubCondition.useAge,
				kshstSphdSubCondition.genderAtr, kshstSphdSubCondition.limitAgeFrom, kshstSphdSubCondition.limitAgeTo,
				kshstSphdSubCondition.ageCriteriaAtr, kshstSphdSubCondition.ageBaseYearAtr,
				kshstSphdSubCondition.ageBaseDates, employmentsList, classficationsList);

		return subCondition;
	}

	/**
	 * 
	 * @param kshstGrantSingle
	 * @return
	 */
	private GrantSingle convertToDomainGrantSingle(KshstGrantSingle kshstGrantSingle) {
		if (kshstGrantSingle == null) {
			return null;
		}
		GrantSingle grantSingle = GrantSingle.createSimpleFromJavaType(kshstGrantSingle.kshstGrantSinglePK.companyId,
				kshstGrantSingle.kshstGrantSinglePK.specialHolidayCode, kshstGrantSingle.grantDaySingleType,
				kshstGrantSingle.fixNumberDays, kshstGrantSingle.makeInvitation, kshstGrantSingle.holidayExcusionAtr);

		return grantSingle;
	}

	/**
	 * Find by Company Id
	 */
	@Override
	public List<SpecialHoliday> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstSpecialHoliday.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

	/**
	 * Check Exists Special Holiday Code
	 */
	@Override
	public boolean checkExists(String companyId, String specialHolidayCode) {
		List<KshstSpecialHoliday> branchs = this.queryProxy().query(CHECK_BY_CID, KshstSpecialHoliday.class)
				.setParameter("companyId", companyId).setParameter("specialHolidayCode", specialHolidayCode).getList();

		return !branchs.isEmpty();
	}

	/**
	 * Add Special Holiday
	 */
	@Override
	public void add(SpecialHoliday specialHoliday) {
		this.commandProxy().insert(convertToDbType(specialHoliday));
	}

	/**
	 * Update Special Holiday
	 */
	@Override
	public void update(SpecialHoliday specialHoliday) {
		KshstSpecialHolidayPK primaryKey = new KshstSpecialHolidayPK(specialHoliday.getCompanyId(),
				specialHoliday.getSpecialHolidayCode().v());
		KshstSpecialHoliday entity = this.queryProxy().find(primaryKey, KshstSpecialHoliday.class).get();
		entity.specialHolidayName = specialHoliday.getSpecialHolidayName().v();
		entity.grantMethod = specialHoliday.getGrantMethod().value;
		entity.memo = specialHoliday.getMemo().v();
		List<KshstSphdWorkType> workTypes = specialHoliday.getWorkTypeList().stream().map(x -> {
			KshstSphdWorkTypePK key = new KshstSphdWorkTypePK(specialHoliday.getCompanyId(),
					specialHoliday.getSpecialHolidayCode().v(), x);
			return new KshstSphdWorkType(key);
		}).collect(Collectors.toList());

		entity.grantRegular = convertToDbTypeRegular(specialHoliday.getGrantRegular());
		entity.grantPeriodic = convertToDbTypePeriodic(specialHoliday.getGrantPeriodic());
		entity.sphdLimit = convertToDbTypeSphdLimit(specialHoliday.getSphdLimit());
		entity.subCondition = convertToDbTypeSubCondition(specialHoliday.getSubCondition());
		entity.grantSingle = convertToDbTypeGrantSingle(specialHoliday.getGrantSingle());
		entity.sphdWorkTypes = workTypes;
		entity.kshstSpecialHolidayPK = primaryKey;
		this.commandProxy().update(entity);
	}

	/**
	 * Delete Special Holiday
	 */
	@Override
	public void delete(String companyId, String specialHolidayCode) {
		KshstSpecialHolidayPK kshstSpecialHolidayPK = new KshstSpecialHolidayPK(companyId, specialHolidayCode);
		this.commandProxy().remove(KshstSpecialHoliday.class, kshstSpecialHolidayPK);
	}

}
