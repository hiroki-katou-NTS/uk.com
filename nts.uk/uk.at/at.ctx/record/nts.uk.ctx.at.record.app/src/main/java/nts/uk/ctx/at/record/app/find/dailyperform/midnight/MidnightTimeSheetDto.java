package nts.uk.ctx.at.record.app.find.dailyperform.midnight;

import lombok.Data;

/**
 * @author Yennh
 *
 */
@Data
public class MidnightTimeSheetDto {	
	/* 開始時刻 */
	private Integer startTime;
	
	/* 終了時刻 */
	private Integer endTime;
}
