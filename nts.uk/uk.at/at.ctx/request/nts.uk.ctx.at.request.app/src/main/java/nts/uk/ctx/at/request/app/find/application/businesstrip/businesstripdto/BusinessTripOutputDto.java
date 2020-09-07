package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfoOutput;

@NoArgsConstructor
@Data
public class BusinessTripOutputDto {
	// 出張申請の表示情報
	BusinessTripInfoOutputDto businessTripInfoOutput;
	//出張申請＜Optional＞
	BusinessTripDto businessTrip;
}
