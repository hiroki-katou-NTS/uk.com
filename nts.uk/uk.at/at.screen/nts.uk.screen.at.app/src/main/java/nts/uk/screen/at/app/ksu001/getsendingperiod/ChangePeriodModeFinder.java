/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getsendingperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DateInformationDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.DisplayControlPersonalCondDto;
import nts.uk.screen.at.app.ksu001.eventinformationandpersonal.PersonalConditionsDto;
import nts.uk.screen.at.app.ksu001.extracttargetemployees.EmployeeInformationDto;
import nts.uk.screen.at.app.ksu001.get28dateperiod.ScreenQuery28DayPeriod;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftMasterDto;
import nts.uk.screen.at.app.ksu001.start.ChangePeriodModeParam;
import nts.uk.screen.at.app.ksu001.start.DataBasicDto;

/**
 * @author laitv 
 *
 */
@Stateless
public class ChangePeriodModeFinder {
	
	@Inject
	private ChangePeriodInShift changePeriodInShift;
	@Inject
	private ChangePeriodInWorkInfomation changePeriodInWorkInfo;
	@Inject
	private ScreenQuery28DayPeriod screenQ28DayPeriod;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	private static final String TIME = "time";
	private static final String SHORTNAME = "shortName";
	private static final String SHIFT = "shift";
	
	public ChangeMonthDto getData(ChangePeriodModeParam param) {
		int modePeriodSelected = Integer.valueOf(param.modePeriodSelected);
		if (modePeriodSelected == ModePeriod.extractionPeriod.value) {
			return getDataInModeExtractPeriod(param);
		} else if (modePeriodSelected == ModePeriod.from1stToLastDay.value) {
			return getDataInModeFrom1stToLastDay(param);
		} else if (modePeriodSelected == ModePeriod.mode28Days.value) {
			return getDataInMode28Days(param);
		}
		return null;
	}
	
	// trong truong hop chon A3_2_2
	public ChangeMonthDto getDataInMode28Days(ChangePeriodModeParam param) {
		//call <<ScreenQuery>> 28日の期間を取得する	(Lấy khoảng thời gian 28 ngày)
		GeneralDate endDate = GeneralDate.fromString(param.endDate, DATE_FORMAT);
		DatePeriodDto datePeriodDto = screenQ28DayPeriod.get(endDate);
		ChangeMonthDto result = new ChangeMonthDto();
		if (param.viewMode.equals(TIME) || param.viewMode.equals(SHORTNAME)) {
			// call <<ScreenQuery>> 表示期間を変更する（勤務情報）
			ChangePeriodInWorkInfoParam_New param1 = new ChangePeriodInWorkInfoParam_New(
					datePeriodDto.getStart(),
					datePeriodDto.getEnd(), 
					param.unit, param.workplaceId, param.workplaceGroupId, param.sids,
					param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null : Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null: Integer.valueOf(param.workplaceSelected),
					new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInWorkInfoResult_New resultOtherMode = changePeriodInWorkInfo.getDataNew(param1);
			result = convertDataTimeShortNameMode(datePeriodDto.getStart(), datePeriodDto.getEnd(), resultOtherMode);
		} else if (param.viewMode.equals(SHIFT)) {
			// call <<ScreenQuery>> 表示期間を変更する（シフト）
			ChangePeriodInShiftParam_New param2 = new ChangePeriodInShiftParam_New(
					datePeriodDto.getStart(), 
					datePeriodDto.getEnd(),
					param.unit, 
					param.workplaceId, 
					param.workplaceGroupId, 
					param.sids,
					param.listShiftMasterNotNeedGetNew, 
					param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null: Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null: Integer.valueOf(param.workplaceSelected),
					new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInShiftResult_New resultShiftMode = changePeriodInShift.getData_New(param2);
			result = convertDataShiftMode(datePeriodDto.getStart(), datePeriodDto.getEnd(), resultShiftMode);
		}
		result.dataBasicDto.startDateNew = datePeriodDto.getStart();
		result.dataBasicDto.endDateNew = datePeriodDto.getEnd();
		return result;
	}

	// trong truong hop chon A3_2_3
	public ChangeMonthDto getDataInModeFrom1stToLastDay(ChangePeriodModeParam param) {
		GeneralDate strDate = GeneralDate.fromString(param.startDate, DATE_FORMAT);
		YearMonth yearMonth = YearMonth.of(strDate.year(), strDate.month());
		DatePeriod datePeriod = DatePeriod.daysFirstToLastIn(yearMonth);
		ChangeMonthDto result = new ChangeMonthDto();
		if (param.viewMode.equals(TIME) || param.viewMode.equals(SHORTNAME)) {
			// call <<ScreenQuery>> 表示期間を変更する（勤務情報）
			ChangePeriodInWorkInfoParam_New param1 = new ChangePeriodInWorkInfoParam_New(
					datePeriod.start(),
					datePeriod.end(), 
					param.unit, param.workplaceId, param.workplaceGroupId, param.sids,
					param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null : Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null: Integer.valueOf(param.workplaceSelected),
					new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInWorkInfoResult_New resultOtherMode = changePeriodInWorkInfo.getDataNew(param1);
			result = convertDataTimeShortNameMode(datePeriod.start(), datePeriod.end(), resultOtherMode);
		} else if (param.viewMode.equals(SHIFT)) {
			// call <<ScreenQuery>> 表示期間を変更する（シフト）
			ChangePeriodInShiftParam_New param2 = new ChangePeriodInShiftParam_New(
					datePeriod.start(), 
					datePeriod.end(),
					param.unit, 
					param.workplaceId, 
					param.workplaceGroupId, 
					param.sids,
					param.listShiftMasterNotNeedGetNew, 
					param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null: Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null: Integer.valueOf(param.workplaceSelected),
					new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInShiftResult_New resultShiftMode = changePeriodInShift.getData_New(param2);
			result = convertDataShiftMode(datePeriod.start(), datePeriod.end(), resultShiftMode);
		}
		result.dataBasicDto.startDateNew = datePeriod.start();
		result.dataBasicDto.endDateNew = datePeriod.end();
		return result;
	}

	// trong truong hop chon A3_2_1
	public ChangeMonthDto getDataInModeExtractPeriod(ChangePeriodModeParam param) {
		GeneralDate startDate = GeneralDate.fromString(param.startDate, DATE_FORMAT);
		GeneralDate	endDate   =	GeneralDate.fromString(param.endDate, DATE_FORMAT);
		ChangeMonthDto result = new ChangeMonthDto();
		if (param.viewMode.equals(TIME) || param.viewMode.equals(SHORTNAME)) {
			// call <<ScreenQuery>> 表示期間を変更する（勤務情報）
			ChangePeriodInWorkInfoParam_New param1 = new ChangePeriodInWorkInfoParam_New(startDate, endDate,
					param.unit, param.workplaceId, param.workplaceGroupId, param.sids, param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null : Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null : Integer.valueOf(param.workplaceSelected), new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInWorkInfoResult_New resultOtherMode = changePeriodInWorkInfo.getDataNew(param1);
			result = convertDataTimeShortNameMode(startDate,endDate, resultOtherMode);

		} else if (param.viewMode.equals(SHIFT)) {
			// call <<ScreenQuery>> 表示期間を変更する（シフト）
			ChangePeriodInShiftParam_New param2 = new ChangePeriodInShiftParam_New(startDate, endDate, param.unit,
					param.workplaceId, param.workplaceGroupId, param.sids, param.listShiftMasterNotNeedGetNew,param.getActualData,
					StringUtil.isNullOrEmpty(param.personTotalSelected, true) ? null : Integer.valueOf(param.personTotalSelected),
					StringUtil.isNullOrEmpty(param.workplaceSelected, true) ? null : Integer.valueOf(param.workplaceSelected), new DateInMonth(param.day, param.isLastDay));
			ChangePeriodInShiftResult_New resultShiftMode = changePeriodInShift.getData_New(param2);;
			result = convertDataShiftMode(startDate,endDate, resultShiftMode);
		}
		result.dataBasicDto.startDateNew = startDate;
		result.dataBasicDto.endDateNew = endDate;
		return result;
	}
	
	private ChangeMonthDto convertDataShiftMode(GeneralDate startDate, GeneralDate endDate,ChangePeriodInShiftResult_New resultShiftMode ) {
		ChangeMonthDto result = new ChangeMonthDto();

		DataBasicDto dataBasicDto = new DataBasicDto();
		dataBasicDto.setStartDate(startDate);
		dataBasicDto.setEndDate(endDate);
		result.setDataBasicDto(dataBasicDto);
		
		List<EmployeeInformationDto> listEmpInfo = resultShiftMode.listEmpInfo.stream().map(item -> {
			return new EmployeeInformationDto(item.getEmployeeId(), item.getEmployeeCode(),
					item.getBusinessName().toString());
		}).collect(Collectors.toList());
		result.setListEmpInfo(listEmpInfo);

		List<DateInformationDto> listDateInfo = resultShiftMode.dataSpecDateAndHolidayDto.listDateInfo.stream().map(i -> {
			return new DateInformationDto(i);
		}).collect(Collectors.toList());
		result.setListDateInfo(listDateInfo);

		List<PersonalConditionsDto> listPersonalConditions = resultShiftMode.dataSpecDateAndHolidayDto.listPersonalConditions.stream().map(i -> {
			return new PersonalConditionsDto(i);
		}).collect(Collectors.toList());
		result.setListPersonalConditions(listPersonalConditions);

		DisplayControlPersonalCondDto displayControlPersonalCond = resultShiftMode.dataSpecDateAndHolidayDto.optDisplayControlPersonalCond.isPresent()
				? new DisplayControlPersonalCondDto(resultShiftMode.dataSpecDateAndHolidayDto.optDisplayControlPersonalCond.get()) : null;
		result.setDisplayControlPersonalCond(displayControlPersonalCond);

		List<ShiftMasterMapWithWorkStyle> shiftMasterWithWorkStyleLst = new ArrayList<>();
		Map<ShiftMasterDto, Integer> mapShiftMasterWithWorkStyle = resultShiftMode.mapShiftMasterWithWorkStyle;
		if(!mapShiftMasterWithWorkStyle.isEmpty()){
			mapShiftMasterWithWorkStyle.forEach((key, value) -> {
				shiftMasterWithWorkStyleLst.add(new ShiftMasterMapWithWorkStyle(key, value == null ? null : String.valueOf(value)));
			});
		}
		result.setShiftMasterWithWorkStyleLst(shiftMasterWithWorkStyleLst);
		result.setListWorkScheduleShift(resultShiftMode.listWorkScheduleShift);
		result.setAggreratePersonal(resultShiftMode.aggreratePersonal);
		result.setAggrerateWorkplace(resultShiftMode.aggrerateWorkplace);
		return result;
	}
	
	private ChangeMonthDto convertDataTimeShortNameMode(GeneralDate startDate, GeneralDate endDate,ChangePeriodInWorkInfoResult_New resultOtherMode ) {
		ChangeMonthDto result = new ChangeMonthDto();

		DataBasicDto dataBasicDto = new DataBasicDto();
		dataBasicDto.setStartDate(startDate);
		dataBasicDto.setEndDate(endDate);
		result.setDataBasicDto(dataBasicDto);
		
		List<EmployeeInformationDto> listEmpInfo = resultOtherMode.listEmpInfo.stream().map(item -> {
			return new EmployeeInformationDto(item.getEmployeeId(), item.getEmployeeCode(),
					item.getBusinessName().toString());
		}).collect(Collectors.toList());
		result.setListEmpInfo(listEmpInfo);

		List<DateInformationDto> listDateInfo = resultOtherMode.dataSpecDateAndHolidayDto.listDateInfo.stream().map(i -> {
			return new DateInformationDto(i);
		}).collect(Collectors.toList());
		result.setListDateInfo(listDateInfo);

		List<PersonalConditionsDto> listPersonalConditions = resultOtherMode.dataSpecDateAndHolidayDto.listPersonalConditions.stream().map(i -> {
			return new PersonalConditionsDto(i);
		}).collect(Collectors.toList());
		result.setListPersonalConditions(listPersonalConditions);

		DisplayControlPersonalCondDto displayControlPersonalCond = resultOtherMode.dataSpecDateAndHolidayDto.optDisplayControlPersonalCond.isPresent()
				? new DisplayControlPersonalCondDto(resultOtherMode.dataSpecDateAndHolidayDto.optDisplayControlPersonalCond.get()) : null;
		result.setDisplayControlPersonalCond(displayControlPersonalCond);

		result.setListWorkScheduleWorkInfor(resultOtherMode.listWorkScheduleWorkInfor);
		
		result.setAggreratePersonal(resultOtherMode.aggreratePersonal);
		result.setAggrerateWorkplace(resultOtherMode.aggrerateWorkplace);
		
		return result;
	}
}
