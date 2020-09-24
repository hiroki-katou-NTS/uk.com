package nts.uk.file.at.app.export.divergence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.http.ContentTooLongException;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypesFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceTimeInputMethodDto;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceTimeInputMethodFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeHistoryDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.CompanyDivergenceReferenceTimeHistoryFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.history.DivergenceReferenceTimeUsageUnitDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.DivergenceReferenceTimeUsageUnitFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.history.WorkTypeDivergenceReferenceTimeDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.WorkTypeDivergenceReferenceTimeFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryDto;
import nts.uk.ctx.at.record.app.find.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.message.DivergenceTimeErrorAlarmMessageDto;
import nts.uk.ctx.at.record.app.find.divergence.time.message.DivergenceTimeErrorAlarmMessageFinder;
import nts.uk.ctx.at.record.app.find.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageDto;
import nts.uk.ctx.at.record.app.find.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergence.time.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeUseSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID(value = "Divergence")
public class DivergenceExportImpl  implements MasterListData{
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	@Inject
	private AttendanceNameDivergenceAdapter atName;
	
	@Inject
	DivergenceReasonSelectRepository divReasonSelectRepo;
	/** The div time inputmethod finder. */
	@Inject
	private DivergenceTimeInputMethodFinder divTimeInputmethodFinder;
	
	@Inject
	private CompanyDivergenceReferenceTimeHistoryFinder historyFinder;
	@Inject
	private CompanyDivergenceReferenceTimeFinder finder;
	
	@Inject
	private DivergenceTimeErrorAlarmMessageFinder findermesg;
	
	@Inject
	private BusinessTypesFinder findAllBusineesType;
	
	@Inject
	private WorkTypeDivergenceReferenceTimeHistoryFinder historyFinderwroktype;
	
	@Inject
	private WorkTypeDivergenceReferenceTimeFinder finderworkType;
	/** The WorkType Divergence Time Error Alarm Message finder. */
	@Inject
	private WorkTypeDivergenceTimeErrorAlarmMessageFinder finderWorkTypeMsg;
	@Inject
	private DivergenceReferenceTimeUsageUnitFinder finderTimeUsageUnit;
	
	private static final String select = "○";
	private static final String unselect = "-";

	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK011_42");
	}
	
	

	@Override
	public MasterListMode mainSheetMode() {
		return MasterListMode.NONE;
	}



	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 SheetData divergencetimeData = new SheetData(getDataDevergenceTimeCompany(query), getHeaderColumnsDevergenceTimeCompany(query), null, null,TextResource.localize("KMK011_47")+" "+TextResource.localize("Com_Company"),MasterListMode.BASE_DATE);	 
		 sheetDatas.add(divergencetimeData);
		 DivergenceReferenceTimeUsageUnitDto uset=finderTimeUsageUnit.findByCompanyId();
		 if(uset.getWorkTypeUseSet()){
			 SheetData divergenceworktypeData = new SheetData(getDataDevergenceTimeWorktype(query), getHeaderColumnsDevergenceTimeWorktype(query), null, null,TextResource.localize("KMK011_83"),MasterListMode.BASE_DATE);
			 sheetDatas.add(divergenceworktypeData);
		 }
		
		return sheetDatas;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();		
		// Get company id
		String companyId = AppContexts.user().companyId();
		// Get list divergence time
		List<DivergenceTimeRoot> listDivTime = this.divTimeRepo.getAllDivTime(companyId).stream().filter(x ->x.getDivTimeUseSet()==DivergenceTimeUseSet.USE).collect(Collectors.toList());
		listDivTime.sort((DivergenceTimeRoot o1,DivergenceTimeRoot o2) -> o1.getDivergenceTimeNo()-o2.getDivergenceTimeNo());
		// Check list empty
		if (listDivTime.isEmpty()) {
			return Collections.emptyList();
		}
		for(DivergenceTimeRoot divergenceTime:listDivTime){
			data=putEntryGetMasterDatas();			
		    data.put("No", divergenceTime.getDivergenceTimeNo());
			data.put("名称",  divergenceTime.getDivTimeName().v());
			data.put("使用区分", getDivergenceTimeUseSet(divergenceTime.getDivTimeUseSet().value));
			//if(divergenceTime.getDivTimeUseSet()==DivergenceTimeUseSet.USE){
				DivergenceTimeInputMethodDto resultDivergenceInfo = this.divTimeInputmethodFinder.getDivTimeInputMethodInfo(divergenceTime.getDivergenceTimeNo());
				data.put("乖離の種類", divergenceTime.getDivType().display);
				List<AttendanceNameDivergenceDto> rsattendanceName = atName.getDailyAttendanceItemName(divergenceTime.getTargetItems());
				String nameCode="";
				if (!CollectionUtil.isEmpty(rsattendanceName)) {
					rsattendanceName.sort((AttendanceNameDivergenceDto o1,AttendanceNameDivergenceDto o2) -> o1.getAttendanceItemDisplayNumber()-o2.getAttendanceItemDisplayNumber());
					nameCode=rsattendanceName.get(0).getAttendanceItemDisplayNumber()+rsattendanceName.get(0).getAttendanceItemName();
					for(int i=1;i<rsattendanceName.size();i++){
						nameCode=nameCode+","+rsattendanceName.get(i).getAttendanceItemDisplayNumber()+rsattendanceName.get(i).getAttendanceItemName();
					}
				}
				data.put("対象項目", nameCode);					
				data.put("乖離理由の選択", getDivergenceTimeReasonSelect(resultDivergenceInfo.isDivergenceReasonSelected()));
				if(resultDivergenceInfo.isDivergenceReasonSelected()){					
					// Get list divergence reason
					String codeNameReason="";
					List<DivergenceReasonSelect> reasonList = divReasonSelectRepo.findAllReason(divergenceTime.getDivergenceTimeNo(), companyId);
					if (!CollectionUtil.isEmpty(reasonList)) {
						codeNameReason=reasonList.get(0).getDivergenceReasonCode().v()+reasonList.get(0).getReason().v();
						for(int i=1;i<reasonList.size();i++){
							codeNameReason=codeNameReason+","+reasonList.get(i).getDivergenceReasonCode().v()+reasonList.get(i).getReason().v();
						}
						data.put("選択肢の設定", codeNameReason);
					}
					if(  divergenceTime.getErrorCancelMedthod().isReasonSelected()){
						data.put("乖離理由が選択された場合、エラー／アラームを解除する",select);
					}else{
						data.put("乖離理由が選択された場合、エラー／アラームを解除する",unselect);
					}
					
				}
				data.put("乖離理由の入力", getDivergenceTimeReasonSelect(resultDivergenceInfo.isDivergenceReasonInputed()));	
				if(resultDivergenceInfo.isDivergenceReasonInputed()){
					if(resultDivergenceInfo.isReasonInput()){
						data.put("乖離理由が入力された場合、エラー／アラームを解除する",select);
					}else{
						data.put("乖離理由が入力された場合、エラー／アラームを解除する",unselect);
					}
				}
					
				
		//	}
			MasterData masterData = new MasterData(data, null, "");
			Map<String, MasterCellData> rowData = masterData.getRowData();
			rowData.get("No").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("使用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("乖離の種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("対象項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("乖離理由の選択").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("選択肢の設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("乖離理由が選択された場合、エラー／アラームを解除する").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("乖離理由の入力").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			rowData.get("乖離理由が入力された場合、エラー／アラームを解除する").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
			datas.add(masterData);
		}

		
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("No","No",ColumnTextAlign.LEFT, "", true));				
		columns.add(new MasterHeaderColumn("名称","名称",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("使用区分","使用区分",ColumnTextAlign.LEFT, "", true));	
		columns.add(new MasterHeaderColumn("乖離の種類","乖離の種類",ColumnTextAlign.LEFT, "", true));	
		columns.add(new MasterHeaderColumn("対象項目","対象項目",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("乖離理由の選択","乖離理由の選択",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("選択肢の設定","選択肢の設定",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("乖離理由が選択された場合、エラー／アラームを解除する","乖離理由が選択された場合、エラー／アラームを解除する",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("乖離理由の入力","乖離理由の入力",ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("乖離理由が入力された場合、エラー／アラームを解除する","乖離理由が入力された場合、エラー／アラームを解除する",ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	private Map<String, Object> putEntryGetMasterDatas (){
		    Map<String, Object> data = new HashMap<>();
		   			data.put("No","");
					data.put("名称","");
					data.put("使用区分", "");
					data.put("乖離の種類","");
					data.put("対象項目", "");
					data.put("乖離理由の選択","");
					data.put("選択肢の設定","");
					data.put("乖離理由が選択された場合、エラー／アラームを解除する","");
					data.put("乖離理由の入力","");
					data.put("乖離理由が入力された場合、エラー／アラームを解除する","");
					
					
					
		return data;
		
	}
	
	
	private List<MasterHeaderColumn> getHeaderColumnsDevergenceTimeCompany (MasterListExportQuery query){
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("開始日", "開始日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("終了日", "終了日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("NO", "NO", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("使用区分", "使用区分", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("アラーム時間", "アラーム時間", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("エラー時間", "エラー時間", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("エラーメッセージ", "エラーメッセージ", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("アラームメッセージ", "アラームメッセージ", ColumnTextAlign.LEFT, "", true));
		return columns;
		
	}
	private List<MasterData> getDataDevergenceTimeCompany(MasterListExportQuery query){
		List<MasterData> datas = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		String companyId = AppContexts.user().companyId();
		GeneralDate basedate=query.getBaseDate();
		// Get list divergence time
		List<CompanyDivergenceReferenceTimeHistoryDto> listHistory=new ArrayList<>();
		List<DivergenceTimeRoot> listDivTime = this.divTimeRepo.getAllDivTime(companyId).stream().filter(x ->x.getDivTimeUseSet()==DivergenceTimeUseSet.USE).collect(Collectors.toList());
		listDivTime.sort((DivergenceTimeRoot o1,DivergenceTimeRoot o2) -> o1.getDivergenceTimeNo()-o2.getDivergenceTimeNo());
		
		List<CompanyDivergenceReferenceTimeHistoryDto> listHis=	this.historyFinder.getAllHistories();
		if (!CollectionUtil.isEmpty(listHis)) {
			listHistory=listHis.stream().filter(x ->x.getEndDate().date().getTime()>=basedate.date().getTime() && 
					basedate.date().getTime()>=x.getStartDate().date().getTime()).collect(Collectors.toList());
		}
				
		if (!CollectionUtil.isEmpty(listHistory)) {
			//set basedate
			for(int i=0;i<listHistory.size();i++){
				data=putEntryDevergenceTimeCompanyDatas();
				List<CompanyDivergenceReferenceTimeDto> listcompanyDiven =this.finder.getDivergenceReferenceTimeItemByHist(listHistory.get(i).getHistoryId());
				boolean check=true;
				if(!CollectionUtil.isEmpty(listcompanyDiven)){							
					for(int j=0;j<listcompanyDiven.size();j++){
						for(DivergenceTimeRoot divergenceTime: listDivTime){
							if(divergenceTime.getDivergenceTimeNo()==listcompanyDiven.get(j).getDivergenceTimeNo()){
								CompanyDivergenceReferenceTimeDto idata=listcompanyDiven.get(j);					
							/*	data.put("開始日",listHistory.get(i).getStartDate().toString());
								data.put("終了日",listHistory.get(i).getEndDate().toString());									
								if(check==false){
									data.put("開始日","");
									data.put("終了日","");	
								}
								check=false;
								data.put("NO",idata.getDivergenceTimeNo());
								data.put("使用区分",getDivergenceTimeUseSet(idata.getNotUseAtr()));
								data.put("名称",divergenceTime.getDivTimeName());*/
								if(idata.getNotUseAtr()==DivergenceTimeUseSet.USE.value){									
									data.put("開始日",listHistory.get(i).getStartDate().toString());
									data.put("終了日",listHistory.get(i).getEndDate().toString());									
									if(check==false){
										data.put("開始日","");
										data.put("終了日","");	
									}
									check=false;
									data.put("NO",idata.getDivergenceTimeNo());
									data.put("使用区分",getDivergenceTimeUseSet(idata.getNotUseAtr()));
									data.put("名称",divergenceTime.getDivTimeName());
									if(idata.getNotUseAtr()==DivergenceTimeUseSet.USE.value){
										data.put("アラーム時間",idata.getDivergenceReferenceTimeValue().getAlarmTime()==null?"": formatValueAttendance(idata.getDivergenceReferenceTimeValue().getAlarmTime()));
										data.put("エラー時間",idata.getDivergenceReferenceTimeValue().getErrorTime()==null?"":formatValueAttendance(idata.getDivergenceReferenceTimeValue().getErrorTime()));
									}else{
										data.put("アラーム時間","");
										data.put("エラー時間","");
									}
									DivergenceTimeErrorAlarmMessageDto divergenceTimeErrorAlarmMessageDto=findermesg.findByDivergenceTimeNo(idata.getDivergenceTimeNo());												
									if(divergenceTimeErrorAlarmMessageDto!=null && divergenceTimeErrorAlarmMessageDto.getErrorMessage() !=null ){
										data.put("エラーメッセージ",divergenceTimeErrorAlarmMessageDto.getErrorMessage());
									}
									if(divergenceTimeErrorAlarmMessageDto!=null && divergenceTimeErrorAlarmMessageDto.getAlarmMessage() !=null ){
										data.put("アラームメッセージ",divergenceTimeErrorAlarmMessageDto.getAlarmMessage());
									}
									
									MasterData masterData = new MasterData(data, null, "");
									Map<String, MasterCellData> rowData = masterData.getRowData();
									rowData.get("開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
									rowData.get("終了日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
									rowData.get("NO").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
									rowData.get("使用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
									rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
									rowData.get("アラーム時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
									rowData.get("エラー時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
									rowData.get("エラーメッセージ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
									rowData.get("アラームメッセージ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));							
									datas.add(masterData);
								}
							/*	else{
									data.put("アラーム時間","");
									data.put("エラー時間","");
									data.put("エラーメッセージ","");
									data.put("アラームメッセージ","");
									
								}
								*/
								
							}
						}
						
						
					}
				}
				
			}
		}
		
		return datas;
	}
	
	private Map<String, Object> putEntryDevergenceTimeCompanyDatas (){
	    Map<String, Object> data = new HashMap<>();
	   			data.put("開始日","");
				data.put("終了日","");
				data.put("NO", "");
				data.put("使用区分","");
				data.put("名称", "");
				data.put("アラーム時間","");
				data.put("エラー時間","");
				data.put("エラーメッセージ","");
				data.put("アラームメッセージ","");
											
	return data;
	
}
	private List<MasterHeaderColumn> getHeaderColumnsDevergenceTimeWorktype (MasterListExportQuery query){
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", "コード", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤務種別", "勤務種別", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("開始日", "開始日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("終了日", "終了日", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("NO", "NO", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("使用区分", "使用区分", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("アラーム時間", "アラーム時間", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("エラー時間", "エラー時間", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("エラーメッセージ", "エラーメッセージ", ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("アラームメッセージ", "アラームメッセージ", ColumnTextAlign.LEFT, "", true));
		return columns;
		
	}
	
	private List<MasterData> getDataDevergenceTimeWorktype(MasterListExportQuery query){
		List<MasterData> datas = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		List<BusinessTypeDto> listBusinesType=this.findAllBusineesType.findAll();
		listBusinesType.sort(Comparator.comparing(BusinessTypeDto:: getBusinessTypeCode));
		String companyId = AppContexts.user().companyId();
		GeneralDate basedate=query.getBaseDate();
		// Get list divergence time
		List<DivergenceTimeRoot> listDivTime = this.divTimeRepo.getAllDivTime(companyId).stream().filter(x ->x.getDivTimeUseSet()==DivergenceTimeUseSet.USE).collect(Collectors.toList());	
		listDivTime.sort((DivergenceTimeRoot o1,DivergenceTimeRoot o2) -> o1.getDivergenceTimeNo()-o2.getDivergenceTimeNo());
			
		if(!CollectionUtil.isEmpty(listBusinesType)){
			for(int i=0;i<listBusinesType.size();i++){
				
				BusinessTypeDto businessTypeDto=listBusinesType.get(i);
			/*	data.put("コード",businessTypeDto.getBusinessTypeCode());
				data.put("勤務種別",businessTypeDto.getBusinessTypeName());*/
				boolean checkShowTypecode=true;
				List<WorkTypeDivergenceReferenceTimeHistoryDto> listHiswork=this.historyFinderwroktype.getAllHistories(businessTypeDto.getBusinessTypeCode());
				List<WorkTypeDivergenceReferenceTimeHistoryDto> listwork= new ArrayList<>();		
				if(!CollectionUtil.isEmpty(listHiswork)){
					 listwork=listHiswork.stream().filter(x ->x!=null && x.getEndDate() !=null && x.getStartDate() !=null && x.getEndDate().date().getTime()>=basedate.date().getTime() && 
							basedate.date().getTime()>=x.getStartDate().date().getTime()).collect(Collectors.toList());
				}
				//set base date them tai day
					if(!CollectionUtil.isEmpty(listwork)){
						for (int j=0;j<listwork.size();j++){
							WorkTypeDivergenceReferenceTimeHistoryDto workTypeDivergenceReferenceTimeHistoryDto=listwork.get(j);
							boolean checkShowStardEnDate=true;
							if(workTypeDivergenceReferenceTimeHistoryDto !=null && businessTypeDto.getBusinessTypeCode() !=null
									&& workTypeDivergenceReferenceTimeHistoryDto.getHistoryId() !=null){
								
								/*if(workTypeDivergenceReferenceTimeHistoryDto !=null && workTypeDivergenceReferenceTimeHistoryDto.getStartDate() !=null ){
									data.put("開始日",workTypeDivergenceReferenceTimeHistoryDto.getStartDate().toString());
								}
								if(workTypeDivergenceReferenceTimeHistoryDto !=null && workTypeDivergenceReferenceTimeHistoryDto.getEndDate() !=null ){
									data.put("終了日",workTypeDivergenceReferenceTimeHistoryDto.getEndDate().toString());
								}	*/						
								List<WorkTypeDivergenceReferenceTimeDto> listworkDivergence=this.finderworkType.
										getDivergenceReferenceTimeItemByHist(workTypeDivergenceReferenceTimeHistoryDto.getHistoryId(), businessTypeDto.getBusinessTypeCode());
								
								listworkDivergence=listworkDivergence.stream().filter(x ->x.getNotUseAtr()==1).collect(Collectors.toList());
								if(!CollectionUtil.isEmpty(listworkDivergence)){
									for(WorkTypeDivergenceReferenceTimeDto workTypeDivergenceReferenceTimeDto:listworkDivergence){									
										for(DivergenceTimeRoot divergenceTime: listDivTime){
											if(workTypeDivergenceReferenceTimeDto.getDivergenceTimeNo()==divergenceTime.getDivergenceTimeNo()
													){												
													/*if(checkShowTypecode==false){
														data.put("コード","");
														data.put("勤務種別","");
													}
													checkShowTypecode=false;
													if(checkShowStardEnDate==false){
														data.put("開始日", "");
														data.put("終了日","");
													}
													 checkShowStardEnDate=false;
													data.put("NO", workTypeDivergenceReferenceTimeDto.getDivergenceTimeNo());
													data.put("使用区分",getDivergenceTimeUseSet(workTypeDivergenceReferenceTimeDto.getNotUseAtr()));
													data.put("名称",divergenceTime.getDivTimeName());*/
												//	if(workTypeDivergenceReferenceTimeDto.getNotUseAtr()==DivergenceTimeUseSet.USE.value){
														
																//add
														data=putEntryDevergenceTimeWorkTypeDatas();
														data.put("コード",businessTypeDto.getBusinessTypeCode());
														data.put("勤務種別",businessTypeDto.getBusinessTypeName());
														if(workTypeDivergenceReferenceTimeHistoryDto !=null && workTypeDivergenceReferenceTimeHistoryDto.getStartDate() !=null ){
															data.put("開始日",workTypeDivergenceReferenceTimeHistoryDto.getStartDate().toString());
														}
														if(workTypeDivergenceReferenceTimeHistoryDto !=null && workTypeDivergenceReferenceTimeHistoryDto.getEndDate() !=null ){
															data.put("終了日",workTypeDivergenceReferenceTimeHistoryDto.getEndDate().toString());
														}
														if(checkShowTypecode==false){
															data.put("コード","");
															data.put("勤務種別","");
														}
														checkShowTypecode=false;
														if(checkShowStardEnDate==false){
															data.put("開始日", "");
															data.put("終了日","");
														}
														 checkShowStardEnDate=false;
														data.put("NO", workTypeDivergenceReferenceTimeDto.getDivergenceTimeNo());
														data.put("使用区分",getDivergenceTimeUseSet(workTypeDivergenceReferenceTimeDto.getNotUseAtr()));
														data.put("名称",divergenceTime.getDivTimeName());
														//end
														if(workTypeDivergenceReferenceTimeDto.getDivergenceReferenceTimeValue().getAlarmTime() !=null){
															data.put("アラーム時間",formatValueAttendance( workTypeDivergenceReferenceTimeDto.getDivergenceReferenceTimeValue().getAlarmTime()));
														}
														if(workTypeDivergenceReferenceTimeDto.getDivergenceReferenceTimeValue().getErrorTime() !=null){
															data.put("エラー時間",formatValueAttendance( workTypeDivergenceReferenceTimeDto.getDivergenceReferenceTimeValue().getErrorTime()));
														}
														
														WorkTypeDivergenceTimeErrorAlarmMessageDto 	workTypeDivergenceTimeErrorAlarmMessageDto=	finderWorkTypeMsg.findByWorkTypeDivTimeErrAlarmMsg(Integer.valueOf(workTypeDivergenceReferenceTimeDto.getDivergenceTimeNo()) , new BusinessTypeCode(businessTypeDto.getBusinessTypeCode()));	
														if(workTypeDivergenceTimeErrorAlarmMessageDto!=null && workTypeDivergenceTimeErrorAlarmMessageDto.getErrorMessage() !=null ){
															data.put("エラーメッセージ",workTypeDivergenceTimeErrorAlarmMessageDto.getErrorMessage());
														}
														if(workTypeDivergenceTimeErrorAlarmMessageDto!=null && workTypeDivergenceTimeErrorAlarmMessageDto.getAlarmMessage() !=null ){
															data.put("アラームメッセージ",workTypeDivergenceTimeErrorAlarmMessageDto.getAlarmMessage());
														}
																												
													//}
												/*	else{
														data.put("アラーム時間","");
														data.put("エラー時間","");
														data.put("エラーメッセージ","");
														data.put("アラームメッセージ","");
													}*/
													
													MasterData masterData = new MasterData(data, null, "");
													Map<String, MasterCellData> rowData = masterData.getRowData();
													//set align
													rowData.get("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
													rowData.get("勤務種別").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));	
													rowData.get("開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
													rowData.get("終了日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
													rowData.get("NO").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
													rowData.get("使用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
													rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
													rowData.get("アラーム時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
													rowData.get("エラー時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
													rowData.get("エラーメッセージ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
													rowData.get("アラームメッセージ").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));	
													datas.add(masterData);
											}
										}								
										
									}
								}
								
							}
						}
					}
			}
		}				
		
		return datas;
	}
	
	private Map<String, Object> putEntryDevergenceTimeWorkTypeDatas (){
	    Map<String, Object> data = new HashMap<>();
	   			data.put("コード","");
				data.put("勤務種別","");
				data.put("開始日", "");
				data.put("終了日","");
				data.put("NO", "");
				data.put("使用区分","");
				data.put("名称","");
				data.put("アラーム時間","");
				data.put("エラー時間","");
				data.put("エラーメッセージ","");
				data.put("アラームメッセージ","");
											
	return data;
	
}
	
	public String getDivergenceTimeUseSet(int attr) {
		DivergenceTimeUseSet divergenceTimeUseSet = DivergenceTimeUseSet.valueOf(attr);
		switch (divergenceTimeUseSet) {
		case NOT_USE:
			return TextResource.localize("Enum_UseAtr_NotUse");
		case USE:
			return TextResource.localize("Enum_UseAtr_Use");		
		default:
			return "";
		}
	}
	
	public String getDivergenceTimeReasonSelect(boolean attr) {
		int att=attr==true?1:0;
		DivergenceTimeUseSet divergenceTimeUseSet = DivergenceTimeUseSet.valueOf(att);
		switch (divergenceTimeUseSet) {
		case NOT_USE:
			return TextResource.localize("Enum_UseAtr_NotUse");
		case USE:
			return TextResource.localize("Enum_UseAtr_Use");		
		default:
			return "";
		}
	}
	
	
	private String formatValueAttendance(int att) {
		Integer hours = att / 60, minutes = att % 60;
		return String.join("", hours < 10 ? "0" : "", hours.toString(), ":", minutes < 10 ? "0" : "", minutes.toString());
	}
	
	

}
