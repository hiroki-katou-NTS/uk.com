package nts.uk.ctx.at.schedule.app.export.budgetexcel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author Hoidd
 *
 */
@Stateless
@DomainID(value = "Budget")
public class BudgetExportImpl implements MasterListData {
	
	@Inject
	private BudgetExcelRepo budgetExcelRepo;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KDL024_16"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KDL024_17"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("属性", TextResource.localize("KDL024_18"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("単位", TextResource.localize("KDL024_19"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<ExternalBudget> listExternalBudget = budgetExcelRepo.findAll(companyId);
		listExternalBudget.sort(Comparator.comparing(ExternalBudget::getExternalBudgetCd));
		if (CollectionUtil.isEmpty(listExternalBudget)) {
			return null;
		} else {
			listExternalBudget.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				putDataEmpty(data);
				if (c.getExternalBudgetCd() != null) {
					data.put("コード", c.getExternalBudgetCd());
				}
				if (c.getExternalBudgetCd() != null) {
					data.put("名称", c.getExternalBudgetName());
				}
				if (c.getExternalBudgetCd() != null) {
					data.put("属性", c.getBudgetAtr().toName());
				}
				if (c.getExternalBudgetCd() != null) {
					data.put("単位", c.getUnitAtr().toName());
				}
				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("属性").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("単位").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				datas.add(masterData);
			});
		}
				
		return datas;
	}
	
	@Override
	public String mainSheetName() {
		return TextResource.localize("KDL024_15");
	}
	
	private void putDataEmpty(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("属性","");
		data.put("単位","");
	}
	
	
}
