package nts.uk.file.at.app.export.otpitem;

import java.util.List;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

public interface CalFormulasItemRepository {
	/* Export Excel */
	List<MasterData> getDataTableOneExport(String companyId);
	List<MasterData> getDataTableTwoExport(String companyId);
	List<MasterData> getDataTableThreeExport(String companyId);
}
