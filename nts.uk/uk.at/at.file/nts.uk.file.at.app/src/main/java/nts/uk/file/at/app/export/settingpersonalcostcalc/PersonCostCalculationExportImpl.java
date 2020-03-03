package nts.uk.file.at.app.export.settingpersonalcostcalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PersonCostCalculationFinder;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PersonCostCalculationSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumSetDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.ShortAttendanceItemDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value = "PersonCostCalculation")
public class PersonCostCalculationExportImpl implements  MasterListData {
	
	@Inject
	private PersonCostCalculationFinder personCostCalculationSettingFinder;
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		String languageId = query.getLanguageId();
		Map<String, Object> data = new HashMap<>();		
		// Get company id
		//String companyId = AppContexts.user().companyId();
		GeneralDate basedate=query.getBaseDate();
		List<PersonCostCalculationSettingDto> listPersonCostCalculationSetting=	personCostCalculationSettingFinder.findPersonCostCalculationByCompanyID().
				stream().filter(x ->x.getEndDate().date().getTime()>=basedate.date().getTime() && 
				basedate.date().getTime()>=x.getStartDate().date().getTime()).collect(Collectors.toList());
		
	if(!CollectionUtil.isEmpty(listPersonCostCalculationSetting)){
			for(PersonCostCalculationSettingDto personCostCalculationSettingDto:listPersonCostCalculationSetting){			
				PersonCostCalculationSettingDto personCostCalculationSetting	=personCostCalculationSettingFinder.findByHistoryID(personCostCalculationSettingDto.getHistoryID());
				
				data=putEntryMasterDatas();				
				data.put("有効開始日",personCostCalculationSettingDto.getStartDate() !=null? personCostCalculationSettingDto.getStartDate().toString():"");
				data.put("終了日",personCostCalculationSettingDto.getEndDate() !=null? personCostCalculationSettingDto.getEndDate().toString():"");
				data.put("人件費を計算する際に使用する単価",!Objects.isNull(personCostCalculationSettingDto.getUnitPrice())==true?getTextResource( personCostCalculationSettingDto.getUnitPrice()):"");
				data.put("備考",!Objects.isNull(personCostCalculationSettingDto.getMemo())==true? personCostCalculationSettingDto.getMemo():"");	
				boolean checkshow=true;
				if(personCostCalculationSetting !=null){
					List<PremiumSetDto> premiumSets=personCostCalculationSetting.getPremiumSets();
					List<PremiumItemDto> listPremiumItemLanguage = personCostCalculationSettingFinder.findWorkTypeLanguage(languageId);
					premiumSets.sort((PremiumSetDto o1,PremiumSetDto o2) -> o1.getDisplayNumber()-o2.getDisplayNumber() );
					if(!CollectionUtil.isEmpty(premiumSets)){
						for(PremiumSetDto premiumSetDto:premiumSets){
							if(premiumSetDto.getUseAtr()==1){
								String nameEnglish = "";
								for(PremiumItemDto premiumItemDto :listPremiumItemLanguage) {
									if(premiumItemDto.getDisplayNumber() == premiumSetDto.getDisplayNumber()) {
										nameEnglish = premiumItemDto.getName();
										break;
									}
								}
								
								data.put("他言語名称",nameEnglish);
								data.put("名称",premiumSetDto.getName());
								data.put("割増率",premiumSetDto.getRate()+"%");	
								//
								List<ShortAttendanceItemDto> listatt=premiumSetDto.getAttendanceItems();
								List<Integer> litatendenceId=listatt.stream().map(x ->x.getShortAttendanceID()).collect(Collectors.toList());
								List<AttendanceNamePriniumDto> rs=personCostCalculationSettingFinder.atNames(litatendenceId);
								rs.sort((AttendanceNamePriniumDto o1,AttendanceNamePriniumDto o2) -> o1.getAttendanceItemDisplayNumber()-o2.getAttendanceItemDisplayNumber());
								String codeName="";
								if(!CollectionUtil.isEmpty(rs)){									
									codeName=rs.get(0).getAttendanceItemDisplayNumber()+rs.get(0).getAttendanceItemName();
									for(int i=1;i<rs.size();i++){
										AttendanceNamePriniumDto attdanceName=rs.get(i);
										codeName=codeName+","+attdanceName.getAttendanceItemDisplayNumber()+attdanceName.getAttendanceItemName();
									}
								}
								data.put("人件費計算用時間",codeName);	
								//
								if(checkshow==false){
									data.put("有効開始日","");
									data.put("終了日","");
									data.put("人件費を計算する際に使用する単価", "");
									data.put("備考","");
								}
								checkshow=false;
								MasterData masterData = new MasterData(data, null, "");
								Map<String, MasterCellData> rowData = masterData.getRowData();
								rowData.get("有効開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
								rowData.get("終了日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
								rowData.get("人件費を計算する際に使用する単価").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
								rowData.get("備考").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
								rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
								rowData.get("割増率").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
								rowData.get("人件費計算用時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
								rowData.get("他言語名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
								datas.add(masterData);
							}
							
						//	personCostCalculationSettingFinder.atNames(premiumSetDto.getAttendanceItems())
						//	data.put("人件費計算用時間","");
						}
					}
				}				
				
				
			}
		}
	
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("有効開始日", "有効開始日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("終了日", "終了日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("人件費を計算する際に使用する単価", "人件費を計算する際に使用する単価", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("備考", "備考", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("割増率", "割増率", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("人件費計算用時間", "人件費計算用時間", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("他言語名称", "他言語名称", ColumnTextAlign.LEFT, "", true));
		
		return columns;
		
		
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.BASE_DATE;
	}
	
	private Map<String, Object> putEntryMasterDatas(){
		 Map<String, Object> data = new HashMap<>();
			data.put("有効開始日","");
			data.put("終了日","");
			data.put("人件費を計算する際に使用する単価", "");
			data.put("備考","");
			data.put("名称", "");
			data.put("割増率","");
			data.put("人件費計算用時間","");	
			data.put("他言語名称", "");
          return data;
	}
	
	// TextResource.localize("KMK007_67")
	private String getTextResource(int att){
		String value="";
		switch (att) {
		case 0:
			value= TextResource.localize("KML001_22");
			break;
		case 1:
			value=  TextResource.localize("KML001_23");
			break;
		case 2:
			value=  TextResource.localize("KML001_24");
			break;
		case 3:
			value=  TextResource.localize("KML001_25");
			break;
		case 4:
			value=  TextResource.localize("KML001_26");
			break;
		default:
			break;
		}
		return value;
	}
	 
	
	
	

	

	
}
