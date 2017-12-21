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
			regularWorkDto.setCalcActualOperation1(regularWork.getCalcActualOperation1());
			regularWorkDto.setExemptTaxTime1(regularWork.getExemptTaxTime1());
			regularWorkDto.setIncChildNursingCare1(regularWork.getIncChildNursingCare1());
			regularWorkDto.setAdditionTime1(regularWork.getAdditionTime1());
			regularWorkDto.setNotDeductLateleave1(regularWork.getNotDeductLateleave1());
			regularWorkDto.setDeformatExcValue1(regularWork.getDeformatExcValue1());
			regularWorkDto.setExemptTaxTime2(regularWork.getExemptTaxTime2());
			regularWorkDto.setCalcActualOperation2(regularWork.getCalcActualOperation2());
			regularWorkDto.setIncChildNursingCare2(regularWork.getIncChildNursingCare2());
			regularWorkDto.setNotDeductLateleave2(regularWork.getNotDeductLateleave2());
			regularWorkDto.setAdditionTime2(regularWork.getAdditionTime2());
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
			flexWorkDto.setCalcActualOperation1(flexWork.getCalcActualOperation1());
			flexWorkDto.setExemptTaxTime1(flexWork.getExemptTaxTime1());
			flexWorkDto.setIncChildNursingCare1(flexWork.getIncChildNursingCare1());
			flexWorkDto.setPredeterminedOvertime1(flexWork.getPredeterminedOvertime1());
			flexWorkDto.setAdditionTime1(flexWork.getAdditionTime1());
			flexWorkDto.setNotDeductLateleave1(flexWork.getNotDeductLateleave1());
			flexWorkDto.setExemptTaxTime2(flexWork.getExemptTaxTime2());
			flexWorkDto.setMinusAbsenceTime2(flexWork.getMinusAbsenceTime2());
			flexWorkDto.setCalcActualOperation2(flexWork.getCalcActualOperation2());
			flexWorkDto.setIncChildNursingCare2(flexWork.getIncChildNursingCare2());
			flexWorkDto.setNotDeductLateleave2(flexWork.getNotDeductLateleave2());
			flexWorkDto.setPredeterminDeficiency2(flexWork.getAdditionTime2());
			flexWorkDto.setAdditionTime2(flexWork.getAdditionTime2());
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
			laborDto.setCalcActualOperation1(labor.getCalcActualOperation1());
			laborDto.setExemptTaxTime1(labor.getExemptTaxTime1());
			laborDto.setIncChildNursingCare1(labor.getIncChildNursingCare1());
			laborDto.setPredeterminedOvertime1(labor.getPredeterminedOvertime1());
			laborDto.setAdditionTime1(labor.getAdditionTime1());
			laborDto.setNotDeductLateleave1(labor.getNotDeductLateleave1());
			laborDto.setExemptTaxTime2(labor.getExemptTaxTime2());
			laborDto.setMinusAbsenceTime2(labor.getMinusAbsenceTime2());
			laborDto.setCalcActualOperation2(labor.getCalcActualOperation2());
			laborDto.setIncChildNursingCare2(labor.getIncChildNursingCare2());
			laborDto.setNotDeductLateleave2(labor.getNotDeductLateleave2());
			laborDto.setPredeterminDeficiency2(labor.getPredeterminDeficiency2());
			laborDto.setAdditionTime2(labor.getAdditionTime2());
		return laborDto;
	}
}
