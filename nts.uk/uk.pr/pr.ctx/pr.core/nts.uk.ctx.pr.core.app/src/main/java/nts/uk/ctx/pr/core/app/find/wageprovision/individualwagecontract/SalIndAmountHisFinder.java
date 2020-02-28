package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.*;
import nts.uk.ctx.pr.core.dom.wageprovision.salaryindividualamountname.SalIndAmountNameRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
 * 給与個人別金額履歴: Finder
 */
public class SalIndAmountHisFinder {

    @Inject
    private SalIndAmountHisRepository finder;

    @Inject
    private SalIndAmountNameRepository salIndAmountNameRepository;

    @Inject
    private AlgorithmProcessYearFromEmp algorithmProcessYearFromEmp;

    public SalIndAmountHisPackDto getSalIndAmountHis(SalIndAmountHisDto dto) {
        List<SalaryIndividualAmountHistory> histories = finder.getSalIndAmountHis(dto.getPerValCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator());
        if (histories.size() > 0) {
            return SalIndAmountHisPackDto.fromSalIndAmountHisDomain(histories);
        } else {
            return null;
        }
    }


    public List<SalIndAmountHisPackDto> salIndAmountHisDisplay(SalIndAmountHisDisplayDto dto) {
        List<SalIndAmountHisPackDto> salIndAmountHisPackDtos = new ArrayList<>();
        String cid = AppContexts.user().companyId();
        List<SalIndAmountNameDto> salIndAmountNameDto = salIndAmountNameRepository.getAllSalIndAmountNameByCateIndi(cid, dto.getCateIndicator()).stream().map(SalIndAmountNameDto::fromDomain).collect(Collectors.toList());
        for (SalIndAmountNameDto salIndAmountName : salIndAmountNameDto) {
            List<SalaryIndividualAmountHistory> histories;
            if (dto.getCurrentProcessYearMonth() != 0) {
                histories = finder.getSalIndAmountHisDisplay(salIndAmountName.getIndividualPriceCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator(), dto.getCurrentProcessYearMonth());
            } else {
                histories = finder.getSalIndAmountHisDisplay(salIndAmountName.getIndividualPriceCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator(), processYearFromEmp(salIndAmountName.getIndividualPriceCode()));
            }
            if (histories.size() > 0) {
                SalIndAmountHisPackDto salIndAmountHisPackDto = SalIndAmountHisPackDto.fromSalIndAmountHisDomain(histories);
                salIndAmountHisPackDto.setPerValName(salIndAmountName.getIndividualPriceName());
                salIndAmountHisPackDtos.add(salIndAmountHisPackDto);
            }
        }
        return salIndAmountHisPackDtos;
    }

    public Integer processYearFromEmp(String employmentCode) {
        return algorithmProcessYearFromEmp.getProcessYear(employmentCode).v();
    }
}
