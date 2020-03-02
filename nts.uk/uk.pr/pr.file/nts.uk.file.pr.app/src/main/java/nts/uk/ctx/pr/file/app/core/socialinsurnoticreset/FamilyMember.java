package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMember {

    // 生年月日
    private String birthday;
    // 死亡年月日
    private String deadDay;
    // 入籍年月日
    private String entryDate;
    // 除籍年月日
    private String expelledDate;
    // 家族ID
    private String familyMemberId;
    // 氏名
    private String fullName;
    // 氏名カナ
    private String fullNameKana;
    // 氏名他言語
    private String nameMultiLangFull;
    // 氏名他言語カナ
    private String nameMultiLangFullKana;
    // 氏名ローマ字
    private String nameRomajiFull;
    // 氏名ローマ字カナ
    private String nameRomajiFullKana;
    // 国籍
    private String nationalityId;
    // 職業
    private String occupationName;
    // 個人ID
    private String personId;
    // 続柄
    private String relationship;
    // 支援介護区分
    private Integer supportCareType;

    private String tokodekeName;

    // 同居別居区分
    private Integer togSepDivisionType;
    // 勤労学生
    private Integer workStudentType;

    private Integer gender;

    public FamilyMember(String birthday, String fullName, String familyMemberId, int gender , String fullNameKana) {
        this.birthday = birthday;
        this.fullName = fullName;
        this.familyMemberId = familyMemberId;
        this.gender = gender;
        this.fullNameKana = fullNameKana;
    }

}
