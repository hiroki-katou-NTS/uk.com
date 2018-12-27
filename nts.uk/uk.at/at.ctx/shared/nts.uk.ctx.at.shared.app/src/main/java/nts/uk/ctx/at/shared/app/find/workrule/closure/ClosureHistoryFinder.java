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

//import nts.arc.time.GeneralDate;
//import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureCdNameDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureEmployDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryFindDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryHeaderDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureHistoryInDto;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.EmpCdNameDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmpCdNameImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureHistoryFinder.
 */
@Stateless
public class ClosureHistoryFinder {

	/** The repository. */
	@Inject
	private ClosureRepository repository;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private ClosureEmploymentRepository closureEmpRepo;
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<ClosureHistoryFindDto> findAll() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get all closure
		List<Closure> closures = this.repository.findAll(companyId);

		// get data
		List<ClosureHistory> closureHistories = new ArrayList<>();

		closures.forEach(closure -> {
			Optional<ClosureHistory> closureHistoryLast = this.repository.findBySelectedYearMonth(companyId,
					closure.getClosureId().value, closure.getClosureMonth().getProcessingYm().v());

			if (closureHistoryLast.isPresent()) {
				closureHistories.add(closureHistoryLast.get());
			}
		});

		// domain to data
		return closureHistories.stream().map(closureHistory -> {
			ClosureHistoryFindDto dto = new ClosureHistoryFindDto();
			closureHistory.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param master the master
	 * @return the closure history header dto
	 */
	public ClosureHistoryHeaderDto findById(ClosureHistoryInDto master) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// call repository find closure history
		Optional<ClosureHistory> closureHistory = this.repository.findById(companyId, master.getClosureId(),
				master.getStartDate());

		// return data
		ClosureHistoryHeaderDto dto = new ClosureHistoryHeaderDto();
		if (closureHistory.isPresent()) {
			closureHistory.get().saveToMemento(dto);
		}
		return dto;
	}
	
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
		List<ClosureHistoryFindDto> findAllClosure = this.findAll().stream()
				.filter(x -> this.repository.findById(companyId, x.getId()).get().getUseClassification() == UseClassification.UseClass_Use).collect(Collectors.toList());
		
		// Map list Employment Dto and list EmployClosure Dom. 取得した雇用コードをもとにドメイン「雇用に紐づく就業締め」を取得する
		empCdNameDtoList.stream().forEach(x->{
			Optional<ClosureEmployment> closureEmp = closureEmpRepo.findByEmploymentCD(companyId, x.getCode());
			if(!closureEmp.isPresent()){
				x.setClosureId(null);
			}else{
				x.setClosureId(closureEmp.get().getClosureId());
			}
		});
		
		return new ClosureEmployDto(empCdNameDtoList, findAllClosure);
	}
	

}
