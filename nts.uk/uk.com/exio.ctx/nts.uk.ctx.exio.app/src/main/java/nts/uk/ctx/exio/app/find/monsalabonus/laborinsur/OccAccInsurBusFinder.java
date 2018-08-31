package nts.uk.ctx.exio.app.find.monsalabonus.laborinsur;

import nts.uk.ctx.exio.dom.monsalabonus.laborinsur.EmpInsurBusBurRatioRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class OccAccInsurBusFinder {
    @Inject
    private EmpInsurBusBurRatioRepository empInsurBusBurRatioReopository;

    public List<EmpInsurPreRateDto> getListEmplInsurPreRate(String hisId){
        return empInsurBusBurRatioReopository.getEmpInsurBusBurRatioByHisId(hisId).stream().
                map(item -> EmpInsurPreRateDto.fromDomain(item)).collect(Collectors.toList());
    }
}
