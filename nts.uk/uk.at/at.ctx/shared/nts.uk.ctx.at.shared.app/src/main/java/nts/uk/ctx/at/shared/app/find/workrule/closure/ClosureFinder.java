/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureCdNameDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDetailDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureEmployDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureForLogDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryMasterDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureIdNameDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.EmpCdNameDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMonthDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ClosureFinder.
 */
@Stateless
public class ClosureFinder {

	/** The repository. */
	@Inject
	private ClosureRepository repository;

	/** The Constant ZERO_START_DATE. */
	public static final int ZERO_START_DATE = 0;

	@Inject
	ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	ClosureEmploymentRepository closureEmpRepo;
	
	/**
	 * 締め日の割付を起動する
	 * 
	 * @param referDate
	 */
	public ClosureEmployDto getClosureEmploy() {
		// Get companyID.
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();

		//Get List Employment Dto by companyId. 雇用を取得する 
		List<EmpCdNameImport> data = shareEmploymentAdapter.findAll(companyId);
		List<EmpCdNameDto> empCdNameDtoList = data.stream().map(x -> {
			return new EmpCdNameDto(x.getCode(), x.getName(), null);
		}).collect(Collectors.toList());

		// Get List Closure Dom by company Id and UseAtr = 1. 就業の締めを取得する
		List<Closure> listClosure = repository.findAllActive(companyId, UseClassification.UseClass_Use);
		// Get List ClosureHistory Dom by companyID, closureID, startDay.
		List<ClosureCdNameDto> lstClosureCdName = new ArrayList<>();
		listClosure.stream().forEach(x -> {
			//取得したドメインモデル「締め」の基準日時点のドメインモデル「締め変更履歴」を取得する
			//vi du
			//期間：2018/02～2019/03
			//締日：20
			//2018/02/21～2019/03/20
			//thi so sanh voi 2018/02/21<= 基準日 <= 2019/03/20
			Optional<ClosureHistory> optClosureInf = repository.findBySelectedYearMonth(companyId, x.getClosureId().value, GeneralDate.today().yearMonth().v());
			String formatDate = "yyyyMMdd";
			if(optClosureInf.isPresent()) {
				ClosureHistory closureInf = optClosureInf.get();
				lstClosureCdName.add(ClosureCdNameDto.fromDomain(closureInf));
				/*if(closureInf.getClosureDate().getLastDayOfMonth()) {
					GeneralDate startDateTmp = GeneralDate.fromString(closureInf.getStartYearMonth().toString() + "01", formatDate);
					GeneralDate startDate = startDateTmp.addMonths(1);
					GeneralDate endDate = GeneralDate.today();
					if(closureInf.getEndYearMonth().toString() == "999912") {
						endDate = GeneralDate.fromString("99991231", formatDate);
					}else {
						endDate = GeneralDate.fromString(closureInf.getEndYearMonth().toString() + "01", formatDate).addMonths(1).addDays(-1);
					}
					if(startDate.addDays(1).before(GeneralDate.today()) && endDate.after(GeneralDate.today())) {
						lstClosureCdName.add(ClosureCdNameDto.fromDomain(closureInf));
					}
					
				}else {
					String strClosureDay = StringUtils.leftPad(closureInf.getClosureDate().getClosureDay().toString(), 2, "0");
					String strStartDate = closureInf.getStartYearMonth().v().toString().concat(strClosureDay);
					GeneralDate startDate = GeneralDate.fromString(strStartDate, formatDate);
					String strEndDate = closureInf.getEndYearMonth().v().toString().concat(strClosureDay);
					GeneralDate endDate = GeneralDate.fromString(strEndDate, formatDate);
					if(startDate.addDays(1).before(GeneralDate.today()) && endDate.after(GeneralDate.today())) {
						lstClosureCdName.add(ClosureCdNameDto.fromDomain(closureInf));
					}
				}*/
			}
		});
		// Map list Employment Dto and list EmployClosure Dom. 取得した雇用コードをもとにドメイン「雇用に紐づく就業締め」を取得する
		empCdNameDtoList.stream().forEach(x->{
			Optional<ClosureEmployment> closureEmp = closureEmpRepo.findByEmploymentCD(companyId, x.getCode());
			if(!closureEmp.isPresent()){
				x.setClosureId(null);
			}else{
				x.setClosureId(closureEmp.get().getClosureId());
			}
		});
		
		return new ClosureEmployDto(empCdNameDtoList, lstClosureCdName);
	}
	
	/**
	 * Gets the closure id name.
	 *
	 * @return the closure id name
	 */
	public List<ClosureIdNameDto> getClosureIdName() {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		
		// Find All Closure in use
		List<Closure> closureList = this.repository.findAllUse(companyId);
		
		// Get List ClosureHistory Domain by companyID, closureID, startDay.
		List<ClosureHistory> lstClosureHistory = new ArrayList<>();
		closureList.stream().forEach(x -> {
			Optional<ClosureHistory> closureHist = repository.findBySelectedYearMonth(companyId, x.getClosureId().value,
					GeneralDate.today().yearMonth().v());
			if (closureHist.isPresent()) {
				lstClosureHistory.add(closureHist.get());
			}
			
		});
		
		// Get List ClosureIdNameDto from ClosureHistory Domain.
		List<ClosureIdNameDto> closureIdNameDtoList = lstClosureHistory.stream().map(x -> {
			return ClosureIdNameDto.fromDomain(x);
		}).collect(Collectors.toList());
		return closureIdNameDtoList;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ClosureFindDto> findAll() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.repository.findAll(companyId).stream().map(closure -> {
			ClosureFindDto dto = new ClosureFindDto();
			closure.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * get listClosure for log
	 */

	public List<ClosureForLogDto> findAllForLog() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.repository.findAll(companyId).stream().map(c -> ClosureForLogDto.fromDomain(c))
				.collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param closureId
	 *            the closure id
	 * @return the closure find dto
	 */
	public ClosureFindDto findById(int closureId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.findById(companyId, closureId);

		ClosureFindDto dto = new ClosureFindDto();

		List<ClosureHistory> closureHistories = this.repository.findByClosureId(companyId, closureId);

		// exist data
		if (closure.isPresent()) {

			// to data
			closure.get().setClosureHistories(closureHistories);
			closure.get().saveToMemento(dto);

			Optional<ClosureHistory> closureHisory = this.repository.findBySelectedYearMonth(companyId, closureId,
					closure.get().getClosureMonth().getProcessingYm().v());

			if (closureHisory.isPresent()) {
				ClosureHistoryMasterDto closureSelected = new ClosureHistoryMasterDto();
				closureHisory.get().saveToMemento(closureSelected);
				dto.setClosureSelected(closureSelected);
			}
		}

		return dto;
	}

	/**
	 * Find by id get month day.
	 *
	 * @param closureId
	 *            the closure id
	 * @return the period
	 */
	public DatePeriod findByIdGetMonthDay(int closureId) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.findById(companyId, closureId);

		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.min());

		List<ClosureHistory> closureHistories = this.repository.findByClosureId(companyId, closureId);

		// exist data
		if (closure.isPresent()) {

			// to data
			closure.get().setClosureHistories(closureHistories);

			Optional<ClosureHistory> closureHisory = this.repository.findBySelectedYearMonth(companyId, closureId,
					closure.get().getClosureMonth().getProcessingYm().v());

			ClosureGetMonthDay closureGetMonthDay = new ClosureGetMonthDay();
			period = closureGetMonthDay.getDayMonth(closureHisory.get().getClosureDate(),
					closure.get().getClosureMonth().getProcessingYm().v());
		}

		return period;
	}

	/**
	 * Find by master.
	 *
	 * @param master
	 *            the master
	 * @return the closure detail dto
	 */
	public ClosureDetailDto findByMaster(ClosureHistoryInDto master) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call service
		Optional<Closure> closure = this.repository.findById(companyId, master.getClosureId());

		ClosureDetailDto dto = new ClosureDetailDto();

		Optional<ClosureHistory> closureHistory = this.repository.findById(companyId, master.getClosureId(),
				master.getStartDate());

		// exist data
		if (closure.isPresent()) {
			closure.get().saveToMemento(dto);
		}

		if (closureHistory.isPresent()) {
			closureHistory.get().saveToMemento(dto);
		}

		return dto;
	}

	/**
	 * Gets the max start date closure.
	 *
	 * @return the max start date closure
	 */
	public GeneralDate getMaxStartDateClosure() {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all closure use
		List<Closure> lstClousreUse = this.repository.findAllUse(companyId);

		GeneralDate startDate = GeneralDate.min();

		for (Closure closure : lstClousreUse) {
			DatePeriod period = this.findByIdGetMonthDay(closure.getClosureId().value);

			if (period.start().compareTo(startDate) > ZERO_START_DATE) {
				startDate = period.start();
			}
		}

		return startDate;
	}
	
	
	/**
	 * Find emp by closure id.
	 *
	 * @param closureId the closure id
	 * @return the list
	 */
	public List<String> findEmploymentCodeByClosureId(int closureId) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		
		// Get ClosureEmployment
		List<ClosureEmployment> closureEmpList = this.closureEmpRepo.findByClosureId(companyId, closureId);
		
		// Get Employment Codes from ClosureEmployment acquired above
		return closureEmpList.stream().map(ClosureEmployment::getEmploymentCD)
				.collect(Collectors.toList());
	}
	
	/**
	 * Find emp by closure ids.
	 *
	 * @param closureIds the closure ids
	 * @return the list
	 */
	public List<String> findEmpByClosureIds(List<Integer> closureIds) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		
		// Get ClosureEmployment
		List<ClosureEmployment> closureEmpList = this.closureEmpRepo.findByClosureIds(companyId, closureIds);
		
		// Get Employment Codes from ClosureEmployment acquired above
		return closureEmpList.stream().map(ClosureEmployment::getEmploymentCD)
				.collect(Collectors.toList());
	}
}
