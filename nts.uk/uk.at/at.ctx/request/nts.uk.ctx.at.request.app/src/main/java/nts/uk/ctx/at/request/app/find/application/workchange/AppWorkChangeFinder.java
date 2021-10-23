package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandCheck;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandPC;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDetailDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.AppWorkChangeDispInfoDto;
import nts.uk.ctx.at.request.app.find.application.workchange.dto.WorkChangeCheckRegisterDto;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.MsgErrorOutput;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeService;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeSetRepository;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.application.workchange.output.WorkChangeCheckRegOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeFinder {

    @Inject
    private AppWorkChangeSetRepository appWorkChangeSetRepo;

    @Inject
    private IWorkChangeRegisterService workChangeRegisterService;

    @Inject
    private AppWorkChangeService appWorkChangeService;

    public AppWorkChangeSetDto findByCompany() {
        String companyId = AppContexts.user().companyId();
        Optional<AppWorkChangeSet> appWorkChangeSet = appWorkChangeSetRepo.findByCompanyId(companyId);
        if (appWorkChangeSet.isPresent()) {
            AppWorkChangeSetDto.fromDomain(appWorkChangeSet.get());
        }
        return null;
    }

    public boolean isTimeRequired(String workTypeCD) {
        return workChangeRegisterService.isTimeRequired(workTypeCD);
    }

    public AppWorkChangeDispInfoDto getStartNew(AppWorkChangeParamPC param) {
        String companyID = AppContexts.user().companyId();
        List<GeneralDate> dateLst = param.getDateLst().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
                .collect(Collectors.toList());
        AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.getStartNew(companyID, param.getEmpLst(),
                dateLst, param.getAppDispInfoStartupOutput().toDomain());
        return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
    }

    public AppWorkChangeDispInfoDto changeAppDate(AppWorkChangeAppdateDto param) {
        String companyID = AppContexts.user().companyId();
        List<GeneralDate> dateLst = param.getDateLst().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
                .collect(Collectors.toList());
        AppWorkChangeDispInfo appWorkChangeDispInfo = appWorkChangeService.changeAppDate(companyID, dateLst,
                param.getAppWorkChangeDispInfo().toDomain());
        return AppWorkChangeDispInfoDto.fromDomain(appWorkChangeDispInfo);
    }

    public AppWorkChangeDispInfoDto changeWorkSelection(AppWorkChangeParamPC param) {
        // error EA refactor 4
        
        return null;
    }

    public WorkChangeCheckRegisterDto checkBeforeRegister(AddAppWorkChangeCommandPC command) {
        // error EA refactor 4
       
        return null;
    }

    public AppWorkChangeOutputDto startDetailScreen(AppWorkChangeDetailParam appWorkChangeDetailParam) {
        String companyID = AppContexts.user().companyId();
        AppWorkChangeOutputDto appWorkChangeOutputDto = new AppWorkChangeOutputDto();
        AppWorkChangeDetailDto appWorkChangeDetailDto = AppWorkChangeDetailDto
                .fromDomain(appWorkChangeService.startDetailScreen(
                        companyID, appWorkChangeDetailParam.getAppDispInfoStartupDto().getAppDetailScreenInfo()
                                .getApplication().getAppID(),
                        appWorkChangeDetailParam.getAppDispInfoStartupDto().toDomain()));
        appWorkChangeOutputDto.setAppWorkChangeDispInfo(appWorkChangeDetailDto.appWorkChangeDispInfo);
        appWorkChangeOutputDto.setAppWorkChange(appWorkChangeDetailDto.appWorkChange);
        return appWorkChangeOutputDto;
    }

    public void checkBeforeUpdate(AddAppWorkChangeCommandPC command) {
        // error EA refactor 4
        
    }

    // start at create and modify mode
    public AppWorkChangeOutputDto getStartKAFS07(AppWorkChangeParam appWorkChangeParam) {

        boolean mode = appWorkChangeParam.getMode();
        String companyId = appWorkChangeParam.getCompanyId();
        String employeeId = null;
        if (appWorkChangeParam.getEmployeeId() != null) {
            employeeId = appWorkChangeParam.getEmployeeId();
        }
        List<GeneralDate> dates = null;

        if (appWorkChangeParam.getListDates() != null) {
            dates = appWorkChangeParam.getListDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
                    .collect(Collectors.toList());
        }
        AppWorkChangeDispInfo appWorkChangeDispInfo = null;
        if (appWorkChangeParam.getMode()) {
            if (appWorkChangeParam.getAppWorkChangeOutputDto() != null) {
                appWorkChangeDispInfo = appWorkChangeParam.getAppWorkChangeOutputDto().getAppWorkChangeDispInfo()
                        .toDomain();
            }

        } else {
            if (appWorkChangeParam.getAppWorkChangeOutputCmd() != null) {
                appWorkChangeDispInfo = appWorkChangeParam.getAppWorkChangeOutputCmd().getAppWorkChangeDispInfo()
                        .toDomain();
            }
        }
        AppWorkChange appWorkChange = null;
        if (appWorkChangeParam.getAppWorkChangeDto() != null) {
            if (appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDetailScreenInfo().isPresent()) {
                appWorkChange = appWorkChangeParam.getAppWorkChangeDto().toDomain(appWorkChangeDispInfo
                        .getAppDispInfoStartupOutput().getAppDetailScreenInfo().get().getApplication());
            }
        }

        return AppWorkChangeOutputDto.fromDomain(appWorkChangeService.getAppWorkChangeOutput(mode, companyId,
                Optional.ofNullable(employeeId), Optional.ofNullable(dates), Optional.ofNullable(appWorkChangeDispInfo),
                Optional.ofNullable(appWorkChange)));
    }

    public AppWorkChangeDispInfoDto getUpdateKAFS07(UpdateWorkChangeParam updateWorkChangeParam) {

        List<GeneralDate> dates = null;

        if (updateWorkChangeParam.getListDates() != null) {
            dates = updateWorkChangeParam.getListDates().stream().map(x -> GeneralDate.fromString(x, "yyyy/MM/dd"))
                    .collect(Collectors.toList());
        }
        AppWorkChangeDispInfo appWorkChangeDispInfo = null;
        if (updateWorkChangeParam.getAppWorkChangeDispInfo() != null) {
            appWorkChangeDispInfo = updateWorkChangeParam.getAppWorkChangeDispInfo().toDomain();
        }

        return AppWorkChangeDispInfoDto.fromDomain(
                appWorkChangeService.changeAppDate(updateWorkChangeParam.getCompanyId(), dates, appWorkChangeDispInfo));

    }

    // 勤務変更申請の登録前チェック処理
    public WorkChangeCheckRegisterDto checkBeforeRegisterNew(AddAppWorkChangeCommandCheck command) {

        Boolean mode = command.getMode();
        String companyId = command.getCompanyId();
        String sId = AppContexts.user().employeeId();
        ApplicationDto applicationDto = command.getApplicationDto();
        applicationDto.setEmployeeID(sId);
        Application application = applicationDto.toDomain();

        AppWorkChangeDto appWorkChangeDto = command.getAppWorkChangeDto();
        List<MsgErrorOutput> msgErrorLst = command.getOpMsgErrorLst();
        WorkChangeCheckRegOutput workChangeCheckRegOutput = appWorkChangeService.checkBeforeRegister(mode, companyId,
                application, appWorkChangeDto.toDomain(application),
                msgErrorLst, command.getAppDispInfoStartupDto().toDomain(), 
                command.getAppWorkChangeDispInfo() != null ? command.getAppWorkChangeDispInfo().toDomain() : null);

        return WorkChangeCheckRegisterDto.fromDomain(workChangeCheckRegOutput);

    }

    // 勤務変更申請の登録前チェック処理
    public WorkChangeCheckRegisterDto checkBeforeRegisterPC(AddAppWorkChangeCommandCheck command) {

        Boolean mode = command.getMode();
        String companyId = command.getCompanyId();
        String sId = AppContexts.user().employeeId();
        Application application = new Application();
        ApplicationDto applicationDto = command.getApplicationDto();
        applicationDto.setEmployeeID(sId);
        if (StringUtils.isBlank(applicationDto.getAppID())) {
            application = Application.createFromNew(
                    EnumAdaptor.valueOf(applicationDto.getPrePostAtr(), PrePostAtr.class),
                    applicationDto.getEmployeeID(),
                    EnumAdaptor.valueOf(applicationDto.getAppType(), ApplicationType.class),
                    new ApplicationDate(GeneralDate.fromString(applicationDto.getAppDate(), "yyyy/MM/dd")),
                    applicationDto.getEnteredPerson(),
                    applicationDto.getOpStampRequestMode() == null ? Optional.empty()
                            : Optional.ofNullable(EnumAdaptor.valueOf(applicationDto.getOpStampRequestMode(),
                                    StampRequestMode.class)),
                    applicationDto.getOpReversionReason() == null ? Optional.empty()
                            : Optional.ofNullable(new ReasonForReversion(applicationDto.getOpReversionReason())),
                    applicationDto.getOpAppStartDate() == null ? Optional.empty()
                            : Optional.ofNullable(new ApplicationDate(
                                    GeneralDate.fromString(applicationDto.getOpAppStartDate(), "yyyy/MM/dd"))),
                    applicationDto.getOpAppEndDate() == null ? Optional.empty()
                            : Optional.ofNullable(new ApplicationDate(
                                    GeneralDate.fromString(applicationDto.getOpAppEndDate(), "yyyy/MM/dd"))),
                    applicationDto.getOpAppReason() == null ? Optional.empty()
                            : Optional.ofNullable(new AppReason(applicationDto.getOpAppReason())),
                    applicationDto.getOpAppStandardReasonCD() == null ? Optional.empty()
                            : Optional.ofNullable(new AppStandardReasonCode(applicationDto.getOpAppStandardReasonCD())));
        } else {
            application = applicationDto.toDomain();
        }

        AppWorkChangeDto appWorkChangeDto = command.getAppWorkChangeDto();
        List<MsgErrorOutput> msgErrorLst = command.getOpMsgErrorLst();
        WorkChangeCheckRegOutput workChangeCheckRegOutput = appWorkChangeService.checkBeforeRegister(mode, companyId,
                application, appWorkChangeDto.toDomain(application),
                msgErrorLst, command.getAppDispInfoStartupDto().toDomain(), 
                command.getAppWorkChangeDispInfo() != null ? command.getAppWorkChangeDispInfo().toDomain() : null);

        return WorkChangeCheckRegisterDto.fromDomain(workChangeCheckRegOutput);

    }

    // 起動する B KAFS07
    public AppWorkChangeOutputDto getDetailKAFS07(AppWorkChangeDetailParam appWorkChangeDetailParam) {
        String companyId = AppContexts.user().companyId();
        String appId = appWorkChangeDetailParam.getAppId();
        AppDispInfoStartupDto appDispInfoStartupDto = appWorkChangeDetailParam.getAppDispInfoStartupDto();
        AppWorkChangeOutputDto appWorkChangeOutputDto = new AppWorkChangeOutputDto();
        AppWorkChangeDetailDto appWorkChangeDetailDto = AppWorkChangeDetailDto
                .fromDomain(appWorkChangeService.startDetailScreen(companyId, appId, appDispInfoStartupDto.toDomain()));
        appWorkChangeOutputDto.setAppWorkChangeDispInfo(appWorkChangeDetailDto.appWorkChangeDispInfo);
        appWorkChangeOutputDto.setAppWorkChange(appWorkChangeDetailDto.appWorkChange);
        return appWorkChangeOutputDto;
    }

    // check worktime
    public ChangeWkTypeTimeDto checkWorkTime(CheckWorkTimeParam param) {
        return ChangeWkTypeTimeDto
                .fromDomain(appWorkChangeService.changeWorkTypeWorkTime(param.getCompanyId(), param.getWorkType(),
                        Optional.ofNullable(param.getWorkTime()), param.getAppWorkChangeSetDto().toDomain()));
    }

}
