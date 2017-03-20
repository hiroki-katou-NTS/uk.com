/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * @author lanlt
 *
 */
public class ResidentialTax  extends AggregateRoot{
	@Getter
	private CompanyCode companyCode;
	@Getter
	private ResiTaxCode resiTaxCode;
	@Getter
	private ResiTaxAutonomy resiTaxAutonomy;
	@Getter
	private PrefectureCode prefectureCode;
	@Getter
	private ResiTaxReportCode resiTaxReportCode;
	@Getter
	private RegisteredName registeredName;
	@Getter
	private CompanyAccountNo companyAccountNo;
	@Getter
	private CompanySpecifiedNo companySpecifiedNo;
	@Getter
	private CordinatePostalCode cordinatePostalCode;
	@Getter
	private CordinatePostOffice cordinatePostOffice;
	@Getter
	private Memo memo;
	public static ResidentialTax createFromJavaType(String companyCode,String companyAccountNo, String companySpecifiedNo,
			String cordinatePostOffice, String cordinatePostalCode, String memo,
			String prefectureCode, String registeredName,
			String resiTaxAutonomy,
			String resiTaxCode, String resiTaxReportCode){
		if(resiTaxCode.isEmpty() || resiTaxAutonomy.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		 
		return new ResidentialTax(
						new CompanyCode(companyCode), new ResiTaxCode(resiTaxCode), new ResiTaxAutonomy(resiTaxAutonomy), 
						new PrefectureCode(prefectureCode), new ResiTaxReportCode(resiTaxReportCode),
						new RegisteredName(registeredName),new CompanyAccountNo(companyAccountNo),
						new CompanySpecifiedNo(companySpecifiedNo),new CordinatePostalCode(cordinatePostalCode),
						new CordinatePostOffice(cordinatePostOffice), new Memo(memo));
	}
/**
 * constructor
 * @param companyCode
 * @param resiTaxCode
 * @param resiTaxAutonomy
 * @param prefectureCode
 * @param resiTaxReportCode
 * @param registeredName
 * @param companyAccountNo
 * @param companySpecifiedNo
 * @param cordinatePostalCode
 * @param cordinatePostOffice
 * @param memo
 */
public ResidentialTax(CompanyCode companyCode, ResiTaxCode resiTaxCode, ResiTaxAutonomy resiTaxAutonomy,
		PrefectureCode prefectureCode, ResiTaxReportCode resiTaxReportCode, RegisteredName registeredName,
		CompanyAccountNo companyAccountNo, CompanySpecifiedNo companySpecifiedNo,
		CordinatePostalCode cordinatePostalCode, CordinatePostOffice cordinatePostOffice, Memo memo) {
	super();
	this.companyCode = companyCode;
	this.resiTaxCode = resiTaxCode;
	this.resiTaxAutonomy = resiTaxAutonomy;
	this.prefectureCode = prefectureCode;
	this.resiTaxReportCode = resiTaxReportCode;
	this.registeredName = registeredName;
	this.companyAccountNo = companyAccountNo;
	this.companySpecifiedNo = companySpecifiedNo;
	this.cordinatePostalCode = cordinatePostalCode;
	this.cordinatePostOffice = cordinatePostOffice;
	this.memo = memo;
}


}
