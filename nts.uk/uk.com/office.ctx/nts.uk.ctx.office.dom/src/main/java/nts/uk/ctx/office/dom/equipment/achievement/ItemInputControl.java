package nts.uk.ctx.office.dom.equipment.achievement;

import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.ErrorMessage;
import nts.arc.error.I18NErrorMessage;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.office.dom.equipment.data.ActualItemUsageValue;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.設備管理.実績項目設定.項目入力制御
 * @author NWS-DungDV
 *
 */
@Getter
public class ItemInputControl extends ValueObject {
	// 項目分類
	private ItemClassification itemCls;
	
	// 必須
	private boolean mandatory;
	
	// 桁数
	private Optional<Integer> digitsNo;
	
	// 最大値
	private Optional<MaximumUsageRecord> maximum;
	
	// 最小値
	private Optional<MinimumUsageRecord> minimum;
	
	/**
	 * [C-1] 新規追加
	 * @param itemCls 項目分類
	 * @param mandatory 必須
	 * @param digitsNo 桁数
	 * @param maximum 最大値
	 * @param minimum 最小値
	 */
	public ItemInputControl(
			ItemClassification itemCls,
			boolean mandatory,
			Optional<Integer> digitsNo,
			Optional<MaximumUsageRecord> maximum,
			Optional<MinimumUsageRecord> minimum) {
		// inv-1 項目分類　＝　文字　&&　桁数.isPresent()
		if (itemCls.equals(ItemClassification.TEXT) && digitsNo.isPresent()) {
			throw new BusinessException("Msg_2248", "itemNo");
		}
		// inv-2 （項目分類　＝　数字　||　時間）　&&　最大値.isPresent()
		if ((itemCls.equals(ItemClassification.NUMBER) || itemCls.equals(ItemClassification.TIME))
			&& maximum.isPresent()) {
			throw new BusinessException("Msg_2249", "itemNo");
		}
		// inv-3 （項目分類　＝　数字　||　時間）　&&　最小値.isPresent()
		if ((itemCls.equals(ItemClassification.NUMBER) || itemCls.equals(ItemClassification.TIME))
			&& maximum.isPresent()) {
			throw new BusinessException("Msg_2250", "itemNo");
		}
		
		// [mapping]
		this.itemCls = itemCls;
		this.mandatory = mandatory;
		this.digitsNo = digitsNo;
		this.maximum = maximum;
		this.minimum = minimum;
	}
	
	/**
	 * [1] 項目制御のエラーをチェックする
	 * @param itemNo 項目NO
	 * @param itemName 項目名称
	 * @param inputVal 入力値
	 * @return エラー
	 */
	public Optional<ErrorMessage> checkErrors(EquipmentItemNo itemNo, UsageItemName itemName, Optional<ActualItemUsageValue> inputVal) {
		if (this.mandatory && !inputVal.isPresent()) {
			return Optional.of(new I18NErrorMessage(I18NText.main("Msg_2228").addRaw(itemName.v()).build()));
		}
		
		if (!inputVal.isPresent()) {
			return Optional.empty();
		}
		
		String inputValue = inputVal.get().v();
		I18NText error;
		switch (itemCls) {
		case TEXT:
			if (this.digitsNo.isPresent() && this.digitsNo.get() < inputValue.length()) {
				error = I18NText.main("Msg_2229")
						.addRaw(itemName.v())
						.addRaw(this.digitsNo.get())
						.build();
				return Optional.of(new I18NErrorMessage(error));
			}
			break;

		case NUMBER:
			if (this.maximum.get().lessThan(Integer.parseInt(inputVal.get().v()))
				|| this.minimum.get().greaterThan(Integer.parseInt(inputVal.get().v())))
			{
				error = I18NText.main("Msg_2246")
						.addRaw(itemName.v())
						.addRaw(this.minimum.get().v())
						.addRaw(this.maximum.get().v())
						.build();
				return Optional.of(new I18NErrorMessage(error));
			}
			break;
		
		case TIME:
			if (this.maximum.get().lessThan(Integer.parseInt(inputVal.get().v()))
					|| this.minimum.get().greaterThan(Integer.parseInt(inputVal.get().v())))
			{
				error = I18NText.main("Msg_2247")
						.addRaw(itemName.v())
						.addRaw(this.minimum.get().v())
						.addRaw(this.maximum.get().v())
						.build();
				return Optional.of(new I18NErrorMessage(error));
			}
			break;
		default:
			return Optional.empty();
		}
		return Optional.empty();
	}
}
