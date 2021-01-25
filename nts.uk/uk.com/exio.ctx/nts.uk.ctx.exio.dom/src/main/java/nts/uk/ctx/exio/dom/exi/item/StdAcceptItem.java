package nts.uk.ctx.exio.dom.exi.item;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionCode;
import nts.uk.ctx.exio.dom.exi.dataformat.DataFormatSetting;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;

/**
 * 受入項目（定型）
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StdAcceptItem extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private AcceptanceConditionCode conditionSetCd;

	/**
	 * 受入項目番号
	 */
	private int acceptItemNumber;
	
	/**
	 * CSV項目番号
	 */
	private Optional<Integer> csvItemNumber;

	/**
	 * CSV項目名
	 */
	private Optional<String> csvItemName;

	/**
	 * 項目型
	 */
	private ItemType itemType;

	/**
	 * カテゴリ項目NO
	 */
	private int categoryItemNo;

	/**
	 * 受入選別条件設定
	 */
	private Optional<AcScreenCondSet> acceptScreenConditionSetting;
	
	/**
	 * データ形式設定
	 */
	private Optional<DataFormatSetting> dataFormatSetting;

}
