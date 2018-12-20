package nts.uk.file.at.app.export.divergence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.http.ContentTooLongException;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceTimeInputMethodDto;
import nts.uk.ctx.at.record.app.find.divergence.time.DivergenceTimeInputMethodFinder;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceAdapter;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

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
	private static final String select = "〇";
	private static final String unselect = "-";

	@Override
	public String mainSheetName() {
		return "乖離時間の名称の登録";
	}

	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 SheetData divergencetimeData = new SheetData(getDataDevergenceTimeCompany(query), getHeaderColumnsDevergenceTimeCompany(query), null, null, "乖離基準時間の設定 会社");
		 sheetDatas.add(divergencetimeData);
		return sheetDatas;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();		
		// Get company id
		String companyId = AppContexts.user().companyId();
	//	data=putEntryGetMasterDatas();
		// Get list divergence time
		List<DivergenceTime> listDivTime = this.divTimeRepo.getAllDivTime(companyId);
		// Check list empty
		if (listDivTime.isEmpty()) {
			return Collections.emptyList();
		}
		for(DivergenceTime divergenceTime:listDivTime){
			data=putEntryGetMasterDatas();
			divergenceTime.getDivergenceTimeNo();
		    divergenceTime.getDivTimeName();
		    divergenceTime.getDivTimeUseSet();
		    divergenceTime.getTargetItems();
		    divergenceTime.getErrorCancelMedthod().isReasonSelected();
		    data.put("No", divergenceTime.getDivergenceTimeNo());
			data.put("名称",  divergenceTime.getDivTimeName().v());
			data.put("使用区分", getDivergenceTimeUseSet(divergenceTime.getDivTimeUseSet().value));
			if(divergenceTime.getDivTimeUseSet()==DivergenceTimeUseSet.USE){
				DivergenceTimeInputMethodDto resultDivergenceInfo = this.divTimeInputmethodFinder.getDivTimeInputMethodInfo(divergenceTime.getDivergenceTimeNo());
				data.put("乖離の種類", divergenceTime.getDivTimeName().v());
				List<AttendanceNameDivergenceDto> rsattendanceName = atName.getDailyAttendanceItemName(divergenceTime.getTargetItems());
				String nameCode="";
				if (!CollectionUtil.isEmpty(rsattendanceName)) {
					nameCode=rsattendanceName.get(0).getAttendanceItemDisplayNumber()+rsattendanceName.get(0).getAttendanceItemName();
					for(int i=1;i<rsattendanceName.size();i++){
						nameCode=nameCode+","+rsattendanceName.get(i).getAttendanceItemDisplayNumber()+rsattendanceName.get(i).getAttendanceItemName();
					}
				}
				data.put("対象項目", nameCode);			
			/*	resultDivergenceInfo.isDivergenceReasonSelected();
				boolean reasonSelected= divergenceTime.getErrorCancelMedthod().isReasonSelected();*/
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
					if(resultDivergenceInfo.isReasonInput()){
						data.put("乖離理由が入力された場合、エラー／アラームを解除する",select);
					}else{
						data.put("乖離理由が入力された場合、エラー／アラームを解除する",unselect);
					}
				
			}
	
			datas.add(new MasterData(data, null, ""));
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
		columns.add(new MasterHeaderColumn("コード", "コード", ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.CENTER, "", true));
		return columns;
		
	}
	private List<MasterData> getDataDevergenceTimeCompany(MasterListExportQuery query){
		List<MasterData> datas = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		data.put("コード", "code2");
		data.put("名称", "name2");
		datas.add(new MasterData(data, null, ""));
		return datas;
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
	
	

}
