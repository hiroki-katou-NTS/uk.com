/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.ReportType;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.ApprovalStatusForRegis;
import nts.uk.ctx.hr.notice.dom.report.registration.person.enu.RegistrationStatus;

/**
 * @author laitv
 * AggregateRoot: 人事届出の登録
 * đăng ký thông tin report cua nhan vien
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationPersonReport extends AggregateRoot{
	
	String cid; // 会社ID
	String rootInstanceID; // ルートインスタンスID
	int workId; // 業務ID
	int reportID; // 届出ID
	int  reportLayoutID; // 個別届出種類ID
	String reportCode; // 届出コード
	String reportName; // 届出名
	String reportDetail; // 届出内容
	RegistrationStatus regStatus; // 登録状態
	ApprovalStatusForRegis aprStatus;// 承認状況
	GeneralDateTime draftSaveDate;//下書き保存日
	String missingDocName;//不足書類名
	String inputPid;//入力者個人ID
	String inputSid;//入力者社員ID
	String inputScd; //入力者社員CD
	String inputBussinessName; //入力者表示氏名
	GeneralDateTime inputDate;//入力日
	String appPid;//申請者個人ID
	String appSid;//申請者社員ID
	String appScd;//申請者社員CD
	String appBussinessName;//申請者表示氏名
	GeneralDateTime appDate;//申請日
	String appDevId ;//申請者部門ID
	String appDevCd ;//申請者部門CD
	String appDevName ;//申請者部門名
	String appPosId ;//申請者職位ID
	String appPosCd ;//申請者職位CD
	String appPosName ;//申請者職位名
	ReportType reportType;//届出種類
	String sendBackSID;//差し戻し先社員ID
	String sendBackComment;//差し戻しコメント
	boolean delFlg;//削除済
	
	public RegistrationPersonReport(String cid, String rootInstanceID, int workId, int reportID, int reportLayoutID,
			String reportCode, String reportName, String reportDetail, int regStatus,
			int aprStatus, GeneralDateTime draftSaveDate, String missingDocName, String inputPid,
			String inputSid, String inputScd, String inputBussinessName, GeneralDateTime inputDate, String appPid,
			String appSid, String appScd, String appBussinessName, GeneralDateTime appDate, String appDevId,
			String appDevCd, String appDevName, String appPosId, String appPosCd, String appPosName,
			int reportType, String sendBackSID, String sendBackComment, boolean delFlg) {
		super();
		this.cid = cid;
		this.rootInstanceID = rootInstanceID;
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

	public static RegistrationPersonReport createFromJavaType(String cid, String rootInstanceID, int workId, int reportID, int reportLayoutID,
			String reportCode, String reportName, String reportDetail, int regStatus,
			int aprStatus, GeneralDateTime draftSaveDate, String missingDocName, String inputPid,
			String inputSid, String inputScd, String inputBussinessName, GeneralDateTime inputDate, String appPid,
			String appSid, String appScd, String appBussinessName, GeneralDateTime appDate, String appDevId,
			String appDevCd, String appDevName, String appPosId, String appPosCd, String appPosName,
			int reportType, String sendBackSID, String sendBackComment, boolean delFlg){
		return new RegistrationPersonReport(cid, rootInstanceID, workId, reportID, reportLayoutID, reportCode,
				reportName, reportDetail, regStatus, aprStatus, draftSaveDate, missingDocName, inputPid, inputSid,
				inputScd, inputBussinessName, inputDate, appPid, appSid, appScd, appBussinessName, appDate, appDevId,
				appDevCd, appDevName, appPosId, appPosCd, appPosName, reportType, sendBackSID, sendBackComment, delFlg);

	}
}
