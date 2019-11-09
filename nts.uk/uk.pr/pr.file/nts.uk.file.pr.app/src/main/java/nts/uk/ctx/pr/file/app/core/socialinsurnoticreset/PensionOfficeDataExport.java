package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PensionOfficeDataExport {

    private String healOfficeNumber1;

    private String welOfficeNumber1;

    private String healOfficeNumber2;

    private String welOfficeNumber2;

    private String healOfficeNumber;

    private String welOfficeNumber;

    private String personName;

    private String personNameKana;

    private String oldName;

    private String oldNameKana;

    private String birthDay;

    private int gender;

    private int underDivision;

    private Integer livingAbroad;

    private Integer shortStay;

    private Integer resonOther;

    private String resonAndOtherContent;

    private String startDate1;

    private String startDate2;

    private String depenAppoint;

    private Integer remunMonthlyAmount;

    private Integer remunMonthlyAmountKind;

    private Integer totalMonthyRemun;

    private Integer percentOrMore;

    private Integer isMoreEmp;

    private Integer shortTimeWorkes;

    private Integer continReemAfterRetirement;

    private String remarksAndOtherContent;

    private Integer healPrefectureNo;

    private Integer welPrefectureNo;

    private String officeNumber1;

    private String officeNumber2;

    private String officeNumber;

    private String unionOfficeNumber;

    private String portCd;

    // = add 1 + add 2
    private String add;
    // = add kana 1 + add kana 2
    private String addKana;


    private String companyName;

    private String repName;

    private String phoneNumber;

    private Integer healInsCtg;

    private String distin;

    private String healInsInherenPr;

    private String healUnionNumber;

    private String hisId;

    private int endDate;

    private int bussinesArrSybol;

    private String sid;

}
