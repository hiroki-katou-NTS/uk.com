package nts.uk.ctx.at.request.infra.repository.application.holidaywork;

import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import com.aspose.cells.Worksheet;

import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;

/**
 *
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
		Boolean c4 = !printContentOfHolidayWork.getApplicationTimeList().stream()
						.filter(applicationTime -> 
							!applicationTime.getApplicationTime().stream()
							.filter(appTime -> appTime.getAttendanceType().equals(AttendanceType_Update.NORMALOVERTIME))
							.collect(Collectors.toList())
							.isEmpty()
							)
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
		return 0;
	}

}
