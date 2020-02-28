package nts.uk.ctx.pr.core.dom.adapter.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CompanyInfor {

	
	/** 会社ID*/
	private String companyId;

	/** 会社コード */
	private String companyCode;

	/** 契約コード */
	private String contractCd;

	/** 会社名 */
	private String companyName;

	/** 代表者名 */
	private String repname;

	/** 代表者職位 */
	private String repost;

	/** 会社名カナ */
	private String comNameKana;

	/** 会社名カナ */
	private String shortComName;

	/** 法人マイナンバー */
	private String taxNo;

	/** FAX番号*/
	private String faxNum;

	/** 住所１ */
	private String add_1;

	/** 住所２ */
	private String add_2;

	/**  住所カナ１ */
	private String addKana_1;

	/** 住所カナ２ */
	private String addKana_2;

	/** 郵便番号 */
	private String postCd;

	/** 電話番号 */
	private String phoneNum;

	public CompanyInfor(String postCd, String add_1, String add_2, String companyName, String repname, String phoneNum) {
		this.postCd = postCd;
		this.add_1 = add_1;
		this.add_2= add_2;
		this.companyName = companyName;
		this.repname = repname;
		this.phoneNum = phoneNum;

	}
}
