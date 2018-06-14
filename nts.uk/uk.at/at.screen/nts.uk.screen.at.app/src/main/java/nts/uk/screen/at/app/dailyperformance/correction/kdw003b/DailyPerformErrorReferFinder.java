package nts.uk.screen.at.app.dailyperformance.correction.kdw003b;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumConstant;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DailyPerformErrorReferFinder {

	@Inject
	private DailyPerformanceScreenRepo repo;

	public Map<String, List<EnumConstant>> findByCidAndListErrCd(List<String> listErrorCode) {
		String companyId = AppContexts.user().companyId();
		return this.repo.findErAlApplicationByCidAndListErrCd(companyId, listErrorCode);
	}
}
