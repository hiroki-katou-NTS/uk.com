package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;

/**
 * 打刻種類
 * @author phongtq
 *
 */
public class StampType {
	
	/** 勤務種類を半休に変更する */
	//勤務種類を半休に変更する 1 old
	@Getter
	private boolean changeHalfDay;

	/** 外出区分 */
	//外出理由 old
	@Getter
	private Optional<GoingOutReason> goOutArt;
	
	/** 所定時刻セット区分 */
	//勤務種類を半休に変更する 2 old
	@Getter
	private SetPreClockArt setPreClockArt;
	
	/** 時刻変更区分 */
	//打刻区分 old
	@Getter
	private ChangeClockArt changeClockArt;
	
	/** 計算区分変更対象 */
	//勤務種類を半休に変更する 3 old
	@Getter
	private ChangeCalArt changeCalArt;

	public StampType(boolean changeHalfDay, Optional<GoingOutReason> goOutArt, SetPreClockArt setPreClockArt,
			ChangeClockArt changeClockArt, ChangeCalArt changeCalArt) {
		super();
		this.changeHalfDay = changeHalfDay;
		this.goOutArt = goOutArt;
		this.setPreClockArt = setPreClockArt;
		this.changeClockArt = changeClockArt;
		this.changeCalArt = changeCalArt;
	}

}
