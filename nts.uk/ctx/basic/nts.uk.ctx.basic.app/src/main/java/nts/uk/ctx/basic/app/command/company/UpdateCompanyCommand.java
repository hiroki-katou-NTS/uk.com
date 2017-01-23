package nts.uk.ctx.basic.app.command.company;

import javax.inject.Inject;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.company.CompanyName;
import nts.uk.ctx.basic.dom.company.CompanyNameAbb;
import nts.uk.ctx.basic.dom.company.CompanyNameKana;
import nts.uk.ctx.basic.dom.company.CorporateMyNumber;
import nts.uk.ctx.basic.dom.company.DepWorkPlaceSet;
import nts.uk.ctx.basic.dom.company.FaxNo;
import nts.uk.ctx.basic.dom.company.Postal;
import nts.uk.ctx.basic.dom.company.PresidentJobTitle;
import nts.uk.ctx.basic.dom.company.TelephoneNo;
import nts.uk.ctx.basic.dom.company.address.Address;
import nts.uk.ctx.basic.dom.company.address.Address1;
import nts.uk.ctx.basic.dom.company.address.Address2;
import nts.uk.ctx.basic.dom.company.address.AddressKana1;
import nts.uk.ctx.basic.dom.company.address.AddressKana2;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author lanlt
 *
 */
@Getter
public class UpdateCompanyCommand {
	private String companyCodeOrginal;
    private String companyCode;
    private String companyName;
    private String address1;
    private String address2;
    private String addressKana1;
    private String addressKana2;
    private String companyNameAbb;
    private String companyNameKana;
    private String corporateMyNumber;
    private int depWorkPlaceSet;
    private String faxNo;
    private String postal;
    private String presidentJobTitle;
    private String telephoneNo;
	private String historyId;
	public Company toDomain(){
		return Company.createFromJavaType(AppContexts.user().companyCode(), companyName, companyNameAbb, 
				companyNameKana, corporateMyNumber, faxNo, postal, presidentJobTitle, telephoneNo,
				 depWorkPlaceSet, address1, address2, addressKana1, addressKana2);
	}
}
