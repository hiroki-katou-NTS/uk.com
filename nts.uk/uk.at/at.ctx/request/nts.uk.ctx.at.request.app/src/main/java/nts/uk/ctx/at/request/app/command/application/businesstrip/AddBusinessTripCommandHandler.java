package nts.uk.ctx.at.request.app.command.application.businesstrip;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.RegisterAtApproveReflectionInfoService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.after.NewAfterRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        String loginSid = AppContexts.user().employeeId();
        ApplicationDto applicationDto = command.getApplication();
        Application application = Application.createFromNew(
                EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                applicationDto.getEmployeeID() == null ? loginSid : applicationDto.getEmployeeID(),
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                loginSid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));
        BusinessTrip businessTrip = command.getBusinessTrip().toDomain(application);
        businessTrip.setAppID(application.getAppID());
        BusinessTripInfoOutput businessTripInfoOutput = command.getBusinessTripInfoOutput().toDomain();

        GeneralDate appStartDate = application.getOpAppStartDate().isPresent() ? application.getOpAppStartDate().get().getApplicationDate() : null;
        GeneralDate appEndDate = application.getOpAppStartDate().isPresent() ? application.getOpAppEndDate().get().getApplicationDate() : null;
        List<GeneralDate> dates = new ArrayList<>();
        if (appStartDate != null && appEndDate != null) {
            DatePeriod period = new DatePeriod(appStartDate, appEndDate);
            dates = period.datesBetween();
        } else {
            dates.add(application.getAppDate().getApplicationDate());
        }

        // ドメインモデル「申請」の新規登録をする
        this.appRepository.insertApp(
                application,
                businessTripInfoOutput
                        .getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState().isPresent() ? businessTripInfoOutput.getAppDispInfoStartup()
                        .getAppDispInfoWithDateOutput()
                        .getOpListApprovalPhaseState()
                        .get() : null);

        // アルゴリズム「2-2.新規画面登録時承認反映情報の整理」を実行する
        this.registerService.newScreenRegisterAtApproveInfoReflect(application.getEmployeeID(), application);

        // ドメインモデル「出張申請」を追加する
        this.businessTripRepository.add(businessTrip);


        // アルゴリズム「出張申請暫定残数を更新する」を実行する
        // アルゴリズム「暫定データの登録」を実行する
        // Refactor not done
//        this.interimRemainDataMngRegisterDateChange.registerDateChange(
//                AppContexts.user().companyId(),
//                application.getEmployeeID(),
//                dates
//        );

        Optional<AppTypeSetting> appTypeSet = businessTripInfoOutput
                .getAppDispInfoStartup()
                .getAppDispInfoNoDateOutput()
                .getApplicationSetting()
                .getAppTypeSettings().stream().filter(i -> i.getAppType()== ApplicationType.BUSINESS_TRIP_APPLICATION)
                .findFirst();

        if (appTypeSet.isPresent()) {
            // 2-3.新規画面登録後の処理
            return this.newAfterRegister.processAfterRegister(
                    application.getAppID(),
                    appTypeSet.get(),
                    businessTripInfoOutput.getAppDispInfoStartup().getAppDispInfoNoDateOutput().isMailServerSet());
        }
        return null;
    }

}
