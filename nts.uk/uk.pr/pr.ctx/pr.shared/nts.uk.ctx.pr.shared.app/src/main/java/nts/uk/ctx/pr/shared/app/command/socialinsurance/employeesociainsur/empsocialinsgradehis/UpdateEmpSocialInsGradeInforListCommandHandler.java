package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empsocialinsgradehis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.CalculationAtr;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGrade;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHisInter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.MonthlyRemuneration;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmpSocialInsGradeInforListCommandHandler extends CommandHandlerWithResult<List<UpdateEmpSocialInsGradeInforCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateEmpSocialInsGradeInforCommand>{

    @Inject
    private EmpSocialInsGradeRepository repository;

    @Inject
    private EmpSocialInsGradeService service;
    
    @Inject
    private EmpSocialInsGradeRepository empSocialInsGradeRepository;

	@Override
	public String targetCategoryCd() {
		
		return "CS00092";
		
	}
	

	@Override
	public Class<?> commandClass() {
		
		return UpdateEmpSocialInsGradeInforCommand.class;
		
	}

	@Override
	protected List<MyCustomizeException> handle(
			CommandHandlerContext<List<UpdateEmpSocialInsGradeInforCommand>> context) {
		
		List<UpdateEmpSocialInsGradeInforCommand> command = context.getCommand();
		
		String cid = AppContexts.user().companyId();
		
		List<String> sidErrorLst = new ArrayList<>();
		
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		
		// sidsPidsMap
		List<String> sids = command.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		
		List<EmpSocialInsGradeHisInter> domainIntermediates = new ArrayList<>();
		
		Map<String, EmpSocialInsGrade> histBySidsMap = repository.getBySidsAndCid(cid, sids);
		
		List<EmpSocialInsGradeInfo> infos = new ArrayList<>();
			
		command.stream().forEach(c -> {
			
			try {
				
				// In case of date period are exist in the screen,
				if (c.getStartYM() != null) {
					
					EmpSocialInsGrade empSocialInsGrade = histBySidsMap.get(c.getEmployeeId());
					
					if (empSocialInsGrade != null) {
						
						Optional<YearMonthHistoryItem> itemToBeUpdated = empSocialInsGrade.getHistory().items().stream()
								
								.filter(h -> h.identifier().equals(c.getHistoryId())).findFirst();
						
						if (itemToBeUpdated.isPresent()) {
							
							empSocialInsGrade.getHistory().changeSpan(itemToBeUpdated.get(), new YearMonthPeriod(c.getStartYM().yearMonth(),
				                   
									c.getEndYM() != null ? c.getEndYM().yearMonth() : GeneralDate.max().yearMonth()));

							EmpSocialInsGradeInfo info = new EmpSocialInsGradeInfo(

									c.getHistoryId(),

									c.getSocInsMonthlyRemune() == null
											? (CollectionUtil.isEmpty(empSocialInsGrade.getInfos()) == true?  0: empSocialInsGrade.getInfos().get(0).getSocInsMonthlyRemune().v())
											: c.getSocInsMonthlyRemune().intValue(),

									c.getCalculationAtr() == null
											? (CollectionUtil.isEmpty(empSocialInsGrade.getInfos()) == true?  CalculationAtr.SCHEDULED.value: empSocialInsGrade.getInfos().get(0).getCalculationAtr().value)
											: c.getCalculationAtr().intValue(),

									c.getHealInsStandMonthlyRemune() != null
											? c.getHealInsStandMonthlyRemune().intValue()
											: null,

									c.getHealInsGrade() != null ? c.getHealInsGrade().intValue() : null,

									c.getPensionInsStandCompenMonthly() != null
											? c.getPensionInsStandCompenMonthly().intValue()
											: null,

									c.getPensionInsGrade() != null ? c.getPensionInsGrade().intValue() : null);

							domainIntermediates.add(new EmpSocialInsGradeHisInter(empSocialInsGrade.getHistory(), info, itemToBeUpdated.get()));
						
						} else {
							
							sidErrorLst.add(c.getEmployeeId());
							
							return;
							
						}
						
					} else {
						
						sidErrorLst.add(c.getEmployeeId());
						
						return;
						
					}
				}else {
					EmpSocialInsGrade empSocialInsGrade = histBySidsMap.get(c.getEmployeeId());
		            EmpSocialInsGradeInfo info = new EmpSocialInsGradeInfo(
		            		
		                    c.getHistoryId(),
		                    
		                    c.getSocInsMonthlyRemune()== null? 0: c.getSocInsMonthlyRemune().intValue(),
		                    
		                    c.getCalculationAtr() == null? CalculationAtr.SCHEDULED.value: c.getCalculationAtr().intValue(),
		                    
		                    c.getHealInsStandMonthlyRemune() != null ? c.getHealInsStandMonthlyRemune().intValue() : 0,
		                    
		                    c.getHealInsGrade() != null ? c.getHealInsGrade().intValue() : null,
		                    		
		                    c.getPensionInsStandCompenMonthly() != null ? c.getPensionInsStandCompenMonthly().intValue() : null,
		                    		
		                    c.getPensionInsGrade() != null ? c.getPensionInsGrade().intValue() : null);
					
		            
		            infos.add(info);
		            
				}

				
			} catch (BusinessException e) {
				
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()), "期間");
				
				errorExceptionLst.add(ex);
			}

		});
		
		if(!domainIntermediates.isEmpty()) {
			
			service.updateAll(domainIntermediates);
			
		}
		
		if(!infos.isEmpty()) {
			
			this.empSocialInsGradeRepository.updateAllInfo(infos);
		}
		
		if(!sidErrorLst.isEmpty()) {
			
			errorExceptionLst.add(new MyCustomizeException("invalid employmentHistory", sidErrorLst));
		
		}
		
		return errorExceptionLst;
	}

}
