package nts.uk.ctx.exio.app.find.exo.dataformat;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.dataformat.init.DateFormatSet;

@AllArgsConstructor
@Value
public class DateFormatSettingDTO {
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
	 * 固定値の値
	 */
	private String valueOfFixedValue;

	/**
	 * NULL値置換の値
	 */
	private String valueOfNullValueSubs;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	public static DateFormatSettingDTO fromDomain(DateFormatSet domain) {
		return new DateFormatSettingDTO(domain.getCid(), domain.getNullValueReplace().value,
				domain.getFixedValue().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get().v() : null,
				domain.getValueOfNullValueReplace().isPresent() ? domain.getValueOfNullValueReplace().get().v() : null,
				domain.getFormatSelection().value);
	}
}
