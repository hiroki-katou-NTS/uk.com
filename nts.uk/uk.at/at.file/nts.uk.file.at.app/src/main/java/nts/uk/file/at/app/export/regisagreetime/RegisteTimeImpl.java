package nts.uk.file.at.app.export.regisagreetime;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("RegisterTime")
public class RegisteTimeImpl implements MasterListData {
	
	@Inject
	private RegistTimeRepository registTimeRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		 List <MasterHeaderColumn> columns = new ArrayList<>();

	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_80, TextResource.localize("KMK008_80"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.HEADER_NONE1,"",
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.HEADER_NONE2, "",
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RegistTimeColumn.KMK008_81, TextResource.localize("KMK008_81"),
	                ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		return registTimeRepository.getDataExport();
	}

	
	
	
}
