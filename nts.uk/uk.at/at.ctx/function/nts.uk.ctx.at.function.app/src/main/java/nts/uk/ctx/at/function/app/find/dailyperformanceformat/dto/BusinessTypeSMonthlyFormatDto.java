package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import java.util.List;

import lombok.Value;
/**
 * 
 * @author anhdt
 * 
 */
@Value
public class BusinessTypeSMonthlyFormatDto {
	/*勤務種別日別実績の修正のフォーマット（スマホ版）*/
	private List<BusinessTypeSFormatDetailDto> businessTypeFormatMonthlyDtos;
}
