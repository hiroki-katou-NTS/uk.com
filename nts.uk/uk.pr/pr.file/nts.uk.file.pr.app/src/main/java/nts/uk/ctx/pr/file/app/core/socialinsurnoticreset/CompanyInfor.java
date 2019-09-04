package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

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
	public String companyCode;

	/** 契約コード */
	public String contractCd;

	/** 会社名 */
	public String companyName;

	/**  期首月 */
	public int startMonth;

	/** 廃止区分 */
	public int isAbolition;

	/** 代表者名 */
	public String repname;

	/** 代表者職位 */
	public String repost;

	/** 会社名カナ */
	public String comNameKana;

	/** 会社名カナ */
	public String shortComName;

	/** 法人マイナンバー */
	public String taxNo;

	/** FAX番号*/
	public String faxNum;

	/** 住所１ */
	public String add_1;

	/** 住所２ */
	public String add_2;

	/**  住所カナ１ */
	public String addKana_1;

	/** 住所カナ２ */
	public String addKana_2;

	/** 郵便番号 */
	public String postCd;

	/** 電話番号 */
	public String phoneNum;
}
