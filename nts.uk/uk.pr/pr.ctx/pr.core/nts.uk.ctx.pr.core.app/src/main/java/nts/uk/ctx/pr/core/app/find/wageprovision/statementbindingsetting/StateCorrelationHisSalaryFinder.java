package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformationRepository;
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
* 明細書紐付け履歴（給与分類）: Finder
*/
public class StateCorrelationHisSalaryFinder {

    @Inject
    private StateCorrelationHisSalaryRepository finder;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    @Inject
    private StateLinkSettingMasterRepository masterFinder;

    @Inject
    private SalaryClassificationInformationRepository salaryClassificationInformationRepository;

    public List<StateCorrelationHisSalaryDto> getStateCorrelationHisSalaryByCid(){
        String cId = AppContexts.user().companyId();
        Optional<StateCorrelationHisSalary> stateCorrelationHisSalary = finder.getStateCorrelationHisSalaryByCid(cId);
        if(!stateCorrelationHisSalary.isPresent()) {
            return null;
        }
        return StateCorrelationHisSalaryDto.fromDomain(stateCorrelationHisSalary.get());
    }

    public List<StateLinkSettingMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<SalaryClassificationInformation> salaryClassificationInformation = salaryClassificationInformationRepository.getAllSalaryClassificationInformation(cId);
        List<StatementLayout> statementLayout = statementLayoutFinder.getStatement(cId, startYearMonth);
        List<StateLinkSettingMaster>  listStateLinkSettingMaster = masterFinder.getStateLinkSettingMasterByHisId(hisId);
        List<StateLinkSettingMasterDto> listStateLinkSettingMasterDto = listStateLinkSettingMaster.stream()
                .map(i -> StateLinkSettingMasterDto.fromDomain(i, statementLayout)).collect(Collectors.toList());
        return salaryClassificationInformation.stream().map(i -> this.addCategoryName(i, listStateLinkSettingMasterDto)).collect(Collectors.toList());
    }

    private StateLinkSettingMasterDto addCategoryName(SalaryClassificationInformation information, List<StateLinkSettingMasterDto> stateLinkSettingMasterDto){
        Optional<StateLinkSettingMasterDto> settingMaster = stateLinkSettingMasterDto.stream().filter(i -> i.getMasterCode().equals(information.getSalaryClassificationCode().v())).findFirst();
        if(settingMaster.isPresent()) {
            return new StateLinkSettingMasterDto(
                    settingMaster.get().getHistoryID(),
                    settingMaster.get().getMasterCode(),
                    information.getSalaryClassificationName().v(),
                    settingMaster.get().getSalaryCode(),
                    settingMaster.get().getBonusCode(),
                    settingMaster.get().getBonusName(),
                    settingMaster.get().getSalaryName()
            );
        }
        return new StateLinkSettingMasterDto(
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
