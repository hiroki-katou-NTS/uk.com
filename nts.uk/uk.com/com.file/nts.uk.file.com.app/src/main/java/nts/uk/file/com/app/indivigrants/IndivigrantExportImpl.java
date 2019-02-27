package nts.uk.file.com.app.indivigrants;

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
@DomainID("Indivigrant")
public class IndivigrantExportImpl implements MasterListData {

	@Inject
	private IndivigrantRepository repository;
	
	//Header
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		//A6_1
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_40, TextResource.localize("CAS013_40"),
				ColumnTextAlign.LEFT, "", true));
		//A6_2
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_41, TextResource.localize("CAS013_41"),
				ColumnTextAlign.LEFT, "", true));
		//A6_3
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_42, TextResource.localize("CAS013_42"),
				ColumnTextAlign.LEFT, "", true));
		//A6_4
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_43, TextResource.localize("CAS013_43"),
				ColumnTextAlign.LEFT, "", true));
		//A6_5
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_44, TextResource.localize("CAS013_44"),
				ColumnTextAlign.LEFT, "", true));
		//A6_6
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_45, TextResource.localize("CAS013_45"),
				ColumnTextAlign.LEFT, "", true));
		//A6_7
		columns.add(new MasterHeaderColumn(IndivigrantColumn.CAS013_46, TextResource.localize("CAS013_46"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	//dataExport
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String baseDate =  query.getBaseDate().toString();
		List<MasterData> datas = new ArrayList<>();
		datas = repository.getDataExport(baseDate);
		return datas;
	}
	
	@Override
	public String mainSheetName(){
		return TextResource.localize("CAS013_47");
	}
	
	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.BASE_DATE;
	}
}
