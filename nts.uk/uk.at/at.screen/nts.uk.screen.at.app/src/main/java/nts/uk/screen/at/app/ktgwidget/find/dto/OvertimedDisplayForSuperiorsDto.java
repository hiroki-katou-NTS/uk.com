package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.CurrentClosingPeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;

@Data
@Builder

//UKDesign.UniversalK.就業.KTG_ウィジェット.KTG027_時間外労働時間の表示(上長用).アルゴリズム.起動する.上長用の時間外時間表示
public class OvertimedDisplayForSuperiorsDto {
	
	//	ログイン者の締めID
	private Integer closureId;

	//	当月の締め情報
	private CurrentClosingPeriod closingInformationForCurrentMonth;
	
	//	配下社員の個人情報
	private List<PersonEmpBasicInfoImport> personalInformationOfSubordinateEmployees;
	
	//	配下社員の時間外時間
	private List<AgreementTimeDetail> overtimeOfSubordinateEmployees;
	
	//	翌月の締め情報
	private CurrentClosingPeriod closingInformationForNextMonth;
	
}
