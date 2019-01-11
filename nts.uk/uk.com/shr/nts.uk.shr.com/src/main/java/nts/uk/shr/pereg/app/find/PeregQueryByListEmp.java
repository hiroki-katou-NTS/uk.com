/**
 * 
 */
package nts.uk.shr.pereg.app.find;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * @author hieult
 *
 */

public class PeregQueryByListEmp {
	
	private String categoryId;
	
	private String categoryCode;
	
	private List<String> listEmpId;
	
	private List<String> listPersonId;
	
	private GeneralDate standardDate;

	public static PeregQueryByListEmp createQueryLayout(String categoryCode,
			List<String> listEmpId, List<String> listPersonId, GeneralDate standardDate) {
		return new PeregQueryByListEmp(categoryCode, listEmpId, listPersonId, standardDate);
	}
	//layout case
	private PeregQueryByListEmp(String categoryCode, List<String> listEmpId, List<String> listPersonId,
			GeneralDate standardDate) {
		this.categoryCode   = categoryCode;
		this.listEmpId    = listEmpId;
		this.listPersonId = listPersonId;
		this.standardDate = standardDate;
	}
	//category case
	public static PeregQueryByListEmp createQueryCategory (String categoryCode , List<String> listEmpId ,List<String> listPersonId ){
		return new PeregQueryByListEmp(categoryCode, listEmpId,listPersonId);
		
	}

	private PeregQueryByListEmp(String categoryCode, List<String> listEmpId, List<String> listPersonId) {
		this.categoryCode  = categoryCode;
		this.listEmpId	   = listEmpId;
		this.listPersonId  = listPersonId;
	}
	

}
