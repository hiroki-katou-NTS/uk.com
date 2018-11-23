package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
/**
* 明細書紐付け履歴（職位）: Finder
*/
public class StateCorrelationHisPositionFinder {

    @Inject
    private StateCorrelationHisPositionRepository finder;

    @Inject
    private SyJobTitleAdapter syJobTitleAdapter;

    @Inject
    private StateLinkSettingMasterFinder stateLinkSettingMasterFinder;

    public List<StateCorrelationHisPositionDto> getStateCorrelationHisPositionByCid() {
        String cId = AppContexts.user().companyId();
        StateCorrelationHisPosition hisPosition = finder.getStateCorrelationHisPositionByCid(cId).orElse(new StateCorrelationHisPosition(cId, new ArrayList<>()));
        return StateCorrelationHisPositionDto.fromDomain(hisPosition);
    }

    public StateLinkSettingDateDto getDateBase(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSettingDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(!stateLinkSettingDate.isPresent()){
            return null;
        }
        return StateLinkSettingDateDto.fromDomain(stateLinkSettingDate.get());
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth, GeneralDate date){
        String cId = AppContexts.user().companyId();
        List<JobTitle> listJobTitle = syJobTitleAdapter.findAll(cId, date);
        if(listJobTitle == null || listJobTitle.isEmpty()){
            return Collections.emptyList();
        }
        List<StateLinkSettingMaster> listStateLinkSettingMaster = finder.getStateLinkSettingMasterByHisId(cId, hisId);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = stateLinkSettingMasterFinder.getStateLinkSettingMaster(startYearMonth, listStateLinkSettingMaster) ;
        return listJobTitle.stream().map(i -> this.addCategoryName(i, listStateLinkSettingMasterDto)).collect(Collectors.toList());
    }

    private StateLinkSettingMasterDto addCategoryName(JobTitle job, List<StateLinkSettingMasterDto> stateLinkSettingMasterDto){
        Optional<StateLinkSettingMasterDto> jobTitle = stateLinkSettingMasterDto.stream().filter(i -> i.getMasterCode().equals(job.getJobTitleCode())).findFirst();
        if(jobTitle.isPresent()) {
        	return new StateLinkSettingMasterDto(
                    jobTitle.get().getHistoryID(),
                    jobTitle.get().getMasterCode(),
        			job.getJobTitleName(),
                    jobTitle.get().getSalaryCode(),
                    jobTitle.get().getBonusCode(),
                    jobTitle.get().getBonusName(),
                    jobTitle.get().getSalaryName()
        			);
        }
        return new StateLinkSettingMasterDto(
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
