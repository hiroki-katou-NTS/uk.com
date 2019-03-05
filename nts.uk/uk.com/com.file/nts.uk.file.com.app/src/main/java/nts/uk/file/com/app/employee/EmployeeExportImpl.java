package nts.uk.file.com.app.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

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
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM008_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM008_8"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("メモ", TextResource.localize("CMM008_10"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("外部コード", TextResource.localize("CMM008_9"),
				ColumnTextAlign.LEFT, "", true));
		
	
		return columns;
	}

	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub

		String companyId = AppContexts.user().companyId();

		List<MasterData> datas = new ArrayList<>();
		
		List<Employment> listEmployeeExport = employmentRepository.findAll(companyId);

		if (CollectionUtil.isEmpty(listEmployeeExport)) {
			return null;
		} else {
			listEmployeeExport.stream().forEach(c -> {

				Map<String, Object> data = new HashMap<>();
				
				putEmptyData(data);
				
				data.put("コード", c.getEmploymentCode());
				data.put("名称", c.getEmploymentName());
				data.put("外部コード", c.getEmpExternalCode());
				data.put("メモ", c.getMemo());
				
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("メモ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("外部コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				
				datas.add(masterData);
			});
		}
		return datas;
	}


	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return TextResource.localize("CMM008_14");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
	
	private void putEmptyData(Map<String, Object> data){
		data.put("コード","");
		data.put("名称", "");
		data.put("メモ", "");
		data.put("外部コード","");
		
	}
	
	
	
	
	
}
