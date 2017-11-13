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
	
	// 会社コード
	private CompanyCode companyCode;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** 職場登録区分 **/
	private RegWorkDiv regWorkDiv;
	
	public void createCompanyId(String companyCode, String contractCd){
		this.setCompanyId("contractCd" + "-" + "companyCode");
	}
	
	public static DivWorkDifferInfor createFromJavaType(String companyId, String companyCode,
														String contractCd,
														int regWorkDiv){
		return new DivWorkDifferInfor(companyId, new CompanyCode(companyCode),
											new ContractCd(contractCd),
										EnumAdaptor.valueOf(regWorkDiv, RegWorkDiv.class));
	}
	
}
