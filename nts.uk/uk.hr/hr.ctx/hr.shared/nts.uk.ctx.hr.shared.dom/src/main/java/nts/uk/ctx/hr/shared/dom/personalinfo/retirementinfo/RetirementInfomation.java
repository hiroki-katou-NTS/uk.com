package nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * @author anhdt 退職情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RetirementInfomation extends AggregateRoot {
	// 履歴ID
	private String historyID;
	// 社員ID
	private String employeeID;
	// 自己都合退職の手続き
	private String retirementProcedure;
	// 解雇の事由・手続き
	private String dismissalReasonProcedure;
	// 解雇の理由1_チェック
	private int checkDismissalReason1;
	// 解雇の理由1_文字列
	private String dismissalReason1;
	// 解雇の理由2_チェック
	private int checkDismissalReason2;
	// 解雇の理由2_文字列
	private String dismissalReason2;
	// 解雇の理由3_チェック
	private int checkDismissalReason3;
	// 解雇の理由3_文字列
	private String dismissalReason3;
	// 解雇の理由4_チェック
	private int checkDismissalReason4;
	// 解雇の理由4_文字列
	private String dismissalReason4;
	// 解雇の理由5_チェック
	private int checkDismissalReason5;
	// 解雇の理由5_文字列
	private String dismissalReason5;
	// 解雇の理由6_チェック
	private int checkDismissalReason6;
	// 解雇の理由6_文字列
	private String dismissalReason6;
	// 解雇の理由7_チェック
	private int checkDismissalReason7;
	// 解雇の理由7_文字列
	private String dismissalReason7;
	// 解雇の理由8_チェック
	private int checkDismissalReason8;
	// 解雇の理由8_文字列
	private String dismissalReason8;
	// 解雇の理由9_チェック
	private int checkDismissalReason9;
	// 解雇の理由9_文字列
	private String dismissalReason9;
	// 解雇の理由10_チェック
	private int checkDismissalReason10;
	// 解雇の理由10_文字列
	private String dismissalReason10;
	// 解雇予告手当支給日
	private GeneralDate dismissalNoticeAllowanceDate;
	// 解雇予告日
	private GeneralDate dismissalNoticeDate;
	// 退職の備考
	private String retirementRemarks;
	// 退職予定日
	private GeneralDate expectedRetirementDate;
	// 退職区分
	private RetirementCategory retirementCategory;
	// 退職理由区分1
	private String resignmentReasonDivision1;
	// 退職理由区分2
	private String resignmentReasonDivision2;

}
