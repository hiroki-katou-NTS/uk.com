package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SpecCountNotCalcSubject {
	// 勤務日のみカウントする
	workDayOnly(0, "勤務日のみカウントする"),
	// 無条件にカウントしない
	NoUncondition(1, "無条件にカウントしない"),
	// 無条件にカウントする
	Uncondition(2, "無条件にカウントする");
	
	public final int value;
	public final String name;
}
