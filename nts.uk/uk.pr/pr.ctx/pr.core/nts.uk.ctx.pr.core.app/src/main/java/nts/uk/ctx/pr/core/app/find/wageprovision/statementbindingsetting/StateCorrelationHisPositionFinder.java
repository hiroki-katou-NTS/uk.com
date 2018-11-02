package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;
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
    private StatementLayoutRepository statementLayoutFinder;

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private StateLinkSettingDateRepository stateLinkSettingDateFinder;

    public List<StateCorrelationHisPositionDto> getStateCorrelationHisPositionByCid() {
        String cId = AppContexts.user().companyId();
        Optional<StateCorrelationHisPosition> hisPosition = finder.getStateCorrelationHisPositionByCid(cId);
        if (!hisPosition.isPresent()) {
            return null;
        }
        return StateCorrelationHisPositionDto.fromDomain(hisPosition.get());
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        Optional<StateLinkSettingDate> stateLinkSettingDate = stateLinkSettingDateFinder.getStateLinkSettingDateById(hisId);
        if(!stateLinkSettingDate.isPresent()) {
            return null;
        }
        List<JobTitle> listJobTitle = syJobTitleAdapter.findAll(cId, stateLinkSettingDate.get().getDate());
        if(listJobTitle == null || listJobTitle.isEmpty()){
            return null;
        }
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSettingMaster>  listStateLinkSettingMaster = masterFinder.getStateLinkSettingMasterByHisId(hisId);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = listStateLinkSettingMaster.stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList());
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
    			jobtitle.get().getHistoryID(), 
    			jobtitle.get().getMasterCode(),
    			job.getJobTitleName(),
    			null,
    			null,
    			null,
    			null
    			);
    }
    

}
