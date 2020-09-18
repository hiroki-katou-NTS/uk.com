package nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.setting.modify.control.CorrectDeadline;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * スケジュール修正の修正期限
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.権限制御.スケジュール修正の修正期限
 * @author hiroko_miura
 *
 */
@Getter
@AllArgsConstructor
public class ScheAuthModifyDeadline implements DomainAggregate {
	// ロールID
	private final String roleId;
	
	// 利用区分
	private NotUseAtr useAtr;
	
	// 修正期限
	private CorrectDeadline deadLine;
	
	/**
	 * 修正可能か
	 * @param targetDate
	 * @return 修正可能な場合 : true
	 */
	public boolean isModifiable (GeneralDate targetDate) {
		if (this.useAtr == NotUseAtr.USE) {
			return targetDate.afterOrEquals(this.modifiableDate());
		}
		
		return true;
	}
	
	/**
	 * 修正が可能になる日
	 * @return
	 */
	public GeneralDate modifiableDate () {
		if (this.useAtr == NotUseAtr.NOT_USE) {
			return GeneralDate.min();
		}
		
		return GeneralDate.today().addDays(this.deadLine.v() + 1);
	}
}
