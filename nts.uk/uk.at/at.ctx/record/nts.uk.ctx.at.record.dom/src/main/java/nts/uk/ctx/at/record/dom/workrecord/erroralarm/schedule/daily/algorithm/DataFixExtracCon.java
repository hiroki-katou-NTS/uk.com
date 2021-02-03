package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;

@AllArgsConstructor
@Getter
public class DataFixExtracCon {
	// List＜勤務実績の固定抽出条件＞
	private List<FixedConditionWorkRecord> listFixConWork;
	
	// 本人確認状況
	private List<IdentityVerifiForDay> identityVerifyStatus;
	
	// 管理者未確認
	private List<IdentityVerifiForDay> adminUnconfirm;
	
	// List＜労働条件＞
	private List<WorkingCondition> listWkConItem;
	
	// List＜打刻カード＞
	private List<StampCard> listStampCard;
	
}
