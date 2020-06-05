/**
 * 
 */
package nts.uk.screen.hr.app.databeforereflecting.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */

@AllArgsConstructor
@Data
public class ChangeRetirementDate {
	public GeneralDate retirementDate; // A222_12  退職日
	public int retirementType;         // A222_16 退職区分
	public String sid;                 // 社員ID = 選択中社員の社員ID(EmployeeID = EmployeeID của employee dang chon)
	public String cid;                 // 会社ID = ログイン会社ID(CompanyID = LoginCompanyID)
	public GeneralDate baseDate;       // 基準日 = システム日付(baseDate = SystemDate)
}
