package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.C：レイアウト複写.メニュー別OCD.フローメニュー作成を複写登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CopyFlowMenuCommandHandler extends CommandHandler<CopyFlowMenuCommand> {

	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;
	
	@Inject
	private FileExportService exportService;

	@Override
	protected void handle(CommandHandlerContext<CopyFlowMenuCommand> context) {
		CopyFlowMenuCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		command.getCreateFlowMenu().setCid(cid);
		Optional<CreateFlowMenu> optCreateFlowMenu = this.createFlowMenuRepository
				.findByPk(cid, command.getFlowMenuCode());
		if (optCreateFlowMenu.isPresent()) {
			CreateFlowMenu oldDomain = optCreateFlowMenu.get();
			if (oldDomain.getFlowMenuLayout().isPresent() && oldDomain.getFlowMenuLayout().get().getFileId() != null) {
				try {
					String htmlContent = unzip(oldDomain.getFlowMenuLayout().get().getFileId());
					command.getCreateFlowMenu().setFileId(exportService.start(new FileExportCommand(
								command.getCreateFlowMenu().getFlowMenuCode(), 
								htmlContent)
							).getTaskId());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.createFlowMenuRepository.delete(oldDomain);
		}
		if (!this.createFlowMenuRepository
				.findByPk(cid, command.getCreateFlowMenu().getFlowMenuCode()).isPresent()) {
			this.createFlowMenuRepository.insert(CreateFlowMenu.createFromMemento(command.getCreateFlowMenu()));
		} else throw new BusinessException("Msg_3");
	}
	
	
	private String unzip(String fileId) throws IOException {
		String path = this.exportService.extract(fileId);
		return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
	}
}
