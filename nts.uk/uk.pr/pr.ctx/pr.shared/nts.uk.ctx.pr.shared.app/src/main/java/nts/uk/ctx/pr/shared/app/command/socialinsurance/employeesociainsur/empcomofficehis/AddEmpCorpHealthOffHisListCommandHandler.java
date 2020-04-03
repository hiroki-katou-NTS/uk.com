package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empcomofficehis;

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
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisInter;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.EmpCorpHealthOffHisService;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.SocialInsuranceOfficeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;

@Stateless
public class AddEmpCorpHealthOffHisListCommandHandler
		extends CommandHandlerWithResult<List<AddEmpCorpHealthOffHisCommand>, List<MyCustomizeException>>
		implements PeregAddListCommandHandler<AddEmpCorpHealthOffHisCommand> {

	@Inject
	private EmpCorpHealthOffHisRepository empCorpHealthOffHisRepo;

	@Inject
	private EmpCorpHealthOffHisService empCorpHealthOffHisService;

	@Override
	public String targetCategoryCd() {

		return "CS00075";

	}

	@Override
	public Class<?> commandClass() {

		return AddEmpCorpHealthOffHisCommand.class;

	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddEmpCorpHealthOffHisCommand>> context) {

		List<AddEmpCorpHealthOffHisCommand> cmd = context.getCommand();

		String cid = AppContexts.user().companyId();
		
		Map<String, String> recordIds = new HashMap<>();
		
		List<EmpCorpHealthOffHisInter> domains= new ArrayList<>();

		List<MyCustomizeException> result = new ArrayList<>();

		List<String> sids = cmd.stream().map(c -> c.getSid()).collect(Collectors.toList());

		Map<String, List<EmpCorpHealthOffHis>> histBySidsMap = empCorpHealthOffHisRepo.getByCidAndSidsDesc(cid, sids)
				.stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		
		cmd.stream().forEach(c -> {
			try {
				
				String newHistID = IdentifierUtil.randomUniqueId();
				
				DateHistoryItem itemAdded = new DateHistoryItem(newHistID,
						
						new DatePeriod(c.getStartDate() != null ? c.getStartDate() : GeneralDate.min(),
								
								c.getEndDate() != null ? c.getEndDate() : GeneralDate.max()));
				
				List<EmpCorpHealthOffHis> histBySidLst = histBySidsMap.get(c.getSid());
				
				EmpCorpHealthOffHis domain = new EmpCorpHealthOffHis(c.getSid(), new ArrayList<>());
				
				if (histBySidLst != null) {
					domain = histBySidLst.get(0);
				}
				
				domain.add(itemAdded);
				
		        AffOfficeInformation newHistInfo = new AffOfficeInformation(itemAdded.identifier(),
		                new SocialInsuranceOfficeCode(c.getSocialInsurOfficeCode()));
						
						
				domains.add(new EmpCorpHealthOffHisInter(domain, itemAdded, newHistInfo));
				
				recordIds.put(c.getSid(), newHistID);
				
			}catch(BusinessException e) {
				
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getSid()),"期間");
				
				result.add(ex);
			}
			
		});
		
		if(!domains.isEmpty()) {
			
			empCorpHealthOffHisService.addAllCPS003(domains);
			
		}
		
		if(!recordIds.isEmpty()) {
			
			result.add(new MyCustomizeException("NOERROR", recordIds));
			
		}
		return result;
	}

}
