/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.commons.lang3.BooleanUtils;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.LeaveSetAdded;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHour;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstAddSetManWKHourPK;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAdditionSet;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstHolidayAdditionSetPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddInclude;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddIncludePK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPK;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddPremium;
import nts.uk.ctx.at.shared.infra.entity.scherec.addsettingofworktime.KsrmtCalcCAddWorktime;
import nts.uk.ctx.at.shared.infra.repository.scherec.addsettingofworktime.JpaAddSettingOfWorkingTimeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaHolidayAddtionRepository.
 */
@Stateless
public class JpaHolidayAddtionRepository extends JpaRepository implements HolidayAddtionRepository {

	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshstHolidayAdditionSet e");
		builderString.append(" WHERE e.kshstHolidayAddtimeSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	private HolidayAddtionSet convertToDomainHolidayAdditionSet(KshstHolidayAdditionSet holidayAddtimeSet) {

        BreakDownTimeDay breakdownTimeDay = new BreakDownTimeDay(new AttendanceTime(holidayAddtimeSet.oneDay.intValue()),
                new AttendanceTime(holidayAddtimeSet.morning.intValue()),
                new AttendanceTime(holidayAddtimeSet.afternoon.intValue()));
        LeaveSetAdded additionVacationSet = new LeaveSetAdded(NotUseAtr.valueOf(holidayAddtimeSet.annualHoliday),
                NotUseAtr.valueOf(holidayAddtimeSet.yearlyReserved),
                NotUseAtr.valueOf(holidayAddtimeSet.specialHoliday));
        List<TimeHolidayAdditionSet> lstTimeHDAddSet = new ArrayList<>();
        TimeHolidayAdditionSet timeHolidayAdditionSet = TimeHolidayAdditionSet.builder()
                .addingMethod(TimeHolidayAddingMethod.valueOf(holidayAddtimeSet.addingMethod1))
                .workClass(WorkClassOfTimeHolidaySet.valueOf(holidayAddtimeSet.workClass1))
                .build();
        lstTimeHDAddSet.add(timeHolidayAdditionSet);

        timeHolidayAdditionSet = TimeHolidayAdditionSet.builder()
                .addingMethod(TimeHolidayAddingMethod.valueOf(holidayAddtimeSet.addingMethod2))
                .workClass(WorkClassOfTimeHolidaySet.valueOf(holidayAddtimeSet.workClass2))
                .build();
        lstTimeHDAddSet.add(timeHolidayAdditionSet);
        RefDesForAdditionalTakeLeave refDesForAdditionalTakeLeave = new RefDesForAdditionalTakeLeave(
                breakdownTimeDay,
                holidayAddtimeSet.refAtrCom,
                holidayAddtimeSet.refAtrEmp
        );

        HolidayAddtionSet addtime = HolidayAddtionSet.createFromJavaType(
                holidayAddtimeSet.kshstHolidayAddtimeSetPK.companyId,
                refDesForAdditionalTakeLeave,
                additionVacationSet,
                lstTimeHDAddSet);

        return addtime;
	}
	
	/**
	 * Convert to domain.
	 *
	 * @param holidayAddtimeSet the holiday addtime set
	 * @return the map
	 */
	private Map<String, AggregateRoot> convertToDomain(
			KshstHolidayAdditionSet holidayAddtimeSet,
			Optional<WorkRegularAdditionSet> regularWork,
			Optional<WorkFlexAdditionSet> flexWork,
			Optional<WorkDeformedLaborAdditionSet> irregularWork,
			Optional<HourlyPaymentAdditionSet> hourlyPaymentAdditionSet){
		
		Map<String, AggregateRoot> mapAggre = new HashMap<>();
		HolidayAddtionSet addtime = convertToDomainHolidayAdditionSet(holidayAddtimeSet);
		AddSetManageWorkHour addSetManageWorkHour = convertToDomainAddSetManageWorkHour(holidayAddtimeSet.addSetManWKHour).orElse(null);
		mapAggre.put("holidayAddtionSet", addtime);
		mapAggre.put("regularWork", regularWork.orElse(null));
		mapAggre.put("flexWork", flexWork.orElse(null));
		mapAggre.put("irregularWork", irregularWork.orElse(null));
		mapAggre.put("hourlyPaymentAdditionSet", hourlyPaymentAdditionSet.orElse(null));
		mapAggre.put("addSetManageWorkHour", addSetManageWorkHour);
		return mapAggre;
	}

	/**
	 * Convert to Database Regular Work
	 * @param regularWork
	 * @return
	 */
	protected KshmtCalcCAddHdReg convertToDbTypeRegularWork(WorkRegularAdditionSet regularWork) {
		KshstWorkRegularSetPK kshstRegularWorkSetPK = new KshstWorkRegularSetPK(regularWork.getCompanyId());
		KshmtCalcCAddHdReg kshstRegularWorkSet;
		Optional<KshmtCalcCAddHdReg> optKshstWorkRegularSet = this.queryProxy().find(kshstRegularWorkSetPK,KshmtCalcCAddHdReg.class);
		if (optKshstWorkRegularSet.isPresent()) {
			kshstRegularWorkSet = optKshstWorkRegularSet.get();
		} else {
			kshstRegularWorkSet = new KshmtCalcCAddHdReg();
		}
		
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = regularWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
		PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
		DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
		
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = regularWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();
		
		kshstRegularWorkSet.calcActualOperation1 = premiumHolidayCalcMethod.getCalculateActualOperation().value;
		kshstRegularWorkSet.exemptTaxTime1 = advanceSetPre.getCalculateIncludIntervalExemptionTime().value;
		kshstRegularWorkSet.incChildNursingCare1 = advanceSetPre.getCalculateIncludCareTime().value;
		kshstRegularWorkSet.additionTime1  = includeHolidaysPremiumCalcDetailSet.getAddition().value;
		kshstRegularWorkSet.notDeductLateleave1 = deductLeaveEarly.getDeduct().isDeduct() ? 1 : 0;
		kshstRegularWorkSet.deformatExcValue1 = includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value;
		kshstRegularWorkSet.exemptTaxTime2 = advanceSetWork.getCalculateIncludIntervalExemptionTime().value;
		kshstRegularWorkSet.calcActualOperation2 = workTimeHolidayCalcMethod.getCalculateActualOperation().value;
		kshstRegularWorkSet.incChildNursingCare2 = advanceSetWork.getCalculateIncludCareTime().value;
		kshstRegularWorkSet.notDeductLateleave2 = advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() ? 1 : 0;
		kshstRegularWorkSet.additionTime2 = includeVacationSet.getAddition().value;
		kshstRegularWorkSet.enableSetPerWorkHour1 = deductLeaveEarly.isEnableSetPerWorkHour();
		kshstRegularWorkSet.enableSetPerWorkHour2 = kshstRegularWorkSet.enableSetPerWorkHour1;
		kshstRegularWorkSet.kshstRegularWorkSetPK = kshstRegularWorkSetPK;
		kshstRegularWorkSet.deductByApplication = deductLeaveEarly.getDeduct().isDeductByApp();
		kshstRegularWorkSet.setPreCalcMethod = BooleanUtils.toBoolean(regularWork.getVacationCalcMethodSet().getUseAtr().value);

		return kshstRegularWorkSet;
	}

	/**
	 * Convert to Domain Irregular Work
	 * @param irregularWorkSet
	 * @return
	 */
	protected WorkDeformedLaborAdditionSet convertToDomainIrregularWork(KshstWorkDepLaborSet irregularWorkSet) {
		if (irregularWorkSet != null) {
			WorkDeformedLaborAdditionSet irregularWork = WorkDeformedLaborAdditionSet.createFromJavaType(irregularWorkSet.kshstWorkDepLaborSetPK.companyId, 
					irregularWorkSet.calcActualOperation1, 
					irregularWorkSet.exemptTaxTime1, 
					irregularWorkSet.incChildNursingCare1, 
					irregularWorkSet.additionTime1, 
					irregularWorkSet.notDeductLateleave1,
					irregularWorkSet.deformatExcValue,
					irregularWorkSet.exemptTaxTime2, 
					irregularWorkSet.minusAbsenceTime2, 
					irregularWorkSet.calcActualOperation2, 
					irregularWorkSet.incChildNursingCare2, 
					irregularWorkSet.notDeductLateleave2,
					irregularWorkSet.additionTime2,
					BooleanUtils.toInteger(irregularWorkSet.enableSetPerWorkHour1),
					BooleanUtils.toInteger(irregularWorkSet.setPreCalcMethod));
			return irregularWork;
		}
		return null;
	}

	/**
	 * Convert to Domain Flex Work
	 * @param flexWorkSet
	 * @return
	 */
	protected WorkFlexAdditionSet convertToDomainFlexWork(KshmtCalcCAddHdFle flexWorkSet) {
		if (flexWorkSet != null) {
			WorkFlexAdditionSet flexWork = WorkFlexAdditionSet.createFromJavaType(flexWorkSet.kshstFlexWorkSetPK.companyId, 
					flexWorkSet.calcActualOperation1, 
					flexWorkSet.exemptTaxTime1, 
					flexWorkSet.incChildNursingCare1, 
					flexWorkSet.predeterminedOvertime1, 
					flexWorkSet.additionTime1, 
					flexWorkSet.notDeductLateleave1, 
					flexWorkSet.exemptTaxTime2, 
					flexWorkSet.minusAbsenceTime2, 
					flexWorkSet.calcActualOperation2, 
					flexWorkSet.incChildNursingCare2, 
					flexWorkSet.notDeductLateleave2, 
					flexWorkSet.predeterminDeficiency2, 
					flexWorkSet.additionTime2,
					BooleanUtils.toInteger(flexWorkSet.enableSetPerWorkHour1),
					flexWorkSet.additionWithinMonthlyStatutory,
					BooleanUtils.toInteger(flexWorkSet.setPreCalcMethod));
			return flexWork;
		}
		return null;
	}

	/**
	 * Convert to Domain Regular Work
	 * @param regularWorkSet
	 * @return
	 */
	protected WorkRegularAdditionSet convertToDomainRegularWork(KshmtCalcCAddHdReg regularWorkSet) {
		if (regularWorkSet != null) {
			WorkRegularAdditionSet regularWork = WorkRegularAdditionSet.createFromJavaType(regularWorkSet.kshstRegularWorkSetPK.companyId, 
					regularWorkSet.calcActualOperation1, 
					regularWorkSet.exemptTaxTime1, 
					regularWorkSet.incChildNursingCare1, 
					regularWorkSet.additionTime1, 
					regularWorkSet.notDeductLateleave1, 
					regularWorkSet.deformatExcValue1, 
					regularWorkSet.exemptTaxTime2, 
					regularWorkSet.calcActualOperation2, 
					regularWorkSet.incChildNursingCare2, 
					regularWorkSet.notDeductLateleave2, 
					regularWorkSet.additionTime2,
					BooleanUtils.toInteger(regularWorkSet.enableSetPerWorkHour1),
					BooleanUtils.toInteger(regularWorkSet.setPreCalcMethod));
			return regularWork;
		}
		return null;
	}
	
	/**
	 * Convert to domain hourly payment add set.
	 *
	 * @param hourPayAaddSet the hour pay aadd set
	 * @return the hourly payment addition set
	 */
	protected HourlyPaymentAdditionSet convertToDomainHourlyPaymentAddSet(KshstHourPayAaddSet hourPayAaddSet) {
		if (hourPayAaddSet != null) {
			HourlyPaymentAdditionSet hourlyPaymentAdditionSet = HourlyPaymentAdditionSet.createFromJavaType(hourPayAaddSet.kshstHourPayAaddSetPK.companyId, 
					hourPayAaddSet.calcPremiumVacation, 
					hourPayAaddSet.addition1, 
					hourPayAaddSet.deformatExcValue, 
					hourPayAaddSet.incChildNursingCare, 
					BooleanUtils.toInteger(hourPayAaddSet.deduct), 
					hourPayAaddSet.calculateIncludeIntervalExemptionTime1, 
					hourPayAaddSet.calcWorkHourVacation, 
					hourPayAaddSet.addition2, 
					hourPayAaddSet.calculateIncludCareTime, 
					hourPayAaddSet.notDeductLateLeaveEarly, 
					hourPayAaddSet.calculateIncludeIntervalExemptionTime2, 
					BooleanUtils.toInteger(hourPayAaddSet.enableSetPerWorkHour1),
					BooleanUtils.toInteger(hourPayAaddSet.setPreCalcMethod));
			return hourlyPaymentAdditionSet;
		}
		return null;
		
	}
	
	/**
	 * Convert to domain add set manage work hour.
	 *
	 * @param kshstAddSetManWKHour the kshst add set man WK hour
	 * @return the adds the set manage work hour
	 */
	public static Optional<AddSetManageWorkHour> convertToDomainAddSetManageWorkHour(KshstAddSetManWKHour kshstAddSetManWKHour) {
		if (kshstAddSetManWKHour != null) {
			AddSetManageWorkHour addSetManageWorkHour = AddSetManageWorkHour.builder()
					.companyId(kshstAddSetManWKHour.kshstAddSetManWKHourPK.companyId)
					.additionSettingOfOvertime(NotUseAtr.valueOf(kshstAddSetManWKHour.addSetOT))
					.build();
			return Optional.of(addSetManageWorkHour);
		}
		return Optional.empty();
	}


	/**
	 * Convert to db type irregular work.
	 *
	 * @param irregularWork the irregular work
	 * @return the kshst work dep labor set
	 */
	private KshstWorkDepLaborSet convertToDbTypeIrregularWork(WorkDeformedLaborAdditionSet irregularWork) {
			KshstWorkDepLaborSetPK kshstWorkDepLaborSetPK = new KshstWorkDepLaborSetPK(irregularWork.getCompanyId());
			KshstWorkDepLaborSet kshstWorkDepLaborSet;
			Optional<KshstWorkDepLaborSet> optKshstWorkDepLaborSet = this.queryProxy().find(kshstWorkDepLaborSetPK,KshstWorkDepLaborSet.class);
			if (optKshstWorkDepLaborSet.isPresent()) {
				kshstWorkDepLaborSet = optKshstWorkDepLaborSet.get();
			} else {
				kshstWorkDepLaborSet = new KshstWorkDepLaborSet();
			}
			
			PremiumHolidayCalcMethod premiumHolidayCalcMethod = irregularWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
			PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
			DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
			IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
			
			WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = irregularWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
			WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
			EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();
			
			kshstWorkDepLaborSet.calcActualOperation1 = premiumHolidayCalcMethod.getCalculateActualOperation().value;
			kshstWorkDepLaborSet.exemptTaxTime1 = advanceSetPre.getCalculateIncludIntervalExemptionTime().value;
			kshstWorkDepLaborSet.incChildNursingCare1 = advanceSetPre.getCalculateIncludCareTime().value;
			kshstWorkDepLaborSet.additionTime1 = includeHolidaysPremiumCalcDetailSet.getAddition().value;
			kshstWorkDepLaborSet.notDeductLateleave1 = deductLeaveEarly.getDeduct().isDeduct() ? 1 : 0;
			kshstWorkDepLaborSet.deformatExcValue = includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value;
			kshstWorkDepLaborSet.exemptTaxTime2 = advanceSetWork.getCalculateIncludIntervalExemptionTime().value;
			kshstWorkDepLaborSet.minusAbsenceTime2 = advanceSetWork.getMinusAbsenceTime().get().value;
			kshstWorkDepLaborSet.calcActualOperation2 = workTimeHolidayCalcMethod.getCalculateActualOperation().value;
			kshstWorkDepLaborSet.incChildNursingCare2 = advanceSetWork.getCalculateIncludCareTime().value;
			kshstWorkDepLaborSet.notDeductLateleave2 = advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() ? 1 : 0;
			kshstWorkDepLaborSet.additionTime2 = includeVacationSet.getAddition().value;
			kshstWorkDepLaborSet.enableSetPerWorkHour1 = deductLeaveEarly.isEnableSetPerWorkHour();
			kshstWorkDepLaborSet.enableSetPerWorkHour2 = kshstWorkDepLaborSet.enableSetPerWorkHour1;
			kshstWorkDepLaborSet.deductByApplication = deductLeaveEarly.getDeduct().isDeductByApp();
			kshstWorkDepLaborSet.kshstWorkDepLaborSetPK = kshstWorkDepLaborSetPK;
			kshstWorkDepLaborSet.setPreCalcMethod = BooleanUtils.toBoolean(irregularWork.getVacationCalcMethodSet().getUseAtr().value);

		return kshstWorkDepLaborSet;
	}
	
	/**
	 * Convert to db type hour pay aadd set.
	 *
	 * @param hourlyPaymentAdditionSet the hourly payment addition set
	 * @return the kshst hour pay aadd set
	 */
	private KshstHourPayAaddSet convertToDbTypeHourPayAaddSet(HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {
		KshstHourPayAaddSetPK kshstHourPayAaddSetPK = new KshstHourPayAaddSetPK(hourlyPaymentAdditionSet.getCompanyId());
		KshstHourPayAaddSet kshstHourPayAaddSet;
		Optional<KshstHourPayAaddSet> optKshstHourPayAaddSet = this.queryProxy().find(kshstHourPayAaddSetPK,KshstHourPayAaddSet.class);
		if (optKshstHourPayAaddSet.isPresent()) {
			kshstHourPayAaddSet = optKshstHourPayAaddSet.get();
		} else {
			kshstHourPayAaddSet = new KshstHourPayAaddSet();
		}
		
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = hourlyPaymentAdditionSet.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
		PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
		DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
		
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = hourlyPaymentAdditionSet.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();
		
		kshstHourPayAaddSet.calcPremiumVacation = premiumHolidayCalcMethod.getCalculateActualOperation().value;
		kshstHourPayAaddSet.addition1 = includeHolidaysPremiumCalcDetailSet.getAddition().value;
		kshstHourPayAaddSet.deformatExcValue = includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value;
		kshstHourPayAaddSet.incChildNursingCare = advanceSetPre.getCalculateIncludCareTime().value;
		kshstHourPayAaddSet.deduct = deductLeaveEarly.getDeduct().isDeduct();
		kshstHourPayAaddSet.calculateIncludeIntervalExemptionTime1 = advanceSetPre.getCalculateIncludIntervalExemptionTime().value;
		kshstHourPayAaddSet.calcWorkHourVacation = workTimeHolidayCalcMethod.getCalculateActualOperation().value;
		kshstHourPayAaddSet.addition2 = includeVacationSet.getAddition().value;
		kshstHourPayAaddSet.calculateIncludCareTime = advanceSetWork.getCalculateIncludCareTime().value;
		kshstHourPayAaddSet.notDeductLateLeaveEarly = advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() ? 1 : 0;
		kshstHourPayAaddSet.calculateIncludeIntervalExemptionTime2 = advanceSetWork.getCalculateIncludIntervalExemptionTime().value;
		kshstHourPayAaddSet.enableSetPerWorkHour1 = deductLeaveEarly.isEnableSetPerWorkHour();
		kshstHourPayAaddSet.enableSetPerWorkHour2 = kshstHourPayAaddSet.enableSetPerWorkHour1;
		kshstHourPayAaddSet.kshstHourPayAaddSetPK = kshstHourPayAaddSetPK;
		kshstHourPayAaddSet.deductByApplication = deductLeaveEarly.getDeduct().isDeductByApp();
		kshstHourPayAaddSet.setPreCalcMethod = BooleanUtils.toBoolean(hourlyPaymentAdditionSet.getVacationCalcMethodSet().getUseAtr().value);

		return kshstHourPayAaddSet;
}

	/**
	 * Convert to db type flex work.
	 *
	 * @param flexWork the flex work
	 * @return the kshst work flex set
	 */
	private KshmtCalcCAddHdFle convertToDbTypeFlexWork(WorkFlexAdditionSet flexWork) {
		KshstWorkFlexSetPK kshstFlexWorkSetPK = new KshstWorkFlexSetPK(flexWork.getCompanyId());
		KshmtCalcCAddHdFle kshstFlexWorkSet;
		Optional<KshmtCalcCAddHdFle> optKshstWorkFlexSet = this.queryProxy().find(kshstFlexWorkSetPK,KshmtCalcCAddHdFle.class);
		if (optKshstWorkFlexSet.isPresent()) {
			kshstFlexWorkSet = optKshstWorkFlexSet.get();
		} else {
			kshstFlexWorkSet = new KshmtCalcCAddHdFle();
		}
			
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = flexWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
		PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
		DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
		
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = flexWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();
		
		kshstFlexWorkSet.calcActualOperation1 = premiumHolidayCalcMethod.getCalculateActualOperation().value;
		kshstFlexWorkSet.exemptTaxTime1 = advanceSetPre.getCalculateIncludIntervalExemptionTime().value;
		kshstFlexWorkSet.incChildNursingCare1 = advanceSetPre.getCalculateIncludCareTime().value;
		kshstFlexWorkSet.predeterminedOvertime1 = includeHolidaysPremiumCalcDetailSet.getPredeterminedExcessTimeOfFlex().get().value;
		kshstFlexWorkSet.additionTime1  = includeHolidaysPremiumCalcDetailSet.getAddition().value;
		kshstFlexWorkSet.notDeductLateleave1 = deductLeaveEarly.getDeduct().isDeduct() ? 1 : 0;
		kshstFlexWorkSet.exemptTaxTime2 = advanceSetWork.getCalculateIncludIntervalExemptionTime().value;
		kshstFlexWorkSet.minusAbsenceTime2 = advanceSetWork.getMinusAbsenceTime().get().value;
		kshstFlexWorkSet.calcActualOperation2 = workTimeHolidayCalcMethod.getCalculateActualOperation().value;
		kshstFlexWorkSet.incChildNursingCare2 = advanceSetWork.getCalculateIncludCareTime().value;
		kshstFlexWorkSet.notDeductLateleave2 = advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() ? 1 : 0;
		kshstFlexWorkSet.predeterminDeficiency2 = includeVacationSet.getPredeterminedDeficiencyOfFlex().get().value;
		kshstFlexWorkSet.additionTime2 = includeVacationSet.getAddition().value;
		kshstFlexWorkSet.enableSetPerWorkHour1 = deductLeaveEarly.isEnableSetPerWorkHour();
		kshstFlexWorkSet.enableSetPerWorkHour2 = kshstFlexWorkSet.enableSetPerWorkHour1;
		kshstFlexWorkSet.additionWithinMonthlyStatutory = includeVacationSet.getAdditionWithinMonthlyStatutory().get().value;
		kshstFlexWorkSet.deductByApplication = deductLeaveEarly.getDeduct().isDeductByApp();
		kshstFlexWorkSet.setPreCalcMethod = BooleanUtils.toBoolean(flexWork.getVacationCalcMethodSet().getUseAtr().value);

		kshstFlexWorkSet.kshstFlexWorkSetPK = kshstFlexWorkSetPK;
		return kshstFlexWorkSet;
	}
	
	private KshstAddSetManWKHour convertToDbTypeAddSetManWKHour(AddSetManageWorkHour addSetManageWorkHour) {
		
		KshstAddSetManWKHourPK kshstAddSetManWKHourPK = new KshstAddSetManWKHourPK(addSetManageWorkHour.getCompanyId());
		KshstAddSetManWKHour kshstAddSetManWKHour;
		Optional<KshstAddSetManWKHour> optKshstAddSetManWKHour = this.queryProxy().find(kshstAddSetManWKHourPK, KshstAddSetManWKHour.class);
		if (optKshstAddSetManWKHour.isPresent()) {
			kshstAddSetManWKHour = optKshstAddSetManWKHour.get();
		} else {
			kshstAddSetManWKHour = new KshstAddSetManWKHour(); 
		}
		kshstAddSetManWKHour.addSetOT = addSetManageWorkHour.getAdditionSettingOfOvertime().value;
		kshstAddSetManWKHour.kshstAddSetManWKHourPK = kshstAddSetManWKHourPK;
		
		return kshstAddSetManWKHour;
	}

	@Override
	public Map<String, AggregateRoot> findByCompanyId(String companyId) {
		Optional<KshstHolidayAdditionSet> entity = this.queryProxy()
				.query(SELECT_BY_CID, KshstHolidayAdditionSet.class)
				.setParameter("companyId", companyId)
				.getSingle();
		if (entity.isPresent()) {
			// 通常勤務の加算設定
			Optional<WorkRegularAdditionSet> regularWork = Optional.empty();
			KsrmtCalcCAddIncludePK pkRegular0 = new KsrmtCalcCAddIncludePK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR, 0);
			Optional<KsrmtCalcCAddInclude> entityRegular0 = this.queryProxy().find(pkRegular0, KsrmtCalcCAddInclude.class);
			if (entityRegular0.isPresent()){
				KsrmtCalcCAddIncludePK pkRegular1 = new KsrmtCalcCAddIncludePK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR, 1);
				Optional<KsrmtCalcCAddInclude> entityRegular1 = this.queryProxy().find(pkRegular1, KsrmtCalcCAddInclude.class);
				KsrmtCalcCAddPK pkRegularWrk = new KsrmtCalcCAddPK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddWorktime> entityRegularWrk = this.queryProxy().find(pkRegularWrk, KsrmtCalcCAddWorktime.class);
				KsrmtCalcCAddPK pkRegularPrm = new KsrmtCalcCAddPK(companyId, WorkRegularAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddPremium> entityRegularPrm = this.queryProxy().find(pkRegularPrm, KsrmtCalcCAddPremium.class);
				regularWork = Optional.of(JpaWorkRegularAdditionSetRepository.toDomain(
						entityRegular0.get(), entityRegular1, entityRegularWrk, entityRegularPrm));
			}
			// フレックス勤務の加算設定
			Optional<WorkFlexAdditionSet> flexWork = Optional.empty();
			KsrmtCalcCAddIncludePK pkFlex0 = new KsrmtCalcCAddIncludePK(companyId, WorkFlexAdditionSet.LABOR_SYSTEM_ATR, 0);
			Optional<KsrmtCalcCAddInclude> entityFlex0 = this.queryProxy().find(pkFlex0, KsrmtCalcCAddInclude.class);
			if (entityFlex0.isPresent()){
				KsrmtCalcCAddIncludePK pkFlex1 = new KsrmtCalcCAddIncludePK(companyId, WorkFlexAdditionSet.LABOR_SYSTEM_ATR, 1);
				Optional<KsrmtCalcCAddInclude> entityFlex1 = this.queryProxy().find(pkFlex1, KsrmtCalcCAddInclude.class);
				KsrmtCalcCAddPK pkFlexWrk = new KsrmtCalcCAddPK(companyId, WorkFlexAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddWorktime> entityFlexWrk = this.queryProxy().find(pkFlexWrk, KsrmtCalcCAddWorktime.class);
				KsrmtCalcCAddPK pkFlexPrm = new KsrmtCalcCAddPK(companyId, WorkFlexAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddPremium> entityFlexPrm = this.queryProxy().find(pkFlexPrm, KsrmtCalcCAddPremium.class);
				flexWork = Optional.of(JpaWorkFlexAdditionSetRepository.toDomain(
						entityFlex0.get(), entityFlex1, entityFlexWrk, entityFlexPrm));
			}
			// 変形労働勤務の加算設定
			Optional<WorkDeformedLaborAdditionSet> irregularWork = Optional.empty();
			KsrmtCalcCAddIncludePK pkDefo0 = new KsrmtCalcCAddIncludePK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, 0);
			Optional<KsrmtCalcCAddInclude> entityDefo0 = this.queryProxy().find(pkDefo0, KsrmtCalcCAddInclude.class);
			if (entityDefo0.isPresent()){
				KsrmtCalcCAddIncludePK pkDefo1 = new KsrmtCalcCAddIncludePK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, 1);
				Optional<KsrmtCalcCAddInclude> entityDefo1 = this.queryProxy().find(pkDefo1, KsrmtCalcCAddInclude.class);
				KsrmtCalcCAddPK pkDefoWrk = new KsrmtCalcCAddPK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddWorktime> entityDefoWrk = this.queryProxy().find(pkDefoWrk, KsrmtCalcCAddWorktime.class);
				KsrmtCalcCAddPK pkDefoPrm = new KsrmtCalcCAddPK(companyId, WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddPremium> entityDefoPrm = this.queryProxy().find(pkDefoPrm, KsrmtCalcCAddPremium.class);
				irregularWork = Optional.of(JpaWorkDeformedLaborAdditionSetRepository.toDomain(
						entityDefo0.get(), entityDefo1, entityDefoWrk, entityDefoPrm));
			}
			// 時給者の加算設定
			Optional<HourlyPaymentAdditionSet> hourlyPaymentAdditionSet = Optional.empty();
			KsrmtCalcCAddIncludePK pkHour0 = new KsrmtCalcCAddIncludePK(companyId, HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR, 0);
			Optional<KsrmtCalcCAddInclude> entityHour0 = this.queryProxy().find(pkHour0, KsrmtCalcCAddInclude.class);
			if (entityHour0.isPresent()){
				KsrmtCalcCAddIncludePK pkHour1 = new KsrmtCalcCAddIncludePK(companyId, HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR, 1);
				Optional<KsrmtCalcCAddInclude> entityHour1 = this.queryProxy().find(pkHour1, KsrmtCalcCAddInclude.class);
				KsrmtCalcCAddPK pkHourWrk = new KsrmtCalcCAddPK(companyId, HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddWorktime> entityHourWrk = this.queryProxy().find(pkHourWrk, KsrmtCalcCAddWorktime.class);
				KsrmtCalcCAddPK pkHourPrm = new KsrmtCalcCAddPK(companyId, HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR);
				Optional<KsrmtCalcCAddPremium> entityHourPrm = this.queryProxy().find(pkHourPrm, KsrmtCalcCAddPremium.class);
				hourlyPaymentAdditionSet = Optional.of(JpaHourlyPaymentAdditionSetRepository.toDomain(
						entityHour0.get(), entityHour1, entityHourWrk, entityHourPrm));
			}
			return convertToDomain(entity.get(), regularWork, flexWork, irregularWork, hourlyPaymentAdditionSet);
		}
		return new HashMap<>();
	}
	
	@Override
	public Optional<HolidayAddtionSet> findByCId(String companyId) {
		return this.queryProxy()
				.find(new KshstHolidayAdditionSetPK(companyId), KshstHolidayAdditionSet.class)
				.map(c -> convertToDomainHolidayAdditionSet(c));
	}

	@Override
	public void updateRefForAddTakeLeave(RefDesForAdditionalTakeLeave refDesForAdditionalTakeLeave) {
		String cid = AppContexts.user().companyId();
		Optional<KshstHolidayAdditionSet> holidayAddtionSet = this.queryProxy().find(new KshstHolidayAdditionSetPK(cid),KshstHolidayAdditionSet.class);
		if (holidayAddtionSet.isPresent()) {
			holidayAddtionSet.get().oneDay = new BigDecimal(refDesForAdditionalTakeLeave.getComUniformAdditionTime().getOneDay().v());
			holidayAddtionSet.get().morning = new BigDecimal(refDesForAdditionalTakeLeave.getComUniformAdditionTime().getMorning().v());
			holidayAddtionSet.get().afternoon = new BigDecimal(refDesForAdditionalTakeLeave.getComUniformAdditionTime().getAfternoon().v());
			holidayAddtionSet.get().refAtrCom = refDesForAdditionalTakeLeave.getReferenceSet().value;
			holidayAddtionSet.get().refAtrEmp = refDesForAdditionalTakeLeave.getReferIndividualSet().isPresent() ? refDesForAdditionalTakeLeave.getReferIndividualSet().get().value : null;
			this.commandProxy().update(holidayAddtionSet.get());
		}
	}

	@Override
	public void add(
			HolidayAddtionSet holidayAddtime,
			WorkRegularAdditionSet regularAdditionSet,
			WorkFlexAdditionSet flexAdditionSet,
			WorkDeformedLaborAdditionSet deformedLaborAdditionSet,
			AddSetManageWorkHour addSetManageWorkHour,
			HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {

		KshstHolidayAdditionSetPK kshstHolidayAddtimeSetPK = new KshstHolidayAdditionSetPK(holidayAddtime.getCompanyId());
		Optional<KshstHolidayAdditionSet> optKshstHolidayAdditionSet = this.queryProxy().find(kshstHolidayAddtimeSetPK, KshstHolidayAdditionSet.class);
		KshstHolidayAdditionSet kshstHolidayAddtimeSet;
		if (optKshstHolidayAdditionSet.isPresent()) {
			kshstHolidayAddtimeSet = optKshstHolidayAdditionSet.get();
		} else {
			kshstHolidayAddtimeSet = new KshstHolidayAdditionSet();
			kshstHolidayAddtimeSet.kshstHolidayAddtimeSetPK = kshstHolidayAddtimeSetPK;
			
			KshstAddSetManWKHour kshstAddSetManWKHour = new KshstAddSetManWKHour();
			KshstAddSetManWKHourPK kshstAddSetManWKHourPK = new KshstAddSetManWKHourPK(holidayAddtime.getCompanyId());
			kshstAddSetManWKHour.kshstAddSetManWKHourPK = kshstAddSetManWKHourPK;
			kshstHolidayAddtimeSet.addSetManWKHour = kshstAddSetManWKHour;
		}
		updateEntity(kshstHolidayAddtimeSet, holidayAddtime, addSetManageWorkHour);
		this.commandProxy().insert(kshstHolidayAddtimeSet);
		
		this.registAddSetOfWorkingTime(regularAdditionSet.getCompanyId(),
				WorkRegularAdditionSet.LABOR_SYSTEM_ATR, regularAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(flexAdditionSet.getCompanyId(),
				WorkFlexAdditionSet.LABOR_SYSTEM_ATR, flexAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(deformedLaborAdditionSet.getCompanyId(),
				WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, deformedLaborAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(hourlyPaymentAdditionSet.getCompanyId(),
				HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR, hourlyPaymentAdditionSet.getAddSetOfWorkingTime());
	}

	@Override
	public void update(
			HolidayAddtionSet holidayAddtime,
			WorkRegularAdditionSet regularAdditionSet,
			WorkFlexAdditionSet flexAdditionSet,
			WorkDeformedLaborAdditionSet deformedLaborAdditionSet,
			AddSetManageWorkHour addSetManageWorkHour,
			HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {
		
		KshstHolidayAdditionSetPK primaryKey = new KshstHolidayAdditionSetPK(holidayAddtime.getCompanyId());
		KshstHolidayAdditionSet entity = this.queryProxy().find(primaryKey, KshstHolidayAdditionSet.class).get();
		updateEntity(entity, holidayAddtime, addSetManageWorkHour);
		this.commandProxy().update(entity);
		
		this.registAddSetOfWorkingTime(regularAdditionSet.getCompanyId(),
				WorkRegularAdditionSet.LABOR_SYSTEM_ATR, regularAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(flexAdditionSet.getCompanyId(),
				WorkFlexAdditionSet.LABOR_SYSTEM_ATR, flexAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(deformedLaborAdditionSet.getCompanyId(),
				WorkDeformedLaborAdditionSet.LABOR_SYSTEM_ATR, deformedLaborAdditionSet.getAddSetOfWorkingTime());
		this.registAddSetOfWorkingTime(hourlyPaymentAdditionSet.getCompanyId(),
				HourlyPaymentAdditionSet.LABOR_SYSTEM_ATR, hourlyPaymentAdditionSet.getAddSetOfWorkingTime());
	}
	
	/**
	 * 労働勤務の加算設定を追加・更新する
	 * @param companyId 会社ID
	 * @param laborSystemAtr 労働制
	 * @param domain 労働勤務の加算設定
	 */
	private void registAddSetOfWorkingTime(
			String companyId,
			int laborSystemAtr,
			AddSettingOfWorkingTime domain){

		// insert用エンティティを作成する
		KsrmtCalcCAddInclude includeWorktime = JpaAddSettingOfWorkingTimeRepository.toEntityIncludeWorktime(
				companyId, laborSystemAtr, domain);
		Optional<KsrmtCalcCAddInclude> includePremium = JpaAddSettingOfWorkingTimeRepository.toEntityIncludePremium(
				companyId, laborSystemAtr, domain);
		KsrmtCalcCAddWorktime worktime = JpaAddSettingOfWorkingTimeRepository.toEntityWorktime(companyId, laborSystemAtr, domain);
		KsrmtCalcCAddPremium premium = JpaAddSettingOfWorkingTimeRepository.toEntityPremium(companyId, laborSystemAtr, domain);
		// 労働勤務の加算設定(含める要素を指定)を追加・更新する(就業時間用)
		KsrmtCalcCAddIncludePK keyIncludeWorktime = new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 0);
		Optional<KsrmtCalcCAddInclude> dbIncludeWorktime = this.queryProxy().find(keyIncludeWorktime, KsrmtCalcCAddInclude.class);
		if (dbIncludeWorktime.isPresent()){
			JpaAddSettingOfWorkingTimeRepository.updateEntityIncludeWorktime(dbIncludeWorktime.get(), domain);
			this.commandProxy().update(dbIncludeWorktime.get());
		}
		else{
			this.commandProxy().insert(includeWorktime);
		}
		// 労働勤務の加算設定(含める要素を指定)を追加・更新する(割増時間用)
		KsrmtCalcCAddIncludePK keyIncludePremium = new KsrmtCalcCAddIncludePK(companyId, laborSystemAtr, 1);
		Optional<KsrmtCalcCAddInclude> dbIncludePremium = this.queryProxy().find(keyIncludePremium, KsrmtCalcCAddInclude.class);
		if (dbIncludePremium.isPresent()){
			if (includePremium.isPresent()){
				JpaAddSettingOfWorkingTimeRepository.updateEntityIncludePremium(dbIncludePremium.get(), domain);
				this.commandProxy().update(dbIncludePremium.get());
			}
			else{
				this.commandProxy().remove(dbIncludePremium.get());
			}
		}
		else{
			if (includePremium.isPresent()) this.commandProxy().insert(includePremium.get());
		}
		// 労働勤務の加算設定共通キーの作成
		KsrmtCalcCAddPK key = new KsrmtCalcCAddPK(companyId, laborSystemAtr);
		// 労働勤務の加算設定(就業時間の加算設定)を追加・更新する
		Optional<KsrmtCalcCAddWorktime> dbWorktime = this.queryProxy().find(key, KsrmtCalcCAddWorktime.class);
		if (dbWorktime.isPresent()){
			JpaAddSettingOfWorkingTimeRepository.updateEntityWorktime(dbWorktime.get(), domain);
			this.commandProxy().update(dbWorktime.get());
		}
		else{
			this.commandProxy().insert(worktime);
		}
		// 労働勤務の加算設定(割増時間の加算設定)を追加・更新する
		Optional<KsrmtCalcCAddPremium> dbPremium = this.queryProxy().find(key, KsrmtCalcCAddPremium.class);
		if (dbPremium.isPresent()){
			// 【注意】　割増時間用の設定がある時のみ、更新する
			if (includePremium.isPresent()){
				JpaAddSettingOfWorkingTimeRepository.updateEntityPremium(dbPremium.get(), domain);
				this.commandProxy().update(dbPremium.get());
			}
			else{
				this.commandProxy().remove(dbPremium.get());
			}
		}
		else{
			if (includePremium.isPresent()) this.commandProxy().insert(premium);
		}
	}
	
	private static void updateEntity(
			KshstHolidayAdditionSet entity,
			HolidayAddtionSet holidayAddtime,
			AddSetManageWorkHour addSetManageWorkHour){
		
		entity.oneDay = new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getOneDay().v());
		entity.morning = new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getMorning().v());
		entity.afternoon = new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getAfternoon().v());
		entity.annualHoliday = holidayAddtime.getAdditionVacationSet().getAnnualHoliday().value;
		entity.specialHoliday = holidayAddtime.getAdditionVacationSet().getSpecialHoliday().value;
		entity.yearlyReserved = holidayAddtime.getAdditionVacationSet().getYearlyReserved().value;;
		
		updateEntityAddSetManWKHour(entity.addSetManWKHour, addSetManageWorkHour);
		
		entity.addingMethod1 = holidayAddtime.getTimeHolidayAddition().get(0).getAddingMethod().value;
		entity.workClass1 = holidayAddtime.getTimeHolidayAddition().get(0).getWorkClass().value;
		entity.addingMethod2 = holidayAddtime.getTimeHolidayAddition().get(1).getAddingMethod().value;
		entity.workClass2 = holidayAddtime.getTimeHolidayAddition().get(1).getWorkClass().value;
		entity.refAtrCom = holidayAddtime.getReference().getReferenceSet().value;
		entity.refAtrEmp = holidayAddtime.getReference().getReferIndividualSet().isPresent() ?  holidayAddtime.getReference().getReferIndividualSet().get().value : null;
	}
	
	private static void updateEntityAddSetManWKHour(
			KshstAddSetManWKHour entity,
			AddSetManageWorkHour addSetManageWorkHour){
		
		entity.addSetOT = addSetManageWorkHour.getAdditionSettingOfOvertime().value;
	}
}