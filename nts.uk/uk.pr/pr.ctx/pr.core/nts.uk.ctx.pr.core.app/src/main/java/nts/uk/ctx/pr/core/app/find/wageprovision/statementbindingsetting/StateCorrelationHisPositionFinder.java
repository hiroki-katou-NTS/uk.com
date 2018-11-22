package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.JobTitle;
import nts.uk.ctx.pr.core.dom.adapter.employee.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.HEAD;

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
        Optional<StateCorrelationHisPosition> hisPosition = finder.getStateCorrelationHisPositionByCid(cId);
        if (!hisPosition.isPresent()) {
            Collections.emptyList();
        }
        return StateCorrelationHisPositionDto.fromDomain(hisPosition.get());
    }

    public StateLinkSettingDateDto getDateBase(String hisId){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSettingDate>  stateLinkSettingDate = finder.getStateLinkSettingDateById(cId,hisId);
        if(stateLinkSettingDate.isPresent()) {
            return StateLinkSettingDateDto.fromDomain(stateLinkSettingDate.get());
        }
        return null;
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth, GeneralDate date){
        String cId = AppContexts.user().companyId();
        List<JobTitle> listJobTitle = syJobTitleAdapter.findAll(cId, date);
        if(listJobTitle == null || listJobTitle.isEmpty()){
            return Collections.emptyList();
        }
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = stateLinkSettingMasterFinder.getStateLinkSettingMaster(hisId, startYearMonth) ;
        return listJobTitle.stream().map(i -> this.addCategoryName(i, listStateLinkSettingMasterDto)).collect(Collectors.toList());
    }

    private StateLinkSettingMasterDto addCategoryName(JobTitle job, List<StateLinkSettingMasterDto> stateLinkSettingMasterDto){
        Optional<StateLinkSettingMasterDto> jobtitle = stateLinkSettingMasterDto.stream().filter(i -> i.getMasterCode().equals(job.getJobTitleCode())).findFirst();
        if(jobtitle.isPresent()) {
        	return new StateLinkSettingMasterDto(
        			jobtitle.get().getHistoryID(), 
        			jobtitle.get().getMasterCode(),
        			job.getJobTitleName(),
        			jobtitle.get().getSalaryCode(),
        			jobtitle.get().getBonusCode(),
        			jobtitle.get().getBonusName(),
        			jobtitle.get().getSalaryName()
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
