package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.CharacterDataFmSetting;

/**
 * 文字型データ形式設定
 */
@AllArgsConstructor
@Value
public class CharacterDfsDto {

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
	private int nullValueReplace;

	/**
	 * コード編集
	 */
	private int cdEditting;

	/**
	 * コード編集方法
	 */
	private int cdEdittingMethod;

	/**
	 * スペース編集
	 */
	private int spaceEditting;

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 有効桁数
	 */
	private int effectDigitLength;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueReplace;

	/**
	 * コード変換コード
	 */
	private String cdConvertCd;
	
	/**
	 * コード変換名称
	 */
	private String cdConvertName;

	/**
	 * コード編集桁
	 */
	private int cdEditDigit;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 有効桁数開始桁
	 */
	private int startDigit;

	/**
	 * 有効桁数終了桁
	 */
	private int endDigit;

	public static CharacterDfsDto fromDomain(CharacterDataFmSetting domain, String cdConvertName) {
		return new CharacterDfsDto(domain.getConditionSettingCode().v(), domain.getOutputItemCode().v(),
				domain.getCid(), domain.getNullValueReplace().value, domain.getCdEditting().value,
				domain.getCdEdittingMethod().value, domain.getSpaceEditting().value, domain.getFixedValue().value,
				domain.getEffectDigitLength().value, domain.getValueOfNullValueReplace().map(i -> i.v()).orElse(null),
				domain.getConvertCode().map(i -> i.v()).orElse(null),
				cdConvertName,
				domain.getCdEditDigit().map(i -> i.v()).orElse(null),
				domain.getValueOfFixedValue().map(i -> i.v()).orElse(null),
				domain.getStartDigit().map(i -> i.v()).orElse(null), domain.getEndDigit().map(i -> i.v()).orElse(null));
	}

}
