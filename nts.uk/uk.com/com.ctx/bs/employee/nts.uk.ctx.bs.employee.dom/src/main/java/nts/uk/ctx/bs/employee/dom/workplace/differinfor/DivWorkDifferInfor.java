package nts.uk.ctx.bs.employee.dom.workplace.differinfor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 部門職場区別情報
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivWorkDifferInfor extends AggregateRoot{
	/**会社ID**/
	private String companyId;
	
	/** 職場登録区分 **/
	private RegWorkDiv regWorkDiv;
	
	public static DivWorkDifferInfor createFromJavaType(String companyId, int regWorkDiv){
		return new DivWorkDifferInfor(companyId, EnumAdaptor.valueOf(regWorkDiv, RegWorkDiv.class));
	}	
}
