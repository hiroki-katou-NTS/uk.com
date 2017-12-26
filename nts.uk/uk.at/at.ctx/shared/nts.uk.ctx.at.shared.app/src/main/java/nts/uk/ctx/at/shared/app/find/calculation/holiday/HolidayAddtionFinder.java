package nts.uk.ctx.at.shared.app.find.calculation.holiday;

/**
 * @author phongtq
 * The class Holiday Addtime Finder
 */
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDepLabor;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidayAddtionFinder {
	@Inject
	private HolidayAddtionRepository repository;

	/**
	 * Find all Holiday Additon
	 * 
	 * @return
	 */
	public List<HolidayAddtionDto> findAllHolidayAddtime() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	/**
	 * Convert to Database Holiday Addtion
	 * 
	 * @param holidayAddtime
	 * @return
	 */
	private HolidayAddtionDto convertToDbType(HolidayAddtion holidayAddtime) {
		HolidayAddtionDto holidayAddtimeDto = new HolidayAddtionDto();
			holidayAddtimeDto.setReferComHolidayTime(holidayAddtime.getReferComHolidayTime());
			holidayAddtimeDto.setOneDay(holidayAddtime.getOneDay());
			holidayAddtimeDto.setMorning(holidayAddtime.getMorning());
			holidayAddtimeDto.setAfternoon(holidayAddtime.getAfternoon());
			holidayAddtimeDto.setReferActualWorkHours(holidayAddtime.getReferActualWorkHours());
			holidayAddtimeDto.setNotReferringAch(holidayAddtime.getNotReferringAch().value);
			holidayAddtimeDto.setAnnualHoliday(holidayAddtime.getAnnualHoliday());
			holidayAddtimeDto.setSpecialHoliday(holidayAddtime.getSpecialHoliday());
			holidayAddtimeDto.setYearlyReserved(holidayAddtime.getYearlyReserved());
			holidayAddtimeDto.setRegularWork(convertToDbTypeRegularWork(holidayAddtime.getRegularWork()));
			holidayAddtimeDto.setFlexWork(convertToDbTypeFlexWork(holidayAddtime.getFlexWork()));
			holidayAddtimeDto.setIrregularWork(convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork()));
		return holidayAddtimeDto;
	}

	/**
	 * Convert to Database Work Regular
	 * 
	 * @param regularWork
	 * @return
	 */
	private RegularWorkDto convertToDbTypeRegularWork(RegularWork regularWork) {
		if (regularWork == null) {
			return null;
		}

		RegularWorkDto regularWorkDto = new RegularWorkDto();
			regularWorkDto.setCalcActualOperationPre(regularWork.getCalcActualOperation1().value);
			regularWorkDto.setExemptTaxTimePre(regularWork.getExemptTaxTime1());
			regularWorkDto.setIncChildNursingCarePre(regularWork.getIncChildNursingCare1());
			regularWorkDto.setAdditionTimePre(regularWork.getAdditionTime1());
			regularWorkDto.setNotDeductLateleavePre(regularWork.getNotDeductLateleave1());
			regularWorkDto.setDeformatExcValuePre(regularWork.getDeformatExcValue1().value);
			regularWorkDto.setExemptTaxTimeWork(regularWork.getExemptTaxTime2());
			regularWorkDto.setCalcActualOperationWork(regularWork.getCalcActualOperation2().value);
			regularWorkDto.setIncChildNursingCareWork(regularWork.getIncChildNursingCare2());
			regularWorkDto.setNotDeductLateleaveWork(regularWork.getNotDeductLateleave2());
			regularWorkDto.setAdditionTimeWork(regularWork.getAdditionTime2());
		return regularWorkDto;
	}

	/**
	 * Convert to Database Work Flex
	 * 
	 * @param flexWork
	 * @return
	 */
	private FlexWorkDto convertToDbTypeFlexWork(FlexWork flexWork) {
		if (flexWork == null) {
			return null;
		}

		FlexWorkDto flexWorkDto = new FlexWorkDto();
			flexWorkDto.setCalcActualOperationPre(flexWork.getCalcActualOperation1().value);
			flexWorkDto.setExemptTaxTimePre(flexWork.getExemptTaxTime1());
			flexWorkDto.setIncChildNursingCarePre(flexWork.getIncChildNursingCare1());
			flexWorkDto.setPredeterminedOvertimePre(flexWork.getPredeterminedOvertime1().value);
			flexWorkDto.setAdditionTimePre(flexWork.getAdditionTime1());
			flexWorkDto.setNotDeductLateleavePre(flexWork.getNotDeductLateleave1());
			flexWorkDto.setExemptTaxTimeWork(flexWork.getExemptTaxTime2());
			flexWorkDto.setMinusAbsenceTimeWork(flexWork.getMinusAbsenceTime2());
			flexWorkDto.setCalcActualOperationWork(flexWork.getCalcActualOperation2().value);
			flexWorkDto.setIncChildNursingCareWork(flexWork.getIncChildNursingCare2());
			flexWorkDto.setNotDeductLateleaveWork(flexWork.getNotDeductLateleave2());
			flexWorkDto.setPredeterminDeficiencyWork(flexWork.getPredeterminDeficiency2().value);
			flexWorkDto.setAdditionTimeWork(flexWork.getAdditionTime2());
		return flexWorkDto;
	}

	/**
	 * Convert to Database Work Dep Labor
	 * 
	 * @param labor
	 * @return
	 */
	private WorkDepLaborDto convertToDbTypeIrregularWork(WorkDepLabor labor) {
		if (labor == null) {
			return null;
		}

		WorkDepLaborDto laborDto = new WorkDepLaborDto();
			laborDto.setCalcActualOperationPre(labor.getCalcActualOperation1().value);
			laborDto.setExemptTaxTimePre(labor.getExemptTaxTime1());
			laborDto.setIncChildNursingCarePre(labor.getIncChildNursingCare1());
			laborDto.setAdditionTimePre(labor.getAdditionTime1());
			laborDto.setNotDeductLateleavePre(labor.getNotDeductLateleave1());
			laborDto.setDeformatExcValue(labor.getDeformatExcValue().value);
			laborDto.setExemptTaxTimeWork(labor.getExemptTaxTime2());
			laborDto.setMinusAbsenceTimeWork(labor.getMinusAbsenceTime2());
			laborDto.setCalcActualOperationWork(labor.getCalcActualOperation2().value);
			laborDto.setIncChildNursingCareWork(labor.getIncChildNursingCare2());
			laborDto.setNotDeductLateleaveWork(labor.getNotDeductLateleave2());
			laborDto.setAdditionTimeWork(labor.getAdditionTime2());
		return laborDto;
	}
}
