/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.app.find.dailyworkschedule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.function.dom.dailyworkschedule.AttendanceItemsDisplay;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.PrintRemarksContent;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemDailyWorkScheduleFinder.
 */
@Stateless
public class OutputItemDailyWorkScheduleFinder {
	
	/** The output item daily work schedule repository. */
	@Inject
	private OutputItemDailyWorkScheduleRepository outputItemDailyWorkScheduleRepository;
	
	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;
	
	@Inject
	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private DailyAttendanceItemRepository dailyAttendanceItemRepository;
	
	// Input of algorithm when use enum ScreenUseAtr: 勤怠項目を利用する画面
	private static final int DAILY_WORK_SCHEDULE = 19;
	
	/**
	 * Find by cid.
	 *
	 * @param code the code
	 * @return the output item daily work schedule dto
	 */
	public OutputItemDailyWorkScheduleDto findByCid() {
		String companyID = AppContexts.user().companyId();
		
		// Start algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する
		// TODO: hoangdd - chua lam xong, xem lai giai thuat
		// Get domain 画面で利用できる勤怠項目一覧
		List<AttendanceType> lstAttendanceType = attendanceTypeRepository.getItemByScreenUseAtr(companyID, DAILY_WORK_SCHEDULE);
		
		// Get domain 任意項目
		List<OptionalItem> lstOptionalItem = optionalItemRepository.findAll(companyID);
		
		// Use condition filter to get 任意項目
		List<OptionalItem> lstOptionalItemFilter = lstAttendanceType.stream()
				.flatMap(domainAttendance -> lstOptionalItem.stream()
								.filter(domainOptionItem -> {
									if (domainOptionItem.getPerformanceAtr().value == PerformanceAtr.DAILY_PERFORMANCE.value
											&& (
													( domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.TIME.value && domainAttendance.getScreenUseAtr().value == ScreenUseAtr.WORK_TIME.value)
													|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.NUMBER.value && domainAttendance.getScreenUseAtr().value == ScreenUseAtr.ATTENDANCE_TIMES.value)
													|| (domainOptionItem.getOptionalItemAtr().value == OptionalItemAtr.AMOUNT.value && domainAttendance.getScreenUseAtr().value == ScreenUseAtr.TOTAL_COMMUTING_AMOUNT.value)
													|| (domainAttendance.getScreenUseAtr().value == ScreenUseAtr.DAILY_WORK_SCHEDULE.value)
												)) {
										return true;
									} 
									return false;
								})
								.map(domainOptionItem -> {
									return domainOptionItem;
								}))
				.collect(Collectors.toList());
		
		/*
		 * 
		 * ドメインモデル「画面で利用できる勤怠項目一覧」に取得した「任意項目」に該当する勤怠項目IDを追加する
		 * (Add attendance ID tương ứng với 「任意項目」 đã lấy vào domain model " 「画面で利用できる勤怠項目一覧」)
		 */
		// TODO: hoangdd - chua lam phan nay
		// End algorithm 画面で利用できる任意項目を含めた勤怠項目一覧を取得する
		
		// Get list attendanceID from domain 画面で利用できる勤怠項目一覧 
		List<Integer> lstAttendanceID = lstAttendanceType.stream()
													.map(domain -> domain.getAttendanceItemId())
													.collect(Collectors.toList());
		// get domain 日次の勤怠項目
		// TODO: hoangdd - chua sort nhu note tren eap
		List<DailyAttendanceItem> lstDailyAttendanceItem = dailyAttendanceItemRepository.getListById(companyID, lstAttendanceID);
		
		// get domain 日別勤務表の出力項目
		// TODO: hoangdd - trong giai thuat dang dung companyID de search, nhung hien tai aggrgate root chi co code, dang doi QA http://192.168.50.4:3000/issues/91487 
		Optional<OutputItemDailyWorkSchedule> optDomain = this.outputItemDailyWorkScheduleRepository.findByCid(companyID);
		
		// if find
		if (optDomain.isPresent()) {
			OutputItemDailyWorkSchedule domain = optDomain.get();
			OutputItemDailyWorkScheduleDto dto = new OutputItemDailyWorkScheduleDto();
			dto.setItemCode(domain.getItemCode().v());
			dto.setItemName(domain.getItemName().v());
			dto.setLstDisplayedAttendance(toDtoTimeitemTobeDisplay(domain.getLstDisplayedAttendance()));
			dto.setLstRemarkContent(toDtoPrintRemarksContent(domain.getLstRemarkContent()));
		}
		
		// find nothing
		return null;
	}
	
	/**
	 * To dto timeitem tobe display.
	 *
	 * @param lstDomainObject the lst domain object
	 * @return the list
	 */
	private List<TimeitemTobeDisplayDto> toDtoTimeitemTobeDisplay(List<AttendanceItemsDisplay> lstDomainObject) {
		List<TimeitemTobeDisplayDto> lstDto = lstDomainObject.stream()
												.map(domain -> {
													TimeitemTobeDisplayDto dto = new TimeitemTobeDisplayDto();
													dto.setAttendanceDisplay(domain.getAttendanceDisplay());
													dto.setOrderNo(domain.getOrderNo());
													return dto;
												}).collect(Collectors.toList());
		return lstDto;
	}
	
	/**
	 * To dto print remarks content.
	 *
	 * @param lstDomainObject the lst domain object
	 * @return the list
	 */
	private List<PrintRemarksContentDto> toDtoPrintRemarksContent(List<PrintRemarksContent> lstDomainObject) {
		List<PrintRemarksContentDto> lstDto = lstDomainObject.stream()
												.map(domain -> {
													PrintRemarksContentDto dto = new PrintRemarksContentDto();
													dto.setPrintitem(domain.getPrintitem().value);
													dto.setUsedClassification(domain.isUsedClassification() == true ? USE : NOT_USE);
													return dto;
												}).collect(Collectors.toList());
		return lstDto;
	} 
	
	/** The Constant USE. */
	private static final int USE = 1;
	
	/** The Constant NOT_USE. */
	private static final int NOT_USE = 0;
}
