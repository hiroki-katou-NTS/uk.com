package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.reflect;

import lombok.AllArgsConstructor;

/**
 * 開始区分
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum StartAtr {

	/** 応援開始*/
	CHEERING(0),
	
	/** 振出 - 応援終了*/
	END_SUPPORT(1);
	
	public final int value;
}
