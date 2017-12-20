/**
 * 
 */
package nts.uk.shr.pereg.app.find.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danpv
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpOptionalDto {
	
	private String itemCode;

	private String perInfoDefId;

	private String recordId;

	private String perInfoCtgId;

	private String perInfoCtgCd;

	private String itemName;

	private int dataType;

	private Object value;
	
	private boolean required;
	
}
