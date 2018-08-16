package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する振休の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class PauseItem {
	/**
	 * 振休残数を出力する
	 */
	private boolean numberRemainingPause;
	/**
	 * 振休未消化を出力する
	 */
	private boolean undigestedPause;
	/**
	 * 振休の項目を出力する
	 */
	private boolean pauseItem;
	public PauseItem(boolean numberRemainingPause, boolean undigestedPause, boolean pauseItem) {
		super();
		this.numberRemainingPause = pauseItem && numberRemainingPause;
		this.undigestedPause = pauseItem && undigestedPause;
		this.pauseItem = pauseItem;
	}
	

}
