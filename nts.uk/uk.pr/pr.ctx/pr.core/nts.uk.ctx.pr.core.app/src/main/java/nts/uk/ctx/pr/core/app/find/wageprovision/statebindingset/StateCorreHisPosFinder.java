package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPo;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPoRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 明細書紐付け履歴（職位）: Finder
*/
public class StateCorreHisPosFinder {

    @Inject
    private StateCorreHisPoRepository finder;

    @Inject
    private SyJobTitleAdapter syJobTitleAdapter;

    @Inject
    private StateLinkSetMasterFinder stateLinkSetMasterFinder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateCorreHisPosDto> getStateCorrelationHisPositionByCid() {
        String cId = AppContexts.user().companyId();
        StateCorreHisPo hisPosition = finder.getStateCorrelationHisPositionByCid(cId).orElse(new StateCorreHisPo(cId, new ArrayList<>()));
        return StateCorreHisPosDto.fromDomain(hisPosition);
    }

    public StateLinkSetDateDto getDateBase(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSetDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(!stateLinkSettingDate.isPresent()){
            return null;
        }
        return StateLinkSetDateDto.fromDomain(stateLinkSettingDate.get());
    }

    public List<StateLinkSetMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth, GeneralDate date){
        String cId = AppContexts.user().companyId();
        List<JobTitle> listJobTitle = syJobTitleAdapter.findAll(cId, date);
        if(listJobTitle == null || listJobTitle.isEmpty()){
            return Collections.emptyList();
        }
        List<StateLinkSetMaster> listStateLinkSetMaster = finder.getStateLinkSettingMasterByHisId(cId, hisId);
        List<StateLinkSetMasterDto> listStateLinkSetMasterDto = stateLinkSetMasterFinder.getStateLinkSettingMaster(startYearMonth, listStateLinkSetMaster) ;
        return listJobTitle.stream().map(i -> this.addCategoryName(i, listStateLinkSetMasterDto,cId,startYearMonth)).collect(Collectors.toList());
    }

    private StateLinkSetMasterDto addCategoryName(JobTitle job, List<StateLinkSetMasterDto> stateLinkSetMasterDto, String cId, int startYearMonth){
        Optional<StateLinkSetMasterDto> jobTitle = stateLinkSetMasterDto.stream().filter(i -> i.getMasterCode().equals(job.getJobTitleCode())).findFirst();
        List<StatementLayout> listStatementLayout = statementLayoutFinder.getStatement(cId,startYearMonth);
        if(jobTitle.isPresent()) {
            String salaryCode = null;
            String salaryLayoutName = null;
            String bonusCode = null;
            String bonusLayoutName = null;

            Optional<StatementLayout> salaryLayout = jobTitle.get().getSalaryCode() == null ? Optional.empty() :
                    listStatementLayout.stream().filter(x ->x.getStatementCode().equals(jobTitle.get().getSalaryCode())).findFirst();
        	if(salaryLayout.isPresent()){
        	    salaryCode = salaryLayout.get().getStatementCode().v();
        	    salaryLayoutName = salaryLayout.get().getStatementName().v();
            }

            Optional<StatementLayout> bonusLayout = jobTitle.get().getBonusCode() == null ? Optional.empty() :
                    listStatementLayout.stream().filter(x -> x.getStatementCode().equals(jobTitle.get().getBonusCode())).findFirst();
            if(bonusLayout.isPresent()){
                bonusCode = bonusLayout.get().getStatementCode().v();
                bonusLayoutName = bonusLayout.get().getStatementName().v();
            }
            return new StateLinkSetMasterDto(
                    jobTitle.get().getHistoryID(),
                    jobTitle.get().getMasterCode(),
        			job.getJobTitleName(),
                    salaryCode,
                    bonusCode,
                    bonusLayoutName,
                    salaryLayoutName
        			);
        }
        return new StateLinkSetMasterDto(
                null,
                job.getJobTitleCode(),
    			job.getJobTitleName(),
    			null,
    			null,
    			null,
    			null
    			);
    }

}
