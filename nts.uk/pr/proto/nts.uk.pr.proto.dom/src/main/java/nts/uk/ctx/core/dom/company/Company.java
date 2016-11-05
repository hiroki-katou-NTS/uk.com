package nts.uk.ctx.core.dom.company;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * Company aggregate root.
 */
public class Company extends AggregateRoot {
	
	/** code */
	@Getter
	private final CompanyCode code;
	
	/** name */
	@Getter
	private CompanyName name;

	/**
	 * Constructs.
	 * 
	 * @param companyCode code
	 * @param companyName name
	 */
	public Company(CompanyCode companyCode, CompanyName companyName) {
		this.code = companyCode;
		this.name = companyName;
	}
	
	/**
	 * Create instance using Java type parameters.
	 * 
	 * @param companyCode code
	 * @param companyName name
	 * @return Company
	 */
	public static Company createFromJavaType(String companyCode, String companyName) {
		return new Company(
				new CompanyCode(companyCode),
				new CompanyName(companyName));
	}
}
