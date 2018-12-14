package nts.uk.file.com.app.employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "Employment")
public class EmployeeExportImpl implements MasterListData {

	@Inject
	private EmploymentRepository employmentRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		// TODO Auto-generated method stub

		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("No", TextResource.localize("CMM008_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("使用区分", TextResource.localize("CMM008_8"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM008_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("略名", TextResource.localize("CMM008_10"),
				ColumnTextAlign.LEFT, "", true));
		
		columns.add(new MasterHeaderColumn("集計区分", TextResource.localize("CMM008_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤務種類", TextResource.localize("CMM008_8"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("就業時間帯", TextResource.localize("CMM008_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("集計条件以上", TextResource.localize("CMM008_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("以上", TextResource.localize("CMM008_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("集計条件未満", TextResource.localize("CMM008_8"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("未満", TextResource.localize("CMM008_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("対象項目", TextResource.localize("CMM008_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半日勤務区分（0.5回カウント）", TextResource.localize("CMM008_10"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub

		String companyId = AppContexts.user().companyId();

		List<MasterData> datas = new ArrayList<>();
		
		List<Employment> listEmployeeExport = employmentRepository.findAll(companyId);
		
//		listEmployeeExport = listEmployeeExport.stream().sorted(
//				Comparator.comparing(Employment::getCompanyId, Comparator.nullsLast(Integer::compareTo)))
//				.collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(listEmployeeExport)) {
			throw new BusinessException("Msg_393");
		} else {
			listEmployeeExport.stream().forEach(c -> {

				Map<String, Object> data = new HashMap<>();
				data.put("コード", c.getEmploymentCode());
				data.put("名称", c.getEmploymentName());
				data.put("外部コード", c.getEmpExternalCode());
				data.put("メモ", c.getMemo());
				datas.add(new MasterData(data, null, ""));
			});
		}
		return datas;
	}
	
	
}
