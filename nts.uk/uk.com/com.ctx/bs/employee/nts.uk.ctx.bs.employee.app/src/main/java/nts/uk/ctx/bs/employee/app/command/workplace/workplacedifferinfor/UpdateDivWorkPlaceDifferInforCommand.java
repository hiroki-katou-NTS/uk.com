package nts.uk.ctx.bs.employee.app.command.workplace.workplacedifferinfor;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class UpdateDivWorkPlaceDifferInforCommand {
	
	// 会社コード
	private String companyCode;
	
	/** 契約コード */
	private String contractCd;
	
	/** 職場登録区分 **/
	private int regWorkDiv;
}
