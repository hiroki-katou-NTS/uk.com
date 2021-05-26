package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataFixExtracCon {
	// List＜勤務実績の固定抽出条件＞
	private List<FixedConditionWorkRecord> listFixConWork;
	
	// 本人確認状況
	private List<Identification> identityVerifyStatus;
	
	// 管理者未確認
	private List<ApproveRootStatusForEmpImport> adminUnconfirm;
	
	// List＜労働条件＞
	private List<WorkingCondition> listWkConItem;
	
	// List＜打刻カード＞
	private List<StampCard> listStampCard;
	/**勤務実績の固定抽出項目	 */
	List<FixedConditionData> lstFixConWorkItem;
	/**日の本人確認を利用する	 */
	private boolean personConfirm;
	/**日の承認者確認を利用する	 */
	private boolean approverConfirm;
	/**労働条件項目	 */
	List<WorkingConditionItem> lstWorkCondItem;
}
