package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 丸め区分(時間帯で丸めるかの区分)
 * @author keisuke_hoshina
 *
 */
public enum TimeSheetRoundingAtr {
	PerTimeSheet, //時間帯毎に丸める
	PerTimeFrame, //時間帯枠毎に丸める
	ALL //全体で丸める
	;
	
}
