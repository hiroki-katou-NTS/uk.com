package nts.uk.shr.infra.file.report.aspose.cells;

import nts.arc.layer.infra.file.export.FileGenerator;

public class AsposeCellsReportGenerator extends FileGenerator {

	public AsposeCellsReportContext createContext(String templateFileInResource) {
		
		return new AsposeCellsReportContext(this.getResourceAsStream(templateFileInResource));
	}
}
