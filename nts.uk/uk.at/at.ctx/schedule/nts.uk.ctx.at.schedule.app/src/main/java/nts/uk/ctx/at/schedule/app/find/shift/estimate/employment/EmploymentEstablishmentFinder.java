/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimatePriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.employment.dto.EmploymentEstablishmentDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.employment.EmploymentEstablishmentRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmploymentEstablishmentFinder.
 */
@Stateless
public class EmploymentEstablishmentFinder {
	
	/** The repository. */
	@Inject
	private EmploymentEstablishmentRepository repository;
	
	
	
	/**
	 * Find estimate time.
	 *
	 * @param targetYear the target year
	 * @param employmentCode the employment code
	 * @return the employment establishment dto
	 */
	public EmploymentEstablishmentDto findEstimateTime(String employmentCode, int targetYear) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find data
		Optional<EmploymentEstablishment> employmentEstablishment = this.repository
				.findById(companyId, employmentCode, targetYear);
		
		EmploymentEstablishmentDto dto = new EmploymentEstablishmentDto();
		
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		
		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		
		// check exist data
		if (employmentEstablishment.isPresent()) {

			EstimateTimeDto yearlyTime = new EstimateTimeDto();
			List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
			employmentEstablishment.get().getAdvancedSetting().getEstimateTime()
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
			employmentEstablishment.get().getAdvancedSetting().getEstimatePrice()
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
			employmentEstablishment.get().getAdvancedSetting().getEstimateNumberOfDay()
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
	
	/**
	 * Find all by target year.
	 *
	 * @param targetYear the target year
	 * @return the list
	 */
	public List<String> findAllByTargetYear(int targetYear) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.repository.findAllEmploymentSetting(companyId, targetYear);
	}

}
