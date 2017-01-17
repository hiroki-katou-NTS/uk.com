package nts.uk.ctx.pr.core.dom.payment.banktranfer;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.PersonId;

public class BankTranfer extends AggregateRoot {
	private CompanyCode companyCode;
	private String companyNameKana;
	private String departmentCode;
	private PersonId personId;
	
	public BankTranfer(CompanyCode companyCode, String companyNameKana, String departmentCode, PersonId personId) {
		super();
		this.companyCode = companyCode;
		this.companyNameKana = companyNameKana;
		this.departmentCode = departmentCode;
		this.personId = personId;
	}
	
	public static BankTranfer createFromJavaType(String companyCode, String companyNameKana, String departmentCode, String personId) {
		return new BankTranfer(
				new CompanyCode(companyCode), companyNameKana, departmentCode, new PersonId(personId));
	}
}
