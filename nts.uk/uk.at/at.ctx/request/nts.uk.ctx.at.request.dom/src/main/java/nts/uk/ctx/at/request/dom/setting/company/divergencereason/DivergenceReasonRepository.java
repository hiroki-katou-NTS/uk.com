package nts.uk.ctx.at.request.dom.setting.company.divergencereason;

import java.util.List;

public interface DivergenceReasonRepository {
	List<DivergenceReason>  getDivergenceReason(String companyID, int appType);
}
