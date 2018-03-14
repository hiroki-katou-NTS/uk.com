package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日付型データ形式設定
 */
@Getter
public class DateDataFormSet extends DataFormatSetting {

	/**
	 * 固定値
	 */
	private NotUseAtr fixedValue;

	/**
	 * 固定値の値
	 */
	private Optional<DataSettingFixedValue> valueOfFixedValue;

	/**
	 * 形式選択
	 */
	private DateOutputFormat formatSelection;

	public DateDataFormSet(int itemType, int fixedValue, int formatSelection, String valueOfFixedValue) {
		super(itemType);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.formatSelection = EnumAdaptor.valueOf(formatSelection, DateOutputFormat.class);
		if (valueOfFixedValue == null)
			this.valueOfFixedValue = Optional.empty();
		else
			this.valueOfFixedValue = Optional.of(new DataSettingFixedValue(valueOfFixedValue));
	}
}
