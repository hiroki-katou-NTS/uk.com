package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

//import nts.uk.com.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
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
	/*
	 * @Inject private EmploymentRepository finderEmployment;
	 */

	public InitialDisplayRegisterProcessingDto getInitialDisplayRegisterProcessing() {
		String cid = AppContexts.user().companyId();
		List<ProcessInformation> optProcessInformation = finderProcessInformation.getProcessInformationByCid(cid);
		if (!optProcessInformation.isEmpty()) {
			for (int i = 0; i < optProcessInformation.size(); i++) {
				int processCateNo = optProcessInformation.get(i).getProcessCateNo();
				Optional<SetDaySupport> optSetDaySupport = finderSetDaySupport.getSetDaySupportById(cid, processCateNo);
				Optional<CurrProcessDate> optCurrProcessDate = finderCurrProcessDate.getCurrProcessDateById(cid,
						processCateNo);
				Optional<EmpTiedProYear> optEmpTiedProYear = finderEmpTiedProYear.getEmpTiedProYearById(cid,
						processCateNo);
				// TODO //ドメインモデル「雇用」を取得する

				List<ProcessInformationDto> informationDto = optProcessInformation.stream()
						.map(item -> ProcessInformationDto.fromDomain(item)).collect(Collectors.toList());
				SetDaySupportDto setDaySupportDto = optSetDaySupport
						.map(x -> new SetDaySupportDto(x.getCid(), x.getProcessCateNo(), x.getCloseDateTime(),
								x.getEmpInsurdStanDate(), x.getClosureDateAccounting(), x.getPaymentDate(),
								x.getEmpExtraRefeDate(), x.getSocialInsurdStanDate(), x.getSocialInsurdCollecMonth(),
								x.getProcessDate().v(), x.getIncomeTaxDate(), x.getNumberWorkDay()))
						.orElse(null);
				CurrProcessDateDto currProcessDateDto = optCurrProcessDate.map(
						x -> new CurrProcessDateDto(x.getCid(), x.getProcessCateNo(), x.getGiveCurrTreatYear().v()))
						.orElse(null);
				EmpTiedProYearDto empTiedProYearDto = optEmpTiedProYear
						.map(x -> new EmpTiedProYearDto(x.getCid(), x.getProcessCateNo(), x.getEmploymentCode().v()))
						.orElse(null);

				InitialDisplayRegisterProcessingDto returnData = new InitialDisplayRegisterProcessingDto(informationDto,
						setDaySupportDto, currProcessDateDto, empTiedProYearDto);

				return returnData;
			}
		}

		return null;

	}
}
