package nts.uk.ctx.hr.notice.app.find.report.regis.person.approve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalPersonReportDto {

	int id;
	String cid; // 会社ID
	int reportID; // 届出ID
	String aprSid; // 承認者社員ID
	String aprBussinessName; // 承認者社員名
	int phaseNum; // フェーズ通番
	int aprStatus; // 承認状況
	int aprNum;// 承認者通番
	boolean arpAgency;// 代行承認
	String comment; // コメント
	int aprActivity;// 承認活性
	String appSid; // 申請者社員ID
	String inputSid; // 入力者社員ID
	String sendBackSID; // 差し戻し先社員ID
	int sendBackClass; // 差し戻し区分
	String infoToDisplay; // hiển thị ở dropdown list
}
