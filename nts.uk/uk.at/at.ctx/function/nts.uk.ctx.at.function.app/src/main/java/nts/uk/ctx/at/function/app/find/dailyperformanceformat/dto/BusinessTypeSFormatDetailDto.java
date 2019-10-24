package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import lombok.Value;

@Value
public class BusinessTypeSFormatDetailDto {
	
	/**
	 * 日次表示項目一覧
	 */
	private int attendanceItemId;
	
	/**
	 * 並び順
	 */
	private int order;

}
