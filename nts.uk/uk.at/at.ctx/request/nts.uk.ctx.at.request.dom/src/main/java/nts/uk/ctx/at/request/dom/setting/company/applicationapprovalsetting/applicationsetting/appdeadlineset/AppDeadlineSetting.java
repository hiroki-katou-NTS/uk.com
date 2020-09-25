package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.UseDivision;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請締切設定.申請締切設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDeadlineSetting {
	
	/**
	 * 利用区分
	 */
	private UseDivision useAtr;
	
	/**
	 * 締めＩＤ
	 */
	private int closureId;
	
	/**
	 * 締切日数
	 */
	private Deadline deadline;
	
	/**
	 * 締切基準
	 */
	private DeadlineCriteria deadlineCriteria;
	
	public AppDeadlineSetting(UseDivision useAtr, int closureId,
			Deadline deadline, DeadlineCriteria deadlineCriteria) {
		this.useAtr = useAtr;
		this.closureId = closureId;
		this.deadline = deadline;
		this.deadlineCriteria = deadlineCriteria;
	}
	
	public static AppDeadlineSetting createNew(int useAtr, int closureId,
			int deadline, int deadlineCriteria) {
		return new AppDeadlineSetting(
				EnumAdaptor.valueOf(useAtr, UseDivision.class), 
				closureId, 
				new Deadline(deadline), 
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
	}
	
}
