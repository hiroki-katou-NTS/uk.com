package nts.uk.file.com.app.rolesetmenu;

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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID("RoleSetMenu")
public class RoleSetMenuExportImpl implements MasterListData {
	
	@Inject
	private RoleSetMenuRepository roleSetMenuRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		 List <MasterHeaderColumn> columns = new ArrayList<>();

	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_35, TextResource.localize("CAS011_35"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_36,TextResource.localize("CAS011_36"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_37, TextResource.localize("CAS011_37"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_38, TextResource.localize("CAS011_38"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_39,TextResource.localize("CAS011_39"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_40, TextResource.localize("CAS011_40"),
	                ColumnTextAlign.LEFT, "", true));
	        columns.add(new MasterHeaderColumn(RoleSetMenuColumn.CAS011_41, TextResource.localize("CAS011_41"),
	                ColumnTextAlign.LEFT, "", true));
	        return columns;
	}
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		return roleSetMenuRepository.exportDataExcel();
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
	
	
}
