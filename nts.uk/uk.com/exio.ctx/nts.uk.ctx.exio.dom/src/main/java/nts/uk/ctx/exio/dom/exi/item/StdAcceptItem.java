package nts.uk.ctx.exio.dom.exi.item;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exi.condset.AcScreenCondSet;
import nts.uk.ctx.exio.dom.exi.condset.AcceptanceConditionCode;
import nts.uk.ctx.exio.dom.exi.dataformat.ChrDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.DataFormatSetting;
import nts.uk.ctx.exio.dom.exi.dataformat.DateDataFormSet;
import nts.uk.ctx.exio.dom.exi.dataformat.InsTimeDatFmSet;
import nts.uk.ctx.exio.dom.exi.dataformat.ItemType;
import nts.uk.ctx.exio.dom.exi.dataformat.NumDataFormatSet;
import nts.uk.ctx.exio.dom.exi.dataformat.TimeDataFormatSet;

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
	/**
	 * 取得した値を編集する（一次は対象外）
	 * @param itemValue
	 * @return
	 */
	public AcceptItemEditValueDto checkCondition(String itemValue) {
		boolean resultCheck = true;
		Object toItemValue = itemValue;
		AcceptItemEditValueDto result = new AcceptItemEditValueDto(toItemValue, resultCheck, "");
		if(this.getDataFormatSetting().isPresent()) {
			switch (this.itemType) {
			case CHARACTER:
				ChrDataFormatSet chrFormatSet = (ChrDataFormatSet) this.getDataFormatSetting().get();
				toItemValue = chrFormatSet.editStringValue(itemValue);
				if(toItemValue == null) {
					//TODO アルゴリズム「コード変換を行う」を実行する
				}
				break;
			case DATE:
				DateDataFormSet dateFormatSet = (DateDataFormSet) this.getDataFormatSetting().get();
				toItemValue = dateFormatSet.editDateValue(itemValue);
				if(toItemValue == null) {
					result.setEditError("Msg_1019");
					result.setResultCheck(false);
					return result;
				}
				break;
			case INS_TIME:
				InsTimeDatFmSet insFormatSet = (InsTimeDatFmSet) this.getDataFormatSetting().get();
				toItemValue = insFormatSet.editTimeValue(itemValue);
				if(toItemValue == null) {
					result.setEditError("Msg_1021");
					result.setResultCheck(false);
					return result;
				}
				break;
			case NUMERIC:
				NumDataFormatSet numFormatSet = (NumDataFormatSet) this.getDataFormatSetting().get();
				toItemValue = numFormatSet.editNumber(itemValue);
				if(toItemValue == null) {
					result.setEditError("Msg_1018");
					result.setResultCheck(false);
					return result;
				}
				break;
			case TIME:
				TimeDataFormatSet timeFormatSet = (TimeDataFormatSet) this.getDataFormatSetting().get();
				toItemValue =  timeFormatSet.editTimeValue(itemValue);
				
				if(toItemValue == null) {
					result.setEditError("Msg_1021");
					result.setResultCheck(false);
					return result;
				}
				
				break;
				default:
					break;
			}	
		}
		result.setEditValue(toItemValue);
		//受入条件の判定
		if(this.acceptScreenConditionSetting.isPresent()) {
			if(this.itemType == ItemType.DATE && !this.getDataFormatSetting().isPresent()) {
				try {
					toItemValue = GeneralDate.fromString(toItemValue.toString(), "yyyy/MM/dd");	
				}catch (Exception e) {
					result.setEditError("Msg_1019");
					result.setResultCheck(false);
					return result;
				}
				
			}
			resultCheck = this.getAcceptScreenConditionSetting().get().checkCondNumber(toItemValue, this.itemType);
			result.setResultCheck(resultCheck);
		}
		return result;
	}

}
