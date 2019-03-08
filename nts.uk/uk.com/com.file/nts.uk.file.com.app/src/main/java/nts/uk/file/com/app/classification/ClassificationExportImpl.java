package nts.uk.file.com.app.classification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
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
@DomainID(value = "Classification")
public class ClassificationExportImpl implements MasterListData{

	@Inject
	private ClassificationRepository classificationRepository;
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		
		String companyId = AppContexts.user().companyId();

		List<MasterData> datas = new ArrayList<>();
		
		List<Classification> listEmployeeExport = classificationRepository.getAllManagementCategory(companyId);
		
		if (CollectionUtil.isEmpty(listEmployeeExport)) {
			return null;
		} else {
			listEmployeeExport.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				putEmptyData(data);
				data.put("コード", c.getClassificationCode());
				data.put("名称", c.getClassificationName());
				data.put("メモ", c.getMemo());
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("メモ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				
				datas.add(masterData);
				
			});
		}
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("CMM014_6"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("CMM014_7"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("メモ", TextResource.localize("CMM014_8"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	private void putEmptyData(Map<String, Object> data){
		data.put("コード", "");
		data.put("名称", "");
		data.put("メモ", "");
	}

	@Override
	public String mainSheetName() {
		return TextResource.localize("CMM014_11");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}

}
