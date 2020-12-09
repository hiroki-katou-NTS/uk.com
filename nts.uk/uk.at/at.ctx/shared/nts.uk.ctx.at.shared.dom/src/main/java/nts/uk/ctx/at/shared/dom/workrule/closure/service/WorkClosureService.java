package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class WorkClosureQueryProcessor.
 */
@Stateless
public class WorkClosureService {
	
	/** The closure repo. */
	@Inject
	private ClosureRepository closureRepo;
	
	/** The closure emp repo. */
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;

	/**
	 * Find closure by reference date.
	 *
	 * @param refDate the ref date
	 * @return the list
	 */
	// request 140: 会社の締めを取得する
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<ClosureResultDto> findClosureByReferenceDate(GeneralDate refDate) {
		// find all active closure
		List<Closure> closures = this.closureRepo.findAllActive(AppContexts.user().companyId(),
				UseClassification.UseClass_Use);

		// get result list
		List<ClosureResultDto> resultList = new ArrayList<>();
		closures.forEach(clo -> {
			ClosureHistory history = clo.getHistoryByBaseDate(refDate);
			ClosureResultDto res = ClosureResultDto.builder()
					.closureId(history.getClosureId().value)
					.closureName(history.getClosureName().v())
					.build();
			resultList.add(res);
		});

		return resultList;
	}

}
