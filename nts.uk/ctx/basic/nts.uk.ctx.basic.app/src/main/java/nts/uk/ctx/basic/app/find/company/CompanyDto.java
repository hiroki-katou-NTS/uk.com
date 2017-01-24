package nts.uk.ctx.basic.app.find.company;

import lombok.Value;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.company.CompanyName;
import nts.uk.ctx.basic.dom.company.CompanyNameAbb;
import nts.uk.ctx.basic.dom.company.CompanyNameKana;
import nts.uk.ctx.basic.dom.company.CorporateMyNumber;
import nts.uk.ctx.basic.dom.company.DepWorkPlaceSet;
import nts.uk.ctx.basic.dom.company.DisplayAttribute;
import nts.uk.ctx.basic.dom.company.FaxNo;
import nts.uk.ctx.basic.dom.company.Postal;
import nts.uk.ctx.basic.dom.company.PresidentJobTitle;
import nts.uk.ctx.basic.dom.company.TelephoneNo;
import nts.uk.ctx.basic.dom.company.TermBeginMon;
import nts.uk.ctx.basic.dom.company.address.Address1;
import nts.uk.ctx.basic.dom.company.address.Address2;
import nts.uk.ctx.basic.dom.company.address.AddressKana1;
import nts.uk.ctx.basic.dom.company.address.AddressKana2;
import nts.uk.ctx.basic.dom.company.useset.UseSet;
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
	String address1;
	String address2;
	String addressKana1;
	String addressKana2;
	
	/** 会社略名 */
	String companyNameAbb;
	
	/** 会社名カ */
	String companyNameKana;
	
	/** 法人マイナンバー*/
	String corporateMyNumber;
	
	/** 部門と職場設定 */
	int depWorkPlaceSet;
	
	int displayAttribute;
	
	/** FAX番号 */
	String faxNo;
	
	/** 郵便番号 */
	String postal;
	
	/** 代表者職位*/
	String presidentJobTitle;
	
	/** 電話番号 	 */
	String telephoneNo;
	int termBeginMon;
	
	/** 権限	 */
	int use_Gr_Set;
	int use_Kt_Set;
	int use_Qy_Set;
	int use_Jj_Set;
	int use_Ac_Set;
	int use_Gw_Set;
	int use_Hc_Set;
	int use_Lc_Set;
	int use_Bi_Set;
	int use_Rs01_Set;
	int use_Rs02_Set;
	int use_Rs03_Set;
	int use_Rs04_Set;
	int use_Rs05_Set;
	int use_Rs06_Set;
	int use_Rs07_Set;
	int use_Rs08_Set;
	int use_Rs09_Set;
    int use_Rs10_Set;	
	/**
	 * 
	 * @param company
	 * @return
	 */
	public static CompanyDto fromDomain(Company company){
		return new CompanyDto(
				company.getCompanyCode().v(), company.getCompanyName().v(),
				company.getAddress().getAddress1().v(), company.getAddress().getAddress2().v(),
				company.getAddress().getAddressKana1().v(), company.getAddress().getAddressKana2().v(),
				company.getCompanyNameAbb().v(), company.getCompanyNameKana().v(),
				company.getCorporateMyNumber().v(), company.getDepWorkPlaceSet().value,
				company.getDisplayAttribute().value, company.getFaxNo().v(),
				company.getPostal().v(), company.getPresidentJobTitle().v(),
				company.getTelephoneNo().v(), company.getTermBeginMon().v(),
				company.getCompanyUseSet().getUse_Gr_Set().v(), company.getCompanyUseSet().getUse_Kt_Set().v(),
				company.getCompanyUseSet().getUse_Qy_Set().v(), company.getCompanyUseSet().getUse_Jj_Set().v(),
				company.getCompanyUseSet().getUse_Ac_Set().v(), company.getCompanyUseSet().getUse_Gw_Set().v(),
				company.getCompanyUseSet().getUse_Hc_Set().v(), company.getCompanyUseSet().getUse_Lc_Set().v(),
				company.getCompanyUseSet().getUse_Bi_Set().v(), company.getCompanyUseSet().getUse_Rs01_Set().v(),
				company.getCompanyUseSet().getUse_Rs02_Set().v(), company.getCompanyUseSet().getUse_Rs03_Set().v(),
				company.getCompanyUseSet().getUse_Rs04_Set().v(), company.getCompanyUseSet().getUse_Rs05_Set().v(),
				company.getCompanyUseSet().getUse_Rs06_Set().v(), company.getCompanyUseSet().getUse_Rs07_Set().v(),
				company.getCompanyUseSet().getUse_Rs08_Set().v(), company.getCompanyUseSet().getUse_Rs09_Set().v(),
				company.getCompanyUseSet().getUse_Rs10_Set().v());
	}

}
