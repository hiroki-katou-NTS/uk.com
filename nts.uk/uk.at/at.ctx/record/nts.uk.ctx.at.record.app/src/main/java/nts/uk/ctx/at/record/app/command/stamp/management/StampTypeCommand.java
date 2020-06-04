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
	private Boolean changeHalfDay;

	/** 外出区分 */
	private Integer goOutArt;
	
	/** 所定時刻セット区分 */
	private Integer setPreClockArt;
	
	/** 時刻変更区分 */
	private Integer changeClockArt;
	
	/** 計算区分変更対象 */
	private Integer changeCalArt;

	public StampTypeCommand(Boolean changeHalfDay, Integer goOutArt, Integer setPreClockArt, Integer changeClockArt,
			Integer changeCalArt) {
		this.changeHalfDay = changeHalfDay;
		this.goOutArt = goOutArt;
		this.setPreClockArt = setPreClockArt;
		this.changeClockArt = changeClockArt;
		this.changeCalArt = changeCalArt;
	}
}
