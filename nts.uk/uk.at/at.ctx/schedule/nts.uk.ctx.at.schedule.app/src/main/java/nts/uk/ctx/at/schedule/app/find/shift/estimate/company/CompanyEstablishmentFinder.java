/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.estimate.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.estimate.company.dto.CompanyEstablishmentDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentNumberOfDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentPriceDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.dto.EstablishmentTimeDto;
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

			estimateTime = this.toEstimateTimeDto(companyEstablishment.get());
			estimatePrice = this.toEstimatePriceDto(companyEstablishment.get());
			estimateNumberOfDay = this.toEstimateDaysDto(companyEstablishment.get());
		}
		
		
		// set data
		dto.setEstimateTime(estimateTime);
		dto.setEstimatePrice(estimatePrice);
		dto.setEstimateNumberOfDay(estimateNumberOfDay);
		dto.setSetting(this.repository.checkSetting(companyId, targetYear));
		return dto;
	}
	
	
	/**
	 * To estimate time dto.
	 *
	 * @param domain the domain
	 * @return the establishment time dto
	 */
	private EstablishmentTimeDto toEstimateTimeDto(CompanyEstablishment domain) {
		EstablishmentTimeDto estimateTime = new EstablishmentTimeDto();
		EstimateTimeDto yearlyTime = new EstimateTimeDto();
		List<EstimateTimeDto> monthlyTimes = new ArrayList<>();
		domain.getAdvancedSetting().getEstimateTime().forEach(estimateTimeSetting -> {

			// check yearly exist data
			if (estimateTimeSetting
					.getTargetClassification().value == EstimateTargetClassification.YEARLY.value) {
				estimateTimeSetting.saveToMemento(yearlyTime);
			} else {
				
				// convert monthly
				EstimateTimeDto monthly = new EstimateTimeDto();
				estimateTimeSetting.saveToMemento(monthly);
				monthlyTimes.add(monthly);
				List<EstimateTimeDto> listEstimateTimeDto = new ArrayList<>();
				listEstimateTimeDto = monthlyTimes.stream().sorted(Comparator.comparing(EstimateTimeDto::getMonth))
						.collect(Collectors.toList());
				monthlyTimes.clear();
				monthlyTimes.addAll(listEstimateTimeDto);
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
	private EstablishmentPriceDto toEstimatePriceDto(CompanyEstablishment domain) {
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
				List<EstimatePriceDto> listEstimatePriceDto = new ArrayList<>();
				listEstimatePriceDto = monthlyPrices.stream().sorted(Comparator.comparing(EstimatePriceDto::getMonth))
						.collect(Collectors.toList());
				monthlyPrices.clear();
				monthlyPrices.addAll(listEstimatePriceDto);
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
	 * @return the establishment number of day dto
	 */
	private EstablishmentNumberOfDayDto toEstimateDaysDto(CompanyEstablishment domain) {

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
				List<EstimateNumberOfDayDto> listEstimateNumberOfDayDto = new ArrayList<>();
				listEstimateNumberOfDayDto = monthlyDays.stream()
						.sorted(Comparator.comparing(EstimateNumberOfDayDto::getMonth)).collect(Collectors.toList());
				monthlyDays.clear();
				monthlyDays.addAll(listEstimateNumberOfDayDto);
			}
		});
		
		// set data
		estimateNumberOfDay.setYearlyEstimate(yearlyDays);
		estimateNumberOfDay.setMonthlyEstimates(monthlyDays);
		return estimateNumberOfDay;
	}
}
