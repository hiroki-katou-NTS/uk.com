package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * VO : 条件表示制御
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLT
 *
 */
@Value

public class PersonInforDisplayControl implements DomainValue {
	/**条件区分 --- 勤務予定の条件区分**/
	private final ConditionATRWorkSchedule conditionATR;
	
	/**	表示区分--- するしない区分 **/
	private final NotUseAtr displayCategory;
	
	
	
}
