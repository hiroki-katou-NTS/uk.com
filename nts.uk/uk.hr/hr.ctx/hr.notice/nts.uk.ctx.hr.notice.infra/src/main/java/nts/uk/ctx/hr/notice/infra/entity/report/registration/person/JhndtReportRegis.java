package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNDT_RPT_REG")
public class JhndtReportRegis extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtReportRegisPK pk;
	
	@Column(name = "ROOT_STATE_ID")
	public String rootSateId; // ルートインスタンスID
	
	@Column(name = "WORK_ID")
	public int workId; // 業務ID
	
	@Column(name = "RPT_LAYOUT_ID")
	public int reportLayoutID; // 個別届出種類ID

	@Column(name = "RPTCD")
	public String reportCode; // 届出コード

	@Column(name = "RPT_NAME")
	public String reportName; // 届出名

	@Column(name = "RPT_DETAIL")
	public String reportDetail; // 届出内容

	@Column(name = "REG_STATUS")
	public int regStatus; // 登録状態

	@Column(name = "APR_STATUS")
	public int aprStatus;// 承認状況

	@Setter
	@Column(name = "DRAFT_SAVE_DATE")
	public GeneralDateTime draftSaveDate;//下書き保存日

	@Column(name = "MISSING_DOC_NAME")
	public String missingDocName;//不足書類名

	@Column(name = "INPUT_PID")
	public String inputPid;//入力者個人ID

	@Column(name = "INPUT_SID")
	public String inputSid;//入力者社員ID

	@Column(name = "INPUT_SCD")
	public String inputScd; //入力者社員CD

	@Column(name = "INPUT_BUSINESS_NAME")
	public String inputBussinessName; //入力者表示氏名

	@Column(name = "INPUT_DATE")
	public GeneralDateTime inputDate;//入力日

	@Column(name = "APP_PID")
	public String appPid;//申請者個人ID

	@Column(name = "APP_SID")
	public String appSid;//申請者社員ID

	@Column(name = "APP_SCD")
	public String appScd;//申請者社員CD

	@Column(name = "APP_BUSINESS_NAME")
	public String appBussinessName;//申請者表示氏名

	@Column(name = "APP_DATE")
	public GeneralDateTime appDate;//申請日

	@Column(name = "APP_DEVID")
	public String appDevId ;//申請者部門ID

	@Column(name = "APP_DEVCD")
	public String appDevCd ;//申請者部門CD

	@Column(name = "APP_DEV_NAME")
	public String appDevName ;//申請者部門名

	@Column(name = "APP_POSID")
	public String appPosId ;//申請者職位ID

	@Column(name = "APP_POSCD")
	public String appPosCd ;//申請者職位CD

	@Column(name = "APP_POS_NAME")
	public String appPosName ;//申請者職位名

	@Column(name = "RPT_TYPE")
	public int reportType;//届出種類

	@Column(name = "SEND_BACK_SID")
	public String sendBackSID;//差し戻し先社員ID

	@Column(name = "SEND_BACK_COMMENT")
	public String sendBackComment;//差し戻しコメント

	@Setter
	@Column(name = "DEL_FLG")
	public int delFlg;//削除済

	@Override
	public Object getKey() {
		return pk;
	}

	public RegistrationPersonReport toDomain() {
		return RegistrationPersonReport.createFromJavaType(
				this.pk.cid,
				this.rootSateId,
				this.workId,
				this.pk.reportId,
				this.reportLayoutID,
				this.reportCode,
				this.reportName ,
				this.reportDetail ,
				this.regStatus ,
				this.aprStatus ,
				this.draftSaveDate ,
				this.missingDocName ,
				this.inputPid ,
				this.inputSid ,
				this.inputScd ,
				this.inputBussinessName ,
				this.inputDate ,
				this.appPid ,
				this.appSid ,
				this.appScd ,
				this.appBussinessName ,
				this.appDate ,
				this.appDevId ,
				this.appDevCd ,
				this.appDevName ,
				this.appPosId ,
				this.appPosCd ,
				this.appPosName, 
				this.reportType ,
				this.sendBackSID, 
				this.sendBackComment, 
				this.delFlg == 1 ? true : false);
	}
	
	public static JhndtReportRegis toEntity(RegistrationPersonReport domain){
		return new JhndtReportRegis(new JhndtReportRegisPK(domain.getCid(), domain.getReportID()), domain.getRootSateId(), domain.getWorkId(), 
				domain.getReportLayoutID(), domain.getReportCode(), domain.getReportName(),
				domain.getReportDetail(), domain.getRegStatus().value, domain.getAprStatus().value, 
				domain.getDraftSaveDate(), domain.getMissingDocName(),
				domain.getInputPid(), domain.getInputSid(),domain.getInputScd(), domain.getInputBussinessName(), 
				domain.getInputDate(), domain.getAppPid(), domain.getAppSid(), domain.getAppScd(), domain.getAppBussinessName(), 
				domain.getAppDate(), domain.getAppDevId(), domain.getAppDevCd(), domain.getAppDevName(),
				domain.getAppPosId(), domain.getAppPosCd(), domain.getAppPosName(), 
				domain.getReportType().value, domain.getSendBackSID(), domain.getSendBackComment(), domain.isDelFlg() == true ? 1 : 0);
		
		
	}
}
