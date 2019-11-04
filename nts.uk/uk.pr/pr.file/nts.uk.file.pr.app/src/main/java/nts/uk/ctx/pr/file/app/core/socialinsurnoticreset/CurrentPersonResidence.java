package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

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

    private String empId;

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
     * 生年月日
     */
    private GeneralDate birthDate;

    /**
     * 住所1
     */
    private String beforeAddress1;

    /**
     * 住所1
     */
    private String beforeAddress2;


    public static List<CurrentPersonResidence> createListPerson(List<String> empIds) {
        List<CurrentPersonResidence> currentPersonResidenceList = new ArrayList<>();
        empIds.forEach(i->{
            CurrentPersonResidence c = new CurrentPersonResidence(
                     i,
                    "1234567",
                    "address1Kana",
                    "address2Kana",
                    "address1",
                    "address2",
                     GeneralDate.fromString("1992/10/01","yyyy/MM/dd" ),
                    "person NameKana",
                    "person Name",
                    "todokede NameKana",
                    "todokede Name",
                     GeneralDate.fromString("2018/10/10","yyyy/MM/dd" ),
                    "beforeAddress1",
                    "beforeAddress2");
            currentPersonResidenceList.add(c);
        });
        return currentPersonResidenceList;
    }
}
