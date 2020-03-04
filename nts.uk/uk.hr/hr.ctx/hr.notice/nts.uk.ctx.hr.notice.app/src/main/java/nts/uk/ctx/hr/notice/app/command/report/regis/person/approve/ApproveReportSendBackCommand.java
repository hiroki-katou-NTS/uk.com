package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveReportSendBackCommand {
	Integer reportID; // 届出ID
	Integer reportLayoutId; 
	Integer phaseNum; // フェーズ通番
	Integer aprNum;// 承認者通番
	String comment; // コメント
	String inputSid;
	String appSid;
	String aprSid;
	String sendBackSID;
	Integer sendBackClass;// phân loại trả về. combobox 2
	Integer selectedReturn; // == 1 là trả về cho người làm đơn. #1 là trả về cho người appro
}
