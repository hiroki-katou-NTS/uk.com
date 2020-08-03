package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

/**
 * Refactor4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請.アルゴリズム.実績の打刻のチェック.打刻エラー情報
 * @author hoangnd
 *
 */
@AllArgsConstructor
public enum StampAtrOther {
	
	ATTEENDENCE_OR_RETIREMENT(0,"出勤／退勤"),
	
	EXTRAORDINARY(1,"臨時"),

	GOOUT_RETURNING (2,"外出／戻り"),

	CHEERING(3,"応援"),

	PARENT(4,"育児"),

	NURSE(5,"介護"),

	BREAK(6,"休憩");
	
	public int value;
	
	public String name;

}
