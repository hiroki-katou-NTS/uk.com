package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyItemDto {
	
	private Integer timeId; 
	
	private String name;

	private Integer attribute;
	
	private Integer masterType;
	
	private Integer displayNumber;
}
