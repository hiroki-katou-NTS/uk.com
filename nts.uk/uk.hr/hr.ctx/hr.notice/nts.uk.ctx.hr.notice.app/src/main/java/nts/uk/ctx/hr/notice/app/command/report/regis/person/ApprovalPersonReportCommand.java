/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.SendBackClass;

/**
 * @author laitv
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPersonReportCommand  {
 
	 String cid; //会社ID
	 String rootSatteId; //ルートインスタンスID
	 int reportID; //届出ID
	 String reportName; //届出名
	 GeneralDate refDate; //基準日
	 GeneralDate inputDate; //入力日
	 GeneralDate appDate; //申請日
	 GeneralDate aprDate; //承認日
	 String aprSid; //承認者社員ID
	 String aprBussinessName; //承認者社員名
	 String emailAddress; //メールアドレス
	 int phaseNum; // フェーズ通番
	 int aprStatusName; //承認状況
	 int aprNum;//承認者通番
	 boolean arpAgency;//代行承認
	 String comment; //コメント
	 int aprActivity;//承認活性
	 int emailTransmissionClass;//メール送信区分
	 String appSid; //申請者社員ID
	 String inputSid; //入力者社員ID
	 int  reportLayoutID;//個別届出種類ID
	 String sendBackSID; //差し戻し先社員ID
	 SendBackClass sendBackClass; //差し戻し区分
	
}
