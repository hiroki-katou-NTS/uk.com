package nts.uk.ctx.hr.notice.app.command.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveReportSendBackCommand {
	String cid; // 会社ID
	int reportID; // 届出ID
	int phaseNum; // フェーズ通番
	int aprNum;// 承認者通番
	String returnTo; // combobox 1
	int sendBackCls;// phân loại trả về. combobox 2
	String comment; // コメント
}
