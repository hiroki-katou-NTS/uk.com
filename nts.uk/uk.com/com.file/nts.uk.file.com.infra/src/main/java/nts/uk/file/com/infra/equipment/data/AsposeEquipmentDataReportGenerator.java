package nts.uk.file.com.infra.equipment.data;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportDataSource;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportGenerator;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsposeEquipmentDataReportGenerator extends AsposeCellsReportGenerator
		implements EquipmentDataExportGenerator {

	@Override
	public void generate(FileGeneratorContext generatorContext, EquipmentDataExportDataSource dataSource) {
		// TODO Auto-generated method stub
		
	}
}
