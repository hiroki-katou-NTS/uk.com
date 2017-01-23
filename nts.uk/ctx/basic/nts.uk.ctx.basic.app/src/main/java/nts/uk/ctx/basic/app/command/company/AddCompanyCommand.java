package nts.uk.ctx.basic.app.command.company;

import lombok.Getter;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author lanlt
 *
 */
@Getter
public class AddCompanyCommand {
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
