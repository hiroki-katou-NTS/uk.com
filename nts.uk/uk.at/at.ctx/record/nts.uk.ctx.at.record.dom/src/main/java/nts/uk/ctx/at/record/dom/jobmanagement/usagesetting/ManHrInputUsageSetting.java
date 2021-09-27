package nts.uk.ctx.at.record.dom.jobmanagement.usagesetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.工数入力の利用設定
 * AR: 工数入力の利用設定
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class ManHrInputUsageSetting extends AggregateRoot {
	
	/** 使用区分*/
	private NotUseAtr usrAtr;
	
}
