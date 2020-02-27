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
	@Getter
	private boolean changeHalfDay;

	/** 外出区分 */
	@Getter
	private Optional<GoingOutReason> goOutArt;
	
	/** 所定時刻セット区分 */
	@Getter
	private SetPreClockArt setPreClockArt;
	
	/** 時刻変更区分 */
	@Getter
	private ChangeClockArt cangeClockArt;
	
	/** 計算区分変更対象 */
	@Getter
	private ChangeCalArt changeCalArt;
}
