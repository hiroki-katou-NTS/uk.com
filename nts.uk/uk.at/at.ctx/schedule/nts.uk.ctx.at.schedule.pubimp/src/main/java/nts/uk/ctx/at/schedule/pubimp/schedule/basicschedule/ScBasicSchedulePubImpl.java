/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pubimp.schedule.basicschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScWorkBreakTimeExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScWorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScWorkScheduleExport_New;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScheduleTimeSheetExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ShortWorkingTimeSheetExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.WorkScheduleTimeZoneExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class ScBasicSchedulePubImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScBasicSchedulePubImpl implements ScBasicSchedulePub {

	/** The repository. */
	@Inject
	private BasicScheduleRepository repository;
	
	@Inject
	private WorkScheduleRepository workScheduleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub#
	 * findById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ScBasicScheduleExport> findById(String employeeId, GeneralDate baseDate) {
		return this.repository.find(employeeId, baseDate).map(domain -> this.convertExport(domain));
	}
	
	@Override
	public List<ScBasicScheduleExport> findById(List<String> employeeID, DatePeriod date) {
		return this.repository.findSomePropertyWithJDBC(employeeID, date).stream().map(domain -> this.convertExport(domain)).collect(Collectors.toList());
	}

	@Override
	public List<ScWorkBreakTimeExport> findWorkBreakTime(String employeeId, GeneralDate baseDate) {
		return this.repository.findWorkBreakTime(employeeId, baseDate).stream()
				.map(x -> new ScWorkBreakTimeExport(x.getScheduleBreakCnt().v(), x.getScheduledStartClock(),
						x.getScheduledEndClock()))
				.collect(Collectors.toList());
	}
	

	/**
	 * Convert export.
	 *
	 * @param domain
	 *            the domain
	 * @return the sc basic schedule export
	 */
	private ScBasicScheduleExport convertExport(BasicSchedule domain) {
		ScBasicScheduleExport export = new ScBasicScheduleExport();
		export.setDate(domain.getDate());
		export.setEmployeeId(domain.getEmployeeId());
		export.setWorkScheduleTimeZones(domain.getWorkScheduleTimeZones().stream()
				.map(timezone -> this.convertTimeZoneExport(timezone)).collect(Collectors.toList()));
		export.setWorkTimeCode(domain.getWorkTimeCode());
		export.setWorkTypeCode(domain.getWorkTypeCode());
		return export;
	}

	/**
	 * Convert time zone export.
	 *
	 * @param timezone
	 *            the timezone
	 * @return the work schedule time zone export
	 */
	private WorkScheduleTimeZoneExport convertTimeZoneExport(WorkScheduleTimeZone timezone) {
		WorkScheduleTimeZoneExport export = new WorkScheduleTimeZoneExport();
		export.setBounceAtr(timezone.getBounceAtr().value);
		export.setScheduleCnt(timezone.getScheduleCnt());
		export.setScheduleStartClock(timezone.getScheduleStartClock().valueAsMinutes());
		export.setScheduleEndClock(timezone.getScheduleEndClock().valueAsMinutes());
		return export;
	}

	@Override
	public GeneralDate acquireMaxDateBasicSchedule(List<String> sIds) {
		return this.repository.findMaxDateByListSid(sIds);
	}


	@Override
	public Optional<ScWorkScheduleExport> findByIdNew(String employeeId, GeneralDate baseDate) {
		Optional<WorkSchedule> workSchedule =  workScheduleRepository.get(employeeId, baseDate);
		if(workSchedule.isPresent()){
			return Optional.empty();
		}
		return Optional.of(convertToWorkSchedule(workSchedule.get()));
	}
	
	private ScWorkScheduleExport convertToWorkSchedule(WorkSchedule ws) {
		List<ShortWorkingTimeSheetExport> listShortWorkingTimeSheetExport = new ArrayList<>();
		if (ws.getOptSortTimeWork().isPresent()) {
			listShortWorkingTimeSheetExport = ws.getOptSortTimeWork().get().getShortWorkingTimeSheets().stream()
					.map(c -> convertToShortWorkingTimeSheet(c)).collect(Collectors.toList());
		}
		List<ScheduleTimeSheetExport> listScheduleTimeSheetExport = ws.getWorkInfo().getScheduleTimeSheets().stream()
				.map(c -> convertToScheduleTimeSheet(c)).collect(Collectors.toList());
		return new ScWorkScheduleExport(ws.getEmployeeID(), ws.getYmd(),
				ws.getWorkInfo().getScheduleInfo().getWorkTypeCode().v(),
				ws.getWorkInfo().getScheduleInfo().getWorkTimeCode() == null ? null
						: ws.getWorkInfo().getScheduleInfo().getWorkTimeCode().v(),
				listScheduleTimeSheetExport, listShortWorkingTimeSheetExport);
	}

	private ShortWorkingTimeSheetExport convertToShortWorkingTimeSheet(ShortWorkingTimeSheet swt) {
		return new ShortWorkingTimeSheetExport(swt.getShortWorkTimeFrameNo().v(), swt.getChildCareAttr().value,
				swt.getStartTime().v(), swt.getEndTime().v(), swt.getDeductionTime().v(), swt.getShortTime().v());
	}

	private ScheduleTimeSheetExport convertToScheduleTimeSheet(ScheduleTimeSheet st) {
		return new ScheduleTimeSheetExport(st.getWorkNo().v(), st.getAttendance(), st.getLeaveWork());
	}

	@Override
	public ScWorkScheduleExport_New findByIdNewV2(String employeeId, GeneralDate baseDate) {
		ScWorkScheduleExport_New record = new ScWorkScheduleExport_New();
//		get 勤務予定
		Optional<WorkSchedule> workSchedule =  workScheduleRepository.get(employeeId, baseDate);
		workSchedule.ifPresent(x -> {
			x.getOptSortTimeWork().ifPresent(y -> {
				List<ShortWorkingTimeSheet> shortWorkingTimeSheet = y.getShortWorkingTimeSheets();
				List<ShortWorkingTimeSheetExport> listExport = shortWorkingTimeSheet.stream().map(a ->{
					return new ShortWorkingTimeSheetExport(
							a.getShortWorkTimeFrameNo().v(),
							a.getChildCareAttr().value,
							a.getStartTime().v(),
							a.getEndTime().v(),
							a.getDeductionTime().v(),
							a.getShortTime().v());
				}).collect(Collectors.toList());
				record.setListShortWorkingTimeSheetExport(listExport);							
			});
		});
		
//		get 勤務予定基本情報
		Optional<BasicSchedule> basicSchedule = repository.find(employeeId, baseDate);
		basicSchedule.ifPresent(x -> {
			record.setEmployeeId(x.getEmployeeId());
			record.setDate(x.getDate());
			record.setWorkTypeCode(x.getWorkTypeCode());
			record.setWorkTimeCode(Optional.ofNullable(x.getWorkTimeCode()));
//			勤務予定基本情報.勤務予定時間帯
			List<WorkScheduleTimeZone> workScheduleTimeZones = x.getWorkScheduleTimeZones();
			Optional<WorkScheduleTimeZone> with1 = workScheduleTimeZones.stream().filter(item -> item.getScheduleCnt() == 1).findFirst();
			Optional<WorkScheduleTimeZone> with2 = workScheduleTimeZones.stream().filter(item -> item.getScheduleCnt() == 2).findFirst();
			with1.ifPresent(a -> {
				record.setScheduleStartClock1(a.getScheduleStartClock());
				record.setScheduleEndClock1(a.getScheduleEndClock());
			});
			with2.ifPresent(a -> {
				record.setScheduleStartClock2(Optional.of(a.getScheduleStartClock()));
				record.setScheduleEndClock2(Optional.of(a.getScheduleEndClock()));
			});
			x.getWorkScheduleTime().ifPresent(a -> {
				record.setChildTime(a.getChildTime().v());
			});
			
		});
		return record;
	}
}
