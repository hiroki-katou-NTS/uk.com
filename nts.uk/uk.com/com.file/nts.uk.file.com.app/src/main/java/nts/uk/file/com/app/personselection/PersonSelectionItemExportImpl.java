package nts.uk.file.com.app.personselection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value = "PersonSelectionItem")
public class PersonSelectionItemExportImpl implements MasterListData {
	@Inject
	private PersonSelectionItemRepository personSelectionItemRepository;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_55, TextResource.localize("CPS017_55"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_56, TextResource.localize("CPS017_56"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_57, TextResource.localize("CPS017_57"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_58, TextResource.localize("CPS017_58"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_59, TextResource.localize("CPS017_59"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_60, TextResource.localize("CPS017_60"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_61, TextResource.localize("CPS017_61"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PersonSelectionItemColumn.CPS017_62, TextResource.localize("CPS017_62"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String contractCd = AppContexts.user().contractCode();
		String date = query.getBaseDate().toString();
		List<MasterData> datas = new ArrayList<>();
		datas = personSelectionItemRepository.getDataExport(contractCd, date);
		return datas;
	}
	
	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.BASE_DATE;
	}
}
