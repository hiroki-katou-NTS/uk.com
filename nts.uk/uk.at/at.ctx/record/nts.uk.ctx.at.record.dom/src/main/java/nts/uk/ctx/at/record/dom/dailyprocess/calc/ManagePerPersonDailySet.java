package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

/**
 * 毎日変更の可能性のあるマスタ管理クラス
 * @author keisuke_hoshina
 *
 */
@Getter
public class ManagePerPersonDailySet {
	//労働条件
	public Optional<WorkingConditionItem> personInfo;
	//法定労働
	public DailyUnit dailyUnit;
	
	/**
	 * Constructor
	 * @param personInfo 労働条件
	 * @param dailyUnit　法定労働時間
	 */
	public ManagePerPersonDailySet(Optional<WorkingConditionItem> personInfo, DailyUnit dailyUnit) {
		super();
		this.personInfo = personInfo;
		this.dailyUnit = dailyUnit;
	}
}
