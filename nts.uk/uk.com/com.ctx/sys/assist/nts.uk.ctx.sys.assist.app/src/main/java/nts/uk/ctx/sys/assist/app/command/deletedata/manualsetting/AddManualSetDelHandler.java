/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.CategoryDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.EmployeesDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.ManualSetDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.manualsetting.ManualSetDeletionService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hiep.th
 *
 */
@Stateless
public class AddManualSetDelHandler extends CommandHandler<ManualSetDelCommand> {
	@Inject
	private ManualSetDeletionRepository repo;
	@Inject
	private EmployeesDeletionRepository repoEmp;
	@Inject
	private CategoryDeletionRepository repoCate;
	@Inject
	private ManualSetDeletionService manualSetDeletionService;

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
		String delId = IdentifierUtil.randomUniqueId();
		
		 // get login info
        LoginUserContext loginUserContext = AppContexts.user();
         // get company id
        String cid = loginUserContext .companyId();
        String sid = loginUserContext.userId();

		ManualSetDeletion domain = manualSetCmd.toDomain(delId, cid, sid);
		
		System.out.println("manualSetCmd: " + manualSetCmd);
		
//		// 画面の保存対象社員から「社員指定の有無」を判定する ( check radio button )presenceOfEmployee
		if (manualSetCmd.getHaveEmployeeSpecifiedFlg() == 1) {
			// 指定社員の有無＝「する」
			repoEmp.addAll(manualSetCmd.getEmployees(delId));
		}
//
//		if (manualSetCmd.getPresenceOfEmployee() == 0) {
			// 指定社員の有無＝「しない」の場合」

//		}
		
		repoCate.addAll(manualSetCmd.getCategories(delId));
		
		repo.addManualSetting(domain);
		
		manualSetDeletionService.serverManualSaveProcessing(delId);
	}
}
