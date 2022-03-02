/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * The Class WorkingConditionItemServiceIpm.
 */
@Stateless
public class WorkingConditionItemServiceImpl implements WorkingConditionItemService {

	/** The Constant STATUTORY_HOLIDAYS. */
	/* 法定内休日 */
	public static final int STATUTORY_HOLIDAYS = 0;

	/** The Constant NON_STATUTORY_HOLIDAYS. */
	/* 法定外休日 */
	public static final int NON_STATUTORY_HOLIDAYS = 1;

	/** The Constant PUBLIC_HOLIDAY. */
	/* 祝日 */
	public static final int PUBLIC_HOLIDAY = 2;

	/** The repository working condition item. */
	@Inject
	private WorkingConditionItemRepository repositoryWorkingConditionItem;

	/** The work type repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService#
	 * getHolidayWorkSchedule(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate, java.lang.String)
	 */
	// 休日出勤時の勤務情報を取得する
	@Override
	public Optional<WorkInformation> getHolidayWorkSchedule(String companyId, String employeeId,
			GeneralDate baseDate, String workTypeCode) {
		//ドメインモデル「労働条件項目」を取得する
		Optional<WorkingConditionItem> optWorkingCondItem = this.repositoryWorkingConditionItem
				.getBySidAndStandardDate(employeeId, baseDate);

		// check list working condition item is present
		if (optWorkingCondItem.isPresent()) {
			
			//ドメインモデル「勤務種類」を取得する 
			Optional<WorkType> workType =  this.workTypeRepository.findByPK(companyId, workTypeCode);
			if(workType.isPresent() && workType.get().getWorkTypeSetByAtr(WorkAtr.OneDay).isPresent()) {
				//TODO:休日出勤時の勤務情報を取得する (TKT) 
				WorkInformation wi = optWorkingCondItem.get().getWorkCategory().getWorkinfoOnVacation(workType.get());
				return Optional.of(wi);
			}
		}
		// 終了状態：勤務情報なし
		return Optional.empty();
	}
	@Override
	public Optional<WorkInformation> getHolidayWorkScheduleNew(String companyId, String employeeId, GeneralDate baseDate,
			String workTypeCode, WorkingDayCategory workingDayCategory) {
		if (workingDayCategory == WorkingDayCategory.workingDay) {
			Optional<WorkingConditionItem> optWorkingCondItem = this.repositoryWorkingConditionItem
					.getBySidAndStandardDate(employeeId, baseDate);

			if (!optWorkingCondItem.isPresent()) {
				return Optional.empty();
			}
			
			// get Working Condition Item
			WorkingConditionItem domain = optWorkingCondItem.get();
			// ドメインモデル「個人勤務日区分別勤務」を取得する (Lấy 「個人勤務日区分別勤務」)
			Optional<WorkTimeCode> workTime = domain.getWorkCategory().getWorkTime().getHolidayWork().getWorkTimeCode();
			WorkTypeByIndividualWorkDay workType = domain.getWorkCategory().getWorkType();
			
			if (!workTime.isPresent() || workType == null) {
				return Optional.empty();
			}
			//個人情報の平日出勤時勤務情報を取得する
			//終了状態：平日時出勤情報を返す
			return Optional.of(new WorkInformation(workType.getWeekdayTimeWTypeCode().v(), workTime.get().v()));
		}
		// 休日出勤時の勤務情報を取得する
		Optional<WorkInformation> data = getHolidayWorkSchedule(companyId, employeeId, baseDate, workTypeCode);

		return data;
	}
	@Override
	public List<WorkingConditionItem> getEmployeesIdListByPeriod(List<String> sIds, DatePeriod datePeriod) {
		List<WorkingCondition> workingConditionList = workingConditionRepository.getBySidsAndDatePeriodNew(sIds, datePeriod);
		List<String> histId = new ArrayList<>();
		workingConditionList.forEach(c->{
			if(!c.dateHistoryItem.isEmpty()) {
				histId.add(c.dateHistoryItem.get(0).identifier());
			}
		});
		if(histId.isEmpty()) return new ArrayList<>();
		
		List<WorkingConditionItem> workingConditionItemList = repositoryWorkingConditionItem.getByListHistoryID(histId);
		
		return workingConditionItemList;
	}
	@Override
	public String getWorkTimeWorkHoliday(String employeeId, GeneralDate ymd) {
		// ドメインモデル「労働条件項目」を取得する
		Optional<WorkingConditionItem> optWorkingConditionItem = this.repositoryWorkingConditionItem
				.getBySidAndStandardDate(employeeId, ymd);
		return optWorkingConditionItem.get().getWorkCategory().getWorkTime().getHolidayWork().getWorkTimeCode().get().v();
	}

}
