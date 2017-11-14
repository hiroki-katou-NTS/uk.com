package nts.uk.ctx.bs.employee.app.find.workplacedifferinfor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DivWorkPlaceDifferInforDto {
	/**会社ID**/
	private String companyId;
	
	// 会社コード
	private String companyCode;
	
	/** 契約コード */
	private String contractCd;
	
	/** 職場登録区分 **/
	private int regWorkDiv;
}
