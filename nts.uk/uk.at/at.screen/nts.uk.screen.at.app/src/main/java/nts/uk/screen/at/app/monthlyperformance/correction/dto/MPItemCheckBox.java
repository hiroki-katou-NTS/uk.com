package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MPItemCheckBox {
	private String rowId;

	private String itemId;

	private boolean value;

	private String valueType;

	private String employeeId;
}
