package nts.uk.ctx.at.request.app.command.application.optionalitem;


import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.optitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplication;
import nts.uk.ctx.at.request.dom.application.optional.OptionalItemApplicationRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateOptionalItemApplicationCommandHandler extends CommandHandlerWithResult<UpdateOptionalItemApplicationCommand, ProcessResult> {


    @Inject
    private OptionalItemApplicationQuery optionalItemApplicationQuery;

    @Inject
    private DetailBeforeUpdate detailBeforeProcessRegisterService;

    @Inject
    private OptionalItemApplicationRepository optionalItemApplicationRepository;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateOptionalItemApplicationCommand> commandHandlerContext) {
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
        OptionalItemApplication optionalItemApplication = command.getOptItemAppCommand().toDomain();
        optionalItemApplication.setAppID(application.getAppID());
        this.optionalItemApplicationRepository.update(optionalItemApplication);
        return this.detailAfterUpdate.processAfterDetailScreenRegistration(cid, application.getAppID(), appDispInfoStartupOutput);
    }
}
