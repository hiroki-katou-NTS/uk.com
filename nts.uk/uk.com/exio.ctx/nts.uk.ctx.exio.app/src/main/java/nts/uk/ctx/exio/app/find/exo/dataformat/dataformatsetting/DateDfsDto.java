package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.DateFormatSetting;

/**
 * 日付型データ形式設定
 */
@AllArgsConstructor
@Value
public class DateDfsDto {

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 出力項目コード
	 */
	private String outItemCd;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * NULL値置換
	 */
	private int nullValueSubstitution;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	public static DateDfsDto fromDomain(DateFormatSetting domain) {
		return new DateDfsDto(domain.getConditionSettingCode().v(), domain.getOutputItemCode().v(), domain.getCid(),
				domain.getNullValueReplace().value, domain.getFixedValue().value, domain.getFormatSelection().value,
				domain.getValueOfFixedValue().map(i -> i.v()).orElse(null),
				domain.getValueOfNullValueReplace().map(i -> i.v()).orElse(null));
	}

}
