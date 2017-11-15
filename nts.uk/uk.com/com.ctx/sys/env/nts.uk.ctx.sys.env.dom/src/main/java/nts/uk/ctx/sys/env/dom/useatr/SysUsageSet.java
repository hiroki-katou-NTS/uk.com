package nts.uk.ctx.sys.env.dom.useatr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	/**会社ID**/
	private String companyId;
	/** 人事システム **/
	private PersonnelSystem personnelSystem;
	/** 就業システム **/
	private EmploymentSys employmentSys;
	/** 給与システム **/
	private PayrollSys payrollSys;
	public static SysUsageSet createFromJavaType(String companyId, int personnelSystem, int employmentSys, int payrollSys){
		return new SysUsageSet(companyId, 
							EnumAdaptor.valueOf(personnelSystem, PersonnelSystem.class),
							EnumAdaptor.valueOf(employmentSys, EmploymentSys.class),
							EnumAdaptor.valueOf(payrollSys, PayrollSys.class));
	}
	
	@Override
	public void validate(){
		super.validate();
	}
}
