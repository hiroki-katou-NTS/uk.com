package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
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
@DomainID(value = "Schedule")
public class HoriTotalCategoryExportImpl implements MasterListData {
	
	@Inject
	private HoriTotalCategoryExcelRepo horiTotalCategoryExcelRepo;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KML004_57"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML004_58"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("メモ", TextResource.localize("KML004_59"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("選択された対象項目", TextResource.localize("KML004_60"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("回数集計集計設定", TextResource.localize("KML004_53"),
				ColumnTextAlign.LEFT, "", true));
 
		return columns;
	}
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		String companyId = AppContexts.user().companyId();
		List<HoriTotalExel> listHoriTotalExel = horiTotalCategoryExcelRepo.getAll(companyId);
		List<MasterData> datas = new ArrayList<>();
		if (CollectionUtil.isEmpty(listHoriTotalExel)) {
			return null;
		} else {
			listHoriTotalExel.stream().forEach(c -> {
				
				Map<String, Object> data = new HashMap<>();
				putDataEmpty(data);
				if(c.getCode()!=null){
				data.put("コード", c.getCode());
				}
				if(c.getName()!=null){
				data.put("名称", c.getName());
				}
				if(c.getMemo()!=null){
				data.put("メモ", c.getMemo());
				}
				List<ItemTotalExcel> listItemTotals = c.getListItemTotals();
				if(!CollectionUtil.isEmpty(listItemTotals)){
					Map<String, Object> dataChild = new HashMap<>();
					for(int i = 0; i < listItemTotals.size();i++){
						ItemTotalExcel itemTotalExcel =listItemTotals.get(i);
						if(i == 0 && itemTotalExcel!=null){
							data.put("選択された対象項目", itemTotalExcel.getNameItemTotal());
							data.put("回数集計集計設定",itemTotalExcel.toStringItemSet());
							if(itemTotalExcel.getItemNo()==3){
								data.put("回数集計集計設定",itemTotalExcel.toStringListDaySet());
							}
							MasterData masterData = new MasterData(data, null, "");
							masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("メモ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("選択された対象項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("回数集計集計設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							datas.add(masterData);
							
						}
						else if(itemTotalExcel!=null){
							putDataEmpty(dataChild);
							dataChild.put("選択された対象項目", itemTotalExcel.getNameItemTotal());
							dataChild.put("回数集計集計設定",itemTotalExcel.toStringItemSet());
							if(itemTotalExcel.getItemNo()==3){
								dataChild.put("回数集計集計設定",itemTotalExcel.toStringListDaySet());
							}
							MasterData masterData = new MasterData(dataChild, null, "");
							masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("メモ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("選択された対象項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							masterData.cellAt("回数集計集計設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							datas.add(masterData);
						}
						
					}
				}
			});
		}
		return datas;
	}
	
	@Override
	public String mainSheetName() {
		return TextResource.localize("KML004_56");
	}
	
	private void putDataEmpty(Map<String, Object> data){
		data.put("コード","");
		data.put("名称","");
		data.put("メモ","");
		data.put("選択された対象項目","");
		data.put("回数集計集計設定","");
	}
	
	
}
