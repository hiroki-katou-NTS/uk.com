package nts.uk.screen.at.app.ktgwidget.find.dto;

import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.AgreementTimeDetail;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.CurrentClosingPeriod;
import nts.uk.ctx.bs.employee.app.find.employment.EmployeeBasicInfoExport;

@Data
@Builder
public class OvertimedDisplayForSuperiorsDto {
	
	//	ログイン者の締めID
	private Integer closureId;

	//	当月の締め情報
	private CurrentClosingPeriod closingInformationForCurrentMonth;
	
	//	配下社員の個人情報
	private List<EmployeeBasicInfoExport> personalInformationOfSubordinateEmployees;
	
	//	配下社員の時間外時間
	private List<AgreementTimeDetail> OvertimeOfSubordinateEmployees;
	
	//	翌月の締め情報
	private Optional<CurrentClosingPeriod> ClosingInformationForNextMonth;
	
	private Integer getClosureId() {
		return closureId;
	}
	
	private void Integer(Integer closureId) {
		this.closureId = closureId;
	}
	
}
