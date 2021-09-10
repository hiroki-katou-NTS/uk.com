package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 様式９の明細設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の明細設定
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class DetailSettingOfForm9 implements DomainValue {
	
	/** 明細開始行 **/
	private final OutputRow detailStartLine;
	
	/** 表示人数 **/
	private final OnePageDisplayNumerOfPeople displayNumerOfPeople;
	
	/** 日にち行 **/
	private final OutputRow dateLine;
	
	/** 曜日行 **/
	private final OutputRow dayLine;
	
}
