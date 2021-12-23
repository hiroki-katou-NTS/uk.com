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
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatVacationTimeForCalcWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatVacationTimeForCalcPremium;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TreatDeductTimeForCalcWorkTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfPremiumTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSettingOfWorkTime;
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
			return new RegularWorkDto(AppContexts.user().companyId());
		}
		RegularWorkDto regularWorkDto = new RegularWorkDto();
		regularWorkDto.setUseAtr(regularWork.getAddSetOfWorkingTime().getUseAtr().value);
		// 割増時間の加算設定
		AddSettingOfPremiumTime addSetOfPremium = regularWork.getAddSetOfWorkingTime().getAddSetOfPremium();
		regularWorkDto.setCalcActualOperationPre(addSetOfPremium.getCalculateActualOperation().value);
		if (addSetOfPremium.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfPremium.getTreatDeduct().get();
			regularWorkDto.setEnableSetPerWorkHour1(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			regularWorkDto.setNotDeductLateleavePre(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			regularWorkDto.setIncChildNursingCarePre(treatDeduct.getCalculateIncludCareTime().value);
			regularWorkDto.setExemptTaxTimePre(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfPremium.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = addSetOfPremium.getTreatVacation().get();
			regularWorkDto.setAdditionTimePre(treatVacation.getAddition().value);
			regularWorkDto.setDeformatExcValuePre(
					treatVacation.getDeformationExceedsPredeterminedValue().isPresent() ?
								treatVacation.getDeformationExceedsPredeterminedValue().get().value : null
			);
		}
		// 就業時間の加算設定
		AddSettingOfWorkTime addSetOfWorkTime = regularWork.getAddSetOfWorkingTime().getAddSetOfWorkTime();
		regularWorkDto.setCalcActualOperationWork(addSetOfWorkTime.getCalculateActualOperation().value);
		if (addSetOfWorkTime.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfWorkTime.getTreatDeduct().get();
			// spec describle enable1 same enable2
			regularWorkDto.setEnableSetPerWorkHour2(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			regularWorkDto.setNotDeductLateleaveWork(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			regularWorkDto.setIncChildNursingCareWork(treatDeduct.getCalculateIncludCareTime().value);
			regularWorkDto.setExemptTaxTimeWork(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfWorkTime.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = addSetOfWorkTime.getTreatVacation().get();
			regularWorkDto.setAdditionTimeWork(treatVacation.getAddition().value);
		}
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
		flexWorkDto.setUseAtr(flexWork.getAddSetOfWorkingTime().getUseAtr().value);
		// 割増時間の加算設定
		AddSettingOfPremiumTime addSetOfPremium = flexWork.getAddSetOfWorkingTime().getAddSetOfPremium();
		flexWorkDto.setCalcActualOperationPre(addSetOfPremium.getCalculateActualOperation().value);
		if (addSetOfPremium.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfPremium.getTreatDeduct().get();
			flexWorkDto.setEnableSetPerWorkHour1(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			flexWorkDto.setNotDeductLateleavePre(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? 1 : 0);
			flexWorkDto.setIncChildNursingCarePre(treatDeduct.getCalculateIncludCareTime().value);
			flexWorkDto.setExemptTaxTimePre(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfPremium.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = addSetOfPremium.getTreatVacation().get();
			flexWorkDto.setAdditionTimePre(treatVacation.getAddition().value);
			flexWorkDto.setPredeterminedOvertimePre(treatVacation.getPredeterminedExcessTimeOfFlex().get().value);
		}
		// 就業時間の加算設定
		AddSettingOfWorkTime addSetOfWorkTime = flexWork.getAddSetOfWorkingTime().getAddSetOfWorkTime();
		flexWorkDto.setCalcActualOperationWork(addSetOfWorkTime.getCalculateActualOperation().value);
		if (addSetOfWorkTime.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfWorkTime.getTreatDeduct().get();
			// spec describle enable2 same enable1
			flexWorkDto.setEnableSetPerWorkHour2(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			flexWorkDto.setNotDeductLateleaveWork(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() ? 1 : 0);
			flexWorkDto.setIncChildNursingCareWork(treatDeduct.getCalculateIncludCareTime().value);
			flexWorkDto.setExemptTaxTimeWork(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfWorkTime.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = addSetOfWorkTime.getTreatVacation().get();
			flexWorkDto.setAdditionTimeWork(treatVacation.getAddition().value);
			flexWorkDto.setPredeterminDeficiencyWork(treatVacation.getPredeterminedDeficiencyOfFlex().get().value);
			flexWorkDto.setAdditionWithinMonthlyStatutory(treatVacation.getAdditionWithinMonthlyStatutory().get().value);
			flexWorkDto.setMinusAbsenceTimeWork(treatVacation.getMinusAbsenceTime().get().value);
		}
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
		laborDto.setUseAtr(labor.getAddSetOfWorkingTime().getUseAtr().value);
		// 割増時間の加算設定
		AddSettingOfPremiumTime addSetOfPremium = labor.getAddSetOfWorkingTime().getAddSetOfPremium();
		laborDto.setCalcActualOperationPre(addSetOfPremium.getCalculateActualOperation().value);
		if (addSetOfPremium.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfPremium.getTreatDeduct().get();
			laborDto.setEnableSetPerWorkHour1(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			laborDto.setNotDeductLateleavePre(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			laborDto.setIncChildNursingCarePre(treatDeduct.getCalculateIncludCareTime().value);
			laborDto.setExemptTaxTimePre(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfPremium.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = addSetOfPremium.getTreatVacation().get();
			laborDto.setAdditionTimePre(treatVacation.getAddition().value);
			laborDto.setDeformatExcValue(treatVacation.getDeformationExceedsPredeterminedValue().get().value);
		}
		// 就業時間の加算設定
		AddSettingOfWorkTime addSetOfWorkTime = labor.getAddSetOfWorkingTime().getAddSetOfWorkTime();
		laborDto.setCalcActualOperationWork(addSetOfWorkTime.getCalculateActualOperation().value);
		if (addSetOfWorkTime.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfWorkTime.getTreatDeduct().get();
			// spec describle enable2 same enable1
			laborDto.setEnableSetPerWorkHour2(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			laborDto.setNotDeductLateleaveWork(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			laborDto.setIncChildNursingCareWork(treatDeduct.getCalculateIncludCareTime().value);
			laborDto.setExemptTaxTimeWork(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfWorkTime.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = addSetOfWorkTime.getTreatVacation().get();
			laborDto.setAdditionTimeWork(treatVacation.getAddition().value);
			laborDto.setMinusAbsenceTimeWork(treatVacation.getMinusAbsenceTime().get().value);
		}
		return laborDto;
	}
	
	private HourlyPaymentAdditionSetDto convertToDbTypeHourlyPaymentAdditionSet(HourlyPaymentAdditionSet hourlyPaymentAdditionSet) {
		if (hourlyPaymentAdditionSet == null) {
			return null;
		}
		HourlyPaymentAdditionSetDto dto = new HourlyPaymentAdditionSetDto();
		dto.setUseAtr(hourlyPaymentAdditionSet.getAddSetOfWorkingTime().getUseAtr().value);
		// 割増時間の加算設定
		AddSettingOfPremiumTime addSetOfPremium = hourlyPaymentAdditionSet.getAddSetOfWorkingTime().getAddSetOfPremium();
		dto.setCalcPremiumVacation(addSetOfPremium.getCalculateActualOperation().value);
		if (addSetOfPremium.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfPremium.getTreatDeduct().get();
			dto.setEnableSetPerWorkHour1(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			dto.setDeduct(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			dto.setIncChildNursingCare(treatDeduct.getCalculateIncludCareTime().value);
			dto.setCalculateIncludeIntervalExemptionTime1(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfPremium.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcPremium treatVacation = addSetOfPremium.getTreatVacation().get();
			dto.setAddition1(treatVacation.getAddition().value);
			dto.setDeformatExcValue(treatVacation.getDeformationExceedsPredeterminedValue().get().value);
		}
		// 就業時間の加算設定
		AddSettingOfWorkTime addSetOfWorkTime = hourlyPaymentAdditionSet.getAddSetOfWorkingTime().getAddSetOfWorkTime();
		dto.setCalcWorkHourVacation(addSetOfWorkTime.getCalculateActualOperation().value);
		if (addSetOfWorkTime.getTreatDeduct().isPresent()){
			TreatDeductTimeForCalcWorkTime treatDeduct = addSetOfWorkTime.getTreatDeduct().get();
			// spec describle dto.setEnableSetPerWorkHour1 is same dto.setEnableSetPerWorkHour2 
			dto.setEnableSetPerWorkHour2(treatDeduct.getTreatLateEarlyTimeSet().isEnableSetPerWorkHour() == true ? 1 : 0);
			dto.setNotDeductLateLeaveEarly(treatDeduct.getTreatLateEarlyTimeSet().getTreatSet().isInclude() == true ? 1 : 0);
			dto.setCalculateIncludCareTime(treatDeduct.getCalculateIncludCareTime().value);
			dto.setCalculateIncludeIntervalExemptionTime2(treatDeduct.getCalculateIncludIntervalExemptionTime().value);
		}
		if (addSetOfWorkTime.getTreatVacation().isPresent()){
			TreatVacationTimeForCalcWorkTime treatVacation = addSetOfWorkTime.getTreatVacation().get();
			dto.setAddition2(treatVacation.getAddition().value);
		}
		return dto;
	}
}
