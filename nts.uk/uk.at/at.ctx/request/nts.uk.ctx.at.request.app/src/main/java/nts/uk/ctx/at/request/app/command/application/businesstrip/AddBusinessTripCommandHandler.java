package nts.uk.ctx.at.request.app.command.application.businesstrip;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


@Stateless
public class AddBusinessTripCommandHandler extends CommandHandlerWithResult<AddBusinessTripCommand, ProcessResult> {


    @Inject
    ApplicationApprovalService appRepository;

    @Inject
    BusinessTripRepository businessTripRepository;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    NewAfterRegister newAfterRegister;

    @Inject
    private RegisterAtApproveReflectionInfoService registerService;


    @Override
    protected ProcessResult handle(CommandHandlerContext<AddBusinessTripCommand> context) {
        AddBusinessTripCommand command = context.getCommand();
        Application application = command.getApplicationDto().toDomain();
        application = Application.createFromNew(
                application.getPrePostAtr(),
                application.getEmployeeID(),
                application.getAppType(),
                application.getAppDate(),
                application.getEnteredPersonID(),
                application.getOpStampRequestMode(),
                application.getOpReversionReason(),
                application.getOpAppStartDate(),
                application.getOpAppEndDate(),
                application.getOpAppReason(),
                application.getOpAppStandardReasonCD());
        BusinessTrip businessTrip = command.getBusinessTripDto().toDomain(application);
        businessTrip.setAppID(application.getAppID());
        BusinessTripInfoOutput businessTripInfoOutput = command.getBusinessTripInfoOutputDto().toDomain();

        this.appRepository.insertApp(
                application,
                businessTripInfoOutput
                        .getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState().isPresent() ? businessTripInfoOutput.getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState()
                        .get() : null);

        // ドメインモデル「出張申請」を追加する
        this.businessTripRepository.add(businessTrip);

        // ドメインモデル「申請」の新規登録をする
        this.registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);
        GeneralDate appStartDate = application.getOpAppStartDate().isPresent() ? application.getOpAppStartDate().get().getApplicationDate() : null;
        GeneralDate appEndDate = application.getOpAppStartDate().isPresent() ? application.getOpAppEndDate().get().getApplicationDate() : null;
        List<GeneralDate> dates = new ArrayList<>();
        if (appStartDate != null && appEndDate != null) {
            DatePeriod period = new DatePeriod(appStartDate, appEndDate);
            dates = period.datesBetween();
        } else {
            dates.add(application.getAppDate().getApplicationDate());
        }

        // アルゴリズム「暫定データの登録」を実行する
        // Refactor not done
//        this.interimRemainDataMngRegisterDateChange.registerDateChange(
//                AppContexts.user().companyId(),
//                application.getEmployeeID(),
//                dates
//        );

        // 2-3.新規画面登録後の処理
        return this.newAfterRegister.processAfterRegister(
                application.getAppID(),
                businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSetting(),
                businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet());
    }

}
