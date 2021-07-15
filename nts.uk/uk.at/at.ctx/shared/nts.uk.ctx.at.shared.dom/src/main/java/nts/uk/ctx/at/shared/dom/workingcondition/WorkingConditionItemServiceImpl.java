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
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;

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
	public Optional<SingleDaySchedule> getHolidayWorkSchedule(String companyId, String employeeId,
			GeneralDate baseDate, String workTypeCode) {
		
		Optional<WorkingConditionItem> optWorkingCondItem = this.repositoryWorkingConditionItem
				.getBySidAndStandardDate(employeeId, baseDate);

		// check list working condition item is present
		if (optWorkingCondItem.isPresent()) {
			// get Working Condition Item
			WorkingConditionItem domain = optWorkingCondItem.get();
			// ドメインモデル「個人勤務日区分別勤務」を取得する (Lấy 「個人勤務日区分別勤務」)
			PersonalWorkCategory category = domain.getWorkCategory().getWorkTime();
			Optional<SingleDaySchedule> optpublicHoliday = Optional.empty(); //domain.getWorkCategory().getPublicHolidayWork();

			// check public holiday is present
			if (category != null) {
				// ドメインモデル「休日設定」を取得する (lấy dữ liệu 「休日設定」)
				Optional<WorkTypeSet> workTypeSet = this.workTypeRepository.findByPK(companyId, workTypeCode).get().getWorkTypeSetByAtr(WorkAtr.OneDay);
				if (workTypeSet.isPresent()) {
					// 勤務種類が公休を消化するか判断(check thông tin 公休を消化する của worktype)
					if(workTypeSet.get().getDigestPublicHd().isCheck()) {
						// 公休出勤時の勤務情報が存在するか確認する (Kiểm tra có tồn tại 公休出勤時の勤務情報không)
						if(optpublicHoliday.isPresent())
						// 終了状態：公休出勤時
						return optpublicHoliday;
					}
					// 取得できた場合
					// filter by holiday Setting atr
					switch (workTypeSet.get().getHolidayAtr()) {
					// 法定内休日
					case STATUTORY_HOLIDAYS:
						if(domain.getWorkCategory().getWorkTime().getHolidayWork() != null)
							return Optional.of(domain.getWorkCategory().getWorkTime().getHolidayWork());
					// 法定外休日			
					case NON_STATUTORY_HOLIDAYS:
						if(domain.getWorkCategory().getWorkTime().getHolidayWork() != null)
							return Optional.of(domain.getWorkCategory().getWorkTime().getHolidayWork());
					// 祝日			
					case PUBLIC_HOLIDAY:
						if(domain.getWorkCategory().getWorkTime().getHolidayWork() != null)
							return Optional.of(domain.getWorkCategory().getWorkTime().getHolidayWork());
					}
					
					// 取得できない場合
					// 休日出勤時の勤務情報が存在するか確認する 
					// (【条件】 個人勤務日区分別勤務．休日出勤時)
					if(category.getHolidayWork() != null)
						return Optional.of(domain.getWorkCategory().getWorkTime().getHolidayWork());
				}
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
			PersonalWorkCategory workTime = domain.getWorkCategory().getWorkTime();
			WorkTypeByIndividualWorkDay workType = domain.getWorkCategory().getWorkType();
			
			if (workTime == null || workType == null) {
				return Optional.empty();
			}
			//個人情報の平日出勤時勤務情報を取得する
			//終了状態：平日時出勤情報を返す
			return Optional.of(new WorkInformation(workType.getWeekdayTimeWTypeCode(), workTime.getWeekdayTime().getWorkTimeCode().orElse(null)));
		}
		// 休日出勤時の勤務情報を取得する
		Optional<SingleDaySchedule> data = getHolidayWorkSchedule(companyId, employeeId, baseDate, workTypeCode);

		if (!data.isPresent()) {
			return Optional.empty();
		}

		return data.map(opt -> new WorkInformation(new WorkTypeCode(workTypeCode), opt.getWorkTimeCode().orElse(null)));
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

}
