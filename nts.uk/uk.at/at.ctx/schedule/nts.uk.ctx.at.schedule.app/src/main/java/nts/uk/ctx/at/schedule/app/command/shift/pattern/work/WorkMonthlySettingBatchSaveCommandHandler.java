/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkMonthlySettingBatchSaveCommandHandler.
 */
@Stateless
public class WorkMonthlySettingBatchSaveCommandHandler
		extends CommandHandler<WorkMonthlySettingBatchSaveCommand> {
	
	 /** The Constant ADD. */
 	public static final int ADD = 1;
	 
 	/** The Constant UPDATE. */
 	public static final int UPDATE = 2;
	
	/** The Constant INDEX_FIRST. */
	public static final int INDEX_FIRST = 0;
	
	/** The repository. */
	@Inject
	private WorkMonthlySettingRepository workMonthlySettingRepository;
	
	/** The monthly pattern repository. */
	@Inject
	private MonthlyPatternRepository monthlyPatternRepository;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkMonthlySettingBatchSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		WorkMonthlySettingBatchSaveCommand command = context.getCommand();
		
		// to list domain
		List<WorkMonthlySetting> lstDomain = command.toDomainMonth(companyId);
		
		// check not setting
		lstDomain.forEach(domain -> {
			if (StringUtil.isNullOrEmpty(domain.getWorkTypeCode().v(), true)) {
				throw new BusinessException("Msg_148");
			}
		});
		// command to domain
		MonthlyPattern domain = command.toDomain(companyId);

		// validate domain
		domain.validate();
		
		// check mode ADD
		if (command.getMode() == ADD) {
			// check exist data add
			Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository
					.findById(companyId, domain.getMonthlyPatternCode().v());

			// show message
			if (monthlyPattern.isPresent()) {
				throw new BusinessException("Msg_3");
			}
			

			// call repository add domain
			this.monthlyPatternRepository.add(domain);
		}
		else {
			// check exist data add
			Optional<MonthlyPattern> monthlyPattern = this.monthlyPatternRepository.findById(companyId,
					domain.getMonthlyPatternCode().v());
			
			// add domain
			if(!monthlyPattern.isPresent()){
				this.monthlyPatternRepository.add(domain);
			}
			// call repository update domain
			this.monthlyPatternRepository.update(domain);
		}

		
		
		
		// get list domain update
		List<WorkMonthlySetting> domainUpdates = this.workMonthlySettingRepository.findByYMD(companyId,
				lstDomain.get(INDEX_FIRST).getMonthlyPatternCode().v(),
				lstDomain.stream().map(domainsetting -> domainsetting.getYmdk()).collect(Collectors.toList()));
		
		
		// convert to map domain update
		Map<BigDecimal, WorkMonthlySetting> mapDomainUpdate = domainUpdates.stream()
				.collect(Collectors.toMap((domainsetting) -> {
					return domainsetting.getYmdk();
				}, Function.identity()));

		// add all domain
		List<WorkMonthlySetting> addAllDomains = new ArrayList<>();
		
		// update all domain
		List<WorkMonthlySetting> updateAllDomains = new ArrayList<>();
		
		// domain update all, add all collection
		lstDomain.forEach(domainsetting->{

			// check exist of domain update
			if(mapDomainUpdate.containsKey(domainsetting.getYmdk())){
				updateAllDomains.add(domainsetting);
			}else {
				addAllDomains.add(domainsetting);
			}
		});
		// update all list domain
		this.workMonthlySettingRepository.updateAll(updateAllDomains);
		
		// add all list domain
		this.workMonthlySettingRepository.addAll(addAllDomains);
	}

}
