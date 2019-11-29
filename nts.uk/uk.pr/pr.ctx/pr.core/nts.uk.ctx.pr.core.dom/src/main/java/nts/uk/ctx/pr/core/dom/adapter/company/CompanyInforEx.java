package nts.uk.ctx.pr.core.dom.adapter.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInforEx {
    /** 会社ID*/
    private String companyId;

    /** 会社コード*/
    private String companyCode;

    /** 会社名*/
    private String companyName;

    /** 会社名カナ */
    private String comNameKana;

    /** 会社略名 */
    private String shortComName;

    /** 代表者名 */
    private String repname;

    /** 代表者職位 */
    private String repjob;

    /** 契約コード */
    private String contractCd;

    /** 法人マイナンバー */
    private String taxNo;

    /** 期首月*/
    private int startMonth;

    /** 住所情報 */
    private AddInforExport addInfo;

    /** 廃止区分*/
    private int isAbolition;
}
