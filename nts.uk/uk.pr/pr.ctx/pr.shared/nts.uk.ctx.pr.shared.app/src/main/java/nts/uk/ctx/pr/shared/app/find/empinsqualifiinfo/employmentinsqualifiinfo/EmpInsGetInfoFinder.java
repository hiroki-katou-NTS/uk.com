package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpInsGetInfoFinder {
    @Inject
    private EmpInsGetInfoRepository finder;

    public List<EmpInsGetInfoDto> getAllEmpInsGetInfo(){
        return finder.getAllEmpInsGetInfo().stream().map(item -> EmpInsGetInfoDto.fromDomain(item))
                .collect(Collectors.toList());
    }

    public EmpInsGetInfoDto getEmpInsGetInfoById(String sid){
        return finder.getEmpInsGetInfo(AppContexts.user().companyId()).map(EmpInsGetInfoDto::fromDomain).orElse(null);
    }
}
