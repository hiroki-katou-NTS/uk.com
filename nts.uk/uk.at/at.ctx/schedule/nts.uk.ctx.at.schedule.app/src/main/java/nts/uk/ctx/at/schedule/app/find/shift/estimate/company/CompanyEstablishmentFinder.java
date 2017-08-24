/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto.CompanyEstablishmentDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimatePriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateTimeDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
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
	
	/**
	 * Find estimate time.
	 *
	 * @param targetYear the target year
	 * @return the company establishment dto
	 */
	public CompanyEstablishmentDto findEstimateTime(int targetYear) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		Optional<CompanyEstablishment> companyEstablishment = this.repository.findById(companyId,
				targetYear);
		
		CompanyEstablishmentDto dto = new CompanyEstablishmentDto();
		
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		
		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		
		// check exist data
		if (companyEstablishment.isPresent()) {

			EstimateTimeDto yearlyTime = new EstimateTimeDto();
			List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
			companyEstablishment.get().getAdvancedSetting().getEstimateTime()
					.forEach(estimateTimeSetting -> {

						// check yearly exist data
						if (estimateTimeSetting
								.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
							estimateTimeSetting.saveToMemento(yearlyTime);
						} else {
							EstimateTimeDto monthly = new EstimateTimeDto();
							estimateTimeSetting.saveToMemento(monthly);
							monthlyTimes.add(monthly);
						}
					});
			estimateTime.setYearlyEstimate(yearlyTime);
			estimateTime.setMonthlyEstimates(monthlyTimes);

			EstimatePriceDto yearlyPrice = new EstimatePriceDto();
			List<EstimatePriceDto> monthlyPrices = new ArrayList<>();
			companyEstablishment.get().getAdvancedSetting().getEstimatePrice()
					.forEach(estimatePriceSetting -> {

						// check yearly exist data
						if (estimatePriceSetting
								.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
							estimatePriceSetting.saveToMemento(yearlyPrice);
						} else {
							EstimatePriceDto monthly = new EstimatePriceDto();
							estimatePriceSetting.saveToMemento(monthly);
							monthlyPrices.add(monthly);
						}
					});
			estimatePrice.setYearlyEstimate(yearlyPrice);
			estimatePrice.setMonthlyEstimates(monthlyPrices);
			
			
			EstimateNumberOfDayDto yearlyDays = new EstimateNumberOfDayDto();
			List<EstimateNumberOfDayDto> monthlyDays = new ArrayList<>();
			companyEstablishment.get().getAdvancedSetting().getEstimateNumberOfDay()
			.forEach(estimateDaysSetting -> {
				
				// check yearly exist data
				if (estimateDaysSetting
						.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
					estimateDaysSetting.saveToMemento(yearlyDays);
				} else {
					EstimateNumberOfDayDto monthly = new EstimateNumberOfDayDto();
					estimateDaysSetting.saveToMemento(monthly);
					monthlyDays.add(monthly);
				}
			});
			estimateNumberOfDay.setYearlyEstimate(yearlyDays);
			estimateNumberOfDay.setMonthlyEstimates(monthlyDays);
			
			
		}
		dto.setEstimateTime(estimateTime);
		dto.setEstimatePrice(estimatePrice);
		dto.setEstimateNumberOfDay(estimateNumberOfDay);
		return dto;
	}
	
}
