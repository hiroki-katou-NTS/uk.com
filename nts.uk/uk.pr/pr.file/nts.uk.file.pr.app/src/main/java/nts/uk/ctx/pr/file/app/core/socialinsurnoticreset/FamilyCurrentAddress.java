package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 家族現住所
 * */
public class FamilyCurrentAddress {

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

    /** 開始日 */
    private GeneralDate startDate;

    /**同居別居区分 : false , 同居 の場合  true*/
    private boolean livingTogether ;
}
