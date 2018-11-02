package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け履歴（雇用）: Finder
*/
@Stateless
public class StateCorrelationHisEmployeeFinder {

    @Inject
    private StateCorrelationHisEmployeeRepository finder;

    @Inject
    private SysEmploymentAdapter sysEmploymentAdapter;

    public List<StateCorrelationHisEmployeeDto> getStateCorrelationHisEmployeeById(String cid){
        List<StateCorrelationHisEmployeeDto> stateCorrelationHisEmployeeDto = new ArrayList<>();
        Optional<StateCorrelationHisEmployee> stateCorrelationHisEmployee = finder.getStateCorrelationHisEmployeeById(cid);
        if(stateCorrelationHisEmployee.isPresent()){
            stateCorrelationHisEmployeeDto = StateCorrelationHisEmployeeDto.fromDomain(cid,stateCorrelationHisEmployee.get());
        }
        return stateCorrelationHisEmployeeDto;
    }

    public List<EmpCdNameImportDto> findEmploymentAll(String cid){
        List<EmpCdNameImport> empCdNameImport = sysEmploymentAdapter.findAll(cid);
        return EmpCdNameImportDto.fromDomain(empCdNameImport);
    }


}
