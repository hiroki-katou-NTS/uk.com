/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.WorkMonthlySettingDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;

/**
 * The Class WorkMonthlySettingFinder.
 */
@Stateless
public class WorkMonthlySettingFinder {

	/** The Constant START_DAY. */
	public static final int START_DAY = 1;

	/** The Constant NEXT_DAY. */
	public static final int NEXT_DAY = 1;

	/** The Constant YEAR_MUL. */
	public static final int YEAR_MUL = 10000;

	/** The Constant MONTH_MUL. */
	public static final int MONTH_MUL = 100;

	/** The Constant HOLIDAY. */
	public static final int HOLIDAY = 0;

	/** The Constant ATTENDANCE. */
	public static final int ATTENDANCE = 1;

	/** The Constant NONE_SETTING. */
	public static final String NONE_SETTING = "KSM005_43";

	/** The work monthly setting repository. */
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepository;

	/** The work time repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;

	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	/**
	 * Find by month.
	 *
	 * @param monthlyPatternCode
	 *            the monthly pattern code
	 * @param yearMonth
	 *            the year month
	 * @return the list
	 */

	public List<WorkMonthlySettingDto> findByMonth(String monthlyPatternCode, int yearMonth) {

		// month setting by connection
		YearMonth monthSetting = YearMonth.of(yearMonth);

		// set start month
		GeneralDate startMonth = GeneralDate.ymd(monthSetting.year(), monthSetting.month(),
				START_DAY);

		// set end month
		GeneralDate endMonth = GeneralDate.ymd(monthSetting.nextMonth().year(),
				monthSetting.nextMonth().month(), START_DAY);

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find by month
		List<WorkMonthlySettingDto> workMonthlySettings = this.workMonthlySettingRepository
				.findByStartEndDate(companyId, monthlyPatternCode, startMonth, endMonth).stream()
				.map(domain -> {
					WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
					domain.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());

		// convert to map
		Map<GeneralDate, WorkMonthlySettingDto> mapWorkMonthlySetting = workMonthlySettings.stream()
				.collect(Collectors.toMap((settings) -> {
					return settings.getYmdk();
				}, Function.identity()));

		List<WorkMonthlySettingDto> resDataWorkMonthlySetting = new ArrayList<>();

		// list work time of company id
		List<WorkTimeSetting> workTimes = workTimeRepository.findByCompanyId(companyId);

		// convert to map work time map
		Map<String, WorkTimeSetting> mapWorkTime = workTimes.stream()
				.collect(Collectors.toMap((worktime) -> {
					return worktime.getWorktimeCode().v();
				}, Function.identity()));

		// list work type of company id
		List<WorkType> workTypes = workTypeRepository.findByCompanyId(companyId);

		// convert to map work type map
		Map<String, WorkType> mapWorkType = workTypes.stream()
				.collect(Collectors.toMap((worktype) -> {
					return worktype.getWorkTypeCode().v();
				}, Function.identity()));

		// loop in month setting
		while (startMonth.yearMonth().v() == yearMonth) {

			// check exist data
			if (mapWorkMonthlySetting.containsKey(startMonth)) {

				// get data object by year month day of start month
				WorkMonthlySettingDto dto = mapWorkMonthlySetting.get(startMonth);

				// check type color
				if (mapWorkTime.containsKey(dto.getWorkingCode())
						&& mapWorkType.containsKey(dto.getWorkTypeCode())) {

					// set type color ATTENDANCE
					dto.setTypeColor(ATTENDANCE);

					// set work type name
					dto.setWorkTypeName(mapWorkType.get(dto.getWorkTypeCode()).getName().v());

					// set work time name
					dto.setWorkingName(mapWorkTime.get(dto.getWorkingCode())
							.getWorkTimeDisplayName().getWorkTimeName().v());
				} else {

					// set type color HOLIDAY
					dto.setTypeColor(HOLIDAY);

					// check exist work type of map
					if (mapWorkType.containsKey(dto.getWorkTypeCode())) {

						// set work type name
						dto.setWorkTypeName(mapWorkType.get(dto.getWorkTypeCode()).getName().v());
					} else {
						// set work type name NONE_SETTING
						dto.setWorkTypeName(TextResource.localize(NONE_SETTING));
					}

					// set work time name ""
					dto.setWorkingName("");
				}
				resDataWorkMonthlySetting.add(dto);

			} else {
				// data default
				WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
				WorkMonthlySetting domain = new WorkMonthlySetting(startMonth, monthlyPatternCode);
				domain.saveToMemento(dto);

				// set type color HOLIDAY
				dto.setTypeColor(HOLIDAY);

				// set work type name ""
				dto.setWorkTypeName("");

				// set work time name ""
				dto.setWorkingName("");

				resDataWorkMonthlySetting.add(dto);
			}
			startMonth = startMonth.addDays(NEXT_DAY);
		}
		return resDataWorkMonthlySetting;
	}

	/**
	 * Gets the default item of month.
	 *
	 * @param yearMonth the year month
	 * @return the default item of month
	 */
	public List<WorkMonthlySettingDto> getDefaultItemOfMonth(int yearMonth) {

		// month setting by connection
		YearMonth monthSetting = YearMonth.of(yearMonth);

		// set start month
		GeneralDate startMonth = GeneralDate.ymd(monthSetting.year(), monthSetting.month(),
				START_DAY);

		List<WorkMonthlySettingDto> resDataWorkMonthlySetting = new ArrayList<>();

		// loop in month setting
		while (startMonth.yearMonth().v() == yearMonth) {

			// get data object by year month day of start month
			WorkMonthlySettingDto dto = new WorkMonthlySettingDto();
			
			dto.setYmdK(startMonth);
			
			resDataWorkMonthlySetting.add(dto);
			
			startMonth = startMonth.addDays(NEXT_DAY);
		}
		
		return resDataWorkMonthlySetting;
	}

}
