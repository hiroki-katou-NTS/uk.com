package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.uk.com.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SysEmploymentAdapter;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InitialDisplayRegisterProcessingFinder {
	@Inject
	private ProcessInformationRepository finderProcessInformation;
	@Inject
	private SetDaySupportRepository finderSetDaySupport;
	@Inject
	private CurrProcessDateRepository finderCurrProcessDate;

	@Inject
	private EmpTiedProYearRepository finderEmpTiedProYear;

	@Inject
	private SysEmploymentAdapter syEmploymentAdapter;

	public InitialDisplayRegisterProcessingDto getInitialDisplayRegisterProcessing() {
		String cid = AppContexts.user().companyId();
		List<ProcessInformation> optProcessInformation = finderProcessInformation.getProcessInformationByCid(cid);

		List<SetDaySupportDto> setDaySupportDto = new ArrayList<SetDaySupportDto>();
		List<ProcessInformationDto> informationDto = new ArrayList<ProcessInformationDto>();
		List<CurrProcessDateDto> currProcessDateDto = new ArrayList<CurrProcessDateDto>();
		List<EmpCdNameImport> employeeList = null;

		List<EmpTiedProYearDto> empTiedProYearDto = new ArrayList<EmpTiedProYearDto>();

		if (!optProcessInformation.isEmpty()) {
			for (int i = 0; i < optProcessInformation.size(); i++) {
				int processCateNo = optProcessInformation.get(i).getProcessCateNo();
				List<SetDaySupport> optSetDaySupport = finderSetDaySupport.getSetDaySupportById(cid, processCateNo);
				List<CurrProcessDate> optCurrProcessDate = finderCurrProcessDate.getCurrProcessDateById(cid,
						processCateNo);
				Optional<EmpTiedProYear> optEmpTiedProYear = finderEmpTiedProYear.getEmpTiedProYearById(cid,
						processCateNo);

				informationDto = optProcessInformation.stream().map(item -> ProcessInformationDto.fromDomain(item))
						.collect(Collectors.toList());

				setDaySupportDto.addAll(optSetDaySupport.stream().map(item -> SetDaySupportDto.fromDomain(item))
						.collect(Collectors.toList()));

				currProcessDateDto.addAll(optCurrProcessDate.stream().map(item -> CurrProcessDateDto.fromDomain(item))
						.collect(Collectors.toList()));

				empTiedProYearDto.add(optEmpTiedProYear
						.map(x -> new EmpTiedProYearDto(x.getCid(), x.getProcessCateNo(),
								x.getEmploymentCodes().stream().map(item -> item.v()).collect(Collectors.toList())))
						.orElse(null));
			}

			InitialDisplayRegisterProcessingDto returnData = new InitialDisplayRegisterProcessingDto(informationDto,
					setDaySupportDto, currProcessDateDto, empTiedProYearDto, employeeList);
			return returnData;
		}

		return null;
	}
}
