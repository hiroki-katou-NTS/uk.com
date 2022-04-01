package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
/**
 * 様式９の時間丸め設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の時間丸め設定
 * @author lan_lt
 *
 */
@Value
public class Form9TimeRoundingSetting implements DomainValue{
	
	/** 小数点以下 **/
	private final RoundingUnit roundingUnit;
	
	/** 端数処理  **/
	private final Rounding roundingMethod;

}
