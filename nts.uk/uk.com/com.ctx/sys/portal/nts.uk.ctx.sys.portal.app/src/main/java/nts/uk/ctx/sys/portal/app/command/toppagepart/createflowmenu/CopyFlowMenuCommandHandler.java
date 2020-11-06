package nts.uk.ctx.sys.portal.app.command.toppagepart.createflowmenu;

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

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FixedClassification;
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

	@Inject
	private FileStorage fileStorage;

	@Override
	protected void handle(CommandHandlerContext<CopyFlowMenuCommand> context) {
		CopyFlowMenuCommand command = context.getCommand();
		List<String> fileIds = new ArrayList<>();

		// 1. get(ログイン会社ID、フローメニューコード)
		Optional<CreateFlowMenu> optCreateFlowMenu = this.createFlowMenuRepository
				.findByPk(AppContexts.user().companyId(), command.getFlowMenuCode());
		// 2. not フローメニュー作成 empty
		optCreateFlowMenu.ifPresent(data -> data.getFlowMenuLayout().ifPresent(layout -> {
			fileIds.add(layout.getFileId());
			layout.getFileAttachmentSettings().stream().forEach(setting -> fileIds.add(setting.getFileId()));
			layout.getImageSettings().stream()
					.filter(setting -> setting.getIsFixed().equals(FixedClassification.RANDOM))
					.forEach(setting -> fileIds.add(setting.getFileId().get()));
			this.createFlowMenuRepository.delete(data);
		}));
		// 3. delete(ログイン会社ID、フローメニューコード)
		if (command.getCreateFlowMenu().getFileId() != null) {
			try {
				// 4. Input内容のファイルIDを別のファイルIDで作成する
				String htmlContent = unzip(command.getCreateFlowMenu().getFileId());
				command.getCreateFlowMenu()
						.setFileId(exportService.start(
								new FileExportCommand(command.getCreateFlowMenu().getFlowMenuCode(), htmlContent))
								.getTaskId());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		// 5. create(inputフローメニュー作成)
		// 6. set(フローメニュー作成)
		// 7. persist
		this.createFlowMenuRepository.insert(CreateFlowMenu.createFromMemento(command.getCreateFlowMenu()));
		// 削除対象に登録されているファイルIDのファイルを削除する
		fileIds.forEach(this.fileStorage::delete);
	}

	private String unzip(String fileId) throws IOException {
		String path = this.exportService.extract(fileId).getPath();
		return FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);
	}
}
