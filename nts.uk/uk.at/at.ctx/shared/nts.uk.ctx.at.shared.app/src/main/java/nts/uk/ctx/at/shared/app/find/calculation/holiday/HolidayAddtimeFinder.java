package nts.uk.ctx.at.shared.app.find.calculation.holiday;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtimeRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.IrregularWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class HolidayAddtimeFinder {
	@Inject
	private HolidayAddtimeRepository repository;

	public List<HolidayAddtimeDto> findAllHolidayAddtime() {
		String companyId = AppContexts.user().companyId();
		return repository.findByCompanyId(companyId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList());
	}

	private HolidayAddtimeDto convertToDbType(HolidayAddtime holidayAddtime) {
		HolidayAddtimeDto holidayAddtimeDto = new HolidayAddtimeDto();
		holidayAddtimeDto.setReferComHolidayTime(holidayAddtime.getReferComHolidayTime());
		holidayAddtimeDto.setOneDay(holidayAddtime.getOneDay());
		holidayAddtimeDto.setMorning(holidayAddtime.getMorning());
		holidayAddtimeDto.setAfternoon(holidayAddtime.getAfternoon());
		holidayAddtimeDto.setReferActualWorkHours(holidayAddtime.getReferActualWorkHours());
		holidayAddtimeDto.setNotReferringAch(holidayAddtime.getNotReferringAch().value);
		holidayAddtimeDto.setAnnualHoliday(holidayAddtime.getAnnualHoliday());
		holidayAddtimeDto.setYearlyReserved(holidayAddtime.getYearlyReserved());
		holidayAddtimeDto.setRegularWork(convertToDbTypeRegularWork(holidayAddtime.getRegularWork()));
		holidayAddtimeDto.setFlexWork(convertToDbTypeFlexWork(holidayAddtime.getFlexWork()));
		holidayAddtimeDto.setIrregularWork(convertToDbTypeIrregularWork(holidayAddtime.getIrregularWork()));
		return holidayAddtimeDto;
	}
	
	private RegularWorkDto convertToDbTypeRegularWork(RegularWork regularWork) {
		if (regularWork == null) {
			return null;
		}
		
		RegularWorkDto regularWorkDto = new RegularWorkDto();
		regularWorkDto.setCalcActualOperationPre( regularWork.getCalcActualOperationPre());
		regularWorkDto.setCalcIntervalTimePre( regularWork.getCalcIntervalTimePre());
		regularWorkDto.setCalcIncludCarePre( regularWork.getCalcIncludCarePre());
		regularWorkDto.setAdditionTimePre( regularWork.getAdditionTimePre());
		regularWorkDto.setNotDeductLateleavePre( regularWork.getNotDeductLateleavePre());
		regularWorkDto.setDeformatExcValuePre( regularWork.getDeformatExcValuePre());
		regularWorkDto.setCalsIntervalTimeWork( regularWork.getCalsIntervalTimeWork());
		regularWorkDto.setMinusAbsenceTimeWork( regularWork.getMinusAbsenceTimeWork());
		regularWorkDto.setCalcActualOperaWork( regularWork.getCalcActualOperaWork());
		regularWorkDto.setCalcIncludCareWork( regularWork.getCalcIncludCareWork());
		regularWorkDto.setNotDeductLateleaveWork( regularWork.getNotDeductLateleaveWork());
		regularWorkDto.setAdditionTimeWork( regularWork.getAdditionTimeWork());
		return regularWorkDto;
	}
	
	private FlexWorkDto convertToDbTypeFlexWork(FlexWork flexWork) {
		if (flexWork == null) {
			return null;
		}
		
		FlexWorkDto flexWorkDto = new FlexWorkDto();
		flexWorkDto.setHolidayCalcMethodSet( flexWork.getHolidayCalcMethodSet());
		flexWorkDto.setAddWithMonthStatutory(flexWork.getAddWithMonthStatutory());
		flexWorkDto.setCalcActualOperationPre( flexWork.getCalcActualOperationPre());
		flexWorkDto.setCalcIntervalTimePre( flexWork.getCalcIntervalTimePre());
		flexWorkDto.setCalcIncludCarePre( flexWork.getCalcIncludCarePre());
		flexWorkDto.setPredExcessTimeflexPre(flexWork.getPredExcessTimeflexPre());
		flexWorkDto.setAdditionTimePre( flexWork.getAdditionTimePre());
		flexWorkDto.setNotDeductLateleavePre( flexWork.getNotDeductLateleavePre());
		flexWorkDto.setDeformatExcValuePre( flexWork.getDeformatExcValuePre());
		flexWorkDto.setCalsIntervalTimeWork( flexWork.getCalsIntervalTimeWork());
		flexWorkDto.setMinusAbsenceTimeWork( flexWork.getMinusAbsenceTimeWork());
		flexWorkDto.setCalcActualOperaWork( flexWork.getCalcActualOperaWork());
		flexWorkDto.setCalcIncludCareWork( flexWork.getCalcIncludCareWork());
		flexWorkDto.setNotDeductLateleaveWork( flexWork.getNotDeductLateleaveWork());
		flexWorkDto.setAdditionTimeWork( flexWork.getAdditionTimeWork());
		return flexWorkDto;
	}
	
	private IrregularWorkDto convertToDbTypeIrregularWork(IrregularWork irregularWork) {
		if (irregularWork == null) {
			return null;
		}
		
		IrregularWorkDto irregularWorkDto = new IrregularWorkDto();
		irregularWorkDto.setCalcActualOperationPre( irregularWork.getCalcActualOperationPre());
		irregularWorkDto.setCalcIntervalTimePre( irregularWork.getCalcIntervalTimePre());
		irregularWorkDto.setCalcIncludCarePre( irregularWork.getCalcIncludCarePre());
		irregularWorkDto.setAdditionTimePre( irregularWork.getAdditionTimePre());
		irregularWorkDto.setNotDeductLateleavePre( irregularWork.getNotDeductLateleavePre());
		irregularWorkDto.setDeformatExcValuePre( irregularWork.getDeformatExcValuePre());
		irregularWorkDto.setCalsIntervalTimeWork( irregularWork.getCalsIntervalTimeWork());
		irregularWorkDto.setMinusAbsenceTimeWork( irregularWork.getMinusAbsenceTimeWork());
		irregularWorkDto.setCalcActualOperaWork( irregularWork.getCalcActualOperaWork());
		irregularWorkDto.setCalcIncludCareWork( irregularWork.getCalcIncludCareWork());
		irregularWorkDto.setNotDeductLateleaveWork( irregularWork.getNotDeductLateleaveWork());
		irregularWorkDto.setAdditionTimeWork( irregularWork.getAdditionTimeWork());
		return irregularWorkDto;
	}
}
