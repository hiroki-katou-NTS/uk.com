package nts.uk.ctx.exio.app.find.exo.dataformat.dataformatsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.dataformatsetting.AwDataFormatSetting;

/**
 * 在職区分型データ形式設定
 */
@AllArgsConstructor
@Value
public class AtWorkClsDfsDto {

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
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 休業時出力
	 */
	private String closedOutput;

	/**
	 * 休職時出力
	 */
	private String absenceOutput;

	/**
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * 在職時出力
	 */
	private String atWorkOutput;

	/**
	 * 退職時出力
	 */
	private String retirementOutput;

	public static AtWorkClsDfsDto fromDomain(AwDataFormatSetting domain) {
		return new AtWorkClsDfsDto(domain.getConditionSettingCode().v(), domain.getOutputItemCode().v(),
				domain.getCid(), domain.getFixedValue().value, domain.getClosedOutput().map(i -> i.v()).orElse(null),
				domain.getAbsenceOutput().map(i -> i.v()).orElse(null),
				domain.getValueOfFixedValue().map(i -> i.v()).orElse(null),
				domain.getAtWorkOutput().map(i -> i.v()).orElse(null),
				domain.getRetirementOutput().map(i -> i.v()).orElse(null));
	}

}
