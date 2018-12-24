/**
 * 
 */
package nts.uk.ctx.pereg.dom.person.setting.matrix.personinfomatrixitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieult
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonInfoMatrixData {

	private String perInfoItemDefID; 
	
	private String itemCD;
	
	private String itemName;
	
	private int regulationATR;
	
	private int dispOrder;
	
}
