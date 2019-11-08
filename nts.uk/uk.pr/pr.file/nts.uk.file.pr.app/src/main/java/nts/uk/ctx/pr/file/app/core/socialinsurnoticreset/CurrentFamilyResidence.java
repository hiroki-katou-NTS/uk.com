package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.person.family.FamilyMemberInfoEx;

import java.util.ArrayList;
import java.util.List;

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
@AllArgsConstructor
public class CurrentFamilyResidence {

    private String personId;

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

    /**同居別居区分*/
    private boolean isLivingSeparate;

    /**生年月日*/
    private GeneralDate birthDate;

    /**氏フリガナ*/
    private String nameKana;

    /**氏名*/
    private String name;

    /**届出氏名 リガナ*/
    private String reportNameKana;

    /**届出氏名 */
    private String reportName;

    /**開始日*/
    private GeneralDate startDate;

    /**終了日*/
    private GeneralDate endDate;

    public CurrentFamilyResidence() {
        this.postCode = "5300025";
        this.address1Kana = "オオサカフ";
        this.address2Kana = "オオカサシキタクオウギマチ1-1-21";
        this.address1 = "大阪府";
        this.address2 = "大阪市北区扇町1-1-21";
        this.isLivingSeparate = false;
        this.reportNameKana = "トドケデ　ダミー";
        this.reportName = "届出　ダミー";
        this.startDate = GeneralDate.fromString("2019/01/01","yyyy/MM/dd" );
        this.endDate = GeneralDate.fromString("2020/01/01","yyyy/MM/dd" );
    }

    public static CurrentFamilyResidence getListFamily(List<FamilyMemberInfoEx> fList, String personId ){
        if(!fList.isEmpty()){
            CurrentFamilyResidence c = new CurrentFamilyResidence();
            c.setPersonId(personId);
            c.setFamilyId(fList.get(0).getFamilyId());
            c.setName(fList.get(0).getRomajiName().isPresent() ? fList.get(0).getRomajiName().get() : "");
            c.setName(fList.get(0).getRomajiNameKana().isPresent() ? fList.get(0).getRomajiNameKana().get() : "");
            c.setName(fList.get(0).getBirthday() != null ? fList.get(0).getBirthday(): "");
        }
        return null;
    }
}
