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
	private Ccd companyCode;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** 職場登録区分 **/
	private RegWorkDiv regWorkDiv;
	
	public static String createCompanyId(String companyCode, String contractCd) {
		return contractCd + "-" + companyCode;
	}
	
	public static DivWorkDifferInfor createFromJavaType(String companyCode,
														String contractCd,
														int regWorkDiv){
		return new DivWorkDifferInfor(new Ccd(companyCode),
											new ContractCd(contractCd),
										EnumAdaptor.valueOf(regWorkDiv, RegWorkDiv.class));
	}

	public DivWorkDifferInfor(Ccd companyCode, ContractCd contractCd, RegWorkDiv regWorkDiv) {
		super();
		this.companyCode = companyCode;
		this.contractCd = contractCd;
		this.regWorkDiv = regWorkDiv;
		this.companyId = createCompanyId(this.companyCode.v(), this.contractCd.v());
	}
	
}
