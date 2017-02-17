/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * @author lanlt
 *
 */
public class ResidentalTax  extends AggregateRoot{
	@Getter
	private CompanyCode companyCode;
	@Getter
	private CompanyAccountNo companyAccountNo;
	@Getter
	private CompanySpecifiedNo companySpecifiedNo;
	@Getter
	private CordinatePostOffice cordinatePostOffice;
	@Getter
	private CordinatePostalCode cordinatePostalCode;
	@Getter
	private Memo memo;
	@Getter
	private PrefectureCode prefectureCode;
	@Getter
	private RegisteredName registeredName;
	@Getter
	private ResiTaxAutonomy resiTaxAutonomy;
	@Getter
	private ResiTaxCode resiTaxCode;
	@Getter
	private ResiTaxReportCode resiTaxReportCode;

	/**
	 * @param companyCode
	 * @param companyAccountNo
	 * @param companySpecifiedNo
	 * @param cordinatePostOffice
	 * @param cordinatePostalCode
	 * @param memo
	 * @param prefectureCode
	 * @param registeredName
	 * @param resiTaxAutonomy
	 * @param resiTaxCode
	 * @param resiTaxReportCode
	 */
	public ResidentalTax(CompanyCode companyCode, CompanyAccountNo companyAccountNo,
			CompanySpecifiedNo companySpecifiedNo, CordinatePostOffice cordinatePostOffice,
			CordinatePostalCode cordinatePostalCode, Memo memo, PrefectureCode prefectureCode,
			RegisteredName registeredName, ResiTaxAutonomy resiTaxAutonomy,
			ResiTaxCode resiTaxCode,ResiTaxReportCode resiTaxReportCode) {
		super();
		this.companyCode = companyCode;
		this.companyAccountNo = companyAccountNo;
		this.companySpecifiedNo = companySpecifiedNo;
		this.cordinatePostOffice = cordinatePostOffice;
		this.cordinatePostalCode = cordinatePostalCode;
		this.memo = memo;
		this.prefectureCode = prefectureCode;
		this.registeredName = registeredName;
		this.resiTaxAutonomy = resiTaxAutonomy;
		this.resiTaxCode = resiTaxCode;
		this.resiTaxReportCode = resiTaxReportCode;
	}

	public static ResidentalTax createFromJavaType(String companyCode,String companyAccountNo, String companySpecifiedNo,
			String cordinatePostOffice, String cordinatePostalCode, String memo,
			String prefectureCode, String registeredName,
			String resiTaxAutonomy,
			String resiTaxCode, String resiTaxReportCode){
		if(companyCode.isEmpty()){
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		 
		return 
				new ResidentalTax(
						new CompanyCode(companyCode), new CompanyAccountNo(companyAccountNo),
						new CompanySpecifiedNo(companySpecifiedNo), new CordinatePostOffice(cordinatePostOffice),
						new CordinatePostalCode(cordinatePostalCode), new Memo(memo),
						new PrefectureCode(prefectureCode), new RegisteredName(registeredName),
						new ResiTaxAutonomy(resiTaxAutonomy), new ResiTaxCode(resiTaxCode),
						new ResiTaxReportCode(resiTaxReportCode));
	}


}
