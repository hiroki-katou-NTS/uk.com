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

	public DailyPerformErrorReferDto findByCidAndListErrCd(List<String> listErrorCode) {
		String companyId = AppContexts.user().companyId();
		String employeeIdLogin = AppContexts.user().employeeId();
		DailyPerformErrorReferDto dto = new DailyPerformErrorReferDto();
		Map<String, List<EnumConstant>> map = this.repo.findErAlApplicationByCidAndListErrCd(companyId, listErrorCode);
		dto.setEmployeeIdLogin(employeeIdLogin);
		dto.setMapErrCdAppTypeCd(map);
		return dto;
	}
}
