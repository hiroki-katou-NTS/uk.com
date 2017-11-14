package nts.uk.ctx.bs.company.dom.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.bs.company.dom.company.primitive.Add_1;
import nts.uk.ctx.bs.company.dom.company.primitive.Add_2;
import nts.uk.ctx.bs.company.dom.company.primitive.Add_Kana_1;
import nts.uk.ctx.bs.company.dom.company.primitive.Add_Kana_2;
import nts.uk.ctx.bs.company.dom.company.primitive.ContractCd;
import nts.uk.ctx.bs.company.dom.company.primitive.FaxNum;
import nts.uk.ctx.bs.company.dom.company.primitive.PhoneNum;
import nts.uk.ctx.bs.company.dom.company.primitive.PostCd;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddInfor {
	/**会社ID**/
	private CompanyId companyId;
	
	// 会社コード
	private CCD companyCode;
	
	/** 契約コード */
	private ContractCd contractCd;
	
	/** FAX番号 **/
	private FaxNum faxNum;
	/** 住所１ **/
	private Add_1 add_1;
	/** 住所２ **/
	private Add_2 add_2;
	/** 住所カナ１ **/
	private Add_Kana_1 addKana_1;
	/** 住所カナ２ **/
	private Add_Kana_2 addKana_2;
	/** 郵便番号 **/
	private PostCd postCd;
	/** 電話番号 **/
	private PhoneNum phoneNum;
	
	public static AddInfor createFromJavaType(String companyId, String companyCode, 
												String contractCd,
												String faxNum, String add_1, 
												String add_2, String addKana_1, 
												String addKana_2, String postCd, 
												String phoneNum){
		return new AddInfor(new CompanyId(companyId), new CCD(companyCode),
							new ContractCd(contractCd),
							new FaxNum(faxNum), new Add_1(add_1),
							new Add_2(add_2), new Add_Kana_1(addKana_1),
							new Add_Kana_2(addKana_2), new PostCd(postCd),
							new PhoneNum(phoneNum));
	}
}
