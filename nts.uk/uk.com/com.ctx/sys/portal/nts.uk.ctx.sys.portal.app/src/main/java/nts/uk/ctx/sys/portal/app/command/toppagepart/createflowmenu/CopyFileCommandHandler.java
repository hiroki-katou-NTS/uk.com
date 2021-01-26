package nts.uk.ctx.sys.portal.app.command.toppagepart.createflowmenu;

import java.io.IOException;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.portal.app.screenquery.topppagepart.createflowmenu.CreateFlowMenuDto;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenu;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FileAttachmentSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FixedClassification;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.ImageSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CopyFileCommandHandler extends CommandHandlerWithResult<String, CreateFlowMenuDto> {

	@Inject
	private CreateFlowMenuRepository createFlowMenuRepository;

	@Inject
	private FileExportService fileExportService;

	@Override
	protected CreateFlowMenuDto handle(CommandHandlerContext<String> context) {
		String flowMenuCode = context.getCommand();
		Optional<CreateFlowMenu> optCreateFlowMenu = this.createFlowMenuRepository
				.findByPk(AppContexts.user().companyId(), flowMenuCode);
		if (!optCreateFlowMenu.isPresent()) {
			return null;
		}

		CreateFlowMenu createFlowMenu = optCreateFlowMenu.get();
		if (!createFlowMenu.getFlowMenuLayout().isPresent()) {
			return null;
		}

		try {
			FlowMenuLayout layout = createFlowMenu.getFlowMenuLayout().get();
			for (FileAttachmentSetting fileSetting : layout.getFileAttachmentSettings()) {
				fileSetting.setFileId(this.fileExportService.copyFile(fileSetting.getFileId()));
			}
			for (ImageSetting imageSetting : layout.getImageSettings()) {
				if (imageSetting.getIsFixed().equals(FixedClassification.RANDOM)
						&& imageSetting.getFileId().isPresent()) {
					imageSetting.setFileId(
							Optional.ofNullable(this.fileExportService.copyFile(imageSetting.getFileId().get())));
				}
			}
		} catch (IOException e) {
			return null;
		}
		return CreateFlowMenuDto.fromDomain(createFlowMenu);
	}
}
