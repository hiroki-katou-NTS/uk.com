package nts.uk.ctx.basic.app.finder.company;

import lombok.Value;
import nts.uk.ctx.basic.dom.company.Company;
/**
 * 
 * @author lanlt
 *
 */
/** find value of company */
@Value
public class CompanyDto {
	/**会社コード */
	String companyCode;
	/**会社名 */
	String companyName;
	/** 住所  */
	String address;
	/** 会社略名 */
	String companyNameAbb;
	/** 会社名カ */
	String companyNameKana;
	/** 法人マイナンバー*/
	String corporateMyNumber;
	/** 部門と職場設定 */
	String depWorkPlaceSet;
	/** FAX番号 */
	String faxNo;
	/** 郵便番号 */
	String postal;
	/** 代表者職位*/
	String presidentJobTitle;
	/**
	 * 電話番号 
	 */
	String telephoneNo;
	/**
	 * 権限
	 */
	String companyUseSet;
	/**
	 * 
	 * @param company
	 * @return
	 */
	public static CompanyDto  fromDomain(Company company) {
		return new CompanyDto(company.getCompanyCode().v(), company.getAddress().toString(),
				company.getCompanyName().v(),company.getCompanyNameAbb().v(),
				company.getCompanyNameKana().v(),company.getCorporateMyNumber().v(),
				company.getDepWorkPlaceSet().name(),company.getFaxNo().v(), company.getPostal().v(),
				company.getPresidentJobTitle().v(),	company.getTelephoneNo().v(),company.getCompanyUseSet().toString());
	}
  
}
