package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
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
	private Optional<GeneralDate> valueOfFixedValue;

	/**
	 * 形式選択
	 */
	private DateOutputFormat formatSelection;

	public DateDataFormSet(int itemType, int fixedValue, int formatSelection, GeneralDate valueOfFixedValue) {
		super(itemType);
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		this.formatSelection = EnumAdaptor.valueOf(formatSelection, DateOutputFormat.class);
		this.valueOfFixedValue = Optional.ofNullable(valueOfFixedValue);		
	}
	/**
	 * 日付型編集
	 * @param itemValue
	 * @return
	 */
	public GeneralDate editDateValue(String itemValue) {
		GeneralDate result = null;
		if(this.fixedValue == NotUseAtr.USE) {
			result = this.valueOfFixedValue.get();
			return result;
		}
		try {
			//「値」が指定した書式に合致するか判別
			result = GeneralDate.fromString(itemValue, this.formatSelection.nameId);
		} catch (Exception e) {
			return result;
		}
		return result;
	}
}
