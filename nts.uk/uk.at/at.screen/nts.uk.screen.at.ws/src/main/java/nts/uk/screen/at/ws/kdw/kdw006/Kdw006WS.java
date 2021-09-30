package nts.uk.screen.at.ws.kdw.kdw006;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWork;
import nts.uk.screen.at.app.kdw006.k.AcquireSelectionHistoryOfWorkDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListDto;
import nts.uk.screen.at.app.kdw006.k.GetManHourRecordItemSpecifiedIDListParam;
import nts.uk.screen.at.app.kdw006.k.WorkInfomations;

/**
 * 
 * @author chungnt
 *
 */

@Path("at/record/kdw006/")
@Produces("application/json")
public class Kdw006WS extends WebService {

	@Inject
	private WorkInfomations workInfomations;
	
	@Inject
	private AcquireSelectionHistoryOfWork acquireSelectionHistoryOfWork;

	// 作業補足情報の選択項目を取得する
	@POST
	@Path("view-k/get-list-work")
	public List<GetManHourRecordItemSpecifiedIDListDto> getListWork(GetManHourRecordItemSpecifiedIDListParam param) {
		return this.workInfomations.get(param);
	}

	// 作業補足情報の選択肢履歴を取得する
	@POST
	@Path("view-k/get-list-history")
	public List<AcquireSelectionHistoryOfWorkDto> getListHistory() {
		return this.acquireSelectionHistoryOfWork.get();
	}

}
