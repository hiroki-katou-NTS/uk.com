package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNDT_RPT_APR")
public class JhndtReportApproval extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtReportApprovalPK pk;
	
	@Column(name = "ROOT_STATE_ID")
	public String rootSatteId; //ルートインスタンスID
	
	@Column(name = "RPT_NAME")
	public String reportName; //届出名
	
	@Column(name = "REF_DATE")
	public GeneralDateTime refDate; //基準日
	
	@Column(name = "INPUT_DATE")
	public GeneralDateTime inputDate; //入力日
	
	@Column(name = "APP_DATE")
	public GeneralDateTime appDate; //申請日
	
	@Column(name = "APR_DATE")
	public GeneralDateTime aprDate; //承認日
	
	@Column(name = "APR_BUSINESS_NAME")
	public String aprBussinessName; //承認者社員名
	
	@Column(name = "EMAIL_ADDRESS")
	public String emailAddress; //メールアドレス
	
	@Column(name = "APR_STATUS")
	public int aprStatus; //承認状況.
	
	@Column(name = "APR_AGENCY")
	public boolean arpAgency;//代行承認
	
	@Column(name = "COMMENT")
	public String comment; //コメント
	
	@Column(name = "APR_ACTIVITY")
	public int aprActivity;//承認活性
	
	@Column(name = "EMAIL_TRNS_CLASS")
	public int emailTransmissionClass;//メール送信区分
	
	@Column(name = "APP_SID")
	public String appSid; //申請者社員ID
	
	@Column(name = "INPUT_SID")
	public String inputSid; //入力者社員ID
	
	@Column(name = "RPT_LAYOUT_ID")
	public int  reportLayoutID;//個別届出種類ID
	
	@Column(name = "SEND_BACK_SID")
	public String sendBackSID; //差し戻し先社員ID
	
	@Column(name = "SEND_BACK_CLASS")
	public Integer sendBackClass; //差し戻し区分
	
	@Override
	public Object getKey() {
		return pk;
	}

	public ApprovalPersonReport toDomain() {
		return ApprovalPersonReport.createFromJavaType(
				this.pk.cid,
				this.rootSatteId ,
				this.pk.reportID ,
				this.reportName ,
				this.refDate ,
				this.inputDate ,
				this.appDate ,
				this.aprDate ,
				this.pk.aprSid ,
				this.aprBussinessName ,
				this.emailAddress ,
				this.pk.levelNum ,
				this.aprStatus ,
				this.pk.aprNum ,
				this.arpAgency ,
				this.comment ,
				this.aprActivity ,
				this.emailTransmissionClass,
				this.appSid ,
				this.inputSid ,
				this.reportLayoutID ,
				this.sendBackSID ,
				this.sendBackClass);
	}
}
