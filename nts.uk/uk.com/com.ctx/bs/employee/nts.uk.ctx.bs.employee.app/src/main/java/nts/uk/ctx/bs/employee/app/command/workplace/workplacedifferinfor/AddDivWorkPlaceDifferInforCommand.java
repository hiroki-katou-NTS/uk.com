package nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddDivWorkPlaceDifferInforCommand {
	/**会社ID**/
	private String companyId;
	
	// 会社コード
	private String companyCode;
	
	/** 契約コード */
	private String contractCd;
	
	/** 職場登録区分 **/
	private int regWorkDiv;
}
