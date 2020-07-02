package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 個人条件の表示制御
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */
@AllArgsConstructor
public class DisplayControlPersonalCondition implements DomainAggregate {

	@Getter
	/** 会社ID **/ 
	private final String companyID;
	@Getter
	/** List<条件表示制御> --- 条件表示制御リスト**/ 
	private List<PersonInforDisplayControl> listConditionDisplayControl;
	
	@Getter
	/** Optional<勤務予定の資格設定> 資格設定**/
	private Optional <WorkscheQualifi> otpWorkscheQualifi;


	public static DisplayControlPersonalCondition get(String companyID,
			List<PersonInforDisplayControl> listConditionDisplayControl, Optional<WorkscheQualifi> otpWorkscheQualifi) {
		if((listConditionDisplayControl.stream().anyMatch(c-> c.getConditionATR()== ConditionATRWorkSchedule.QUALIFICATION)) && (!otpWorkscheQualifi.isPresent())){
			throw new BusinessException("Msg_1682");
		}
		return new DisplayControlPersonalCondition(companyID, listConditionDisplayControl, otpWorkscheQualifi);
	}
	

	
}
