package nts.uk.ctx.at.request.app.command.application.optionalitem;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.find.application.optitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UpdateOptionalItemApplicationCommandHandler extends CommandHandler<UpdateOptionalItemApplicationCommand> {


    @Inject
    private OptionalItemApplicationQuery optionalItemApplicationQuery;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    @Inject
    private OptionalItemApplicationRepository optionalItemApplicationRepository;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Override
    protected void handle(CommandHandlerContext<UpdateOptionalItemApplicationCommand> commandHandlerContext) {
        UpdateOptionalItemApplicationCommand command = commandHandlerContext.getCommand();
        Application application = command.getApplication().toDomain();
        AppDispInfoStartupOutput appDispInfoStartupOutput = command.getAppDispInfoStartup().toDomain();
        String cid = AppContexts.user().companyId();
        // アルゴリズム「4-1.詳細画面登録前の処理」を実行する
        this.detailBeforeProcessRegisterService.processBeforeDetailScreenRegistration(
                cid,
                application.getEmployeeID(),
                application.getAppDate().getApplicationDate(),
                EmploymentRootAtr.APPLICATION.value,
                application.getAppID(),
                application.getPrePostAtr(),
                application.getVersion(),
                "", "",
                appDispInfoStartupOutput
        );
        this.optionalItemApplicationQuery.checkBeforeUpdate(command.getOptItemAppCommand());
        this.optionalItemApplicationRepository.update(command.getOptItemAppCommand().toDomain());
        this.detailAfterUpdate.processAfterDetailScreenRegistration(cid, application.getAppID(), appDispInfoStartupOutput);
    }
}
