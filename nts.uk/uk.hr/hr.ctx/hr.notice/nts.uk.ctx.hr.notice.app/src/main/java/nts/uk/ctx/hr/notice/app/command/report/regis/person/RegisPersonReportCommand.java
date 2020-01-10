/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisPersonReportCommand {
	 String cid; // 会社ID
	 String rootSateId; // ルートインスタンスID
	 int workId; // 業務ID
	 int reportID; // 届出ID
	 int  reportLayoutID; // 個別届出種類ID
	 String reportCode; // 届出コード
	 String reportName; // 届出名
	 String reportDetail; // 届出内容
	 int regStatus; // 登録状態
	 int aprStatus;// 承認状況
	 GeneralDate draftSaveDate;//下書き保存日
	 String missingDocName;//不足書類名
	 String inputPid;//入力者個人ID
	 String inputSid;//入力者社員ID
	 String inputScd; //入力者社員CD
	 String inputBussinessName; //入力者表示氏名
	 GeneralDate inputDate;//入力日
	 String appPid;//申請者個人ID
	 String appSid;//申請者社員ID
	 String appScd;//申請者社員CD
	 String appBussinessName;//申請者表示氏名
	 GeneralDate appDate;//申請日
	 String appDevId ;//申請者部門ID
	 String appDevCd ;//申請者部門CD
	 String appDevName ;//申請者部門名
	 String appPosId ;//申請者職位ID
	 String appPosCd ;//申請者職位CD
	 String appPosName ;//申請者職位名
	 int reportType;//届出種類
	 String sendBackSID;//差し戻し先社員ID
	 String sendBackComment;//差し戻しコメント
	 boolean delFlg;//削除済
	 
	 
}
