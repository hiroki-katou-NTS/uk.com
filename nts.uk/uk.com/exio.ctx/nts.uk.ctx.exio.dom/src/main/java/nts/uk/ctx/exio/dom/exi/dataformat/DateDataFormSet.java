package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日付型データ形式設定
 */
@AllArgsConstructor
@Getter
@Setter
public class DateDataFormSet extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private String conditionSetCd;

	/**
	 * 受入項目番号
	 */
	private int acceptItemNum;

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

	public DateDataFormSet(String cid, String conditionSetCd, int acceptItemNum, int fixedValue,
			String valueOfFixedValue, int formatSelection) {
		super();
		this.cid = cid;
		this.conditionSetCd = conditionSetCd;
		this.acceptItemNum = acceptItemNum;
		this.fixedValue = EnumAdaptor.valueOf(fixedValue, NotUseAtr.class);
		if (valueOfFixedValue == null) {
			this.valueOfFixedValue = Optional.empty();
		} else {
			this.valueOfFixedValue = Optional.of(new DataSettingFixedValue(valueOfFixedValue));
		}
		this.formatSelection = EnumAdaptor.valueOf(formatSelection, DateOutputFormat.class);
	}
}
