package nts.uk.ctx.hr.shared.dom.employee;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GrpCmonMasterImport {
 
	private String commonMasterName;
	
	private List<GrpCmmMastItImport> commonMasterItems;

}
