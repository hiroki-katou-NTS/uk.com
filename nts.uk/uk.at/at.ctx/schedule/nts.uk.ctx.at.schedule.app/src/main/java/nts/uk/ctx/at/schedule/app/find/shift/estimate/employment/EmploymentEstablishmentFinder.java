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

			estimateTime = this.toEstimateTimeDto(employmentEstablishment.get());
			estimatePrice = this.toEstimatePriceDto(employmentEstablishment.get());
			estimateNumberOfDay = this.toEstimateDaysDto(employmentEstablishment.get());
			dto.setEmploymentCode(employmentEstablishment.get().getEmploymentCode().v());
		}
		
		// set data
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

		// call repository find by id
		return this.repository.findAllEmploymentSetting(companyId, targetYear);
	}
	
	/**
	 * To estimate time dto.
	 *
	 * @param domain the domain
	 * @return the establishment time dto
	 */
	private EstablishmentTimeDto toEstimateTimeDto(EmploymentEstablishment domain) {
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		EstimateTimeDto yearlyTime = new EstimateTimeDto();
		List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
		domain.getAdvancedSetting().getEstimateTime().forEach(estimateTimeSetting -> {

			// check yearly exist data
			if (estimateTimeSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimateTimeSetting.saveToMemento(yearlyTime);
			} else {
				
				// convert monthly data
				EstimateTimeDto monthly = new EstimateTimeDto();
				estimateTimeSetting.saveToMemento(monthly);
				monthlyTimes.add(monthly);
			}
		});
		
		// set data
		estimateTime.setYearlyEstimate(yearlyTime);
		estimateTime.setMonthlyEstimates(monthlyTimes);
		return estimateTime;
	}
	
	/**
	 * To estimate price dto.
	 *
	 * @param domain the domain
	 * @return the establishment price dto
	 */
	private EstablishmentPriceDto toEstimatePriceDto(EmploymentEstablishment domain) {
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		EstimatePriceDto yearlyPrice = new EstimatePriceDto();
		List<EstimatePriceDto> monthlyPrices = new ArrayList<>();
		domain.getAdvancedSetting().getEstimatePrice().forEach(estimatePriceSetting -> {

			// check yearly exist data
			if (estimatePriceSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimatePriceSetting.saveToMemento(yearlyPrice);
			} else {
				
				// convert monthly 
				EstimatePriceDto monthly = new EstimatePriceDto();
				estimatePriceSetting.saveToMemento(monthly);
				monthlyPrices.add(monthly);
			}
		});
		
		// set data
		estimatePrice.setYearlyEstimate(yearlyPrice);
		estimatePrice.setMonthlyEstimates(monthlyPrices);
		return estimatePrice;
	}
	
	/**
	 * To estimate days dto.
	 *
	 * @param domain the domain
	 * @return the establishment number of day dto
	 */
	private EstablishmentNumberOfDayDto toEstimateDaysDto(EmploymentEstablishment domain) {

		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		EstimateNumberOfDayDto yearlyDays = new EstimateNumberOfDayDto();
		List<EstimateNumberOfDayDto> monthlyDays = new ArrayList<>();
		domain.getAdvancedSetting().getEstimateNumberOfDay().forEach(estimateDaysSetting -> {

			// check yearly exist data
			if (estimateDaysSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimateDaysSetting.saveToMemento(yearlyDays);
			} else {
				
				// convert monthly
				EstimateNumberOfDayDto monthly = new EstimateNumberOfDayDto();
				estimateDaysSetting.saveToMemento(monthly);
				monthlyDays.add(monthly);
			}
		});
		
		// set data
		estimateNumberOfDay.setYearlyEstimate(yearlyDays);
		estimateNumberOfDay.setMonthlyEstimates(monthlyDays);
		return estimateNumberOfDay;
	}

}
