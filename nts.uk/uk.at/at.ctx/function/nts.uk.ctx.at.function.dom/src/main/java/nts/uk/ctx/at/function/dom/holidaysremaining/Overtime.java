package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する時間外超過項目
 */
@Getter
@Setter
@NoArgsConstructor
public class Overtime {
	/**
	 * 時間外超過項目を出力する
	 */
	private boolean overtimeItem;
	/**
	 * 時間外超過残数を出力する
	 */
	private boolean overtimeRemaining;
	/**
	 * 時間外超過未消化を出力する
	 */
	private boolean overtimeOverUndigested;

	public Overtime(boolean overtimeItem, boolean overtimeRemaining, boolean overtimeOverUndigested) {
		super();
		this.overtimeItem = overtimeItem;
		this.overtimeRemaining = overtimeRemaining;
		this.overtimeOverUndigested = overtimeOverUndigested;
	}

}
