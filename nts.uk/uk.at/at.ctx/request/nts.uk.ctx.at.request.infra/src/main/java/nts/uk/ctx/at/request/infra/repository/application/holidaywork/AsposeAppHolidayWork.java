package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.shr.com.enumcommon.DayAttr;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 *Refactor5
 * @author huylq
 *
 */
@Stateless
public class AsposeAppHolidayWork {

	public int printAppHolidayWorkContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		Stack<Integer> deleteRows = new Stack<>();
		Optional<PrintContentOfHolidayWork> opPrintContentOfHolidayWork = printContentOfApp.getOpPrintContentOfHolidayWork();
		if (!opPrintContentOfHolidayWork.isPresent()) return 0 ;
		PrintContentOfHolidayWork printContentOfHolidayWork = opPrintContentOfHolidayWork.get();
//		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput;
//		AppHolidayWork appHolidayWork;
		
		//	「複数回勤務の管理．複数回勤務の管理」がtrue
		Boolean c1 = printContentOfApp.isManagementMultipleWorkCycles();
		
		//	「休日出勤申請の印刷内容．休憩・外出を申請反映する」．休憩を反映するが反映する
		Boolean c2 = printContentOfHolidayWork.getBreakReflect().equals(NotUseAtr.USE);
		
		//	「休日出勤申請の印刷内容」．時間外深夜時間を反映するが「反映する」
		Boolean c3 = printContentOfHolidayWork.getOvertimeMidnightUseAtr().equals(NotUseAtr.USE);
		
		//	「休日出勤申請の印刷内容」．申請時間．申請時間．申請時間（Type＝残業時間）に項目がある
		Boolean c4 = !printContentOfHolidayWork.getApplicationTime().getApplicationTime().stream()
							.filter(appTime -> appTime.getAttendanceType().equals(AttendanceType_Update.NORMALOVERTIME))
							.collect(Collectors.toList())
							.isEmpty();
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND (休日出勤申請の印刷内容．乖離理由の選択肢を利用する」がtrue OR 休日出勤申請の印刷内容．乖離理由の入力を利用する」がtrue)
		Boolean c5 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& (printContentOfHolidayWork.isUseComboDivergenceReason() || printContentOfHolidayWork.isUseInputDivergenceReason());
		
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND 休日出勤申請の印刷内容．乖離理由の選択肢を利用する」がtrue
		Boolean c6 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& printContentOfHolidayWork.isUseComboDivergenceReason();
		
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND 休日出勤申請の印刷内容．乖離理由の入力を利用する」がtrue
		Boolean c7 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& printContentOfHolidayWork.isUseInputDivergenceReason();
		
		Cells cells = worksheet.getCells();
		Cell cellB8 = cells.get("B8");
		Cell cellB9 = cells.get("B9");
		Cell cellB10 = cells.get("B10");
		Cell cellB11 = cells.get("B11");
		Cell cellB12 = cells.get("B12");
		
		
		Cell cellD8 = cells.get("D8");
		Cell cellD9 = cells.get("D9");
		Cell cellD10 = cells.get("D10");
		Cell cellD11 = cells.get("D11");
		Cell cellD12 = cells.get("D12");
		
		cellB8.setValue(I18NText.getText("KAF010_34"));
		cellB9.setValue(I18NText.getText("KAF010_35"));
		cellB10.setValue(I18NText.getText("KAF010_37"));
		if(c1) {
			cellB11.setValue(I18NText.getText("KAF010_339"));
		}
		if(c2) {
			cellB12.setValue(I18NText.getText("KAF005_40"));
		}
		
		String workTypeName = printContentOfHolidayWork.getWorkTypeName().orElse(new WorkTypeName(printContentOfHolidayWork.getWorkTypeCode() + " " + I18NText.getText("KAF005_345"))).v();
		String workTimeName = printContentOfHolidayWork.getWorkTimeName().orElse(new WorkTimeName(printContentOfHolidayWork.getWorkTimeCode() + " " + I18NText.getText("KAF005_345"))).v();
		cellD8.setValue(workTypeName);
		cellD9.setValue(workTimeName);
		
		List<TimeZoneWithWorkNo> workingTimeList = printContentOfHolidayWork.getWorkingTimeList().orElse(Collections.emptyList());
		workingTimeList.stream().forEach(workingTime -> {
			String timeZone = "";
			if(workingTime.getWorkNo().v() == 1) {
				timeZone = formatTimeWithDayAttr(workingTime.getTimeZone().getStartTime()) + " " 
							+ I18NText.getText("KAF010_38") + " " + formatTimeWithDayAttr(workingTime.getTimeZone().getEndTime());
				cellD10.setValue(timeZone);
			}
			if(c1 && workingTime.getWorkNo().v() == 2) {
				timeZone = formatTimeWithDayAttr(workingTime.getTimeZone().getStartTime()) + " " 
						+ I18NText.getText("KAF010_38") + " " + formatTimeWithDayAttr(workingTime.getTimeZone().getEndTime());
				cellD11.setValue(timeZone);
			}
		});
		if(c2) {
			List<TimeZoneWithWorkNo> breakTimeList = printContentOfHolidayWork.getBreakTimeList().orElse(Collections.emptyList());
			if(!breakTimeList.isEmpty()) {
				Collections.sort(breakTimeList, (breakTime1, breakTime2) -> breakTime1.getWorkNo().v().compareTo(breakTime2.getWorkNo().v()));
				List<String> breakTimeZoneList = new ArrayList<String>();
				breakTimeList.stream().forEach(breakTime -> {
					String breakTimeZone = formatTimeWithDayAttr(breakTime.getTimeZone().getStartTime()) + " " 
							+ I18NText.getText("KAF010_38") + " " + formatTimeWithDayAttr(breakTime.getTimeZone().getEndTime());
					breakTimeZoneList.add(breakTimeZone);
				});
				String breakTimeString = breakTimeZoneList.stream().collect(Collectors.joining("、"));
				cellD12.setValue(breakTimeString);
			}
		}
		
		Cell cellB13 = cells.get("B13");
		cellB13.setValue(I18NText.getText("KAF010_50"));
		
		List<WorkdayoffFrame> workdayoffFrameList = printContentOfHolidayWork.getWorkdayoffFrameList();
		Collections.sort(workdayoffFrameList, 
				(workdayoffFrame1, workdayoffFrame2) -> workdayoffFrame1.getWorkdayoffFrNo().v().compareTo(workdayoffFrame2.getWorkdayoffFrNo().v()));
		List<OvertimeApplicationSetting> workdayoffApplicationTimeList = printContentOfHolidayWork.getApplicationTime().getApplicationTime();
		workdayoffApplicationTimeList = workdayoffApplicationTimeList.stream()
				.filter(applicationTime -> applicationTime.getAttendanceType().equals(AttendanceType_Update.BREAKTIME)).collect(Collectors.toList());
		final List<OvertimeApplicationSetting> workdayoffApplicationTimeListFinal = workdayoffApplicationTimeList;
		AtomicInteger workdayoffCountRow = new AtomicInteger(12);
		AtomicInteger workdayoffCountColumn = new AtomicInteger(3);
		workdayoffFrameList.stream().forEach(workdayoffFrame -> {
			Optional<OvertimeApplicationSetting> applicationTimeOp = workdayoffApplicationTimeListFinal.stream()
								.filter(applicationTime -> applicationTime.getFrameNo().v() == workdayoffFrame.getWorkdayoffFrNo().v().intValue())
								.findFirst();
			if(applicationTimeOp.isPresent()) {
				cells.get(workdayoffCountRow.get(), workdayoffCountColumn.getAndIncrement()).setValue(workdayoffFrame.getWorkdayoffFrName().v());
				workdayoffCountColumn.getAndIncrement();
				cells.get(workdayoffCountRow.get(), workdayoffCountColumn.getAndIncrement()).setValue(applicationTimeOp.get().getApplicationTime().getInDayTimeWithFormat());
				workdayoffCountColumn.getAndIncrement();
				if(workdayoffCountColumn.get() > 10) {
					workdayoffCountColumn.set(3);
					workdayoffCountRow.incrementAndGet();
				}
			}
		});
		
		if(c3) {
			Cell cellD18 = cells.get("D18");
			cellD18.setValue(I18NText.getText("KAF010_342"));
			Cell cellD19 = cells.get("D19");
			cellD19.setValue(I18NText.getText("KAF010_343"));
			Cell cellH18 = cells.get("H18");
			cellH18.setValue(I18NText.getText("KAF010_344"));
			
			Optional<OverTimeShiftNight> overTimeShiftNight = printContentOfHolidayWork.getApplicationTime().getOverTimeShiftNight();
			
			Cell cellF18 = cells.get("F18");
			Optional<Integer> withinPrescribedHolidayWorkTime = overTimeShiftNight.isPresent() ? overTimeShiftNight.get().getMidNightHolidayTimes().stream()
						.filter(midNightHolidayTime -> midNightHolidayTime.getLegalClf().equals(StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork))
						.map(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).findFirst() : Optional.empty();
			String withinPrescribedHolidayWorkTimeText = withinPrescribedHolidayWorkTime.isPresent() ? 
					(new TimeWithDayAttr(withinPrescribedHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellF18.setValue(withinPrescribedHolidayWorkTimeText);
			
			Cell cellF19 = cells.get("F19");
			Optional<Integer> excessOfStatutoryHolidayWorkTime = overTimeShiftNight.isPresent() ? overTimeShiftNight.get().getMidNightHolidayTimes().stream()
					.filter(midNightHolidayTime -> midNightHolidayTime.getLegalClf().equals(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork))
					.map(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).findFirst() : Optional.empty();
			String excessOfStatutoryHolidayWorkTimeText = excessOfStatutoryHolidayWorkTime.isPresent() ? 
					(new TimeWithDayAttr(excessOfStatutoryHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellF19.setValue(excessOfStatutoryHolidayWorkTimeText);
			
			Cell cellJ18 = cells.get("J18");
			Optional<Integer> publicHolidayWorkTime = overTimeShiftNight.isPresent() ? overTimeShiftNight.get().getMidNightHolidayTimes().stream()
					.filter(midNightHolidayTime -> midNightHolidayTime.getLegalClf().equals(StaturoryAtrOfHolidayWork.PublicHolidayWork))
					.map(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).findFirst() : Optional.empty();
			String publicHolidayWorkTimeText = publicHolidayWorkTime.isPresent() ? 
					(new TimeWithDayAttr(publicHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellJ18.setValue(publicHolidayWorkTimeText);
		}
		
		if(c4) {
			Cell B20 = cells.get("B20");
			B20.setValue(I18NText.getText("KAF005_50"));
			
			List<OvertimeWorkFrame> overtimeFrameList = printContentOfHolidayWork.getOvertimeFrameList();
			Collections.sort(overtimeFrameList, 
					(overtimeFrame1, overtimeFrame2) -> overtimeFrame1.getOvertimeWorkFrNo().v().compareTo(overtimeFrame2.getOvertimeWorkFrNo().v()));
			List<OvertimeApplicationSetting> overtimeWorkApplicationTimeList = printContentOfHolidayWork.getApplicationTime().getApplicationTime();
			overtimeWorkApplicationTimeList = overtimeWorkApplicationTimeList.stream()
					.filter(applicationTime -> applicationTime.getAttendanceType().equals(AttendanceType_Update.NORMALOVERTIME)).collect(Collectors.toList());
			final List<OvertimeApplicationSetting> overtimeWorkApplicationTimeListFinal = overtimeWorkApplicationTimeList;
			AtomicInteger overtimeWorkCountRow = new AtomicInteger(19);
			AtomicInteger overtimeWorkCountColumn = new AtomicInteger(3);
			overtimeFrameList.stream().forEach(overtimeFrame -> {
				Optional<OvertimeApplicationSetting> applicationTimeOp = overtimeWorkApplicationTimeListFinal.stream()
									.filter(applicationTime -> applicationTime.getFrameNo().v() == overtimeFrame.getOvertimeWorkFrNo().v().intValue())
									.findFirst();
				if(applicationTimeOp.isPresent()) {
					cells.get(overtimeWorkCountRow.get(), overtimeWorkCountColumn.getAndIncrement()).setValue(overtimeFrame.getOvertimeWorkFrName().v());
					overtimeWorkCountColumn.getAndIncrement();
					cells.get(overtimeWorkCountRow.get(), overtimeWorkCountColumn.getAndIncrement()).setValue(applicationTimeOp.get().getApplicationTime().getInDayTimeWithFormat());
					overtimeWorkCountColumn.getAndIncrement();
					if(overtimeWorkCountColumn.get() > 10) {
						overtimeWorkCountColumn.set(3);
						overtimeWorkCountRow.incrementAndGet();
					}
				}
			});
		}
		
		if(c3 && c4) {
			Cell cellD24 = cells.get("D24");
			cellD24.setValue(I18NText.getText("KAF005_63"));
			
			Cell cellF24 = cells.get("F24");
			Optional<OverTimeShiftNight> overTimeShiftNight = printContentOfHolidayWork.getApplicationTime().getOverTimeShiftNight();
			if(overTimeShiftNight.isPresent()) {
				Integer overTimeMidNightTime = overTimeShiftNight.get().getOverTimeMidNight() != null ? 
						overTimeShiftNight.get().getOverTimeMidNight().v() : null;
				String overTimeMidNightText = overTimeMidNightTime != null ? (new TimeWithDayAttr(overTimeMidNightTime)).getInDayTimeWithFormat() : "";
				cellF24.setValue(overTimeMidNightText);
			}
		}
		
		if(c5) {
			Cell cellB30 = cells.get("B30");
			cellB30.setValue(I18NText.getText("KAF010_86"));
		}
		if(c6 || c7) {
			Cell cellD30 = cells.get("D30");
			String cellD30Text = "";
			Optional<List<ReasonDivergence>> reasonDissociation = printContentOfHolidayWork.getApplicationTime().getReasonDissociation();
			List<ReasonDivergence> reasonDivergenceList = reasonDissociation.isPresent() ? reasonDissociation.get() : Collections.emptyList();
			ReasonDivergence reasonDivergence = reasonDivergenceList.isEmpty() ? reasonDivergenceList.get(0) : new ReasonDivergence(null, null, null);
			if(c6) {
				cellD30Text += reasonDivergence.getReasonCode() != null ? reasonDivergence.getReasonCode().v() + "\n" : "";
			}
			if(c7) {
				cellD30Text += reasonDivergence.getReason() != null ? reasonDivergence.getReason().v() + "\n" : "";
			}
			cellD30.setValue(cellD30Text);
		}
		//huytodo deleteRows

		return 0;
	}
	
	public String formatTimeWithDayAttr(TimeWithDayAttr timeWithDayAttr) {
		if(timeWithDayAttr.dayAttr().equals(DayAttr.THE_PRESENT_DAY)) {
			return timeWithDayAttr.getInDayTimeWithFormat();
		} else {
			return timeWithDayAttr.getFullText();
		}
	}
}
