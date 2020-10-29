package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.flowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.flowmenu.FixedClassification;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG034_フローページの作成.C：レイアウト複写.メニュー別OCD.フローメニュー作成を複写登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CopyFlowMenuCommandHandler extends CommandHandlerWithResult<CopyFlowMenuCommand, List<String>> {

	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;

	@Inject
	private FileExportService exportService;

	@Override
	protected List<String> handle(CommandHandlerContext<CopyFlowMenuCommand> context) {
		CopyFlowMenuCommand command = context.getCommand();
		List<String> fileIds = new ArrayList<>();
		Optional<CreateFlowMenu> optCreateFlowMenu = this.createFlowMenuRepository
				.findByPk(AppContexts.user().companyId(), command.getFlowMenuCode());
		optCreateFlowMenu.ifPresent(data -> {
			data.getFlowMenuLayout().ifPresent(layout -> {
				fileIds.add(layout.getFileId());
				layout.getFileAttachmentSettings().stream()
					.forEach(setting -> fileIds.add(setting.getFileId()));
				layout.getImageSettings().stream()
					.filter(setting -> setting.getIsFixed().equals(FixedClassification.RANDOM))
					.forEach(setting -> fileIds.add(setting.getFileId().get()));
				this.createFlowMenuRepository.delete(data);
			});
		});
		try {
			String htmlContent = unzip(command.getCreateFlowMenu().getFileId());
			command.getCreateFlowMenu()
					.setFileId(exportService.start(new FileExportCommand(
											command.getCreateFlowMenu().getFlowMenuCode(), 
											htmlContent))
									.getTaskId());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		this.createFlowMenuRepository.insert(CreateFlowMenu.createFromMemento(command.getCreateFlowMenu()));
		return fileIds;
	}

	private String unzip(String fileId) throws IOException {
		String path = this.exportService.extract(fileId).getPath();
		return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
	}
}
