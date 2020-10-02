package nts.uk.ctx.at.request.app.command.application.businesstrip;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.BusinessTripService;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;
import org.apache.logging.log4j.util.Strings;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateBusinessTripCommandHandler extends CommandHandlerWithResult<UpdateBusinessTripCommand, ProcessResult> {

    @Inject
    BusinessTripRepository businessTripRepository;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    @Inject
    private ApplicationRepository applicationRepository;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    private BusinessTripService businessTripService;

    @Inject
    private AtEmployeeAdapter atEmployeeAdapter;

    @Inject
    private CommonAlgorithm commonAlgorithm;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateBusinessTripCommand> context) {
        UpdateBusinessTripCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        CreateApplicationCommand applicationCommand = command.getApplication();
        Application application = command
                .getBusinessTripInfoOutput().getAppDispInfoStartup().getAppDetailScreenInfo().getApplication().toDomain();
        application.setOpAppReason(Strings.isEmpty(applicationCommand.getOpAppReason()) ? Optional.empty() : Optional.of(new AppReason(applicationCommand.getOpAppReason())));
        application.setOpAppStandardReasonCD(applicationCommand.getOpAppStandardReasonCD() == null
                ? Optional.empty()
                : Optional.of(new AppStandardReasonCode(applicationCommand.getOpAppStandardReasonCD())));
        BusinessTrip businessTrip = command.getBusinessTrip().toDomain(application);
        BusinessTripInfoOutput infoOutput = command.getBusinessTripInfoOutput().toDomain();

        // アルゴリズム「4-1.詳細画面登録前の処理」を実行する
        businessTrip.getInfos().stream().forEach(i -> {
            this.detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
                    cid,
                    application.getEmployeeID(),
                    i.getDate(),
                    EmploymentRootAtr.APPLICATION.value,
                    application.getAppID(),
                    application.getPrePostAtr(),
                    application.getVersion(),
                    i.getWorkInformation().getWorkTypeCode().v(),
                    i.getWorkInformation().getWorkTimeCode() == null ? null : i.getWorkInformation().getWorkTimeCode().v(),
                    infoOutput.getAppDispInfoStartup()
            );
        });

        this.checkBeforeUpdate(businessTrip, infoOutput.getAppDispInfoStartup());

        //ドメインモデル「申請」の更新をする
        applicationRepository.update(application);

        //ドメインモデル「出張申請」を更新する
        businessTripRepository.update(businessTrip);

        //アルゴリズム「出張申請暫定残数を更新する」を実行する
        //アルゴリズム「暫定データの登録」を実行する
//        this.interimRemainDataMngRegisterDateChange.registerDateChange(cid,
//                application.getEmployeeID(),
//                businessTrip.getInfos().stream().map(i -> i.getDate()).collect(Collectors.toList()));

        // アルゴリズム「4-2.詳細画面登録後の処理」を実行する
        return detailAfterUpdate.processAfterDetailScreenRegistration(cid, application.getAppID());
    }

    private void checkBeforeUpdate(BusinessTrip businessTrip, AppDispInfoStartupOutput appDispInfoStartupOutput) {

        String inputSid = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication().getEnteredPersonID();
        String cid = AppContexts.user().companyId();

        if (businessTrip.getInfos().isEmpty()) {
            throw new BusinessException("Msg_1703");
        }
        // loop 年月日　in　期間
        businessTrip.getInfos().stream().forEach(i -> {

            String wkTypeCd = i.getWorkInformation().getWorkTypeCode().v();
            String wkTimeCd = i.getWorkInformation().getWorkTimeCode() == null ? null : i.getWorkInformation().getWorkTimeCode().v();
            Integer workTimeStart = null;
            Integer workTimeEnd = null;

            if (i.getWorkingHours().isPresent() && !i.getWorkingHours().get().isEmpty()) {
                workTimeStart = i.getWorkingHours().get().get(0).getTimeZone().getStartTime().v();
                workTimeEnd = i.getWorkingHours().get().get(0).getTimeZone().getEndTime().v();
            }

            // アルゴリズム「出張申請就業時間帯チェック」を実行する
            businessTripService.checkInputWorkCode(wkTypeCd, wkTimeCd, i.getDate(), workTimeStart, workTimeEnd);
        });

        List<GeneralDate> dates = businessTrip.getInfos().stream().map(i -> i.getDate()).collect(Collectors.toList());
        List<String> workTypeCodes = businessTrip.getInfos().stream().map(i -> i.getWorkInformation().getWorkTypeCode().v()).collect(Collectors.toList());

        List<EmployeeInfoImport> employeeInfoImports = atEmployeeAdapter.getByListSID(Arrays.asList(inputSid));
        // 申請の矛盾チェック
        this.commonAlgorithm.appConflictCheck(
                cid,
                employeeInfoImports.get(0),
                dates,
                workTypeCodes,
                appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get()
        );
    }
}
