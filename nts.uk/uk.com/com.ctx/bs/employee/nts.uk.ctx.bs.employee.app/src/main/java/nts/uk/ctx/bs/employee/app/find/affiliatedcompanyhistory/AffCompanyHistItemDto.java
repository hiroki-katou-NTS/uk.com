/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.affiliatedcompanyhistory;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;

/**
 * @author hieult
 *
 */
@Getter
@Setter	
public class AffCompanyHistItemDto {

	/** 社員ID */
	private String employeeID;

	/** 履歴 */
	private List<AffCompanyHistItem> lstAffCompanyHistoryItem;
	
	public AffCompanyHistItemDto (String employeeID , List<AffCompanyHistItem> lstAffCompanyHistoryItem ){
		this.employeeID = employeeID;
		this.lstAffCompanyHistoryItem = lstAffCompanyHistoryItem;
	}

}
