package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SaveReportInputContainer {
	
	public String personId;
	public String employeeId;
	public List<ItemsByCategory> inputs;
	public List<ItemDfCommand> listItemDf;
	public String cid; // 会社ID
	public String rootSateId; // ルートインスタンスID
	public Integer workId; // 業務ID
	public Integer reportID; // 届出ID
	public int  reportLayoutID; // 個別届出種類ID
	public String reportCode; // 届出コード
	public String reportName; // 届出名
	public String reportDetail; // 届出内容
	public int regStatus; // 登録状態
	public int aprStatus;// 承認状況
	public GeneralDate draftSaveDate;//下書き保存日
	public String missingDocName;//不足書類名
	
	public String inputPid;//入力者個人ID
	public String inputSid;//入力者社員ID
	public String inputScd; //入力者社員CD
	public String inputBussinessName; //入力者表示氏名
	public GeneralDate inputDate;//入力日
	
	public String appPid;//申請者個人ID
	public String appSid;//申請者社員ID
	public String appScd;//申請者社員CD
	public String appBussinessName;//申請者表示氏名
	public GeneralDate appDate;//申請日
	
	public String appDevId ;//申請者部門ID
	public String appDevCd ;//申請者部門CD
	public String appDevName ;//申請者部門名
	
	public String appPosId ;//申請者職位ID
	public String appPosCd ;//申請者職位CD
	public String appPosName ;//申請者職位名
	
	public int reportType;//届出種類
	public String sendBackSID;//差し戻し先社員ID
	public String sendBackComment;//差し戻しコメント
	public boolean delFlg;//削除済
	public int isSaveDraft;
	
}
