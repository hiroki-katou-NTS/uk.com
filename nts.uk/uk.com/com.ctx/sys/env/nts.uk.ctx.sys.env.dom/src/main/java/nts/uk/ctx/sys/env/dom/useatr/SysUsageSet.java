package nts.uk.ctx.sys.env.dom.useatr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * システム利用設定
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SysUsageSet extends AggregateRoot{
	@Setter
	/**会社ID**/
	private String companyId;
	
	// 会社コード
	private CompanyCode companyCode;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** 人事システム **/
	private PersonnelSystem personnelSystem;
	/** 就業システム **/
	private EmploymentSys employmentSys;
	/** 給与システム **/
	private PayrollSys payrollSys;
	public static SysUsageSet createFromJavaType(String companyId, String companyCd, String contractCd, 
													int personnelSystem, int employmentSys, int payrollSys){
		return new SysUsageSet(companyId, new CompanyCode(companyCd),
								new ContractCd(contractCd),
							EnumAdaptor.valueOf(personnelSystem, PersonnelSystem.class),
							EnumAdaptor.valueOf(employmentSys, EmploymentSys.class),
							EnumAdaptor.valueOf(payrollSys, PayrollSys.class));
	}
	
	public void createCompanyId(String companyCode, String contractCd){
		this.setCompanyId("contractCd" + "-" + "companyCode");
	}
	
	@Override
	public void validate(){
		super.validate();
	}
}
