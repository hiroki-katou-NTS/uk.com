package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEm;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEmRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
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
public class StateCorreHisEmpFinder {

    @Inject
    private StateCorreHisEmRepository stateCorreHisEmRepository;

    @Inject
    private SysEmploymentAdapter sysEmploymentAdapter;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;


    public List<StateCorreHisEmpDto> getStateCorrelationHisEmployeeById(String cid){
        List<StateCorreHisEmpDto> stateCorreHisEmpDto = new ArrayList<>();
        Optional<StateCorreHisEm> stateCorrelationHisEmployee = stateCorreHisEmRepository.getStateCorrelationHisEmployeeById(cid);
        if(stateCorrelationHisEmployee.isPresent()){
            stateCorreHisEmpDto = StateCorreHisEmpDto.fromDomain(cid,stateCorrelationHisEmployee.get());
        }
        return stateCorreHisEmpDto;
    }

    public List<StateCorreHisEmpSetDto> getStateLinkSettingMasterByHisId(String cid, String hisId, int startYearMonth){
        List<StateCorreHisEmpSetDto> stateCorreHisEmpSetDto = new ArrayList<>();
        List<EmpCdNameImportDto>  listEmployee = this.findEmploymentAll(cid);
        if(listEmployee == null || listEmployee.isEmpty()){
            return Collections.emptyList();
        }
        List<StateLinkSetMaster> listStateLinkSetMaster = stateCorreHisEmRepository.getStateLinkSettingMasterByHisId(cid,hisId);
        List<StatementLayout> listStatementLayout = this.getStatementLayout(cid,startYearMonth);
        if(!listStateLinkSetMaster.isEmpty()){
            stateCorreHisEmpSetDto = listEmployee.stream().map(item ->{
                String salaryCode = null;
                String salaryLayoutName = null;
                String bonusCode = null;
                String bonusLayoutName = null;
                Optional<StateLinkSetMaster> oStateLinkSettingMaster = listStateLinkSetMaster.stream().filter(o -> o.getMasterCode().v().equals(item.getCode())).findFirst();
                if(oStateLinkSettingMaster.isPresent()){
                    StateLinkSetMaster stateLinkSetMaster = oStateLinkSettingMaster.get();

                    if(stateLinkSetMaster.getSalaryCode().isPresent()){
                        Optional<StatementLayout> salaryLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().v().equals(stateLinkSetMaster.getSalaryCode().get().v())).findFirst();
                        if(salaryLayout.isPresent()){
                            salaryCode = salaryLayout.get().getStatementCode().v();
                            salaryLayoutName = salaryLayout.get().getStatementName().v();
                        }
                    }

                    if(stateLinkSetMaster.getBonusCode().isPresent()){
                        Optional<StatementLayout> bonusLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().v().equals(stateLinkSetMaster.getBonusCode().get().v())).findFirst();
                        if(bonusLayout.isPresent()){
                            bonusCode = bonusLayout.get().getStatementCode().v();
                            bonusLayoutName = bonusLayout.get().getStatementName().v();
                        }
                    }
                }

                return new StateCorreHisEmpSetDto(item.getCode(),item.getName(),hisId,item.getCode(),salaryCode,salaryLayoutName,bonusCode,bonusLayoutName);
            }).sorted(Comparator.comparing(StateCorreHisEmpSetDto::getCode)).collect(Collectors.toList());
        }else{
            stateCorreHisEmpSetDto = listEmployee.stream().map(item -> new StateCorreHisEmpSetDto(item.getCode(),item.getName(),hisId,null,null,null,null,null)).sorted(Comparator.comparing(StateCorreHisEmpSetDto::getCode)).collect(Collectors.toList());
        }

        return stateCorreHisEmpSetDto;
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
