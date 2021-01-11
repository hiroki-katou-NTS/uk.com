package nts.uk.ctx.at.request.ws.application.holidayshipment;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.HolidayShipmentScreenAFinder;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/holidayshipment/mobile")
@Produces("application/json")
public class HolidayShipmentMobileWS extends WebService {
	
	@Inject
	private HolidayShipmentScreenAFinder screenAFinder;
	
	@POST
	@Path("start")
	public DisplayInforWhenStarting startPageARefactor(HdShipmentMobileStartParam param) {
		// INPUT．「画面モード」を確認する
		if(!param.isNewMode()) {
			// INPUT「振休振出申請起動時の表示情報」を返す
			return param.getDisplayInforWhenStarting();
		}
		// 起動時の申請表示情報を取得する
		// trên UI
		// 1.振休振出申請（新規）起動処理
		String companyID = AppContexts.user().companyId();
		return screenAFinder.startPageARefactor(
				companyID, 
				Arrays.asList(param.getEmployeeID()), 
				param.getDateLst().stream().map(x -> GeneralDate.fromString(x, "YYYY/MM/DD")).collect(Collectors.toList()),
				param.getAppDispInfoStartupCmd()
			);
	}
}
