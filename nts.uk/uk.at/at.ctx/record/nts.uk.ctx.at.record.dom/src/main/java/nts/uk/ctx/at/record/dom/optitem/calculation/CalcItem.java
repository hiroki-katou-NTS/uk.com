package nts.uk.ctx.at.record.dom.optitem.calculation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 計算項目
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class CalcItem {
	private int leftItemValue = 0;
	private int rightItemValue = 0;
	
	/**
	 * 項目順序を基に計算項目値を保持
	 * @param dispOrder　項目順番
	 * @param setValue　計算値
	 */
	public void setValueByOrder(SettingItemOrder dispOrder,int setValue) {
		switch(dispOrder) {
		case LEFT:
			this.leftItemValue = setValue;
			break;
		case RIGHT:
			this.rightItemValue = setValue;
			break;
		default:
			throw new RuntimeException("unknown Item Order:"+dispOrder);
		}
	}
}
