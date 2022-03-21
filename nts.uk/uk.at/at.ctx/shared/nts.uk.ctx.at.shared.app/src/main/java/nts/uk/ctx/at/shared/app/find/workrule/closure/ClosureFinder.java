/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
//import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ApprovalComfirmDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDetailDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureForLogDto;
//import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryForComDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryMasterDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureIdNameDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresInfoDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileClosurePeriod;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileClosureTime;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.SmileEmpClosure;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
//import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosuresDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureGetMonthDay;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
//import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

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
	
	@Inject
	private ClosureEmploymentService service;

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
	 * Gets the closures by base date.
	 *
	 * @param baseDate the base date
	 * @return the closures by base date
	 */
	public List<ClosureIdNameDto> getClosuresByBaseDate(GeneralDate baseDate) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();

		// Find All Closure in use
		List<Closure> closureList = this.repository.findAllUse(companyId);

		// Get List ClosureHistory by base date
		List<ClosureIdNameDto> lstClosureHistory = new ArrayList<>();
		closureList.forEach(clo -> {
			ClosureIdNameDto closureDto = ClosureIdNameDto.fromDomain(clo.getHistoryByBaseDate(baseDate));
			lstClosureHistory.add(closureDto);
		});
		return lstClosureHistory;
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
			Optional<ClosureHistory> closureHisory = this.repository.findBySelectedYearMonth(companyId, closure.getClosureId().value,
					closure.getClosureMonth().getProcessingYm().v());

			if (closureHisory.isPresent()) {
				ClosureHistoryMasterDto closureSelected = new ClosureHistoryMasterDto();
				closureHisory.get().saveToMemento(closureSelected);
				dto.setClosureSelected(closureSelected);
			}
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
	
	/**
	 * Find emp by closure ids.
	 *
	 * @param closureIds the closure ids
	 * @return the list
	 */
	public List<ClosureEmployment> findEmpByClosureIdsMob(List<Integer> closureIds) {
		// Get companyID.
		String companyId = AppContexts.user().companyId();
		
		// Get ClosureEmployment
		List<ClosureEmployment> closureEmpList = this.closureEmpRepo.findByClosureIds(companyId, closureIds);
		
		// Get Employment Codes from ClosureEmployment acquired above
		return closureEmpList;
	}

	/**
	 * Gets the closure id by employment code.
	 *
	 * @param employmentCode the employment code
	 * @return the closure id by employment code
	 */
	public Integer getClosureIdByEmploymentCode(String employmentCode) {
		Optional<ClosureEmployment> closureEmp = this.closureEmpRepo.findByEmploymentCD(AppContexts.user().companyId(),
				employmentCode);
		if (closureEmp.isPresent()) {
			return closureEmp.get().getClosureId();
		}
		return null;
	}
	
	//find by employeeID
	public ClosureDto findClosureByEmployeeId() {
		String employeeId = AppContexts.user().employeeId();

		Optional<Closure> closureOpt = service.findClosureByEmployee(employeeId, GeneralDate.today());

		if (closureOpt.isPresent()) {
			return ClosureDto.fromDomain(closureOpt.get());
		}

		return null;

	}
	
	// closures Information
	public List<ClosuresInfoDto> findClosureByCid(String companyId) {

		List<Closure> closureOpt = repository.findAll(companyId);
		List<ClosuresInfoDto> listClosures = closureOpt.stream().map(x -> ClosuresInfoDto.fromDomain(x)).collect(Collectors.toList());

		return listClosures;

	}
	
	public List<SmileEmpClosure> getEmpCloSmile(String companyId){
		List<ClosureEmployment> empClo = closureEmpRepo.findAllByCid(companyId);
		return empClo.stream().map(x -> new SmileEmpClosure(x.getEmploymentCD(), x.getClosureId())).collect(Collectors.toList());
	}
	
	public SmileClosureTime getTimeSmile(String companyId, int closureId, int startYM){
		Optional<ClosureHistory> closureTime = repository.findById(companyId, closureId, startYM);
		if (closureTime.isPresent())
			return new SmileClosureTime(closureTime.get().getCompanyId().toString(), closureTime.get().getClosureId().value, 
					closureTime.get().getClosureName().v(), closureTime.get().getClosureYMD(),
					new SmileClosurePeriod(closureTime.get().getStartYearMonth().v(), closureTime.get().getEndYearMonth().v()));
		return null;
	}
}
