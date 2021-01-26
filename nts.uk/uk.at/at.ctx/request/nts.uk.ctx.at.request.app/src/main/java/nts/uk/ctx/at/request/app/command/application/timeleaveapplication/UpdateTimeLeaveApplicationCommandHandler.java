package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeLeaveAppDisplayInfoDto;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationRepository;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.TimeLeaveApplicationOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 時間休暇申請を更新する
 */
@Stateless
@Transactional
public class UpdateTimeLeaveApplicationCommandHandler extends CommandHandlerWithResult<UpdateTimeLeaveApplicationCommand, ProcessResult> {

    @Inject
    private TimeLeaveApplicationRepository timeLeaveApplicationRepository;

    @Inject
    private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Inject
    private ApplicationRepository applicationRepository;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateTimeLeaveApplicationCommand> context) {
        String companyId = AppContexts.user().companyId();
        UpdateTimeLeaveApplicationCommand command = context.getCommand();
        TimeLeaveApplicationOutput timeLeaveApplicationOutput = TimeLeaveAppDisplayInfoDto.mappingData(command.getTimeLeaveAppDisplayInfo());

        Application application = command.getApplication().toDomain(command.getTimeLeaveAppDisplayInfo().getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication());
        TimeLeaveApplication timeLeaveApplication = new TimeLeaveApplication(
                application,
                command.getDetails().stream().map(TimeLeaveAppDetailCommand::toDomain).collect(Collectors.toList())
        );

        //ドメインモデル「申請」の更新をする
        applicationRepository.update(application);

        //時間休暇申請を更新する
        timeLeaveApplicationRepository.update(timeLeaveApplication);

        //暫定データの登録
        // TODO: wait for update
//        this.interimRemainDataMngRegisterDateChange.registerDateChange(
//                companyId,
//                application.getEmployeeID(),
//                Arrays.asList(application.getAppDate().getApplicationDate())
//        );

        //4-2.詳細画面登録後の処理
        return detailAfterUpdate.processAfterDetailScreenRegistration(AppContexts.user().companyId(), application.getAppID(),
            timeLeaveApplicationOutput.getAppDispInfoStartup());
    }
}
