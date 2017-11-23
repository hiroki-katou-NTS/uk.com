package nts.uk.ctx.bs.employee.dom.workplace.differinfor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DivWorkDifferInfor extends AggregateRoot{
	@Setter
	/**会社ID**/
	private String companyId;
	
	/** 職場登録区分 **/
	private RegWorkDiv regWorkDiv;
	
//	public static String createCompanyId(String companyCode, String contractCd) {
//		return contractCd + "-" + companyCode;
//	}
	
	public static DivWorkDifferInfor createFromJavaType(String companyId, int regWorkDiv){
		return new DivWorkDifferInfor(companyId, EnumAdaptor.valueOf(regWorkDiv, RegWorkDiv.class));
	}

//	public DivWorkDifferInfor(RegWorkDiv regWorkDiv) {
//		super();
//		this.regWorkDiv = regWorkDiv;
//		this.companyId = createCompanyId(this.companyCode.v(), this.contractCd.v());
//	}
	
}
