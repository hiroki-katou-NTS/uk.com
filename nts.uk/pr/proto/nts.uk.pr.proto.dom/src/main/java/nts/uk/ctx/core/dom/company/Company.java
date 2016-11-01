package nts.uk.ctx.core.dom.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

public class Company extends AggregateRoot {
	
	@Getter
	private final CompanyCode code;
	
	@Getter
	private CompanyName name;

	public Company(CompanyCode companyCode, CompanyName companyName) {
		this.code = companyCode;
		this.name = companyName;
	}
	
	public static Company createToAddOrUpdate(String companyCode, String companyName) {
		return createFromJavaType(companyCode, companyName);
	}
	
	public static Company createFromJavaType(String companyCode, String companyName) {
		return new Company(
				new CompanyCode(companyCode),
				new CompanyName(companyName));
		
	}
}
