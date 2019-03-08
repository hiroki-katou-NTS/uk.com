package nts.uk.file.com.app.workplaceselection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunction;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceFunctionRepository;
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
@DomainID(value = "WorkPlaceSelection")
public class WorkPlaceSelectionExportImpl implements MasterListData {
	@Inject
	private WorkPlaceSelectionRepository workplaceManagerRepository;

	@Inject
	private WorkPlaceFunctionRepository workPlaceFunctionRepository;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_27, TextResource.localize("CMM051_27"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_28, TextResource.localize("CMM051_28"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_29, TextResource.localize("CMM051_29"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_30, TextResource.localize("CMM051_30"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_31, TextResource.localize("CMM051_31"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceSelectionColumn.CMM051_32, TextResource.localize("CMM051_32"),
				ColumnTextAlign.LEFT, "", true));
		for (WorkPlaceFunction item : workPlaceFunction) {
			columns.add(new MasterHeaderColumn(item.getFunctionNo().v().toString(), item.getDisplayName().v(),
					ColumnTextAlign.LEFT, "", true));
		}
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		String baseDate = query.getBaseDate().toString();
		List<MasterData> datas = new ArrayList<>();
		List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
		datas = workplaceManagerRepository.getDataExport(companyId, workPlaceFunction, baseDate);
		return datas;
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.BASE_DATE;
	}
}
