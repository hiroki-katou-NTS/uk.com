package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class StampCombinationDto {
	
	// giá trị enum
	private Integer value;
	
	// tên enum
	private String name;
}
