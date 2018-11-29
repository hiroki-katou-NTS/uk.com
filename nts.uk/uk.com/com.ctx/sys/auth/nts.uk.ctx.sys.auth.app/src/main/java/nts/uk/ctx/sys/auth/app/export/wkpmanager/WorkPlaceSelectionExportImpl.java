package nts.uk.ctx.sys.auth.app.export.wkpmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.export.wkpmanager.WorkPlaceSelectionExportData;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthority;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.WorkPlaceAuthorityRepository;
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
	private WorkplaceManagerRepository workplaceManagerRepository;

	@Inject
	private WorkPlaceAuthorityRepository workPlaceAuthorityRepository;
	
	@Inject
	private WorkPlaceFunctionRepository workPlaceFunctionRepository;

	private static final String CMM051_32 = "職場コード";
	private static final String CMM051_33 = "職場名";
	private static final String CMM051_34 = "職場管理者コード";
	private static final String CMM051_35 = "職場管理者名";
	private static final String CMM051_36 = "期間．開始日";
	private static final String CMM051_37 = "期間．終了日";
	private static final String FUNCTION_NO_ = "FUNCTION_NO_";


	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
		columns.add(
				new MasterHeaderColumn(CMM051_32, TextResource.localize("CMM051_32"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CMM051_33, TextResource.localize("CMM051_33"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CMM051_34, TextResource.localize("CMM051_34"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CMM051_35, TextResource.localize("CMM051_35"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CMM051_36, TextResource.localize("CMM051_36"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CMM051_37, TextResource.localize("CMM051_37"), ColumnTextAlign.CENTER, "",
				true));
		for (WorkPlaceFunction item : workPlaceFunction) {
			columns.add(new MasterHeaderColumn(FUNCTION_NO_ + item.getFunctionNo().v(), item.getDisplayName().v(),
					ColumnTextAlign.CENTER, "", true));
		}
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String languageId = query.getLanguageId();
		String companyId = AppContexts.user().companyId();
		String workplaceId = query.getData().toString();
		List<MasterData> datas = new ArrayList<>();

		List<WorkPlaceSelectionExportData> listWorkPlaceSelectionExportData = workplaceManagerRepository
				.findAllWorkPlaceSelection(companyId, workplaceId, languageId);
		if (CollectionUtil.isEmpty(listWorkPlaceSelectionExportData)) {
			throw new BusinessException("Msg_7");
		} else {
			listWorkPlaceSelectionExportData.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				data.put(CMM051_32, c.getWorkplaceCode());
				data.put(CMM051_33, c.getWorkplaceName());
				data.put(CMM051_34, c.getEmployeeCode());
				data.put(CMM051_35, c.getBusinessName());
				data.put(CMM051_36, c.getStartDate());
				data.put(CMM051_37, c.getEndDate());
				
				List<WorkPlaceAuthority> workPlaceAuthority = workPlaceAuthorityRepository
						.getAllWorkPlaceAuthorityByRoleId(companyId, c.getWorkplaceManagerId());
				if (!workPlaceAuthority.isEmpty()) {
					List<WorkPlaceFunction> workPlaceFunction = workPlaceFunctionRepository.getAllWorkPlaceFunction();
					if (!workPlaceFunction.isEmpty()) {
						for (WorkPlaceFunction item : workPlaceFunction) {
							Boolean availability = workPlaceAuthority.stream()
									.filter(x -> x.getFunctionNo().v().equals(item.getFunctionNo().v())).findFirst()
									.map(x1 -> x1.isAvailability()).orElse(null);
							if (Objects.isNull(availability) || !availability) {
								data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "ー");
							} else {
								data.put(FUNCTION_NO_ + item.getFunctionNo().v(), "○");
							}
						}
					}
				}
				
				datas.add(new MasterData(data, null, ""));
			});
		}
		return datas;
	}
}
