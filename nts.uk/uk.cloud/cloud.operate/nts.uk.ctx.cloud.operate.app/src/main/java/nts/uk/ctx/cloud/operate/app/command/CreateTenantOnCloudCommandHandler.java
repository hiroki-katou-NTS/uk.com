package nts.uk.ctx.cloud.operate.app.command;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.cloud.operate.dom.service.CreateInitialOptionsService;
import nts.uk.shr.com.license.option.purchasestate.OptionLicensePurchaseState;

/**
 * クラウド環境にテナントを追加する
 * @author ai_muto
 *
 */
@Stateless
@Transactional
public class CreateTenantOnCloudCommandHandler extends CommandHandler<CreateTenantOnCloudCommand> {
    //@Inject
    //private CreateTenantPub createTenantPub;

//	@Inject
//    private CreateInitialOptionsService createInisialOptionService;

	@Override
	protected void handle(CommandHandlerContext<CreateTenantOnCloudCommand> context) {
		//TODO

		RequireImple require = new RequireImple();
		AtomTask at = CreateInitialOptionsService.create(require,
				context.getCommand().getTenanteCode(),
				context.getCommand().getOptionCode());

		at.run();
	}

	@AllArgsConstructor
	private class RequireImple implements CreateInitialOptionsService.Require{
		@Override
		public void add(OptionLicensePurchaseState optionLicensePurchaseState) {
			// TODO 自動生成されたメソッド・スタブ

		}

	}
}
