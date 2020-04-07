package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealInsQualifiInfoService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforParams;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddEmpHealInsQualifiInfoListCommandHandler extends CommandHandlerWithResult<List<AddEmpHealInsQualifiInfoCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddEmpHealInsQualifiInfoCommand>{

    @Inject
    private EmplHealInsurQualifiInforRepository emplHealInsurQualifiInforRepository;

    //@Inject
    //private HealInsurNumberInforRepository healInsurNumberInforRepository;

    @Inject
    private EmpHealInsQualifiInfoService empHealInsQualifiInfoService;
    
	@Override
	public String targetCategoryCd() {
		return "CS00082";
	}

	@Override
	public Class<?> commandClass() {
		return AddEmpHealInsQualifiInfoCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddEmpHealInsQualifiInfoCommand>> context) {
		
		List<AddEmpHealInsQualifiInfoCommand> commands = context.getCommand();
		
		String cid = AppContexts.user().companyId();
		
		Map<String, String> recordIds = new HashMap<>();
		
		List<MyCustomizeException> result = new ArrayList<>();
		
		List<EmplHealInsurQualifiInforParams> domains = new ArrayList<>();
		
		List<String> sids = commands.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		
		Map<String, List<EmplHealInsurQualifiInfor>> histBySidsMap = emplHealInsurQualifiInforRepository
				
				.getEmplHealInsurQualifiInforDesc(cid, sids).stream()
				
				.collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		
		commands.stream().forEach(c ->{
			
			try {
				
				String newHistID = IdentifierUtil.randomUniqueId();
				
		        EmpHealthInsurBenefits dateItem = new EmpHealthInsurBenefits(newHistID,
		                new DateHistoryItem(newHistID,
		                        new DatePeriod(c.getStartDate()!= null? c.getStartDate() : GeneralDate.min(), c.getEndDate() != null ? c.getEndDate() : GeneralDate.max()
		        )));
		        
				List<EmplHealInsurQualifiInfor> histBySidLst = histBySidsMap.get(c.getEmployeeId());
				
				EmplHealInsurQualifiInfor qualifiInfor = new EmplHealInsurQualifiInfor(c.getEmployeeId(), new ArrayList<>());
				
				
		        HealInsurNumberInfor numberInfor = HealInsurNumberInfor.createFromJavaType(
		        		
		                dateItem.identifier(),
		                
		                c.getNurCaseInsNumber(),
		                
		                c.getHealInsNumber()
		        );
		        
				if (histBySidLst != null) {
					
					qualifiInfor = histBySidLst.get(0);
					
				}
				
				qualifiInfor.add(dateItem);
				
				domains.add(new EmplHealInsurQualifiInforParams(cid, dateItem, numberInfor, qualifiInfor));
				
				recordIds.put(c.getEmployeeId(), newHistID);
				
			}catch(BusinessException e) {
				
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()),"期間");
				
				result.add(ex);
				
			}
			
		});
		
		if(!domains.isEmpty()) {
			
			empHealInsQualifiInfoService.addAll(domains);
			
		}
		
		return result;
	}

}
