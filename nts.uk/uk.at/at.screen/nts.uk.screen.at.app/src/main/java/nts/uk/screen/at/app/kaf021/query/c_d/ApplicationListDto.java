package nts.uk.screen.at.app.kaf021.query.c_d;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationListDto {
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * 職場
     */
    private String workplaceName;
    /**
     * 個人: code
     */
    private String employeeCode;
    /**
     * 個人: name
     */
    private String employeeName;
    /**
     * 申請時間
     */
    private ApplicationTimeDto applicationTime;
    /**
     * 画面表示情報
     */
    private ScreenDisplayInfoDto screenDisplayInfo;
    /**
     * 申請理由
     */
    private String reason;
    /**
     * コメント
     */
    private String comment;
    /**
     * 申請者
     */
    private String applicant;
    /**
     * 入力日付
     */
    private GeneralDate inputDate;
    /**
     * 承認者
     */
    private String approver;
    /**
     * 承認状況
     */
    private int approvalStatus;
    /**
     * 従業員代表
     */
    private String confirmer;
    /**
     * 確認状況
     */
    private int confirmStatus;
}
