package nts.uk.ctx.basic.dom.company;

import javax.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.basic.dom.company.address.Address;
import nts.uk.ctx.basic.dom.company.address.Address1;
import nts.uk.ctx.basic.dom.company.address.Address2;
import nts.uk.ctx.basic.dom.company.address.AddressKana1;
import nts.uk.ctx.basic.dom.company.address.AddressKana2;
import nts.uk.ctx.basic.dom.company.useset.CompanyUseSet;

public class Company extends AggregateRoot{
	/**会社コード */
	
	@Getter
	@Setter
	private CompanyCode companyCode;
	
   /** 住所  */
	@Setter
	@Getter
	private Address address;
	
	/**会社名 */
	@Setter
	@Getter
	private CompanyName companyName;
	
	/** 会社略名 */
	@Setter
	@Getter
	private CompanyNameAbb companyNameAbb;
	
	/** 会社名カ */
	@Setter
	@Getter
	private CompanyNameKana companyNameKana;
	
	/** 法人マイナンバー*/
	@Setter
	@Getter
	private CorporateMyNumber corporateMyNumber;
	
	/** 部門と職場設定 */
	@Setter
	@Getter
	private DepWorkPlaceSet depWorkPlaceSet;
	
	/** FAX番号 */
	@Setter
	@Getter
	private FaxNo faxNo;
	
	/** 郵便番号 */
	@Setter
	@Getter
	private Postal postal;
	
	/** 代表者職位*/
	@Setter
	@Getter
	private PresidentJobTitle presidentJobTitle;
	
	/** 電話番号 */
	@Setter
	@Getter
	private TelephoneNo telephoneNo;
	
	/** 権限 */
	@Getter
	private CompanyUseSet companyUseSet;
	/**
	 * @param companyCode
	 * @param address
	 * @param companyName
	 * @param companyNameAbb
	 * @param companyNameKana
	 * @param corporateMyNumber
	 * @param depWorkPlaceSet
	 * @param faxNo
	 * @param postal
	 * @param presidentJobTitle
	 * @param telephoneNo
	 */
	public Company(CompanyCode companyCode, Address address, CompanyName companyName, CompanyNameAbb companyNameAbb,
			CompanyNameKana companyNameKanal, CorporateMyNumber corporateMyNumber, DepWorkPlaceSet depWorkPlaceSet,
			FaxNo faxNo, Postal postal, PresidentJobTitle presidentJobTitle, TelephoneNo telephoneNo) {
		super();
		this.companyCode = companyCode;
		this.address = address;
		this.companyName = companyName;
		this.companyNameAbb = companyNameAbb;
		this.companyNameKana = companyNameKanal;
		this.corporateMyNumber = corporateMyNumber;
		this.depWorkPlaceSet = depWorkPlaceSet;
		this.faxNo = faxNo;
		this.postal = postal;
		this.presidentJobTitle = presidentJobTitle;
		this.telephoneNo = telephoneNo;
		this.companyUseSet = companyUseSet;
	}
	
	/**
	 * @param companyCode
	 * @param address
	 * @param companyName
	 * @param companyNameAbb
	 * @param companyNameKana
	 * @param corporateMyNumber
	 * @param faxNo
	 * @param postal
	 * @param presidentJobTitle
	 * @param telephoneNo
	 */
	public Company(CompanyCode companyCode,CompanyName companyName, CompanyNameAbb companyNameAbb,
			CompanyNameKana companyNameKana, CorporateMyNumber corporateMyNumber, FaxNo faxNo, Postal postal,
			PresidentJobTitle presidentJobTitle, TelephoneNo telephoneNo,DepWorkPlaceSet depWorkPlaceSet,
			Address1 address1,Address2 address2,AddressKana1 addressKana1,AddressKana2 addressKana2) {
		super();
		this.companyCode = companyCode;
		this.address = new Address(address1,address2,addressKana1,addressKana2);
		this.companyName = companyName;
		this.companyNameAbb = companyNameAbb;
		this.companyNameKana = companyNameKana;
		this.corporateMyNumber = corporateMyNumber;
		this.depWorkPlaceSet = depWorkPlaceSet;
		this.faxNo = faxNo;
		this.postal = postal;
		this.presidentJobTitle = presidentJobTitle;
		this.telephoneNo = telephoneNo;
		
	}
	public static Company createFromJavaType(String companyCode,String companyName,
			String companyNameAbb,String companyNameKana,String corporateMyNumber,
			String faxNo, String postal, String presidentJobTitle, String telephoneNo,
			int depWorkPlaceSet, String address1,String address2,
			String addressKana1,String addressKana2)
	{
		if (companyCode.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		 Company company= new Company(new CompanyCode(companyCode),new Address(new Address1(address1), new Address2(address2), new AddressKana1(addressKana1), new AddressKana2(addressKana2)),
                           new CompanyName(companyName), new CompanyNameAbb(companyNameAbb), new CompanyNameKana(companyNameKana),
                           new CorporateMyNumber(corporateMyNumber), EnumAdaptor.valueOf(depWorkPlaceSet, DepWorkPlaceSet.class), 
                           new FaxNo(faxNo), new Postal(postal), new PresidentJobTitle(presidentJobTitle), new TelephoneNo(telephoneNo));
		return company;
		
	}


}
