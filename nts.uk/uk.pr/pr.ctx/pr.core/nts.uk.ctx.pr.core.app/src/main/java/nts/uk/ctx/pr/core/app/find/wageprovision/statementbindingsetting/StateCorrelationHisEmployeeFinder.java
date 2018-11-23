package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;


/**
* 明細書紐付け履歴（雇用）: Finder
*/
@Stateless
public class StateCorrelationHisEmployeeFinder {

    @Inject
    private StateCorrelationHisEmployeeRepository stateCorrelationHisEmployeeRepository;

    @Inject
    private SysEmploymentAdapter sysEmploymentAdapter;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;


    public List<StateCorrelationHisEmployeeDto> getStateCorrelationHisEmployeeById(String cid){
        List<StateCorrelationHisEmployeeDto> stateCorrelationHisEmployeeDto = new ArrayList<>();
        Optional<StateCorrelationHisEmployee> stateCorrelationHisEmployee = stateCorrelationHisEmployeeRepository.getStateCorrelationHisEmployeeById(cid);
        if(stateCorrelationHisEmployee.isPresent()){
            stateCorrelationHisEmployeeDto = StateCorrelationHisEmployeeDto.fromDomain(cid,stateCorrelationHisEmployee.get());
        }
        return stateCorrelationHisEmployeeDto;
    }

    public List<StateCorrelationHisEmployeeSettingDto> getStateLinkSettingMasterByHisId(String cid,String hisId,int startYearMonth){
        List<StateCorrelationHisEmployeeSettingDto> stateCorrelationHisEmployeeSettingDto = new ArrayList<>();
        List<EmpCdNameImportDto>  listEmployee = this.findEmploymentAll(cid);
        if(listEmployee == null || listEmployee.isEmpty()){
            return stateCorrelationHisEmployeeSettingDto;
        }
        List<StateLinkSettingMaster> listStateLinkSettingMaster = stateCorrelationHisEmployeeRepository.getStateLinkSettingMasterByHisId(cid,hisId);
        List<StatementLayout> listStatementLayout = this.getStatementLayout(cid,startYearMonth);
        if(!listStateLinkSettingMaster.isEmpty()){
            stateCorrelationHisEmployeeSettingDto = listEmployee.stream().map(item ->{
                String salaryCode = null;
                String salaryLayoutName = null;
                String bonusCode = null;
                String bonusLayoutName = null;
                Optional<StateLinkSettingMaster> oStateLinkSettingMaster = listStateLinkSettingMaster.stream().filter(o -> o.getMasterCode().v().equals(item.getCode())).findFirst();
                if(oStateLinkSettingMaster.isPresent()){
                    StateLinkSettingMaster stateLinkSettingMaster = oStateLinkSettingMaster.get();

                    if(stateLinkSettingMaster.getSalaryCode().isPresent()){
                        Optional<StatementLayout> salaryLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().v().equals(stateLinkSettingMaster.getSalaryCode().get().v())).findFirst();
                        if(salaryLayout.isPresent()){
                            salaryCode = salaryLayout.get().getStatementCode().v();
                            salaryLayoutName = salaryLayout.get().getStatementName().v();
                        }
                    }

                    if(stateLinkSettingMaster.getBonusCode().isPresent()){
                        Optional<StatementLayout> bonusLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().v().equals(stateLinkSettingMaster.getBonusCode().get().v())).findFirst();
                        if(bonusLayout.isPresent()){
                            bonusCode = bonusLayout.get().getStatementCode().v();
                            bonusLayoutName = bonusLayout.get().getStatementName().v();
                        }
                    }
                }

                return new StateCorrelationHisEmployeeSettingDto(item.getCode(),item.getName(),hisId,item.getCode(),salaryCode,salaryLayoutName,bonusCode,bonusLayoutName);
            }).sorted(Comparator.comparing(StateCorrelationHisEmployeeSettingDto::getCode)).collect(Collectors.toList());
        }else{
            stateCorrelationHisEmployeeSettingDto = listEmployee.stream().map(item -> new StateCorrelationHisEmployeeSettingDto(item.getCode(),item.getName(),hisId,null,null,null,null,null)).sorted(Comparator.comparing(StateCorrelationHisEmployeeSettingDto::getCode)).collect(Collectors.toList());
        }

        return stateCorrelationHisEmployeeSettingDto;
    }

    public List<EmpCdNameImportDto> findEmploymentAll(String cid){
        List<EmpCdNameImport> empCdNameImport = sysEmploymentAdapter.findAll(cid);
        return EmpCdNameImportDto.fromDomain(empCdNameImport);
    }

    private  List<StatementLayout> getStatementLayout(String cid, int startYearMonth){
        List<StatementLayout> statementLayout  = statementLayoutFinder.getStatement(cid,startYearMonth);
        return statementLayout;
    }

}
