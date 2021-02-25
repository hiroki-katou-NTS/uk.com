package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.app.command.application.holidaywork.PrintContentOfHolidayWorkCmd;
import nts.uk.ctx.at.request.app.command.application.overtime.DetailOutputCommand;
import nts.uk.ctx.at.request.app.command.application.workchange.AppWorkChangeOutputCmd;
import nts.uk.ctx.at.request.app.find.application.appabsence.dto.ApplyForleaveOutputCmd;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInformationApplication;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemApplicationPrintDto;
import nts.uk.ctx.at.request.app.find.application.stamp.dto.AppStampOutputDto;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDetailDto;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApplyForLeave;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfEachApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeOutput;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * refactor 4
 *
 * @author Doan Duy Hung
 */
public class PrintContentOfEachAppDto {

    /**
     * 休暇申請の印刷内容
     */
    public ApplyForleaveOutputCmd opPrintContentApplyForLeave;

    /**
     * 勤務変更申請の印刷内容
     */
    public AppWorkChangeOutputCmd opPrintContentOfWorkChange;

    /**
     * 時間休暇申請の印刷内容
     */
    public List<TimeLeaveAppDetailDto> opPrintContentOfTimeLeave;

    /**
     * 打刻申請の印刷内容
     */
    public AppStampOutputDto opAppStampOutput;

    /**
     * 遅刻早退取消申請の印刷内容
     */
    public ArrivedLateLeaveEarlyInfoDto opArrivedLateLeaveEarlyInfo;

    /**
     * 直行直帰申請の印刷内容
     */
    public InforGoBackCommonDirectDto opInforGoBackCommonDirectOutput;

    public BusinessTripDto opBusinessTripInfoOutput;

	public OptionalItemApplicationPrintDto opOptionalItemOutput;
	
	public DetailOutputCommand  opDetailOutput;

	/**
	 * 休暇申請の印刷内容
	 */
	
	/**
	 * 休日出勤の印刷内容
	 */
	public PrintContentOfHolidayWorkCmd opPrintContentOfHolidayWork;
	
	/**
	 * 振休振出申請の印刷内容
     */
	public DisplayInforWhenStarting optHolidayShipment;

    public PrintContentOfEachApp toDomain() {
        PrintContentOfEachApp printContentOfEachApp = new PrintContentOfEachApp();
        if (opPrintContentOfWorkChange != null) {
            AppWorkChangeOutput appWorkChangeOutput = opPrintContentOfWorkChange.toDomain();
            PrintContentOfWorkChange printContentOfWorkChange = new PrintContentOfWorkChange(
                    appWorkChangeOutput.getAppWorkChange().orElse(null),
                    appWorkChangeOutput.getAppWorkChangeDispInfo());
            printContentOfEachApp.setOpPrintContentOfWorkChange(Optional.of(printContentOfWorkChange));
        }
        if (opAppStampOutput != null) {
            printContentOfEachApp.setOpAppStampOutput(Optional.of(opAppStampOutput.toDomain()));
        }
        if (opArrivedLateLeaveEarlyInfo != null) {
            printContentOfEachApp.setOpArrivedLateLeaveEarlyInfo(Optional.of(opArrivedLateLeaveEarlyInfo.toDomain()));
        }
        if (opInforGoBackCommonDirectOutput != null) {
            printContentOfEachApp.setOpInforGoBackCommonDirectOutput(Optional.of(opInforGoBackCommonDirectOutput.toDomain()));
        }
        if (opBusinessTripInfoOutput != null) {
            printContentOfEachApp.setOpBusinessTrip(Optional.of(opBusinessTripInfoOutput.toPrintContentOutput()));
        }
        if (opOptionalItemOutput != null) {
            printContentOfEachApp.setOpOptionalItem(Optional.of(opOptionalItemOutput.toPrintContentOutput()));
		}
		if(opPrintContentOfHolidayWork != null) {
			printContentOfEachApp.setOpPrintContentOfHolidayWork(Optional.of(opPrintContentOfHolidayWork.toDomain()));
		}
		if (opPrintContentOfTimeLeave != null) {
            printContentOfEachApp.setOpPrintContentOfTimeLeave(Optional.of(opPrintContentOfTimeLeave.stream().map(TimeLeaveAppDetailDto::toDomain).collect(Collectors.toList())));
        }
		if (opDetailOutput != null) {
			printContentOfEachApp.setOpDetailOutput(Optional.of(opDetailOutput.toDomain()));
		}
		if (opPrintContentApplyForLeave != null) {
		    printContentOfEachApp.setOpPrintContentApplyForLeave(Optional.of(opPrintContentApplyForLeave.toDomain()));
		}
		if (optHolidayShipment != null) {
		    printContentOfEachApp.setOptHolidayShipment(Optional.of(optHolidayShipment.toDomain()));
		}
        return printContentOfEachApp;
    }

}
