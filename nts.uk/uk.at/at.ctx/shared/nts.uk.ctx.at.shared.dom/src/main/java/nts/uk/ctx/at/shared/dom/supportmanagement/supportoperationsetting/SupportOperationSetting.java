package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 応援の運用設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援の運用設定.応援の運用設定
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class SupportOperationSetting implements DomainAggregate{
	
	/** 利用するか **/
	private boolean isUsed;
	
	/** 応援先が応援者を指定できるか **/
	private boolean supportDestinationCanSpecifySupporter;
	
	/** 一日の最大応援回数 **/
	private MaximumNumberOfSupport maxNumberOfSupportOfDay;

}
