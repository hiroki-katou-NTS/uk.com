package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GridLayoutPersonInfoClsDto {
	private String employeeId;
	private String personId;
	
	private List<LayoutPersonInfoClsDto> layoutDtos;
}
