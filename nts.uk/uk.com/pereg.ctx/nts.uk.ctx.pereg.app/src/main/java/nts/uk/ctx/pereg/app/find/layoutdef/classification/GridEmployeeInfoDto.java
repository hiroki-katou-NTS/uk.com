package nts.uk.ctx.pereg.app.find.layoutdef.classification;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GridEmployeeInfoDto {
	private String personId;
	private String employeeId;
		
	/** Contains all columns (fixed and selected category) */
	private List<GridEmpBody> items;
}
