package nts.uk.screen.at.app.ksu001.getinfoofInitstartup;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataScreenQueryGetInforDto_New {

	public GeneralDate startDate; // ・期間 A3_1_2
	public GeneralDate endDate;   // ・期間 A3_1_4
	public TargetOrgIdenInforDto targetOrgIdenInfor; // 対象組織識別情報
	public DisplayInfoOrganization displayInforOrganization; // 組織の表示情報
	public GeneralDate scheduleModifyStartDate;  // 修正可能開始日
	public Boolean usePublicAtr; // 公開を利用するか
	public Boolean useWorkAvailabilityAtr; // 勤務希望を利用するか

	// スケジュール修正の機能制御
	public ScheFunctionControlDto scheFunctionControl;
	// スケジュール修正職場別の機能制御
	public ScheFunctionCtrlByWorkplaceDto scheFunctionCtrlByWorkplace;
	// スケジュール修正共通の権限制御
	public List<ScheModifyAuthCtrlCommonDto> scheModifyAuthCtrlCommon;
	// スケジュール修正職場別の権限制御
	public List<ScheModifyAuthCtrlByWorkplaceDto> scheModifyAuthCtrlByWorkplace;
	
	public boolean medicalOP; // 医療OP
	
	public boolean nursingCareOP; // 介護OP 
	
	//List<個人計カテゴリ> list item combobox A11_1			 		
	
	//List<職場計カテゴリ> list item combobox A12_1					

}
