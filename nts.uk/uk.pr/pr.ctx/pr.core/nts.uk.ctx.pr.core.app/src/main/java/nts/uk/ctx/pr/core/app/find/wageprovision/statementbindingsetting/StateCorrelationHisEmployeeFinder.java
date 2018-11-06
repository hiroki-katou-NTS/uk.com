package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.app.find.laborinsurance.OccAccInsurBusDto;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

import java.util.ArrayList;
import java.util.Comparator;
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

    @Inject
    private StateLinkSettingMasterRepository stateLinkSettingMasterRepository;

    @Inject
    private StatementLayoutRepository statementLayoutRepository;

    public List<StateCorrelationHisEmployeeDto> getStateCorrelationHisEmployeeById(String cid){
        List<StateCorrelationHisEmployeeDto> stateCorrelationHisEmployeeDto = new ArrayList<>();
        Optional<StateCorrelationHisEmployee> stateCorrelationHisEmployee = finder.getStateCorrelationHisEmployeeById(cid);
        if(stateCorrelationHisEmployee.isPresent()){
            stateCorrelationHisEmployeeDto = StateCorrelationHisEmployeeDto.fromDomain(cid,stateCorrelationHisEmployee.get());
        }
        return stateCorrelationHisEmployeeDto;
    }

    public List<StateCorrelationHisEmployeeSettingDto> getStateLinkSettingMasterByHisId(String cid,String hisId){
        List<StateCorrelationHisEmployeeSettingDto> stateCorrelationHisEmployeeSettingDto = new ArrayList<>();
        List<StateLinkSettingMaster> stateLinkSettingMaster = stateLinkSettingMasterRepository.getStateLinkSettingMasterByHisId(hisId);
        List<EmpCdNameImportDto>  listEmployee = this.findEmploymentAll(cid);
        if(stateLinkSettingMaster.size() > 0){
            stateCorrelationHisEmployeeSettingDto = stateLinkSettingMaster.stream().map(item ->{
                String salaryLayoutName = null;
                String bonusLayoutName = null;
                EmpCdNameImportDto employee = listEmployee.stream().filter(o ->o.getCode().equals(item.getMasterCode().v())).findFirst().get();

                List<StatementLayout> listSatementLayout = statementLayoutRepository.getAllStatementLayout();
                Optional<StatementLayout> salaryLayout = listSatementLayout.stream().filter(o -> o.getStatementCode().equals(item.getSalaryCode().get().v())).findFirst();
                if(salaryLayout.isPresent()){
                    salaryLayoutName = salaryLayout.get().getStatementName().v();
                }
                Optional<StatementLayout> bonusLayout = listSatementLayout.stream().filter(o -> o.getStatementCode().equals(item.getBonusCode().get().v())).findFirst();
                if(bonusLayout.isPresent()){
                    bonusLayoutName = bonusLayout.get().getStatementName().v();
                }
                return new StateCorrelationHisEmployeeSettingDto(employee.getCode(),employee.getName(),item.getHistoryID(),item.getMasterCode().v(),item.getSalaryCode().get().v(),salaryLayoutName,item.getBonusCode().get().v(),bonusLayoutName);
            }).sorted(Comparator.comparing(StateCorrelationHisEmployeeSettingDto::getCode)).collect(Collectors.toList());
        }else{
            stateCorrelationHisEmployeeSettingDto = listEmployee.stream().map(item ->{
                return new StateCorrelationHisEmployeeSettingDto(item.getCode(),item.getName(),hisId,null,null,null,null,null);
            }).sorted(Comparator.comparing(StateCorrelationHisEmployeeSettingDto::getCode)).collect(Collectors.toList());
        }
        return stateCorrelationHisEmployeeSettingDto;
    }
    public List<EmpCdNameImportDto> findEmploymentAll(String cid){
        List<EmpCdNameImport> empCdNameImport = sysEmploymentAdapter.findAll(cid);
        return EmpCdNameImportDto.fromDomain(empCdNameImport);
    }


}
