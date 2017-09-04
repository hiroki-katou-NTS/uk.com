/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.personal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimatePriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstimateTimeDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.personal.dto.PersonalEstablishmentDto;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateTargetClassification;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.personal.PersonalEstablishmentRepository;

/**
 * The Class PersonalEstablishmentFinder.
 */
@Stateless
public class PersonalEstablishmentFinder {
	
	/** The repository. */
	@Inject
	private PersonalEstablishmentRepository repository;
	
	/**
	 * Find estimate time.
	 *
	 * @param targetYear the target year
	 * @return the Personal establishment dto
	 */
	public PersonalEstablishmentDto findEstimateTime(int targetYear, String employeeId) {
		
		// call repository find data
		Optional<PersonalEstablishment> PersonalEstablishment = this.repository.findById(employeeId,
				targetYear);
		
		PersonalEstablishmentDto dto = new PersonalEstablishmentDto();
		
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		
		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		
		// check exist data
		if (PersonalEstablishment.isPresent()) {

			EstimateTimeDto yearlyTime = new EstimateTimeDto();
			List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
			PersonalEstablishment.get().getAdvancedSetting().getEstimateTime()
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
			PersonalEstablishment.get().getAdvancedSetting().getEstimatePrice()
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
			PersonalEstablishment.get().getAdvancedSetting().getEstimateNumberOfDay()
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
	 * Find all personal setting.
	 *
	 * @param targetYear the target year
	 * @return the list
	 */
	public List<PersonalEstablishmentDto> findAllPersonalSetting(int targetYear){
		return this.repository.findAll(targetYear).stream().map(employeeId->{
			PersonalEstablishmentDto dto = new PersonalEstablishmentDto();
			dto.setEmployeeId(employeeId);
			return dto;
		}).collect(Collectors.toList());
	}
	
}
