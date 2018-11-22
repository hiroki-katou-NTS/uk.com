package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistDto;

import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementNameLayoutHistDto;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.SysEmploymentAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpCdNameImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployeeRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMasterRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

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

    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepository;

    public List<StateCorrelationHisEmployeeDto> getStateCorrelationHisEmployeeById(String cid){
        List<StateCorrelationHisEmployeeDto> stateCorrelationHisEmployeeDto = new ArrayList<>();
        Optional<StateCorrelationHisEmployee> stateCorrelationHisEmployee = finder.getStateCorrelationHisEmployeeById(cid);
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
        List<StateLinkSettingMaster> listStateLinkSettingMaster = stateLinkSettingMasterRepository.getStateLinkSettingMasterByHisId(hisId);
        List<StatementNameLayoutHistDto> listStatementLayout = this.getAllStatementLayoutHis(startYearMonth);
        if(listStateLinkSettingMaster.size() > 0){
            stateCorrelationHisEmployeeSettingDto = listEmployee.stream().map(item ->{
                String salaryCode = null;
                String salaryLayoutName = null;
                String bonusCode = null;
                String bonusLayoutName = null;
                StateLinkSettingMaster stateLinkSettingMaster = listStateLinkSettingMaster.stream().filter(o -> o.getMasterCode().v().equals(item.getCode())).findFirst().get();
                Optional<StatementNameLayoutHistDto> salaryLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().equals(stateLinkSettingMaster.getSalaryCode().get().v())).findFirst();
                if(salaryLayout.isPresent()){
                    salaryCode = salaryLayout.get().getStatementCode();
                    salaryLayoutName = salaryLayout.get().getStatementName();
                }

                Optional<StatementNameLayoutHistDto> bonusLayout = listStatementLayout.stream().filter(o ->o.getStatementCode().equals(stateLinkSettingMaster.getBonusCode().get().v())).findFirst();
                if(bonusLayout.isPresent()){
                    bonusCode = bonusLayout.get().getStatementCode();
                    bonusLayoutName = bonusLayout.get().getStatementName();
                }

                return new StateCorrelationHisEmployeeSettingDto(item.getCode(),item.getName(),stateLinkSettingMaster.getHistoryID(),stateLinkSettingMaster.getMasterCode().v(),salaryCode,salaryLayoutName,bonusCode,bonusLayoutName);
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

    public List<StatementNameLayoutHistDto> getAllStatementLayoutHis(int startYearMonth) {
        String cid = AppContexts.user().companyId();
        List<StatementNameLayoutHistDto> result = new ArrayList<>();
        List<StatementLayoutHist> listStatementLayoutHistory = statementLayoutHistRepository.getAllStatementLayoutHistByCid(cid, startYearMonth);
        List<StatementLayout> listStatementLayout = statementLayoutRepository.getStatementLayoutByCId(cid);
        listStatementLayoutHistory.forEach(item -> {
            String name = listStatementLayout.stream().filter(elementToSearch -> elementToSearch.getStatementCode().v().equals(item.getStatementCode().v())).findFirst().get().getStatementName().v();
            result.add(new StatementNameLayoutHistDto(item.getCid(), item.getStatementCode().v(),
                    name,
                    item.getHistory().get(0).identifier(),
                    item.getHistory().get(0).start().v(),
                    item.getHistory().get(0).end().v()
            ));
        });
        return result;
    }

}
