package nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 加算する休暇設定
 * @author ken_takasu
 *
 */
@AllArgsConstructor
public class AddVacationSet {
	@Getter
	private NotUseAtr annualLeave;//年休
	@Getter
	private NotUseAtr ｒetentionYearly;//積立年休
	@Getter
	private NotUseAtr specialHoliday;//特別休暇
	
	/**
	 * 全てしない設定の加算する休暇設定を返す
	 * @author ken_takasu
	 * @return
	 */
	public AddVacationSet createAllDonot() {
		return new AddVacationSet(NotUseAtr.Donot,NotUseAtr.Donot,NotUseAtr.Donot);
	}
	
}
