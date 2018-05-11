package nts.uk.ctx.at.record.ws.annualholiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Value;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.annualholiday.AnnualHolidayDto;
import nts.uk.ctx.at.record.app.find.annualholiday.AnnualHolidayFinder;

@Path("at/record/annualholiday")
@Produces("application/json")
public class AnnualHolidayWebService extends WebService {
	@Inject
	private AnnualHolidayFinder annualFinder;

	@POST
	@Path("startPage")
	public AnnualHolidayDto startPage(StartParam param) {
		return this.annualFinder.startPage(param.getSelectionMode(), param.getRefDate(), param.getSelectedIDs());
	}

}

@Value
class StartParam {
	// 選択モード
	private int selectionMode;
	// 基準日
	private GeneralDate refDate;
	// 選択済項目
	private List<String> selectedIDs;
}
