package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;

/**
 * 休暇分を含める就業計算詳細設定
 * @author ken_takasu
 *
 */
public class IncludeHolidaysWorkCalcDetailSet {
	@Getter
	private NotUseAtr toAdd;

	public IncludeHolidaysWorkCalcDetailSet(NotUseAtr toAdd) {
		super();
		this.toAdd = toAdd;
	}//型の参照先は変更する必要あり
	
}
