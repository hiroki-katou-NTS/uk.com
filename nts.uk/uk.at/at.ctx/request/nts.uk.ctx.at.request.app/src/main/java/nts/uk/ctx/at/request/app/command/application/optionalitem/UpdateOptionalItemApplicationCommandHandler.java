package nts.uk.ctx.at.request.app.command.application.optionalitem;


import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.find.application.optitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
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
    private DetailBeforeUpdate detailBeforeUpdate;

    @Inject
    private OptionalItemApplicationRepository optionalItemApplicationRepository;

    @Inject
    private DetailAfterUpdate detailAfterUpdate;


    @Inject
    private NewBeforeRegister registerBefore;

    @Override
    protected ProcessResult handle(CommandHandlerContext<UpdateOptionalItemApplicationCommand> commandHandlerContext) {
        UpdateOptionalItemApplicationCommand command = commandHandlerContext.getCommand();
        Application application = command.getAppDispInfoStartup().getAppDetailScreenInfo().getApplication().toDomain();
        AppDispInfoStartupOutput appDispInfoStartupOutput = command.getAppDispInfoStartup().toDomain();
        String cid = AppContexts.user().companyId();
        /**
         * ?????????????????????????????????????????????????????????
         */
        this.registerBefore.confirmationCheck(
                cid,
                application.getEmployeeID(),
                application.getAppDate().getApplicationDate(),
                command.getAppDispInfoStartup().toDomain()
        );
        /**
         * ?????????????????????????????????????????????????????????
         */
        this.detailBeforeUpdate.exclusiveCheck(cid, application.getAppID(), application.getVersion());
        /**
         * ???????????????????????????
         */
        this.optionalItemApplicationQuery.checkBeforeUpdate(command.getOptItemAppCommand());
        OptionalItemApplication optionalItemApplication = command.getOptItemAppCommand().toDomain();
        optionalItemApplication.setAppID(application.getAppID());
        /**
         * ?????????????????????????????????????????????????????????????????????
         */
        this.optionalItemApplicationRepository.update(optionalItemApplication, application);
        /**
         * 4-2.??????????????????????????????
         * */
        return this.detailAfterUpdate.processAfterDetailScreenRegistration(cid, application.getAppID(), appDispInfoStartupOutput);
    }
}
