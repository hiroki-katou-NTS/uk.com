package nts.uk.ctx.exio.app.find.exi.dataformat;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;

/**
 * 日付型データ形式設定
 */

@Value
public class DateDataFormSetDto {

	/**
	 * 固定値
	 */
	private int fixedValue;

	/**
	 * 固定値の値
	 */
	private GeneralDate valueOfFixedValue;

	/**
	 * 形式選択
	 */
	private int formatSelection;

	public static DateDataFormSetDto fromDomain(DateDataFormSet domain) {
		return new DateDataFormSetDto(domain.getFixedValue().value,
				domain.getValueOfFixedValue().isPresent() ? domain.getValueOfFixedValue().get() : null,
				domain.getFormatSelection().value);
	}

}
