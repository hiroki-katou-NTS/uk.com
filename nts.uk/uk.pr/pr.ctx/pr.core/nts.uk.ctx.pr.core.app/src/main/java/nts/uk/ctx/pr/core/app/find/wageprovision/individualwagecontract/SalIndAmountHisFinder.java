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
    private SalIndAmountRepository salIndAmountRepository;

    @Inject
    private AlgorithmProcessYearFromEmp algorithmProcessYearFromEmp;

    public SalIndAmountHisPackDto getSalIndAmountHis(SalIndAmountHisDto dto) {
        Optional<SalIndAmountHis> salIndAmountHis = finder.getSalIndAmountHis(dto.getPerValCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator());
        if (salIndAmountHis.isPresent()) {
            SalIndAmountHis salIndAmount = salIndAmountHis.get();
            SalIndAmountHisPackDto salIndAmountHisPackDto = SalIndAmountHisPackDto.fromSalIndAmountHisDomain(salIndAmount);
            List<GenericHistYMPeriod> list = salIndAmount.getPeriod();
            if (!list.isEmpty()) {
                for (GenericHistYMPeriod item : list) {
                    SalIndAmount amount = salIndAmountRepository.getSalIndAmountById(item.getHistoryID()).get();
                    salIndAmountHisPackDto.getSalIndAmountList().add(SalIndAmountDto.fromDomain(amount));
                }
            }
            return salIndAmountHisPackDto;
        } else {
            return null;
        }
    }


    public List<SalIndAmountHisPackDto> salIndAmountHisDisplay(SalIndAmountHisDisplayDto dto) {
        List<SalIndAmountHisPackDto> salIndAmountHisPackDtos = new ArrayList<>();
        String cid = AppContexts.user().companyId();
        List<SalIndAmountNameDto> salIndAmountNameDto = salIndAmountNameRepository.getAllSalIndAmountNameByCateIndi(cid, dto.getCateIndicator()).stream().map(item -> SalIndAmountNameDto.fromDomain(item)).collect(Collectors.toList());
        for (SalIndAmountNameDto salIndAmountName : salIndAmountNameDto) {
            Optional<SalIndAmountHis> salIndAmountHis;
            if(dto.getCurrentProcessYearMonth() != 0){
                salIndAmountHis = finder.getSalIndAmountHisDisplay(salIndAmountName.getIndividualPriceCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator(), dto.getCurrentProcessYearMonth());
            }else {
                salIndAmountHis = finder.getSalIndAmountHisDisplay(salIndAmountName.getIndividualPriceCode(), dto.getEmpId(), dto.getSalBonusCate(), dto.getCateIndicator(), processYearFromEmp(salIndAmountName.getIndividualPriceCode()));
            }
            if (salIndAmountHis.isPresent()) {
                SalIndAmountHis salIndAmount = salIndAmountHis.get();
                SalIndAmountHisPackDto salIndAmountHisPackDto = SalIndAmountHisPackDto.fromSalIndAmountHisDomain(salIndAmount);
                salIndAmountHisPackDto.setPerValName(salIndAmountName.getIndividualPriceName());
                List<GenericHistYMPeriod> list = salIndAmount.getPeriod();
                if (!list.isEmpty()) {
                    for (GenericHistYMPeriod item : list) {
                        SalIndAmount amount = salIndAmountRepository.getSalIndAmountById(item.getHistoryID()).get();
                        salIndAmountHisPackDto.getSalIndAmountList().add(SalIndAmountDto.fromDomain(amount));
                    }
                }
                salIndAmountHisPackDtos.add(salIndAmountHisPackDto);
            }
        }
        return salIndAmountHisPackDtos;
    }


    public Integer processYearFromEmp(String employmentCode) {
        return algorithmProcessYearFromEmp.getProcessYear(employmentCode).v();
    }
}
