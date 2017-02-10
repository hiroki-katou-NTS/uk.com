package nts.uk.ctx.basic.dom.company;

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
import nts.uk.ctx.basic.dom.company.useset.UseSet;

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
	/** 期首月	 */
	@Setter
	@Getter
	private TermBeginMon termBeginMon;
	
	/** 権限 */
	@Getter
	private CompanyUseSet companyUseSet;
	
    //** 表示区分  */
	@Getter
	private DisplayAttribute displayAttribute;
	/**
	 * @param companyCode
	 * @param address
	 * @param companyName
	 * @param companyNameAbb
	 * @param companyNameKana
	 * @param corporateMyNumber
	 * @param depWorkPlaceSet
	 * @param displayAttribute2
	 * @param faxNo2
	 * @param postal2
	 * @param presidentJobTitle2
	 */
	public Company(CompanyCode companyCode, Address address, CompanyName companyName,
			CompanyNameAbb companyNameAbb,CompanyNameKana companyNameKanal,
			CorporateMyNumber corporateMyNumber, DepWorkPlaceSet depWorkPlaceSet,
			DisplayAttribute displayAttribute, FaxNo faxNo, Postal postal, 
			PresidentJobTitle presidentJobTitle, TelephoneNo telephoneNo,TermBeginMon termBeginMon,CompanyUseSet companyUseSet) {
		super();
		this.companyCode = companyCode;
		this.address = address;
		this.companyName = companyName;
		this.companyNameAbb = companyNameAbb;
		this.companyNameKana = companyNameKanal;
		this.corporateMyNumber = corporateMyNumber;
		this.depWorkPlaceSet = depWorkPlaceSet;
		this.displayAttribute = displayAttribute;
		this.faxNo = faxNo;
		this.postal = postal;
		this.presidentJobTitle =presidentJobTitle;
		this.telephoneNo =telephoneNo ;
		this.termBeginMon = termBeginMon;
		this.companyUseSet = companyUseSet;
	}
	public static Company createFromJavaType(String companyCode,String companyName,
			String companyNameAbb,String companyNameKana,String corporateMyNumber,
			String faxNo, String postal, String presidentJobTitle, String telephoneNo,
			int depWorkPlaceSet, int displayAttribute, String address1,String address2,
			String addressKana1,String addressKana2, int termBeginMon,
			int use_Gr_Set, int use_Kt_Set, int use_Qy_Set, int use_Jj_Set,
			int use_Ac_Set, int use_Gw_Set,	int use_Hc_Set, int use_Lc_Set, int use_Bi_Set,
			int use_Rs01_Set, int use_Rs02_Set,int use_Rs03_Set,int use_Rs04_Se,
			int use_Rs05_Set, int use_Rs06_Set, int use_Rs07_Set, int use_Rs08_Set,
			int use_Rs09_Set,int use_Rs10_Set)
	{
		if (companyCode.isEmpty()|| companyName.isEmpty()||
				companyNameAbb.isEmpty()) {
			//TODO-LanLT
			throw new BusinessException(new RawErrorMessage("明細書名が入力されていません。"));
		}
		 Company company= new Company(new CompanyCode(companyCode),
				          new Address
				          (new Address1(address1), new Address2(address2), 
				           new AddressKana1(addressKana1), new AddressKana2(addressKana2)),
                           new CompanyName(companyName), new CompanyNameAbb(companyNameAbb), new CompanyNameKana(companyNameKana),
                           new CorporateMyNumber(corporateMyNumber), EnumAdaptor.valueOf(depWorkPlaceSet, DepWorkPlaceSet.class), 
                           EnumAdaptor.valueOf(displayAttribute, DisplayAttribute.class),
                           new FaxNo(faxNo), new Postal(postal), new PresidentJobTitle(presidentJobTitle), 
                           new TelephoneNo(telephoneNo), new TermBeginMon(termBeginMon),
                           new CompanyUseSet(
                        		   new UseSet(use_Gr_Set),new  UseSet(use_Kt_Set),new UseSet(use_Qy_Set), new UseSet(use_Jj_Set), new UseSet(use_Ac_Set),
                        		   new UseSet(use_Gw_Set), new UseSet(use_Hc_Set), new UseSet(use_Lc_Set), new UseSet(use_Bi_Set), new UseSet(use_Rs01_Set),
                        		   new UseSet(use_Rs02_Set) , new UseSet(use_Rs03_Set), new UseSet (use_Rs04_Se), new UseSet(use_Rs05_Set), new UseSet(use_Rs06_Set),
                        		   new UseSet(use_Rs07_Set), new UseSet(use_Rs08_Set), new UseSet(use_Rs09_Set), new UseSet(use_Rs10_Set)));
		return company;
		
	}


}
