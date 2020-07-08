package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.事前の受付制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class BeforehandRestriction {
	
	/**
	 * 日数
	 */
	private AppAcceptLimitDay dateBeforehandRestrictions;
	
	/**
	 * 利用する
	 */
	private boolean toUse;
	
	public BeforehandRestriction(AppAcceptLimitDay dateBeforehandRestrictions, boolean toUse) {
		this.dateBeforehandRestrictions = dateBeforehandRestrictions;
		this.toUse = toUse;
	}
	
	public static BeforehandRestriction createNew(int dateBeforehandRestrictions, boolean toUse) {
		return new BeforehandRestriction(
				EnumAdaptor.valueOf(dateBeforehandRestrictions, AppAcceptLimitDay.class), 
				toUse);
	}
	
}
