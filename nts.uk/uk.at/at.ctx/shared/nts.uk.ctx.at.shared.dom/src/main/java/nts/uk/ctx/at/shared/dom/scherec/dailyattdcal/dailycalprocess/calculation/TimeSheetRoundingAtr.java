package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

/**
 * 丸め区分(時間帯で丸めるかの区分)
 * @author keisuke_hoshina
 *
 */
public enum TimeSheetRoundingAtr {
	/** 時間帯毎に丸める */
	PerTimeSheet,
	/** 時間帯枠毎に丸める */
	PerTimeFrame,
	/** 全体で丸める */
	ALL;
}
