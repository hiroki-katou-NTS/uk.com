package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).月の勤怠計算.月別集計処理.縦計方法.休暇取得時の出勤日数カウント
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class WorkDaysNumberOnLeaveCount extends AggregateRoot {
	
	// 会社ID						
	private final String cid;

	// カウントする休暇一覧						
	private final List<LeaveCountedAsWorkDaysType> countedLeaveList;
}
