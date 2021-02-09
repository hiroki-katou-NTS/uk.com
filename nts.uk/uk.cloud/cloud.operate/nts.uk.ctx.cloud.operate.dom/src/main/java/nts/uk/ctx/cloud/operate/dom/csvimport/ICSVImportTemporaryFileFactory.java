package nts.uk.ctx.cloud.operate.dom.csvimport;

import nts.arc.layer.infra.file.temp.ApplicationTemporaryFilesContainer;

public interface ICSVImportTemporaryFileFactory {

	ApplicationTemporaryFilesContainer createContainer(String contractCode);

}
