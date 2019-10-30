package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 個人現住所
 個人前住所
 個人情報
 現住所履歴項目
 現住所履歴
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPersonResidence {


    private String personId;

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

    /** 開始日 */
    private GeneralDate startDate;

    /**個人名*/
    private String personNameKana;

    /**個人名*/
    private String personName;

    /**個人届出名称*/
    private String todokedeNameKana;

    /**個人届出名称*/
    private String todokedeName;

    /**生年月日*/
    private GeneralDate birthDate;

    /**住所1*/
    private String beforeAddress1;

    /**住所1*/
    private String beforeAddress2;
}
