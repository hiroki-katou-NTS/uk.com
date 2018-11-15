package nts.uk.ctx.pr.core.app.find.wageprovision.orginfo.salarycls.salaryclsmaster;

import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfo;
import nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster.SalaryClsInfoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class SalaryClsInfoFinder {
    @Inject
    SalaryClsInfoRepository repository;

    public List<SalaryClsInfoDto> findAll() {
        List<SalaryClsInfoDto> list = repository.findAll()
                .stream().map(x -> SalaryClsInfoDto.of(x)).collect(Collectors.toList());
        return list;
    }

    public SalaryClsInfoDto get(String salaryClsCode) {
        Optional<SalaryClsInfo> salaryClsInfoOpt = repository.get(salaryClsCode);
        return salaryClsInfoOpt.isPresent() ? SalaryClsInfoDto.of(salaryClsInfoOpt.get()) : null;
    }
}
