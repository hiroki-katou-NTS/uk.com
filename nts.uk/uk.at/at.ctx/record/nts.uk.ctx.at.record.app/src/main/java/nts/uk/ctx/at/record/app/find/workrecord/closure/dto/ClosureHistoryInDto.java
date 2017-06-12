package nts.uk.ctx.at.record.app.find.workrecord.closure.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClosureHistoryInDto implements Serializable{

	/** The history id. */
	private String historyId;

	/** The closure id. */
	// 締めＩＤ
	private int closureId;

	/** The end date. */
	// 終了年月: 年月
	private int endDate;

	/** The start date. */
	// 開始年月: 年月
	private int startDate;
}
