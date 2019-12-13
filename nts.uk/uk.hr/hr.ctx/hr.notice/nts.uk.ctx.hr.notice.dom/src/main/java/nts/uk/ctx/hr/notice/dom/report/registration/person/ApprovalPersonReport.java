/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPersonReport extends AggregateRoot{
 
	String cid; //会社ID
	String rootInstanceID; //ルートインスタンスID
	int reportID; //届出ID
	String reportName; //届出名
	GeneralDateTime refDate; //基準日
	GeneralDateTime inputDate; //入力日
	GeneralDateTime appDate; //申請日
	GeneralDateTime aprDate; //承認日
	String aprSid; //承認者社員ID
	String aprBussinessName; //承認者社員名
	String emailAddress; //メールアドレス
	int phaseNum; // フェーズ通番
	ApprovalStatus aprStatusName; //承認状況
	int aprNum;//承認者通番
	boolean arpAgency;//代行承認
	String_Any_400 comment; //コメント
	ApprovalActivity aprActivity;//承認活性
	EmailTransmissionClass emailTransmissionClass;//メール送信区分
	String appSid; //申請者社員ID
	String inputSid; //入力者社員ID
	int  reportLayoutID;//個別届出種類ID
	Optional<String> sendBackSID; //差し戻し先社員ID
	Optional<SendBackClass> sendBackClass; //差し戻し区分
	
	public ApprovalPersonReport(String cid, String rootInstanceID, int reportID, String reportName,
			GeneralDateTime refDate, GeneralDateTime inputDate, GeneralDateTime appDate, GeneralDateTime aprDate,
			String aprSid, String aprBussinessName, String emailAddress, int phaseNum, int aprStatusName,
			int aprNum, boolean arpAgency, String comment, int aprActivity,
			int emailTransmissionClass, String appSid, String inputSid, int reportLayoutID,
			String sendBackSID, Integer sendBackClass) {
		super();
		this.cid = cid;
		this.rootInstanceID = rootInstanceID;
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
		this.aprStatusName = EnumAdaptor.valueOf(aprStatusName, ApprovalStatus.class);
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

	public static ApprovalPersonReport createFromJavaType(String cid, String rootInstanceID, int reportID, String reportName,
			GeneralDateTime refDate, GeneralDateTime inputDate, GeneralDateTime appDate, GeneralDateTime aprDate,
			String aprSid, String aprBussinessName, String emailAddress, int phaseNum, int aprStatusName,
			int aprNum, boolean arpAgency, String comment, int aprActivity,
			int emailTransmissionClass, String appSid, String inputSid, int reportLayoutID,
			String sendBackSID, Integer sendBackClass){
		return new ApprovalPersonReport(cid, rootInstanceID, reportID, reportName, refDate, inputDate, appDate, aprDate,
				aprSid, aprBussinessName, emailAddress, phaseNum, aprStatusName, aprNum, arpAgency, comment,
				aprActivity, emailTransmissionClass, appSid, inputSid, reportLayoutID, sendBackSID, sendBackClass);
		
	}

}
