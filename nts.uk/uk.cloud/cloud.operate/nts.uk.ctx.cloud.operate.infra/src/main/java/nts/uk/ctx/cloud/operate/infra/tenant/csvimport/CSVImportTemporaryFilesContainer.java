package nts.uk.ctx.cloud.operate.infra.tenant.csvimport;

import java.nio.file.Path;

import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;

public class CSVImportTemporaryFilesContainer extends ApplicationTemporaryFilesContainer{

	/**
	 * Constructs.
	 * @param path path
	 */
	CSVImportTemporaryFilesContainer(Path path) {
		super(path);
	}
}
