package nts.uk.ctx.at.shared.app.find.worktime_old;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime_old.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktimeset_old.TimeDayAtr;
import nts.uk.ctx.at.shared.dom.worktimeset_old.WorkTimeSetRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class WorktimeFinderNew {

	@Inject
	private WorkTimeRepository workTimeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

	@Inject
	private WorkTimeSetRepository workTimeSetRepository;
	private String[] timeDayAtr = new String[4];
	private String[] workTimeMethodSet = new String[4];
	
	public static final int FIRST_ITEM = 0;
	public static final int TWO_ITEM = 1;
	public static final int TWO_TIMEZONE = 2;
	
	@PostConstruct
	private void loadInitialData() {

		timeDayAtr[0] = TextResource.localize(TimeDayAtr.Enum_DayAtr_PreviousDay.name());
		timeDayAtr[1] = TextResource.localize(TimeDayAtr.Enum_DayAtr_Day.name());
		timeDayAtr[2] = TextResource.localize(TimeDayAtr.Enum_DayAtr_NextDay.name());
		timeDayAtr[3] = TextResource.localize(TimeDayAtr.Enum_DayAtr_SkipDay.name());

		// workTimeMethodSet[0] =
		// internationalization.getItemName(WorkTimeMethodSet.Enum_Fixed_Work.name()).get();
		// workTimeMethodSet[1] =
		// internationalization.getItemName(WorkTimeMethodSet.Enum_Jogging_Time.name()).get();
		workTimeMethodSet[0] = TextResource.localize(WorkTimeMethodSet.Enum_Overtime_Work.name());
		workTimeMethodSet[1] = TextResource.localize(WorkTimeMethodSet.Enum_Overtime_Work.name());
		workTimeMethodSet[2] = TextResource.localize(WorkTimeMethodSet.Enum_Overtime_Work.name());
		workTimeMethodSet[3] = TextResource.localize(WorkTimeMethodSet.Enum_Fluid_Work.name());
	}

	/**
	 * Find by company ID.
	 *
	 * @return the list
	 */
	public List<WorkTimeDto> findByCompanyID() {
		String companyID = AppContexts.user().companyId();
		List<WorkTimeSetting> workTimeItems = this.workTimeSettingRepository.findByCompanyId(companyID);
		List<PredetemineTimeSetting> workTimeSetItems = this.predetemineTimeSettingRepository.findByCompanyID(companyID);
		return getWorkTimeDtos(workTimeItems, workTimeSetItems);
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<WorkTimeDto> findAll() {
		String companyID = AppContexts.user().companyId();
		List<WorkTimeSetting> workTimeItems = this.workTimeSettingRepository.findByCompanyId(companyID);
		List<PredetemineTimeSetting> workTimeSetItems = this.predetemineTimeSettingRepository.findByCompanyID(companyID);
		return getWorkTimeDtos(workTimeItems, workTimeSetItems);
	}

	/**
	 * Find by codes.
	 *
	 * @param codes the codes
	 * @return the list
	 */
	public List<WorkTimeDto> findByCodes(List<String> codes) {
		String companyID = AppContexts.user().companyId();
		if (codes.isEmpty()) {
			return Collections.emptyList();
		} else {
			List<WorkTimeSetting> workTimeItems = this.workTimeSettingRepository.findByCodes(companyID, codes);
			List<PredetemineTimeSetting> workTimeSetItems = this.predetemineTimeSettingRepository.findByCodeList(companyID, codes);
			return getWorkTimeDtos(workTimeItems, workTimeSetItems);
		}
	}

	/**
	 * find list Work Time Dto by code list
	 * 
	 * @param codeList
	 *            code list
	 * @return list Work Time Dto
	 */
	public List<WorkTimeDto> findByCodeList(List<String> codeList) {
		String companyID = AppContexts.user().companyId();
		if (codeList.isEmpty()) {
			return Collections.emptyList();
		} else {
			List<WorkTimeSetting> workTimeItems = this.workTimeSettingRepository.findByCodes(companyID, codeList);
			List<PredetemineTimeSetting> workTimeSetItems = this.predetemineTimeSettingRepository.findByCodeList(companyID, codeList);
			return getWorkTimeDtos(workTimeItems, workTimeSetItems);
		}
	}

	/**
	 * find list Work Time Dto by input time and code list
	 * 
	 * @param codeList
	 *            code list
	 * @param startAtr
	 *            start time option
	 * @param startTime
	 *            start time
	 * @param endAtr
	 *            end time option
	 * @param endTime
	 *            end time
	 * @return list Work Time Dto
	 */
	public List<WorkTimeDto> findByTime(List<String> codeList, Integer startTime, Integer endTime) {
		if (codeList.isEmpty()) {
			return Collections.emptyList();
		} else {
			String companyID = AppContexts.user().companyId();
			List<WorkTimeSetting> workTimeItems = new ArrayList<>();
			List<PredetemineTimeSetting> workTimeSetItems = new ArrayList<>();
			// when both start time and end time is valid
			if ((startTime != null) && (endTime != null)) {
				// compare start time and end time
				if (startTime > endTime)
					throw new BusinessException("Msg_54");
				workTimeItems = this.workTimeSettingRepository.findByCodes(companyID, codeList);
				workTimeSetItems = this.predetemineTimeSettingRepository.findByStartAndEnd(companyID, codeList,startTime,endTime);
				// when only start time is select
			} else if ((startTime != null) && (endTime == null)) {
				workTimeItems = this.workTimeSettingRepository.findByCodes(companyID, codeList);
				workTimeSetItems = this.predetemineTimeSettingRepository.findByStart(companyID, codeList,startTime);
				// when only end time is select
			} else if ((startTime == null) && (endTime != null)) {
				workTimeItems = this.workTimeSettingRepository.findByCodes(companyID, codeList);
				workTimeSetItems = this.predetemineTimeSettingRepository.findByEnd(companyID, codeList,endTime);
				// when both start time and end time is invalid
			} else {
				throw new BusinessException("Msg_53");
			}
			return getWorkTimeDtos(workTimeItems, workTimeSetItems);
		}
	}

	/**
	 * get WorkTimeDto list by WorkTime list and WorkTimeSet list
	 * 
	 * @param workTimeItems
	 *            WorkTime list
	 * @param workTimeSetItems
	 *            WorkTimeSet list
	 * @return WorkTimeDto list
	 */
	private List<WorkTimeDto> getWorkTimeDtos(List<WorkTimeSetting> workTimeItems, List<PredetemineTimeSetting> workTimeSetItems) {
		List<WorkTimeDto> workTimeDtos = new ArrayList<>();
		if (CollectionUtil.isEmpty(workTimeItems) || CollectionUtil.isEmpty(workTimeSetItems)) {
			workTimeDtos = Collections.emptyList();
		} else {
			for (PredetemineTimeSetting item : workTimeSetItems) {
				WorkTimeSetting currentWorkTime = workTimeItems.stream().filter(x -> x.getWorktimeCode().toString().equals(item.getWorkTimeCode().toString())).findAny().get();
				if (item.getPrescribedTimezoneSetting().getLstTimezone().isEmpty()) {
					continue;
				} 
				/*else if (this.checkNotUse(item)) {
					continue;
				}*/ 
				else {
					TimezoneUse timezone1 = item.getPrescribedTimezoneSetting().getLstTimezone().get(FIRST_ITEM);
					TimezoneUse timezone2 = null;
					//if have 2 timezone
					if (item.getPrescribedTimezoneSetting().getLstTimezone().size() >= TWO_TIMEZONE) {
						timezone2 = item.getPrescribedTimezoneSetting().getLstTimezone().get(TWO_ITEM);
					}
					workTimeDtos.add(new WorkTimeDto(currentWorkTime.getWorktimeCode().v(),
							currentWorkTime.getWorkTimeDisplayName().getWorkTimeName().v(),
							(timezone1 != null) ? createWorkTimeField(timezone1.getUseAtr(), timezone1.getStart(),
									timezone1.getEnd()) : null,
							(timezone2 != null) ? createWorkTimeField(timezone2.getUseAtr(), timezone2.getStart(),
									timezone2.getEnd()) : null,
							workTimeMethodSet[currentWorkTime.getWorkTimeDivision().getWorkTimeMethodSet().value],
							currentWorkTime.getNote().v()));
				}
			}
			;
		}
		return workTimeDtos;
	}
	/**
	 * if not use 
	 * @param workTimeSet
	 * @return if not use => True
	 *  if use => Fasle
	 */
	private boolean checkNotUse(PredetemineTimeSetting workTimeSet) {
		for (TimezoneUse timezone : workTimeSet.getPrescribedTimezoneSetting().getLstTimezone()) {
			if (!timezone.getUseAtr().equals(UseSetting.NOT_USE))
				return false;
		}
		return true;
	}
	/**
	 * format to String form input time day
	 * 
	 * @param useAtr
	 *            time day use atr
	 * @param start
	 *            time day start time
	 * @param startAtr
	 *            time day start atr
	 * @param end
	 *            time day end time
	 * @param endAtr
	 *            time day end atr
	 * @return result string
	 * @throws ParseException
	 */
	private String createWorkTimeField(nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting useAtr, TimeWithDayAttr start, TimeWithDayAttr end) {
		if (useAtr.equals(UseSetting.USE)) {
			return start.dayAttr().description+ convert(start.v())+ " ~ " + end.dayAttr().description+ convert(end.v());
		} else
			return null;
	}

	/**
	 * Find by id.
	 *
	 * @param workTimeCode the work time code
	 * @return the work time dto
	 */
	public WorkTimeDto findById(String workTimeCode){
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// call repository find by id
		Optional<WorkTime> opWorkTime = this.workTimeRepository.findByCode(companyId, workTimeCode);

		WorkTimeDto dto = new WorkTimeDto(null, null, null, null, null, null);
		// check exist data
		if(opWorkTime.isPresent()){
			dto.setCode(opWorkTime.get().getSiftCD().v());
			dto.setName(opWorkTime.get().getWorkTimeDisplayName().getWorkTimeName().v());
		}
		return dto;
	}
	private String convert(int minute) {
		String hourminute = "";
		if (minute == 0) {
			hourminute = "00:00";
		} else {
			int hour = Math.abs(minute) / 60;
			int hourInDay = hour % 24;
			int minutes = minute % 60;
			hourminute = (hourInDay) + ":" + (minutes < 10 ? ("0" + minutes) : minutes);
		}
		return hourminute;
	}
}