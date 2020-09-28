package nts.uk.ctx.at.request.app.find.application.businesstrip;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeParam;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
//import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoDto;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppBusinessParam {

	// 画面モード
	private Boolean mode;
	// 会社ID
	private String companyId;
	// 申請者ID
	private String employeeID;
//	申請対象日リスト
	private List<String> listDates;
	// 出張申請の表示情報
	private BusinessTripInfoOutputDto businessTripInfoOutput;
	// 出張申請
	private BusinessTripDto businessTrip;
}
