package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

public interface AnnualWorkScheduleRepository {
	List<ExportItem> geExportItems();
	ExportData getData(String cid, String setItemsOutputCd);
}
