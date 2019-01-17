package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.ConditionThresholdLimit;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.CountAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.SummaryAtr;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
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
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "TotalTimes")
public class TotalTimesRepositoryImpl implements MasterListData{

	@Inject
	private TotalTimesRepository totalTimesRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
	
	public List<String> listWorkTypeCodes, listCodes ;
	
	private static final String hyphen = "-";
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<TotalTimes> listTotalTimesItem = totalTimesRepository.getAllTotalTimes(companyId);
		
		if(CollectionUtil.isEmpty(listTotalTimesItem)){
			return null;
		}else{
			listTotalTimesItem.stream().forEach(c->{
				
				listWorkTypeCodes = new ArrayList<>();
				listCodes = new ArrayList<>();
				
				Map<String, Object> data = new HashMap<>();
				
					
				
				Optional<TotalTimes> optTotalTimes = this.totalTimesRepository.getTotalTimesDetail(companyId,
						c.getTotalCountNo());

				if(c.getUseAtr() == UseAtr.NotUse){
					// neu =0 
					// khong in ra
				}else{
					putEmptyData(data);	
					data.put("No", c.getTotalCountNo());
					data.put("使用区分", UseAtr.Use.nameId);

					List<String> lista= optTotalTimes.get().getSummaryList().get().getWorkTypeCodes();
					List<String> listb= optTotalTimes.get().getSummaryList().get().getWorkTimeCodes();
					
					for(int n= 0;n<lista.size();n++){
						// chua kt de add
						listWorkTypeCodes.add(lista.get(n));
					}
					listb.stream().forEach(n->{
						listCodes.add(n);
					});
					
					List<WorkTypeInfor> lst = workTypeRepository.getPossibleWorkTypeAndOrder(companyId, listWorkTypeCodes)
							.stream().sorted(Comparator
									.comparing(WorkTypeInfor::getWorkTypeCode))
									.collect(Collectors.toList());
					
					List<WorkTimeSetting> listFindByCodes = workTimeSettingRepository.findByCodes(companyId,listCodes)
							.stream().sorted(Comparator
									.comparing(WorkTimeSetting::getWorktimeCode))
									.collect(Collectors.toList());
					
					List<Integer> listAtdtemId = new ArrayList<>();
					listAtdtemId.add(c.getTotalCondition().getAtdItemId());
					
					List<DailyAttendanceItemNameAdapterDto> dailyAttendanceItemDomainServiceDtos = this.dailyAttendanceItemNameAdapter
							.getDailyAttendanceItemName(listAtdtemId);
					
					if(CollectionUtil.isEmpty(lst)){
						data.put("勤務種類","");
					}else{
						if(c.getSummaryAtr() == SummaryAtr.WORKINGTIME){
							data.put("勤務種類","");
						}else {
							//勤務種類
							String typeOfDuty = "";
							for (int n = 0; n < lst.size(); n++) {
								
								
								
								String workTypeName = (String)lst.get(n).getName();
								if (!StringUtil.isNullOrEmpty(lst.get(n).getWorkTypeCode(), true) && StringUtil.isNullOrEmpty(lst.get(n).getName(), true)) {
									workTypeName = TextResource.localize("KSM006_13");
								}
								
								if (n == 0) {
									typeOfDuty = lst.get(n).getWorkTypeCode() +""+ workTypeName;
								} else {
									typeOfDuty += ","+lst.get(n).getWorkTypeCode() + workTypeName;
								}
							}
							data.put("勤務種類", typeOfDuty);
						}
						
					}
					if(CollectionUtil.isEmpty(listFindByCodes)){
						data.put("就業時間帯", "");
					}else{
						if(c.getSummaryAtr() == SummaryAtr.DUTYTYPE){
							data.put("就業時間帯", "");
						}else{
							//sort
							listFindByCodes = listFindByCodes.stream()
									.sorted(Comparator.comparing(WorkTimeSetting::getWorktimeCode))
									.collect(Collectors.toList());

							//就業時間帯
							String  workingHours= "";
							for (int n = 0; n < listFindByCodes.size(); n++) {
								
								
								String workTimeName = String.valueOf(listFindByCodes.get(n).getWorkTimeDisplayName().getWorkTimeName());
								if (!StringUtil.isNullOrEmpty(String.valueOf(listFindByCodes.get(n).getWorktimeCode()), true) && StringUtil.isNullOrEmpty(workTimeName, true)) {
									workTimeName = TextResource.localize("KSM006_13");
								}
								
								if (n == 0) {
									workingHours = listFindByCodes.get(n).getWorktimeCode() +""+ workTimeName;
								} else {
									workingHours += ","+listFindByCodes.get(n).getWorktimeCode() + workTimeName;
								}
							}
							data.put("就業時間帯", workingHours);
						}
					}
					
					if(c.getTotalCondition().getLowerLimitSettingAtr() == UseAtr.Use){
						data.put("集計条件以上", "○");

						ConditionThresholdLimit cond = new ConditionThresholdLimit(c.getTotalCondition().getThresoldLowerLimit().valueAsMinutes());
						String ThresholdLimit = "";
						if(cond.minute()<10){
							ThresholdLimit =  cond.hour() +":0"+ cond.minute();
						}else{
							ThresholdLimit =  cond.hour() +":"+ cond.minute();
						}
						data.put("以上", ThresholdLimit + "以上");
					}else{
						data.put("集計条件以上", "-");
						data.put("以上", "");
					}
					
					if(c.getTotalCondition().getUpperLimitSettingAtr() == UseAtr.Use){
						data.put("集計条件未満", "○");
						ConditionThresholdLimit cond2 = new ConditionThresholdLimit(c.getTotalCondition().getThresoldUpperLimit().valueAsMinutes());
						String thresoldUpperLimit = "";
						if(cond2.minute()<10){
							thresoldUpperLimit =  cond2.hour() +":0"+ cond2.minute();
						}else{
							thresoldUpperLimit =  cond2.hour() +":"+ cond2.minute();
						}
						data.put("未満",thresoldUpperLimit +"未満");
					}else{
						data.put("集計条件未満", "-");
						data.put("未満", "");
					}
					
					if(c.getCountAtr() ==CountAtr.HALFDAY){
						data.put("半日勤務区分", "○");
					}else{
						data.put("半日勤務区分", "-");
					}
					
					if(c.getTotalCondition().getUpperLimitSettingAtr() == UseAtr.NotUse && c.getTotalCondition().getLowerLimitSettingAtr() == UseAtr.NotUse){
						data.put("対象項目", "");
					}else{
						if(CollectionUtil.isEmpty(dailyAttendanceItemDomainServiceDtos)){
							data.put("対象項目", "");
						}else{
							dailyAttendanceItemDomainServiceDtos.stream().forEach(m->{
								data.put("対象項目", m.getAttendanceItemName());
							});
						}
					}
					data.put("名称", c.getTotalTimesName());
					data.put("略名", c.getTotalTimesABName());
					if(c.getSummaryAtr() == SummaryAtr.DUTYTYPE){
						data.put("集計区分", SummaryAtr.DUTYTYPE.nameId);
					}else if(c.getSummaryAtr() == SummaryAtr.WORKINGTIME){
						data.put("集計区分", SummaryAtr.WORKINGTIME.nameId);
					}else{
						data.put("集計区分", SummaryAtr.COMBINATION.nameId);
					}
					
					MasterData masterData = new MasterData(data, null, "");
					masterData.cellAt("No").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("使用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("略名").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("集計区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("集計条件以上").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("以上").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("集計条件未満").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("未満").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("対象項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("半日勤務区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					datas.add(masterData);
				}
			});	
		}
		return datas;
	}


	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("No",TextResource.localize("KMK009_4"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("使用区分",TextResource.localize("KMK009_5"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称",TextResource.localize("KMK009_6"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("略名",TextResource.localize("KMK009_22"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("集計区分",TextResource.localize("KMK009_14"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤務種類",TextResource.localize("KMK009_8"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("就業時間帯",TextResource.localize("KMK009_9"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("集計条件以上",TextResource.localize("KMK009_10")+" "+TextResource.localize("KMK009_17"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("以上",TextResource.localize("KMK009_17"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("集計条件未満", TextResource.localize("KMK009_10")+" "+TextResource.localize("KMK009_20"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("未満", TextResource.localize("KMK009_20"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("対象項目", TextResource.localize("KMK009_23"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半日勤務区分", TextResource.localize("KMK009_11")+" "+TextResource.localize("KMK009_27"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	private void putEmptyData (Map<String, Object> data){
		data.put("No", "");
		data.put("使用区分", "");
		data.put("名称", "");
		data.put("略名", "");
		data.put("集計区分", "");
		data.put("勤務種類", "");
		data.put("就業時間帯", "");
		data.put("集計条件以上", "");
		data.put("以上", "");
		data.put("集計条件未満", "");
		data.put("未満","");
		data.put("対象項目", "");
		data.put("半日勤務区分", "");
		
	}


	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return TextResource.localize("KMK009_26");
	}
	
	


}
