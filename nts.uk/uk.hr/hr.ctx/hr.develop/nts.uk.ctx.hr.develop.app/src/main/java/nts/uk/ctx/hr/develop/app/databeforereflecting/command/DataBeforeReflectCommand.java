package nts.uk.ctx.hr.develop.app.databeforereflecting.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataBeforeReflectCommand {
	// 履歴ID
		public String historyId;
		
		// 反映先_履歴ID
		public String histId_Refer;

		// 契約コード
		public String contractCode;

		// 会社ID
		public String companyId;

		// 会社コード
		public String companyCode;

		// 業務ID
		public Integer workId;

		// 業務名称
		public String workName;

		// 社員ID
		public String sId;
		
		public String pId;

		// 社員コード
		public String scd;

		// 社員名 ********** can hoi lai truong nay
		public String employeeName;

		// 保留フラグ
		public int pendingFlag;

		// 入力日
		public GeneralDate inputDate;

		// ステータス
		public int status;

		// 退職日
		public GeneralDateTime retirementDate;

		// 公開日
		public GeneralDateTime releaseDate;

		// 退職区分（必須） RetirementCategory A222_16
		public int retirementType; 

		// 届出区分
		public int selectedCode_Retiment;

		// 退職理由区分1
		public int selectedCode_Reason1;

		// 退職理由区分2
		public int selectedCode_Reason2;

		// 退職の備考
		public String retirementRemarks;

		// 自己都合退職の手続き
		public String retirementReasonVal;

		// 解雇予告日
		public GeneralDate dismissalNoticeDate;

		// 解雇予告手当支給日
		public GeneralDate dismissalNoticeDateAllow;

		// 解雇の事由・手続き
		public String reaAndProForDis;

		// 解雇の理由1_チェック
		public int naturalUnaReasons_1;

		// 解雇の理由1_文字列
		public String naturalUnaReasons_1Val;

		// 解雇の理由2_チェック
		public int businessReduction_2;

		// 解雇の理由2_文字列
		public String businessReduction_2Val;

		// 解雇の理由3_チェック
		public int seriousViolationsOrder_3;

		// 解雇の理由3_文字列
		public String seriousViolationsOrder_3Val;

		// 解雇の理由4_チェック
		public int unauthorizedConduct_4;

		// 解雇の理由4_文字列
		public String unauthorizedConduct_4Val;

		// 解雇の理由5_チェック
		public int leaveConsiderableTime_5;

		// 解雇の理由5_文字列
		public String leaveConsiderableTime_5Val;

		// 解雇の理由6_チェック
		public int other_6;

		// 解雇の理由6_文字列
		public String other_6Val;
}
