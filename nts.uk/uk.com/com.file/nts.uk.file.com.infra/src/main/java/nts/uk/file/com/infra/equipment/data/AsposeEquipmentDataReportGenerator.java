package nts.uk.file.com.infra.equipment.data;

import javax.ejb.Stateless;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportDataSource;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class AsposeEquipmentDataReportGenerator extends AsposeCellsReportGenerator
		implements EquipmentDataExportGenerator {

	@Override
	public void generate(FileGeneratorContext generatorContext, EquipmentDataExportDataSource dataSource) {
		// TODO Auto-generated method stub
		
	}
}
