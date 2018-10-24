package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
/**
 * 給与個人別金額履歴: Finder
 */
public class SalIndAmountHisFinder {

    @Inject
    private SalIndAmountHisRepository finder;

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

    public Integer processYearFromEmp(String employmentCode) {
        return algorithmProcessYearFromEmp.getProcessYear(employmentCode).v();
    }
}
