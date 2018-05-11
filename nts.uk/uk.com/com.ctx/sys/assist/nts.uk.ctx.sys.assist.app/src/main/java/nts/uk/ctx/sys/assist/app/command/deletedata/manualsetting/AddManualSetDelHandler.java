/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.ManualSetDeletion;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hiep.th
 *
 */
@Stateless
public class AddManualSetDelHandler extends CommandHandler<ManualSetDelCommand> {
//	@Inject
//	private ManualSetOfDataSaveRepository repo;
//	@Inject
//	private TargetEmployeesRepository repoTargetEmp;
//	@Inject
//	private ManualSetOfDataSaveService manualSetOfDataSaveService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ManualSetDelCommand> context) {
		ManualSetDelCommand manualSetCmd = context.getCommand();
		String storeProcessingId = IdentifierUtil.randomUniqueId();
		
		 // get login info
        LoginUserContext loginUserContext = AppContexts.user();
         // get company id
        String cid = loginUserContext .companyId();
        String sid = loginUserContext.userId();


		ManualSetDeletion domain = manualSetCmd.toDomain(storeProcessingId, cid, sid);
		
		System.out.println("manualSetCmd: " + manualSetCmd);
		
//		// 画面の保存対象社員から「社員指定の有無」を判定する ( check radio button )presenceOfEmployee
//		if (manualSetCmd.getPresenceOfEmployee() == 1) {
//			// 指定社員の有無＝「する」
//			repoTargetEmp.addAll(domain.getEmployees());
//		}
//
//		if (manualSetCmd.getPresenceOfEmployee() == 0) {
//			// 指定社員の有無＝「しない」の場合」
//
//		}
//		
//		repo.addManualSetting(domain);
//
//		manualSetOfDataSaveService.start(storeProcessingId);

	}
}
