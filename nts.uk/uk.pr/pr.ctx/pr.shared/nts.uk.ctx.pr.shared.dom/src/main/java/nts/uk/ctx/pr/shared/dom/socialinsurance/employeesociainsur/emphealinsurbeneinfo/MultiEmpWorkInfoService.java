package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class MultiEmpWorkInfoService {

    @Inject
    MultiEmpWorkInfoRepository repository;

    Optional<MultiEmpWorkInfo> getMultiEmpWorkInfoById(String employeeId){
        return repository.getMultiEmpWorkInfoById(employeeId);
    }

    void add(MultiEmpWorkInfo domain){
        repository.add(domain);
    }

    void update(MultiEmpWorkInfo domain){
        repository.update(domain);
    }
}
