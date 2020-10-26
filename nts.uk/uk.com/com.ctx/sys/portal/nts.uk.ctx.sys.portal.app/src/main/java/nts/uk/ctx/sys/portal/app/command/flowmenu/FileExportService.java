package nts.uk.ctx.sys.portal.app.command.flowmenu;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.flowmenu.HtmlFileGenerator;

@Stateless
public class FileExportService extends ExportService<FileExportCommand> {
	
	@Inject
	private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;
	
	@Inject
	private HtmlFileGenerator htmlGenerator;

	@Override
	protected void handle(ExportServiceContext<FileExportCommand> context) {
		//Get fileGeneratorContext
		FileGeneratorContext generator = context.getGeneratorContext();	
		//Write html content into html file and store as temp
		htmlGenerator.generate(generator, context.getQuery().getHtmlContent(), IdentifierUtil.randomUniqueId() + ".html");
		//Zip html file into a .zip and delete the temp html file
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
	
	public String extract(String fileId) {
		return null;
	}
}
