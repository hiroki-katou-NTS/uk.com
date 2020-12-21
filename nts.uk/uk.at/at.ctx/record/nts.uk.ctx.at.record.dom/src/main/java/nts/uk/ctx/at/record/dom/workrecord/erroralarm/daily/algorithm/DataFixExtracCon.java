package nts.uk.ctx.at.record.dom.workrecord.erroralarm.daily.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;

@AllArgsConstructor
public class DataFixExtracCon {
	// List＜勤務実績の固定抽出条件＞
	private List<FixedConditionWorkRecord> listFixConWork;
	
	// 本人確認状況
	private List<IdentityVerifiForDay> identityVerifyStatus;
	
	// 管理者未確認
	private List<IdentityVerifiForDay> adminUnconfirm;
	
}
