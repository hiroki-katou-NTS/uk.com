package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class EmpBasicPenNumInforService {

    @Inject
    EmpBasicPenNumInforRepository repository;

    Optional<EmpBasicPenNumInfor> getEmpBasicPenNumInforById(String employeeId){
        return repository.getEmpBasicPenNumInforById(employeeId);
    }

    void add(EmpBasicPenNumInfor domain){
        repository.add(domain);
    }

    void update(EmpBasicPenNumInfor domain){
        repository.update(domain);
    }


}
