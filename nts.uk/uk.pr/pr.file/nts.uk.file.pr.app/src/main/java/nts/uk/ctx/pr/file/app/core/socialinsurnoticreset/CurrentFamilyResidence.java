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

    private Integer familyId;

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

    public CurrentFamilyResidence(String postCode, String address1Kana, String address2Kana, String address1, String address2, GeneralDate baseDate, boolean isLivingTogether, String postCodeTogether, String address1KanaTogether, String address2KanaTogether, String address1Together, String address2Together, String add1BeforeChange, String add2BeforeChange, String add1BeforeChangeTogether, String add2BeforeChangeTogether, GeneralDate birthDate, String nameKana, String name, String reportNameKana, String reportName, GeneralDate startDate) {
        this.postCode = postCode;
        this.address1Kana = address1Kana;
        this.address2Kana = address2Kana;
        this.address1 = address1;
        this.address2 = address2;
        this.baseDate = baseDate;
        this.isLivingTogether = isLivingTogether;
        this.postCodeTogether = postCodeTogether;
        this.address1KanaTogether = address1KanaTogether;
        this.address2KanaTogether = address2KanaTogether;
        this.address1Together = address1Together;
        this.address2Together = address2Together;
        this.add1BeforeChange = add1BeforeChange;
        this.add2BeforeChange = add2BeforeChange;
        this.add1BeforeChangeTogether = add1BeforeChangeTogether;
        this.add2BeforeChangeTogether = add2BeforeChangeTogether;
        this.birthDate = birthDate;
        this.nameKana = nameKana;
        this.name = name;
        this.reportNameKana = reportNameKana;
        this.reportName = reportName;
        this.startDate = startDate;
    }
}
