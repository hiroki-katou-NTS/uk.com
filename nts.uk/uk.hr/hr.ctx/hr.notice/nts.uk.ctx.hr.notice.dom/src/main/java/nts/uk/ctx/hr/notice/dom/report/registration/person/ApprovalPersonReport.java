/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalActivity;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.EmailTransmissionClass;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.SendBackClass;
import nts.uk.ctx.hr.shared.dom.primitiveValue.String_Any_400;

/**
 * @author laitv
 * Domain : 人事届出の承認
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPersonReport extends AggregateRoot{
 
	private String cid; //会社ID
	private String rootSatteId; //ルートインスタンスID
	private int reportID; //届出ID
	private String reportName; //届出名
	private GeneralDateTime refDate; //基準日
	private GeneralDateTime inputDate; //入力日
	private GeneralDateTime appDate; //申請日
	private GeneralDateTime aprDate; //承認日
	private String aprSid; //承認者社員ID sid của người Approver 
	private String aprBussinessName; //承認者社員名
	private String emailAddress; //メールアドレス
	private int phaseNum; // フェーズ通番
	private ApprovalStatus aprStatus; //承認状況
	private int aprNum;//承認者通番
	private boolean arpAgency;//代行承認
	private String_Any_400 comment; //コメント
	private ApprovalActivity aprActivity;//承認活性
	private EmailTransmissionClass emailTransmissionClass;//メール送信区分
	private String appSid; //申請者社員ID sid của người nộp đơn
	private String inputSid; //入力者社員ID sid cua người login
	private int  reportLayoutID;//個別届出種類ID
	private Optional<String> sendBackSID; //差し戻し先社員ID sid của người bị return
	private Optional<SendBackClass> sendBackClass; //差し戻し区分
	
	public ApprovalPersonReport(String cid, String rootSatteId, int reportID, String reportName,
			GeneralDateTime refDate, GeneralDateTime inputDate, GeneralDateTime appDate, GeneralDateTime aprDate,
			String aprSid, String aprBussinessName, String emailAddress, int phaseNum, int aprStatus,
			int aprNum, boolean arpAgency, String comment, int aprActivity,
			int emailTransmissionClass, String appSid, String inputSid, int reportLayoutID,
			String sendBackSID, Integer sendBackClass) {
		super();
		this.cid = cid;
		this.rootSatteId = rootSatteId;
		this.reportID = reportID;
		this.reportName = reportName;
		this.refDate = refDate;
		this.inputDate = inputDate;
		this.appDate = appDate;
		this.aprDate = aprDate;
		this.aprSid = aprSid;
		this.aprBussinessName = aprBussinessName;
		this.emailAddress = emailAddress;
		this.phaseNum = phaseNum;
		this.aprStatus = EnumAdaptor.valueOf(aprStatus, ApprovalStatus.class);
		this.aprNum = aprNum;
		this.arpAgency = arpAgency;
		this.comment = new String_Any_400(comment);
		this.aprActivity = EnumAdaptor.valueOf(aprActivity, ApprovalActivity.class);
		this.emailTransmissionClass = EnumAdaptor.valueOf(emailTransmissionClass, EmailTransmissionClass.class);
		this.appSid = appSid;
		this.inputSid = inputSid;
		this.reportLayoutID = reportLayoutID;
		this.sendBackSID = StringUtil.isNullOrEmpty(sendBackSID, true) ? Optional.empty() : Optional.of(sendBackSID);
		this.sendBackClass = sendBackClass == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(sendBackClass, SendBackClass.class));
	}

	public static ApprovalPersonReport createFromJavaType(String cid, String rootSatteId, int reportID, String reportName,
			GeneralDateTime refDate, GeneralDateTime inputDate, GeneralDateTime appDate, GeneralDateTime aprDate,
			String aprSid, String aprBussinessName, String emailAddress, int phaseNum, int aprStatusName,
			int aprNum, boolean arpAgency, String comment, int aprActivity,
			int emailTransmissionClass, String appSid, String inputSid, int reportLayoutID,
			String sendBackSID, Integer sendBackClass){
		return new ApprovalPersonReport(cid, rootSatteId, reportID, reportName, refDate, inputDate, appDate, aprDate,
				aprSid, aprBussinessName, emailAddress, phaseNum, aprStatusName, aprNum, arpAgency, comment,
				aprActivity, emailTransmissionClass, appSid, inputSid, reportLayoutID, sendBackSID, sendBackClass);
		
	}
}
