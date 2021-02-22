/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.calculation.holiday;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * The class Holiday Addtime Finder
 */
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.EmploymentCalcDetailedSetIncludeVacationAmount;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.IncludeHolidaysPremiumCalcDetailSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.PremiumCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.PremiumHolidayCalcMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkTimeHolidayCalcMethod;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HolidayAddtionFinder.
 */
@Stateless
public class HolidayAddtionFinder {
	
	/** The repository. */
	@Inject
	private HolidayAddtionRepository repository;

	/**
	 * Find all holiday addtime.
	 *
	 * @return the list
	 */
	public List<HolidayAddtionDto> findAllHolidayAddtime() {
		String companyId = AppContexts.user().companyId();
//		return repository.findByCompanyId(companyId).stream().map(e -> {
//			return convertToDbType(e);
//		}).collect(Collectors.toList());
		
		Map<String, AggregateRoot> mapAggre = repository.findByCompanyId(companyId);
		List<HolidayAddtionDto> dto = new ArrayList<>();
		dto.add(convertToDbType(mapAggre));
		return dto;
		
	}

	/**
	 * Convert to db type.
	 *
	 * @param mapAggre the map aggre
	 * @return the holiday addtion dto
	 */
	private HolidayAddtionDto convertToDbType(Map<String, AggregateRoot> mapAggre) {
		if (mapAggre.isEmpty() || mapAggre == null) {
			return null;
		}
		HolidayAddtionSet holidayAddtime = (HolidayAddtionSet) mapAggre.get("holidayAddtionSet");
		AddSetManageWorkHour addSetManageWorkHour = (AddSetManageWorkHour) mapAggre.get("addSetManageWorkHour");
		WorkFlexAdditionSet workFlexAdditionSet = (WorkFlexAdditionSet) mapAggre.get("flexWork");
		WorkRegularAdditionSet workRegularAdditionSet = (WorkRegularAdditionSet) mapAggre.get("regularWork");
		WorkDeformedLaborAdditionSet deformedLaborAdditionSet = (WorkDeformedLaborAdditionSet) mapAggre.get("irregularWork");
		HourlyPaymentAdditionSet hourlyPaymentAdditionSet = (HourlyPaymentAdditionSet) mapAggre.get("hourlyPaymentAdditionSet");

		HolidayAddtionDto holidayAddtimeDto = new HolidayAddtionDto();
			holidayAddtimeDto.setOneDay(new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getOneDay().v()));
			holidayAddtimeDto.setMorning(new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getMorning().v()));
			holidayAddtimeDto.setAfternoon(new BigDecimal(holidayAddtime.getReference().getComUniformAdditionTime().getAfternoon().v()));
			holidayAddtimeDto.setAnnualHoliday(holidayAddtime.getAdditionVacationSet().getAnnualHoliday().value);
			holidayAddtimeDto.setSpecialHoliday(holidayAddtime.getAdditionVacationSet().getSpecialHoliday().value);
			holidayAddtimeDto.setYearlyReserved(holidayAddtime.getAdditionVacationSet().getYearlyReserved().value);
			holidayAddtimeDto.setRegularWork(convertToDbTypeRegularWork(workRegularAdditionSet));
			holidayAddtimeDto.setFlexWork(convertToDbTypeFlexWork(workFlexAdditionSet));
			holidayAddtimeDto.setIrregularWork(convertToDbTypeIrregularWork(deformedLaborAdditionSet));
			holidayAddtimeDto.setHourlyPaymentAdditionSet(convertToDbTypeHourlyPaymentAdditionSet(hourlyPaymentAdditionSet));
			
			holidayAddtimeDto.setAddSetManageWorkHour(addSetManageWorkHour == null ? 0 : addSetManageWorkHour.getAdditionSettingOfOvertime().value);
			holidayAddtimeDto.setAddingMethod1(holidayAddtime.getTimeHolidayAddition().get(0).getAddingMethod().value);
			holidayAddtimeDto.setAddingMethod2(holidayAddtime.getTimeHolidayAddition().get(1).getAddingMethod().value);
			holidayAddtimeDto.setWorkClass1(holidayAddtime.getTimeHolidayAddition().get(0).getWorkClass().value);
			holidayAddtimeDto.setWorkClass2(holidayAddtime.getTimeHolidayAddition().get(1).getWorkClass().value);
			holidayAddtimeDto.setRefAtrCom(holidayAddtime.getReference().getReferenceSet().value);
			holidayAddtimeDto.setRefAtrEmp(
					holidayAddtime.getReference().getReferIndividualSet().isPresent() ?
							holidayAddtime.getReference().getReferIndividualSet().get().value : null);
			
		return holidayAddtimeDto;
	}

	/**
	 * Convert to Database Work Regular
	 * 
	 * @param regularWork
	 * @return
	 */
	private RegularWorkDto convertToDbTypeRegularWork(WorkRegularAdditionSet regularWork) {
		if (regularWork == null) {
			return null;
		}

		RegularWorkDto regularWorkDto = new RegularWorkDto();
			// 休暇の割増計算方法
			PremiumHolidayCalcMethod premiumHolidayCalcMethod = regularWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
			PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
			DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
			IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
			// 休暇の就業時間計算方法
			WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = regularWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
			WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
			EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();

			// TODO Hai.tt
			// Fake use Atr
//			regularWorkDto.setUseAtr(regularWork.getVacationCalcMethodSet().getUseAtr().value);
			regularWorkDto.setUseAtr(1);
			regularWorkDto.setCalcActualOperationPre(premiumHolidayCalcMethod.getCalculateActualOperation().value);
			regularWorkDto.setExemptTaxTimePre(advanceSetPre.getCalculateIncludIntervalExemptionTime().value);
			regularWorkDto.setIncChildNursingCarePre(advanceSetPre.getCalculateIncludCareTime().value);
			regularWorkDto.setAdditionTimePre(includeHolidaysPremiumCalcDetailSet.getAddition().value);
			regularWorkDto.setNotDeductLateleavePre(deductLeaveEarly.getDeduct().isDeduct() == true ? 1 : 0);
			regularWorkDto.setDeformatExcValuePre(
					includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().isPresent() ?
					includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value : null
			);
			regularWorkDto.setExemptTaxTimeWork(advanceSetWork.getCalculateIncludIntervalExemptionTime().value);
			regularWorkDto.setCalcActualOperationWork(workTimeHolidayCalcMethod.getCalculateActualOperation().value);
			regularWorkDto.setIncChildNursingCareWork(advanceSetWork.getCalculateIncludCareTime().value);
			regularWorkDto.setNotDeductLateleaveWork(advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() == true ? 1 : 0);
			regularWorkDto.setAdditionTimeWork(includeVacationSet.getAddition().value);
			regularWorkDto.setEnableSetPerWorkHour1(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
			// spec describle enable1 same enable2
			regularWorkDto.setEnableSetPerWorkHour2(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		return regularWorkDto;
	}

	/**
	 * Convert to Database Work Flex
	 * 
	 * @param flexWork
	 * @return
	 */
	private FlexWorkDto convertToDbTypeFlexWork(WorkFlexAdditionSet flexWork) {
		if (flexWork == null) {
			return null;
		}

		FlexWorkDto flexWorkDto = new FlexWorkDto();
		// 休暇の割増計算方法
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = flexWork.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
		PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
		DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
		// 休暇の就業時間計算方法
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = flexWork.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();
		// TODO Hai.tt
		// Fake use Atr
//		flexWorkDto.setUseAtr(flexWork.getVacationCalcMethodSet().getUseAtr().value);
		flexWorkDto.setUseAtr(1);
		flexWorkDto.setCalcActualOperationPre(premiumHolidayCalcMethod.getCalculateActualOperation().value);
		flexWorkDto.setExemptTaxTimePre(advanceSetPre.getCalculateIncludIntervalExemptionTime().value);
		flexWorkDto.setIncChildNursingCarePre(advanceSetPre.getCalculateIncludCareTime().value);
		flexWorkDto.setPredeterminedOvertimePre(includeHolidaysPremiumCalcDetailSet.getPredeterminedExcessTimeOfFlex().get().value);
		flexWorkDto.setAdditionTimePre(includeHolidaysPremiumCalcDetailSet.getAddition().value);
		flexWorkDto.setNotDeductLateleavePre(deductLeaveEarly.getDeduct().isDeduct() ? 1 : 0);
		flexWorkDto.setExemptTaxTimeWork(advanceSetWork.getCalculateIncludIntervalExemptionTime().value);
		flexWorkDto.setMinusAbsenceTimeWork(advanceSetWork.getMinusAbsenceTime().get().value);

		flexWorkDto.setCalcActualOperationWork(workTimeHolidayCalcMethod.getCalculateActualOperation().value);
		flexWorkDto.setIncChildNursingCareWork(advanceSetWork.getCalculateIncludCareTime().value);
		flexWorkDto.setNotDeductLateleaveWork(advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() ? 1 : 0);
		flexWorkDto.setPredeterminDeficiencyWork(includeVacationSet.getPredeterminedDeficiencyOfFlex().get().value);
		flexWorkDto.setAdditionTimeWork(includeVacationSet.getAddition().value);
		flexWorkDto.setEnableSetPerWorkHour1(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		// spec describle enable2 same enable1
		flexWorkDto.setEnableSetPerWorkHour2(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		flexWorkDto.setAdditionWithinMonthlyStatutory(includeVacationSet.getAdditionWithinMonthlyStatutory().get().value);
		return flexWorkDto;
	}

	/**
	 * Convert to Database Work Dep Labor
	 * 
	 * @param labor
	 * @return
	 */
	private WorkDepLaborDto convertToDbTypeIrregularWork(WorkDeformedLaborAdditionSet labor) {
		if (labor == null) {
			return null;
		}

		WorkDepLaborDto laborDto = new WorkDepLaborDto();
			// 休暇の割増計算方法
			PremiumHolidayCalcMethod premiumHolidayCalcMethod = labor.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
			PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
			DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
			IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
			// 休暇の就業時間計算方法
			WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = labor.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
			WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
			EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();

			// TODO Hai.tt
			// Fake use Atr
			laborDto.setUseAtr(1);
			laborDto.setCalcActualOperationPre(premiumHolidayCalcMethod.getCalculateActualOperation().value);
			laborDto.setExemptTaxTimePre(advanceSetPre.getCalculateIncludIntervalExemptionTime().value);
			laborDto.setIncChildNursingCarePre(advanceSetPre.getCalculateIncludCareTime().value);
			laborDto.setAdditionTimePre(includeHolidaysPremiumCalcDetailSet.getAddition().value);
			laborDto.setNotDeductLateleavePre(deductLeaveEarly.getDeduct().isDeduct() == true ? 1 : 0);
			laborDto.setDeformatExcValue(includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value);
			laborDto.setExemptTaxTimeWork(advanceSetWork.getCalculateIncludIntervalExemptionTime().value);
			laborDto.setMinusAbsenceTimeWork(advanceSetWork.getMinusAbsenceTime().get().value);
			laborDto.setCalcActualOperationWork(workTimeHolidayCalcMethod.getCalculateActualOperation().value);
			laborDto.setIncChildNursingCareWork(advanceSetWork.getCalculateIncludCareTime().value);
			laborDto.setNotDeductLateleaveWork(advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() == true ? 1 : 0);
			laborDto.setAdditionTimeWork(includeVacationSet.getAddition().value);
			laborDto.setEnableSetPerWorkHour1(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
			// spec describle enable2 same enable1
			laborDto.setEnableSetPerWorkHour2(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		return laborDto;
	}
	
	private HourlyPaymentAdditionSetDto convertToDbTypeHourlyPaymentAdditionSet(HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {
		if (hourlyPaymentAdditionSet == null) {
			return null;
		}
		
		HourlyPaymentAdditionSetDto dto = new HourlyPaymentAdditionSetDto();
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = hourlyPaymentAdditionSet.getVacationCalcMethodSet().getPremiumCalcMethodOfHoliday();
		PremiumCalcMethodDetailOfHoliday advanceSetPre = premiumHolidayCalcMethod.getAdvanceSet().get();
		DeductLeaveEarly deductLeaveEarly = advanceSetPre.getNotDeductLateLeaveEarly();
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = advanceSetPre.getIncludeVacationSet();
		
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = hourlyPaymentAdditionSet.getVacationCalcMethodSet().getWorkTimeCalcMethodOfHoliday();
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = workTimeHolidayCalcMethod.getAdvancedSet().get();
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet = advanceSetWork.getIncludeVacationSet();

		// TODO Hai.tt
		// Fake use Atr
		dto.setUseAtr(1);
		dto.setCalcPremiumVacation(premiumHolidayCalcMethod.getCalculateActualOperation().value);
		dto.setAddition1(includeHolidaysPremiumCalcDetailSet.getAddition().value);
		dto.setDeformatExcValue(includeHolidaysPremiumCalcDetailSet.getDeformationExceedsPredeterminedValue().get().value);
		dto.setIncChildNursingCare(advanceSetPre.getCalculateIncludCareTime().value);
		dto.setDeduct(deductLeaveEarly.getDeduct().isDeduct() == true ? 1 : 0);
		dto.setCalculateIncludeIntervalExemptionTime1(advanceSetPre.getCalculateIncludIntervalExemptionTime().value);

		dto.setCalcWorkHourVacation(workTimeHolidayCalcMethod.getCalculateActualOperation().value);
		dto.setAddition2(includeVacationSet.getAddition().value);
		dto.setCalculateIncludCareTime(advanceSetWork.getCalculateIncludCareTime().value);
		dto.setNotDeductLateLeaveEarly(advanceSetWork.getNotDeductLateLeaveEarly().getDeduct().isDeduct() == true ? 1 : 0);
		dto.setCalculateIncludeIntervalExemptionTime2(advanceSetWork.getCalculateIncludIntervalExemptionTime().value);
		dto.setEnableSetPerWorkHour1(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		// spec describle dto.setEnableSetPerWorkHour1 is same dto.setEnableSetPerWorkHour2 
		dto.setEnableSetPerWorkHour2(deductLeaveEarly.isEnableSetPerWorkHour() == true ? 1 : 0);
		return dto;
	}
}
