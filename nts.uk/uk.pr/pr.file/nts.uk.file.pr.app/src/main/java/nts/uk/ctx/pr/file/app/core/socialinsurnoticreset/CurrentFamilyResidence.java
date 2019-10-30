package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 家族現住所
 家族現同居住所
 家族情報
 家族前同居住所
 家族前住所
 家族
 家族住所履歴項目
 家族住所履歴
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentFamilyResidence {

    private String familyId;

    /** 郵便番号*/
    private String postCode;

    /** 住所1カナ*/
    private String address1Kana;

    /**住所2カナ */
    private String address2Kana;

    /**住所1 */
    private String address1;

    /**住所2 */
    private String address2;

    private GeneralDate baseDate;

    private boolean isLivingTogether;

    /** 郵便番号*/
    private String postCodeTogether;

    /** 住所1カナ*/
    private String address1KanaTogether;

    /**住所2カナ */
    private String address2KanaTogether;

    /**住所1 */
    private String address1Together;

    /**住所2 */
    private String address2Together;

    /**変更前住所*/
    private String add1BeforeChange;

    /**変更前住所*/
    private String add2BeforeChange;

    /**住所1 */
    private String add1BeforeChangeTogether;

    /**住所2 */
    private String add2BeforeChangeTogether;

    private GeneralDate birthDate;

    /**氏フリガナ*/
    private String nameKana;

    /**氏名*/
    private String name;

    /**届出氏名 リガナ*/
    private String reportNameKana;

    /**届出氏名 */
    private String reportName;

    private GeneralDate startDate;


}
