package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;

import java.util.ArrayList;
import java.util.List;

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

    private String pId;

    /**
     * 郵便番号
     */
    private String postCode;

    /**
     * 住所1カナ
     */
    private String address1Kana;

    /**
     * 住所2カナ
     */
    private String address2Kana;

    /**
     * 住所1
     */
    private String address1;

    /**
     * 住所2
     */
    private String address2;

    /**
     * 住所1
     */
    private String address1BeforeChangePs;

    /**
     * 住所2
     */
    private String address2BeforeChangePs;

    /**
     * 開始日
     */
    private GeneralDate startDate;

    /**
     * 個人名
     */
    private String personNameKana;

    /**
     * 個人名
     */
    private String personName;

    /**
     * 個人届出名称
     */
    private String todokedeNameKana;

    /**
     * 個人届出名称
     */
    private String todokedeName;
    
    /**
     * 個人.個人名グループ.個人名ローマ字
     */
    private String romanjiName;

    /**
     * 生年月日
     */
    private GeneralDate birthDate;


    public static List<CurrentPersonResidence> createListPerson(List<PersonExport> personExportList) {
        List<CurrentPersonResidence> currentPersonResidenceList = new ArrayList<>();
        personExportList.forEach(i->{
            CurrentPersonResidence c = new CurrentPersonResidence(
                     i.getPersonId(),
                    "1230012",
                    "トウキョウト",
                    "トウキョウクトウキョウ1-1-1",
                    "東京都",
                    "東京区東京1-1-1",
                    "東京都",
                    "東京区東京2-2-2",
                    GeneralDate.fromString("2019/01/01","yyyy/MM/dd" ),
                    i.getPersonNameGroup().getPersonName().getFullNameKana(),
                    i.getPersonNameGroup().getPersonName().getFullName(),
                    i.getPersonNameGroup().getTodokedeFullName().getFullNameKana(),
                    i.getPersonNameGroup().getTodokedeFullName().getFullName(),
                    i.getPersonNameGroup().getPersonRomanji().getFullName(),
                    i.getBirthDate());
            currentPersonResidenceList.add(c);
        });
        return currentPersonResidenceList;
    }
}
