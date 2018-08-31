/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.ErrorAlarmCondition;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.SimpleWorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.FrameNoAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class DailyAttendanceItemFinder.
 * アルゴリズム「特定属性の任意項目を取得する」を実行する
 * HieuNV
 */
@Stateless
public class DailyAttendanceItemFinder {

	/** The Daily repo. */
	@Inject
	private DailyAttendanceItemRepository dailyRepo;

	/** The opt item repo. */
    @Inject
    private OptionalItemRepository optItemRepo;
    
    /** The frame adapter. */
    @Inject
    private FrameNoAdapter frameAdapter;
    
    //@Inject
    //private AlarmCheckByCategoryRepository alarmCheckByCategoryRepo;
    
    @Inject
    private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepo;
    
    @Inject
    private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
    
    @Inject
    private WorkTypeRepository workTypeRepo;
    
    @Inject
    private WorkTimeSettingRepository workTimeSettingRepo;
    
    private int convertToOptionalItemAtr(DailyAttendanceAtr dailyAttendanceAtr){
    	switch (dailyAttendanceAtr) {
		case AmountOfMoney:
			return OptionalItemAtr.AMOUNT.value;
		case Time:
			return OptionalItemAtr.TIME.value;
		case NumberOfTime:
			return OptionalItemAtr.NUMBER.value;
		default:
			return 0;
		}
    }
    
	/**
	 * Find Daily Attendance Item by checkItem and eralCheckId.
	 *
	 * @param request the request
	 * @return the list
	 */
	public List<AttdItemDto> findDailyAttendanceItemBy(DailyAttendanceAtr checkItem) {
		// find list optional item by attribute
		List<Integer> filteredOptionItemByAtr = this.optItemRepo
				.findByAtr(AppContexts.user().companyId(), convertToOptionalItemAtr(checkItem)).stream()
				.filter(ii -> ii.isUsed())
				.map(OptionalItem::getOptionalItemNo).map(OptionalItemNo::v).collect(Collectors.toList());
		if (filteredOptionItemByAtr.isEmpty())
			return Collections.emptyList();

		// > ドメインモデル「勤怠項目と枠の紐付け」を取得する
		// return list AttendanceItemLinking after filtered by list optional item.
		int TypeOfAttendanceItemDaily = 1;
		List<Integer> attdItemLinks = this.frameAdapter.getByAnyItem(TypeOfAttendanceItemDaily).stream()
				.filter(item -> filteredOptionItemByAtr.contains(item.getFrameNo()))
				.map(FrameNoAdapterDto::getAttendanceItemId).collect(Collectors.toList());
		if (attdItemLinks.isEmpty())
			return Collections.emptyList();

		// get list attendance item filtered by attdItemLinks
		String companyId = AppContexts.user().companyId();
		List<AttdItemDto> attdItems = this.dailyRepo.getListById(companyId, attdItemLinks).stream()
				.map(dom -> this.toDto(dom)).collect(Collectors.toList());

		return attdItems;
	}

	/**
	 * アルゴリズム「連続勤務の項目取得」を実行する
	 * Execute the algorithm "Item acquisition for continuous work"
	 * @return
	 */
	public List<WorkTypeDto> findDailyAttendanceContinousWorkType() {
	    String companyId = AppContexts.user().companyId();
	    
	    List<WorkTypeDto> lstWorkTypeDtos = workTypeRepo.findNotDeprecated(companyId).stream()
	            .map(dom -> WorkTypeDto.fromDomain(dom))
	            .sorted(Comparator.comparing(WorkTypeDto::getWorkTypeCode))
	            .collect(Collectors.toList());
	    return lstWorkTypeDtos;
	}
	
	/**
	 * アルゴリズム「連続時間帯の項目取得」を実行する
	 * @param eralCheckId
	 * @return
	 */
    public List<SimpleWorkTimeSettingDto> findDailyAttendanceContinuesTimeZoneBy() {
        String companyId = AppContexts.user().companyId();
        List<WorkTimeSetting> WorkTimeSettings = workTimeSettingRepo.findByCompanyId(companyId).stream()
                .filter(item -> !item.isAbolish())
                .collect(Collectors.toList());

        List<SimpleWorkTimeSettingDto> lstSimpleWorkTimeSettingDto = WorkTimeSettings.stream().map(item -> {
            return SimpleWorkTimeSettingDto.builder()
                    .worktimeCode(item.getWorktimeCode().v())
                    .workTimeName(item.getWorkTimeDisplayName().getWorkTimeName().v()).build();
        }).collect(Collectors.toList());

        return lstSimpleWorkTimeSettingDto;
    }

    /**
     * アルゴリズム「複合条件の項目取得」を実行する
     * @param eralCheckId
     * @return
     */
    public List<AttdItemDto> findDailyAttendanceCompoundBy(int eralCheckId) {
        List<AttdItemDto> attdItems = new ArrayList<>();
        /*
        Optional<ErrorAlarmWorkRecord> optErAlCondition = errorAlarmWorkRecordRepo.findByErrorAlamCheckId(String.valueOf(eralCheckId));
        if (optErAlCondition.isPresent()) {
          //ドメインモデル「勤怠項目に対する条件」を取得する - Acquire domain model "Condition for attendance item"
            ErrorAlarmCondition errorAlarmCondition = optErAlCondition.get().getErrorAlarmCondition();
            if (errorAlarmCondition != null) {
                AttendanceItemCondition attendanceItemCondition = errorAlarmCondition.getAtdItemCondition();
                //勤怠項目のエラーアラーム条件NOをもとにドメインモデル「勤怠項目のエラーアラーム条件」を取得する
                // Failure item error Acquire the domain model "ErAlAttendanceItemCondition" based on the alarm condition NO
                //errorAlarmCondition
            }
        }
        */
        return attdItems;
    }
	
	/**
	 * /アルゴリズム「勤怠項目に対応する名称を生成する」を実行する
	 * @param dailyAttendanceItemIds
	 * @return
	 */
	public List<DailyAttendanceItemNameAdapterDto> getDailyAttendanceItemName(List<Integer> dailyAttendanceItemIds) {
	    return dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(dailyAttendanceItemIds);
	}
	/**
	 * Find by atr.
	 *
	 * @param atr the atr
	 * @return the list
	 */
	public List<AttdItemDto> findByAtr(DailyAttendanceAtr atr) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.dailyRepo
				.findByAtr(companyId, atr).stream().map(dom -> this.toDto(dom))
				.collect(Collectors.toList());

		return attendanceItemDtos;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<AttdItemDto> findAll() {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttdItemDto> attendanceItemDtos = this.dailyRepo.getList(companyId).stream().map(dom -> this.toDto(dom))
				.collect(Collectors.toList());

		return attendanceItemDtos;
	}

	/**
	 * getErrorAlarmCategoryAndCondition
	 * @param eralCheckId
	 */
	public ErrorAlarmWorkRecordDto getErrorAlarmCategoryAndCondition(String eralCheckId) {
	    // ドメインモデル「カテゴリ別アラームチェック条件」．抽出条件を元に勤務実績のエラーアラームチェックIDを取得する
        // get AlarmCheckByCategory by ErrorAlarmCheckID
        //AlarmCheckByCategoryRepos.get... TODO
        
        //勤務実績のエラーアラームチェックIDをもとにドメインモデル「勤務実績のエラーアラームチェック」を取得する
        // Acquisition error error Acquire the domain model "ErrorAlarmWorkRecord" on the basis of the alarm check ID
        Optional<ErrorAlarmWorkRecord> optErAlCondition = errorAlarmWorkRecordRepo.findByErrorAlamCheckId(eralCheckId);
        if (optErAlCondition.isPresent()) {
        	ErrorAlarmWorkRecord errorAlarmWorkRecord = optErAlCondition.get();
        	Optional<ErrorAlarmCondition> optOrrorAlarmCondition = errorAlarmWorkRecordRepo.findConditionByErrorAlamCheckId(errorAlarmWorkRecord.getErrorAlarmCheckID());
            return ErrorAlarmWorkRecordDto.fromDomain(optErAlCondition.get(), 
            		optOrrorAlarmCondition.isPresent() ? optOrrorAlarmCondition.get() : null);
        }
        return null;
	}
	
	/**
	 * To dto.
	 *
	 * @param dom the dom
	 * @return the attd item dto
	 */
	private AttdItemDto toDto(DailyAttendanceItem dom) {
		AttdItemDto attdItemDto = new AttdItemDto();
		attdItemDto.setAttendanceItemDisplayNumber(dom.getDisplayNumber());
		attdItemDto.setAttendanceItemId(dom.getAttendanceItemId());
		attdItemDto.setAttendanceItemName(dom.getAttendanceName().v());
		attdItemDto.setDailyAttendanceAtr(dom.getDailyAttendanceAtr().value);
		attdItemDto.setNameLineFeedPosition(dom.getNameLineFeedPosition());
		return attdItemDto;

	}
}
