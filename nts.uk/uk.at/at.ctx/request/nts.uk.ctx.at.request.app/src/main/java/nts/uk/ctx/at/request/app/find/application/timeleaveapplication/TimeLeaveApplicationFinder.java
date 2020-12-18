package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.CheckBeforeRegisterDto;
import nts.uk.ctx.at.request.dom.application.*;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.BusinessTripService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Stateless
public class TimeLeaveApplicationFinder {

    @Inject
    private NewBeforeRegister processBeforeRegister;

    @Inject
    private BusinessTripService businessTripService;

    /**
     * アルゴリズム「出張申請登録前エラーチェック」を実行する
     */
    public List<ConfirmMsgOutput> checkBeforeRegister(CheckBeforeRegisterDto param) {
        String sid = AppContexts.user().employeeId();
        List<ConfirmMsgOutput> confirmMsgOutputs;

        ApplicationDto applicationDto = param.getApplication();

        Application application = Application.createFromNew(
                EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                sid,
                EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                sid,
                Optional.empty(),
                Optional.empty(),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                Optional.ofNullable(new ApplicationDate(GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                applicationDto.getOpAppReason() == null ? Optional.empty() : Optional.of(new AppReason(applicationDto.getOpAppReason())),
                applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty() : Optional.of(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())
                ));

        BusinessTripInfoOutput output = param.getBusinessTripInfoOutput().toDomain();

        Optional<ApplicationDate> appStartDate = application.getOpAppStartDate();
        Optional<ApplicationDate> appEndDate = application.getOpAppEndDate();
        DatePeriod period = new DatePeriod(appStartDate.get().getApplicationDate(), appEndDate.get().getApplicationDate());
        List<GeneralDate> lstDate = period.datesBetween();

        // アルゴリズム「2-1.新規画面登録前の処理」を実行する
        confirmMsgOutputs = processBeforeRegister.processBeforeRegister_New(
                AppContexts.user().companyId(),
                EmploymentRootAtr.APPLICATION,
                true,
                application,
                null,
                output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpErrorFlag().get(),
                Collections.emptyList(),
                output.getAppDispInfoStartup()
        );

        BusinessTrip businessTrip = param.getBusinessTrip().toDomain(application);

        if (confirmMsgOutputs.isEmpty()) {
            // アルゴリズム「出張申請個別エラーチェック」を実行する
            if (businessTrip.getInfos().isEmpty()) {
                throw new BusinessException("Msg_1703");
            }
            businessTripService.businessTripIndividualCheck(
                    businessTrip.getInfos(),
                    output.getAppDispInfoStartup().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get()
            );
        }
        return confirmMsgOutputs;
    }

}
