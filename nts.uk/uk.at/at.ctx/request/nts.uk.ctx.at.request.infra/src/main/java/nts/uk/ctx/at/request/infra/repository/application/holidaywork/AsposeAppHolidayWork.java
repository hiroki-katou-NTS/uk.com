package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Worksheet;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
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

	public List<Integer> printAppHolidayWorkContent(Worksheet worksheet, PrintContentOfApp printContentOfApp) {
		List<Integer> deleteRowsList = new ArrayList<Integer>();
		deleteRowsList.add(0);
		deleteRowsList.add(0);
		int deleteRows = 0;
		Optional<PrintContentOfHolidayWork> opPrintContentOfHolidayWork = printContentOfApp.getOpPrintContentOfHolidayWork();
		if (!opPrintContentOfHolidayWork.isPresent()) return deleteRowsList;
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
		DivergenceReasonInputMethod divergenceReasonInputMethod= !printContentOfHolidayWork.getDivergenceReasonInputMethod().isEmpty() ? 
				printContentOfHolidayWork.getDivergenceReasonInputMethod().get(0) : null;
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND (休日出勤申請の印刷内容．乖離理由の選択肢を利用する」がtrue OR 休日出勤申請の印刷内容．乖離理由の入力を利用する」がtrue)
		Boolean c5 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& (divergenceReasonInputMethod != null && (divergenceReasonInputMethod.isDivergenceReasonSelected() || divergenceReasonInputMethod.isDivergenceReasonInputed()));
		
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND 休日出勤申請の印刷内容．乖離理由の選択肢を利用する」がtrue
		Boolean c6 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& (divergenceReasonInputMethod != null && divergenceReasonInputMethod.isDivergenceReasonSelected());
		
		//	休日出勤申請の印刷内容．乖離理由を反映するが反映する AND 休日出勤申請の印刷内容．乖離理由の入力を利用する」がtrue
		Boolean c7 = printContentOfHolidayWork.getDivergenceReasonReflect().equals(NotUseAtr.USE)
						&& (divergenceReasonInputMethod != null && divergenceReasonInputMethod.isDivergenceReasonInputed());
		
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
		int deleteC1 = 0;
		if(c1) {
			cellB11.setValue(I18NText.getText("KAF010_339"));
		} else {
			deleteC1 += 1;
		}
		int deleteC2 = 0;
		if(c2) {
			cellB12.setValue(I18NText.getText("KAF005_40"));
		} else {
			deleteC2 += 1;
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
		
		int deleteWorkdayoff = 0;
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
			if(applicationTimeOp.isPresent() && applicationTimeOp.get().getApplicationTime().v() != 0) {
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
		if(workdayoffCountColumn.get() == 3) {
			deleteWorkdayoff = 16 - workdayoffCountRow.get() + 1 ;
		} else {
			deleteWorkdayoff = 16 - workdayoffCountRow.get();
		}
		
		int deleteWorkdayoffNight = 0;
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
			String withinPrescribedHolidayWorkTimeText = withinPrescribedHolidayWorkTime.isPresent() && (new TimeWithDayAttr(withinPrescribedHolidayWorkTime.get())).v() != 0 ? 
					(new TimeWithDayAttr(withinPrescribedHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellF18.setValue(withinPrescribedHolidayWorkTimeText);
			
			Cell cellF19 = cells.get("F19");
			Optional<Integer> excessOfStatutoryHolidayWorkTime = overTimeShiftNight.isPresent() ? overTimeShiftNight.get().getMidNightHolidayTimes().stream()
					.filter(midNightHolidayTime -> midNightHolidayTime.getLegalClf().equals(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork))
					.map(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).findFirst() : Optional.empty();
			String excessOfStatutoryHolidayWorkTimeText = excessOfStatutoryHolidayWorkTime.isPresent() && (new TimeWithDayAttr(excessOfStatutoryHolidayWorkTime.get())).v() != 0 ? 
					(new TimeWithDayAttr(excessOfStatutoryHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellF19.setValue(excessOfStatutoryHolidayWorkTimeText);
			
			Cell cellJ18 = cells.get("J18");
			Optional<Integer> publicHolidayWorkTime = overTimeShiftNight.isPresent() ? overTimeShiftNight.get().getMidNightHolidayTimes().stream()
					.filter(midNightHolidayTime -> midNightHolidayTime.getLegalClf().equals(StaturoryAtrOfHolidayWork.PublicHolidayWork))
					.map(midNightHolidayTime -> midNightHolidayTime.getAttendanceTime().v()).findFirst() : Optional.empty();
			String publicHolidayWorkTimeText = publicHolidayWorkTime.isPresent() && (new TimeWithDayAttr(publicHolidayWorkTime.get())).v() != 0? 
					(new TimeWithDayAttr(publicHolidayWorkTime.get())).getInDayTimeWithFormat() : "";
			cellJ18.setValue(publicHolidayWorkTimeText);
		} else {
			deleteWorkdayoffNight += 2;
		}
		
		int deleteOvertime = 0;
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
				if(applicationTimeOp.isPresent() && applicationTimeOp.get().getApplicationTime().v() != 0) {
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
			if(overtimeWorkCountColumn.get() == 3) {
				deleteOvertime = 23 - overtimeWorkCountRow.get() + 1 ;
			} else {
				deleteOvertime = 23 - overtimeWorkCountRow.get();
			}
		} else {
			deleteOvertime = 5;
		}
		
		int deleteOvertimeNight = 0;
		if(c3 && c4) {
			Cell cellD24 = cells.get("D25");
			cellD24.setValue(I18NText.getText("KAF005_63"));
			
			Cell cellF24 = cells.get("F25");
			Optional<OverTimeShiftNight> overTimeShiftNight = printContentOfHolidayWork.getApplicationTime().getOverTimeShiftNight();
			if(overTimeShiftNight.isPresent()) {
				Integer overTimeMidNightTime = overTimeShiftNight.get().getOverTimeMidNight() != null ? 
						overTimeShiftNight.get().getOverTimeMidNight().v() : null;
				String overTimeMidNightText = overTimeMidNightTime != null && overTimeMidNightTime != 0 ? (new TimeWithDayAttr(overTimeMidNightTime)).getInDayTimeWithFormat() : "";
				cellF24.setValue(overTimeMidNightText);
			}
		} else {
			deleteOvertimeNight += 1;
		}
		
		if(c5) {
			Cell cellB30 = cells.get("B30");
			Optional<DivergenceTimeRoot> divergenceTimeRoot = 
					printContentOfHolidayWork.getDivergenceTimeRoots().stream().filter(root -> root.getDivergenceTimeNo() == 3).findFirst();
			String cellB30Text = divergenceTimeRoot.isPresent() ? 
					I18NText.getText("KAF005_93", divergenceTimeRoot.get().getDivTimeName().v()) : 
						I18NText.getText("KAF005_93");
			cellB30.setValue(cellB30Text);
		}
		if(c6 || c7) {
			Cell cellD30 = cells.get("D30");
			String cellD30Text = "";
			Optional<List<ReasonDivergence>> reasonDissociation = printContentOfHolidayWork.getApplicationTime().getReasonDissociation();
			List<ReasonDivergence> reasonDivergenceList = reasonDissociation.isPresent() ? reasonDissociation.get() : Collections.emptyList();
			ReasonDivergence reasonDivergence = !reasonDivergenceList.isEmpty() ? reasonDivergenceList.get(0) : new ReasonDivergence(null, null, null);
			if(c6) {
				cellD30Text += reasonDivergence.getReasonCode() != null ? reasonDivergence.getReasonCode().v() + "\n" : "";
			}
			if(c7) {
				cellD30Text += reasonDivergence.getReason() != null ? reasonDivergence.getReason().v() + "\n" : "";
			}
			cellD30.setValue(cellD30Text);
		}

		if(!c5 && !c6 && !c7) {
			cells.deleteRows(29, 3);
			deleteRowsList.set(1, 3);
		}
		if(deleteOvertimeNight > 0) {
			cells.deleteRow(24);
			deleteRows += deleteOvertimeNight;
		}
		if(deleteOvertime > 0) {
			if(deleteOvertime < 5) {
				cells.deleteRows(24 - deleteOvertime, deleteOvertime);
				deleteRows += deleteOvertime;
			} else {
				if(deleteOvertimeNight > 0) {
					cells.deleteRows(24 - deleteOvertime, deleteOvertime);
					deleteRows += deleteOvertime;
				} else {
					cells.deleteRows(24 - deleteOvertime + 1, deleteOvertime - 1);
					deleteRows += deleteOvertime -1;
					cells.get("B21").copy(cells.get("B20"));
					cells.get("C21").copy(cells.get("C20"));
					cells.deleteRow(19);
					deleteRows += 1;
				}
			}
		}
		if(deleteWorkdayoffNight > 0) {
			cells.deleteRows(17, deleteWorkdayoffNight);
			deleteRows += deleteWorkdayoffNight;
		}
		if(deleteWorkdayoff > 0) {
			if(deleteWorkdayoff < 5) {
				cells.deleteRows(17 - deleteWorkdayoff, deleteWorkdayoff);
				deleteRows += deleteWorkdayoff;
			} else {
				if(deleteWorkdayoffNight > 0) {
					cells.deleteRows(17 - deleteWorkdayoff + 1, deleteWorkdayoff - 1);
					deleteRows += deleteWorkdayoff - 1;
				} else {
					cells.deleteRows(17 - deleteWorkdayoff + 1, deleteWorkdayoff - 1);
					deleteRows += deleteWorkdayoff - 1;
					cells.get("B14").copy(cells.get("B13"));
					cells.get("C14").copy(cells.get("C13"));
					cells.deleteRow(12);
					deleteRows += 1;
				}
			}
		}
		if(deleteC2 > 0) {
			cells.deleteRow(11);
			deleteRows += 1;
		}
		if(deleteC1 > 0) {
			cells.deleteRow(10);
			deleteRows += 1;
		}
		deleteRowsList.set(0, deleteRows);
		return deleteRowsList;
	}
	
	public String formatTimeWithDayAttr(TimeWithDayAttr timeWithDayAttr) {
		if(timeWithDayAttr.dayAttr().equals(DayAttr.THE_PRESENT_DAY)) {
			return timeWithDayAttr.getInDayTimeWithFormat();
		} else {
			return timeWithDayAttr.getFullText();
		}
	}
}
