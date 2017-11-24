/**
 * 
 */
package nts.uk.shr.pereg.app.find.dto;

import lombok.Data;

/**
 * @author danpv
 *
 */
@Data
public class PersonOptionalDto {
	
	private String itemCode;

	private String perInfoCtgId;

	private String perInfoCtgCd;

	private String itemName;

	private String perInfoItemDefId;

	private String recordId;
	
	private int dataType;

	private Object value;
	
	private boolean required;
	
}
