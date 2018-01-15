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
		Optional<PersonalEstablishment> personalEstablishment = this.repository.findById(employeeId,
				targetYear);
		
		PersonalEstablishmentDto dto = new PersonalEstablishmentDto();
		
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		
		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		
		// check exist data
		if (personalEstablishment.isPresent()) {

			estimateTime = this.toEstimateTimeDto(personalEstablishment.get());
			estimatePrice = this.toEstimatePriceDto(personalEstablishment.get());
			estimateNumberOfDay = this.toEstimateDaysDto(personalEstablishment.get());
			
		}
		
		// set data 
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
	/**
	 * To estimate time dto.
	 *
	 * @param domain the domain
	 * @return the establishment time dto
	 */
	private EstablishmentTimeDto toEstimateTimeDto(PersonalEstablishment domain) {
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		EstimateTimeDto yearlyTime = new EstimateTimeDto();
		List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
		domain.getAdvancedSetting().getEstimateTime().forEach(estimateTimeSetting -> {

			// check yearly exist data
			if (estimateTimeSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimateTimeSetting.saveToMemento(yearlyTime);
			} else {
				// convert to monthly
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
	private EstablishmentPriceDto toEstimatePriceDto(PersonalEstablishment domain) {
		EstablishmentPriceDto estimatePrice = new EstablishmentPriceDto();
		EstimatePriceDto yearlyPrice = new EstimatePriceDto();
		List<EstimatePriceDto> monthlyPrices = new ArrayList<>();
		domain.getAdvancedSetting().getEstimatePrice().forEach(estimatePriceSetting -> {

			// check yearly exist data
			if (estimatePriceSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimatePriceSetting.saveToMemento(yearlyPrice);
			} else {
				// convert to monthly price
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
	private EstablishmentNumberOfDayDto toEstimateDaysDto(PersonalEstablishment domain) {

		EstablishmentNumberOfDayDto estimateNumberOfDay = new EstablishmentNumberOfDayDto();
		EstimateNumberOfDayDto yearlyDays = new EstimateNumberOfDayDto();
		List<EstimateNumberOfDayDto> monthlyDays = new ArrayList<>();
		domain.getAdvancedSetting().getEstimateNumberOfDay().forEach(estimateDaysSetting -> {

			// check yearly exist data
			if (estimateDaysSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimateDaysSetting.saveToMemento(yearlyDays);
			} else {
				// convert monthly days
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
