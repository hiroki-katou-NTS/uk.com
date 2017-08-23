/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto.CompanyEstimateTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateTimeDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
import nts.uk.ctx.at.schedule.dom.shift.estimate.time.EstimateTimeSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class CompanyEstablishmentFinder.
 */
@Stateless
public class CompanyEstablishmentFinder {
	
	/** The repository. */
	@Inject
	private CompanyEstablishmentRepository repository;
	
	/** The Constant FIRST_MONTH. */
	public static final int FIRST_MONTH = 1;
	
	/** The Constant TOTAL_MONTH. */
	public static final int TOTAL_MONTH = 12;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public CompanyEstimateTimeDto findEstimateTime(int targetYear) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		Optional<CompanyEstablishment> companyEstablishment = this.repository.findById(companyId,
				targetYear);
		CompanyEstimateTimeDto dto = new CompanyEstimateTimeDto();
		// check exist data
		if (companyEstablishment.isPresent()) {

			// get estimate time setting
			List<EstimateTimeSetting> estimateTimeSetting = companyEstablishment.get()
					.getAdvancedSetting().getEstimateTime();

			// convert to map
			Map<EstimateTargetClassification, EstimateTimeSetting> mapEstimateTimeSetting = estimateTimeSetting
					.stream().collect(Collectors.toMap((monthly) -> {
						return monthly.getTargetClassification();
					}, Function.identity()));
			EstimateTimeDto yearly = new EstimateTimeDto();

			// check yearly exist data
			if (mapEstimateTimeSetting.containsKey(EstimateTargetClassification.YEARLY)) {
				mapEstimateTimeSetting.get(EstimateTargetClassification.YEARLY)
						.saveToMemento(yearly);
			}
			dto.setYearlyEstimate(yearly);

			List<EstimateTimeDto> monthlys = new ArrayList<>();
			for (int index = FIRST_MONTH; index <= TOTAL_MONTH; index++) {

				EstimateTimeDto monthly = new EstimateTimeDto();

				// check exist data
				if (mapEstimateTimeSetting
						.containsKey(EstimateTargetClassification.valueOf(index))) {
					mapEstimateTimeSetting.get(EstimateTargetClassification.valueOf(index))
							.saveToMemento(monthly);
				}

				// add to monthly s
				monthlys.add(monthly);

			}
			dto.setMonthlyEstimates(monthlys);
		}
		return dto;
	}
	
}
