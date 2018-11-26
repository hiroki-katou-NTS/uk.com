package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorreHisSala;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorreHisSalaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSetMaster;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
/**
* 明細書紐付け履歴（給与分類）: Finder
*/
public class StateCorreHisSalaFinder {

    @Inject
    private StateCorreHisSalaRepository finder;

    @Inject
    private StateLinkSetMasterFinder stateLinkSetMasterFinder;


    @Inject
    private SalaryClassificationInformationRepository salaryClassificationInformationRepository;

    public List<StateCorreHisSalaDto> getStateCorrelationHisSalaryByCid(){
        String cId = AppContexts.user().companyId();
        StateCorreHisSala stateCorreHisSala = finder.getStateCorrelationHisSalaryByCid(cId).orElse(new StateCorreHisSala(cId, new ArrayList<>()));
        return StateCorreHisSalaDto.fromDomain(stateCorreHisSala);
    }

    public List<StateLinkSetMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<SalaryClassificationInformation> salaryClassificationInformation = salaryClassificationInformationRepository.getAllSalaryClassificationInformation(cId);
        List<StateLinkSetMaster> listStateLinkSetMaster =  finder.getStateLinkSettingMasterByHisId(cId, hisId);
        List<StateLinkSetMasterDto> listStateLinkSetMasterDto = stateLinkSetMasterFinder.getStateLinkSettingMaster(startYearMonth, listStateLinkSetMaster);
        return salaryClassificationInformation.stream().map(i -> this.addCategoryName(i, listStateLinkSetMasterDto)).collect(Collectors.toList());
    }

    private StateLinkSetMasterDto addCategoryName(SalaryClassificationInformation information, List<StateLinkSetMasterDto> stateLinkSetMasterDto){
        Optional<StateLinkSetMasterDto> settingMaster = stateLinkSetMasterDto.stream().filter(i -> i.getMasterCode().equals(information.getSalaryClassificationCode().v())).findFirst();
        if(settingMaster.isPresent()) {
            return new StateLinkSetMasterDto(
                    settingMaster.get().getHistoryID(),
                    settingMaster.get().getMasterCode(),
                    information.getSalaryClassificationName().v(),
                    settingMaster.get().getSalaryCode(),
                    settingMaster.get().getBonusCode(),
                    settingMaster.get().getBonusName(),
                    settingMaster.get().getSalaryName()
            );
        }
        return new StateLinkSetMasterDto(
                null,
                information.getSalaryClassificationCode().v(),
                information.getSalaryClassificationName().v(),
                null,
                null,
                null,
                null
        );
    }

}
