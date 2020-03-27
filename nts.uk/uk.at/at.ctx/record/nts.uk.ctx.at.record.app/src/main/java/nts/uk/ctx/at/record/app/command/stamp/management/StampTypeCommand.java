package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class StampTypeCommand {
	/** 勤務種類を半休に変更する */
	private boolean changeHalfDay;

	/** 外出区分 */
	private Integer goOutArt;
	
	/** 所定時刻セット区分 */
	private int setPreClockArt;
	
	/** 時刻変更区分 */
	private int changeClockArt;
	
	/** 計算区分変更対象 */
	private int changeCalArt;

	public StampTypeCommand(boolean changeHalfDay, Integer goOutArt, int setPreClockArt, int changeClockArt,
			int changeCalArt) {
		this.changeHalfDay = changeHalfDay;
		this.goOutArt = goOutArt;
		this.setPreClockArt = setPreClockArt;
		this.changeClockArt = changeClockArt;
		this.changeCalArt = changeCalArt;
	}
}
