package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYearRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
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

        List<SetDaySupportDto> setDaySupportDto = new ArrayList<>();
        List<ProcessInformationDto> informationDto = new ArrayList<>();
        List<CurrProcessDateDto> currProcessDateDto = new ArrayList<>();
        List<EmpCdNameImport> employeeList = syEmploymentAdapter.findAll(cid);
        List<EmpTiedProYearDto> empTiedProYearDto = new ArrayList<>();

        if (!optProcessInformation.isEmpty()) {
            for (int i = 0; i < optProcessInformation.size(); i++) {
                int processCateNo = optProcessInformation.get(i).getProcessCateNo();
                List<SetDaySupport> optSetDaySupport = finderSetDaySupport.getSetDaySupportById(cid, processCateNo);
                List<CurrProcessDate> optCurrProcessDate = finderCurrProcessDate.getCurrProcessDateById(cid,
                        processCateNo);
                Optional<EmpTiedProYear> optEmpTiedProYear = finderEmpTiedProYear.getEmpTiedProYearById(cid,
                        processCateNo);

                informationDto = optProcessInformation.stream().map(ProcessInformationDto::fromDomain)
                        .collect(Collectors.toList());

                setDaySupportDto.addAll(optSetDaySupport.stream().map(SetDaySupportDto::fromDomain)
                        .collect(Collectors.toList()));

                currProcessDateDto.addAll(optCurrProcessDate.stream().map(CurrProcessDateDto::fromDomain)
                        .collect(Collectors.toList()));

                empTiedProYearDto.add(optEmpTiedProYear
                        .map(x -> new EmpTiedProYearDto(x.getCid(), x.getProcessCateNo(),
                                x.getEmploymentCodes().stream().map(PrimitiveValueBase::v).collect(Collectors.toList())))
                        .orElse(null));
            }

            return new InitialDisplayRegisterProcessingDto(informationDto,
                    setDaySupportDto, currProcessDateDto, empTiedProYearDto, employeeList);
        }

        return null;
    }
}
