package nts.uk.ctx.at.record.dom.workrecord.erroralarm.otkcustomize;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousVacationDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
/** 大塚用連続休暇チェック設定 */
public class ContinuousHolCheckSet {

	/** 会社ID */
	private String companyId;
	
	/** チェックする勤務種類 */
	private List<WorkTypeCode> targetWorkType;
	
	/** チェックを飛ばす勤務種類 */
	private List<WorkTypeCode> ignoreWorkType;
	
	/** 使用する */
	private boolean useAtr;
	
	/** 表示するメッセージ */
	private DisplayMessage displayMessege;
	
	/** 連続日数 */
	private ContinuousVacationDays maxContinuousDays;
}
