package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomworkstlinfor;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomworkstlinfor.CorEmpWorkHisRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CorEmpWorkHisFinder {

    @Inject
    CorEmpWorkHisRepository repository;

    public List<CorEmpWorkHisDto> getAllCorEmpWorkHisByEmpId(String empId){
        Optional<CorEmpWorkHis> domain  = repository.getAllCorEmpWorkHisByEmpId(empId);
        if(domain.isPresent()){
            return CorEmpWorkHisDto.fromDomain(domain.get());
        }
        return null;
    }
}
