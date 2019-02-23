package nts.uk.ctx.pereg.app.find.layout.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpBody;

@Getter
@AllArgsConstructor
public class EmpMainCategoryDto {
	private String employeeId;
	/** Contains all columns (fixed and selected category) */
	private List<GridEmpBody> items;
}
