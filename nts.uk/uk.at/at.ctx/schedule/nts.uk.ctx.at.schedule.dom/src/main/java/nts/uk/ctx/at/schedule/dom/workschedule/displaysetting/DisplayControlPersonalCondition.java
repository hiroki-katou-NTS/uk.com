package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * 個人条件の表示制御
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */

public class DisplayControlPersonalCondition {

	@Getter
	/** 会社ID **/ 
	private final String companyID;
	@Getter
	/** List<条件表示制御> --- 条件表示制御リスト**/ 
	private List<PersonInforDisplayControl> listConditionDisplayControl;
	
	@Getter
	/** Optional<勤務予定の資格設定> 資格設定**/
	private Optional <WorkscheQualifi> otpWorkscheQualifi;

	public DisplayControlPersonalCondition(String companyID,
			List<PersonInforDisplayControl> listConditionDisplayControl, Optional<WorkscheQualifi> otpWorkscheQualifi) {
		super();
		//	[C-1] 個人条件の表示制御
		this.companyID = companyID;
		this.listConditionDisplayControl = listConditionDisplayControl;
		this.otpWorkscheQualifi = otpWorkscheQualifi;
	}
	

	
}
