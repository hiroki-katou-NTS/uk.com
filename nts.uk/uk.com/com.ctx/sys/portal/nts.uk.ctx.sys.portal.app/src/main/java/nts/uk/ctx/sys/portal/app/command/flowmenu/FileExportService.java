package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.system.ServerSystemProperties;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.HtmlFileGenerator;

@Stateless
public class FileExportService extends ExportService<FileExportCommand> {
	
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();
	
	@Inject
	private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;
	
	@Inject
	private HtmlFileGenerator htmlGenerator;

	@Override
	protected void handle(ExportServiceContext<FileExportCommand> context) {
		FileGeneratorContext generator = context.getGeneratorContext();	
		htmlGenerator.generate(generator, context.getQuery().getHtmlContent(), IdentifierUtil.randomUniqueId() + ".html");
		ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
				.createContainer();
		String fileName = String.format("%s_%s%s.%s", 
				"CCG034", 
				context.getQuery().getFlowMenuCode(), 
				GeneralDateTime.now().toString("yyyyMMddhhmmss"),
				"zip");
		applicationTemporaryFilesContainer.zipWithName(generator, fileName, false);
		applicationTemporaryFilesContainer.removeContainer();
	}
}
