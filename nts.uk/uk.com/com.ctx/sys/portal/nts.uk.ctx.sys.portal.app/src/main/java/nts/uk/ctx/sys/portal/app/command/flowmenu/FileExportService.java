package nts.uk.ctx.sys.portal.app.command.flowmenu;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFileFactory;
import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;
import nts.arc.system.ServerSystemProperties;
import nts.arc.time.GeneralDateTime;

@Stateless
public class FileExportService extends ExportService<FileExportCommand> {
	
	private static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();
	
	@Inject
	private StoredFileStreamService fileStreamService;
	
	@Inject
	private ApplicationTemporaryFileFactory applicationTemporaryFileFactory;

	@Override
	protected void handle(ExportServiceContext<FileExportCommand> context) {
		String fileId = context.getQuery().getFileId();
		FileGeneratorContext generator = context.getGeneratorContext();	
		InputStream inputStream = this.fileStreamService.takeOutFromFileId(fileId);
		Path destinationDirectory = Paths.get(DATA_STORE_PATH + "//packs" + "//" + fileId);
		ApplicationTemporaryFilesContainer applicationTemporaryFilesContainer = applicationTemporaryFileFactory
				.createContainer();
		String fileName = String.format("%s_%s%s", "CCG034", context.getQuery().getFlowMenuCode(), GeneralDateTime.now().toString("yyyyMMddhhmmss"));
		applicationTemporaryFilesContainer.zipWithName(generator, fileName, false);
	}
}
