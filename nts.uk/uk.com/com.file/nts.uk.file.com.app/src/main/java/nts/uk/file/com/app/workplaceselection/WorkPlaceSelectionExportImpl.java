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
			columns.add(new MasterHeaderColumn(item.getFunctionNo().v().toString(), WorkPlaceSelectionColumn.CMM051_32_2+item.getDisplayOrder(),
					ColumnTextAlign.LEFT, "", true));
		}
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		String baseDate = query.getStartDate().toString();
		List<MasterData> datas = new ArrayList<>();
		List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();

		/*
		 * if (CollectionUtil.isEmpty(listWorkPlaceSelectionExportData)) { throw
		 * new BusinessException("Msg_7"); } else {
		 * listWorkPlaceSelectionExportData.stream().forEach(c -> { Map<String,
		 * Object> data = new HashMap<>(); data.put(CMM051_32,
		 * c.getWorkplaceCode()); data.put(CMM051_33, c.getWorkplaceName());
		 * data.put(CMM051_34, c.getEmployeeCode()); data.put(CMM051_35,
		 * c.getBusinessName()); data.put(CMM051_36, c.getStartDate());
		 * data.put(CMM051_37, c.getEndDate());
		 * c.getFunctionNo().entrySet().forEach(x -> { data.put(FUNCTION_NO_ +
		 * x.getKey(), "1".equals(x.getValue()) ? "○" : "ー"); }); //
		 * List<WorkPlaceAuthority> workPlaceAuthority =
		 * workPlaceAuthorityRepository //
		 * .getAllWorkPlaceAuthorityByRoleId(companyId,
		 * c.getWorkplaceManagerId()); //if (!workPlaceAuthority.isEmpty()) {
		 * //List<WorkPlaceFunction> workPlaceFunction =
		 * workPlaceFunctionRepository.getAllWorkPlaceFunction(); //if
		 * (!workPlaceFunction.isEmpty()) { for (WorkPlaceFunction item :
		 * workPlaceFunction) { Boolean availability =
		 * workPlaceAuthority.stream() .filter(x ->
		 * x.getFunctionNo().v().equals(item.getFunctionNo().v())).findFirst()
		 * .map(x1 -> x1.isAvailability()).orElse(null); if
		 * (Objects.isNull(availability) || !availability) {
		 * data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "ー"); } else {
		 * data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "○"); } } //} //}
		 * datas.add(new MasterData(data, null, "")); }); } return datas;
		 */

		datas = workplaceManagerRepository.getDataExport(companyId, workPlaceFunction, baseDate);
		return datas;
	}
}
