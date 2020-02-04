/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatusForRegis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.RegistrationStatus;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ReportType;

/**
 * @author laitv
 * AggregateRoot: 人事届出の登録
 * đăng ký thông tin report cua nhan vien
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPersonReport extends AggregateRoot{
	
	private String cid; // 会社ID
	private String rootSateId; // ルートインスタンスID
	private int workId; // 業務ID
	private int reportID; // 届出ID
	private int  reportLayoutID; // 個別届出種類ID
	private String reportCode; // 届出コード
	private String reportName; // 届出名
	private String reportDetail; // 届出内容
	private RegistrationStatus regStatus; // 登録状態
	private ApprovalStatusForRegis aprStatus;// 承認状況
	private GeneralDateTime draftSaveDate;//下書き保存日
	private String missingDocName;//不足書類名
	private String inputPid;//入力者個人ID
	private String inputSid;//入力者社員ID
	private String inputScd; //入力者社員CD
	private String inputBussinessName; //入力者表示氏名
	private GeneralDateTime inputDate;//入力日
	private String appPid;//申請者個人ID
	private String appSid;//申請者社員ID
	private String appScd;//申請者社員CD
	private String appBussinessName;//申請者表示氏名
	private GeneralDateTime appDate;//申請日
	private String appDevId ;//申請者部門ID
	private String appDevCd ;//申請者部門CD
	private String appDevName ;//申請者部門名
	private String appPosId ;//申請者職位ID
	private String appPosCd ;//申請者職位CD
	private String appPosName ;//申請者職位名
	private ReportType reportType;//届出種類
	private String  sendBackSID;//差し戻し先社員ID
	private String  sendBackComment;//差し戻しコメント
	private boolean delFlg;//削除済
	
	public RegistrationPersonReport(String cid, String rootSateId, int workId, int reportID, int reportLayoutID,
			String reportCode, String reportName, String reportDetail, int regStatus,
			int aprStatus, GeneralDateTime draftSaveDate, String missingDocName, String inputPid,
			String inputSid, String inputScd, String inputBussinessName, GeneralDateTime inputDate, String appPid,
			String appSid, String appScd, String appBussinessName, GeneralDateTime appDate, String appDevId,
			String appDevCd, String appDevName, String appPosId, String appPosCd, String appPosName,
			int reportType, String sendBackSID, String sendBackComment, boolean delFlg) {
		super();
		this.cid = cid;
		this.rootSateId = rootSateId;
		this.workId = workId;
		this.reportID = reportID;
		this.reportLayoutID = reportLayoutID;
		this.reportCode = reportCode;
		this.reportName = reportName;
		this.reportDetail = reportDetail;
		this.regStatus = EnumAdaptor.valueOf(regStatus, RegistrationStatus.class);
		this.aprStatus = EnumAdaptor.valueOf(aprStatus, ApprovalStatusForRegis.class);
		this.draftSaveDate = draftSaveDate;
		this.missingDocName = missingDocName;
		this.inputPid = inputPid;
		this.inputSid = inputSid;
		this.inputScd = inputScd;
		this.inputBussinessName = inputBussinessName;
		this.inputDate = inputDate;
		this.appPid = appPid;
		this.appSid = appSid;
		this.appScd = appScd;
		this.appBussinessName = appBussinessName;
		this.appDate = appDate;
		this.appDevId = appDevId;
		this.appDevCd = appDevCd;
		this.appDevName = appDevName;
		this.appPosId = appPosId;
		this.appPosCd = appPosCd;
		this.appPosName = appPosName;
		this.reportType = EnumAdaptor.valueOf(reportType, ReportType.class);
		this.sendBackSID = sendBackSID;
		this.sendBackComment = sendBackComment;
		this.delFlg = delFlg;
	}

	public static RegistrationPersonReport createFromJavaType(String cid, String rootSateId, int workId, int reportID, int reportLayoutID,
			String reportCode, String reportName, String reportDetail, int regStatus,
			int aprStatus, GeneralDateTime draftSaveDate, String missingDocName, String inputPid,
			String inputSid, String inputScd, String inputBussinessName, GeneralDateTime inputDate, String appPid,
			String appSid, String appScd, String appBussinessName, GeneralDateTime appDate, String appDevId,
			String appDevCd, String appDevName, String appPosId, String appPosCd, String appPosName,
			int reportType, String sendBackSID, String sendBackComment, boolean delFlg){
		return new RegistrationPersonReport(cid, rootSateId, workId, reportID, reportLayoutID, reportCode,
				reportName, reportDetail, regStatus, aprStatus, draftSaveDate, missingDocName, inputPid, inputSid,
				inputScd, inputBussinessName, inputDate, appPid, appSid, appScd, appBussinessName, appDate, appDevId,
				appDevCd, appDevName, appPosId, appPosCd, appPosName, reportType, sendBackSID, sendBackComment, delFlg);

	}
}
