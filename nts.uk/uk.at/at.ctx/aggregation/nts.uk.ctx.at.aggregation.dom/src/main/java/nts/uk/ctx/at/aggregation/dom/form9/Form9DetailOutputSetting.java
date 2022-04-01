package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 様式９の詳細出力設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の詳細出力設定
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class Form9DetailOutputSetting implements DomainAggregate{
	
	/** 時間丸め設定 **/
	private Form9TimeRoundingSetting timeRoundingSetting;
	
	/** 勤務時間がない場合に属性を空白とするか **/
	private boolean allZeroIsAttributeBlank;
	
}
