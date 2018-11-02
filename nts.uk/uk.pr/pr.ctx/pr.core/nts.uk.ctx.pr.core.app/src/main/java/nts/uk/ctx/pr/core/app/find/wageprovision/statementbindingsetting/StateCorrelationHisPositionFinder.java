package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting.*;
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
        List<JobTitle> listJobTitle = syJobTitleAdapter.findAll(stateLinkSettingDate.get().getDate());
        if(listJobTitle == null || listJobTitle.isEmpty()){
            return null;
        }
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSettingMaster>  listStateLinkSettingMaster = masterFinder.getStateLinkSettingMasterByHisId(hisId);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = listStateLinkSettingMaster.stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList()).stream()
        return listJobTitle.stream().filter(i -> i.getJobTitleCode().equals());
    private List<StateLinkSettingMasterDto> addCategoryName(JobTitle job, List<StateLinkSettingMasterDto> stateLinkSettingMasterDto){
            return null;

        }
    }


}
