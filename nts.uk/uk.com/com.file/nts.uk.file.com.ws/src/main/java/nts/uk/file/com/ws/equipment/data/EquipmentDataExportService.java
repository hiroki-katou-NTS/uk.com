package nts.uk.file.com.ws.equipment.data;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.file.com.app.equipment.data.EquipmentDataExportFileQuery;
import nts.uk.file.com.app.equipment.data.EquipmentDataQuery;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class EquipmentDataExportService extends ExportService<EquipmentDataQuery> {

	@Inject
	private EquipmentDataExportFileQuery fileQuery;
	
	@Override
	protected void handle(ExportServiceContext<EquipmentDataQuery> context) {
		TaskDataSetter setter = context.getDataSetter();
		this.fileQuery.handle(context, context.getQuery());
	}

}
