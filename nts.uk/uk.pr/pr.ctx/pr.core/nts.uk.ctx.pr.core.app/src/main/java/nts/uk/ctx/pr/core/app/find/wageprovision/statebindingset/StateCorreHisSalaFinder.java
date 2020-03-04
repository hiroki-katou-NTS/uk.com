package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSala;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSalaRepository;
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
* 明細書紐付け履歴（給与分類）: Finder
*/
public class StateCorreHisSalaFinder {

    @Inject
    private StateCorreHisSalaRepository finder;

    @Inject
    private StateLinkSetMasterFinder stateLinkSetMasterFinder;


    @Inject
    private SalaryClassificationInformationRepository salaryClassificationInformationRepository;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;

    public List<StateCorreHisSalaDto> getStateCorrelationHisSalaryByCid(){
        String cId = AppContexts.user().companyId();
        StateCorreHisSala stateCorreHisSala = finder.getStateCorrelationHisSalaryByCid(cId).orElse(new StateCorreHisSala(cId, new ArrayList<>()));
        return StateCorreHisSalaDto.fromDomain(stateCorreHisSala);
    }

    public List<StateLinkSetMasterDto> getStateLinkSettingMaster(String hisId, int startYearMonth){
        String cId = AppContexts.user().companyId();
        List<SalaryClassificationInformation> salaryClassificationInformation = salaryClassificationInformationRepository.getAllSalaryClassificationInformation(cId);
        if(salaryClassificationInformation == null || salaryClassificationInformation.size() == 0) {
            Collections.emptyList();
        }
        List<StateLinkSetMaster> listStateLinkSetMaster =  finder.getStateLinkSettingMasterByHisId(cId, hisId);
        List<StateLinkSetMasterDto> listStateLinkSetMasterDto = stateLinkSetMasterFinder.getStateLinkSettingMaster(startYearMonth, listStateLinkSetMaster);
        return salaryClassificationInformation.stream().map(i -> this.addCategoryName(i, listStateLinkSetMasterDto,cId,startYearMonth)).collect(Collectors.toList());
    }

    private StateLinkSetMasterDto addCategoryName(SalaryClassificationInformation information, List<StateLinkSetMasterDto> stateLinkSetMasterDto,String cId, int startYearMonth){
        Optional<StateLinkSetMasterDto> settingMaster = stateLinkSetMasterDto.stream().filter(i -> i.getMasterCode().equals(information.getSalaryClassificationCode().v())).findFirst();
        List<StatementLayout> listStatementLayout = statementLayoutFinder.getStatement(cId,startYearMonth);
        if(settingMaster.isPresent()) {
            String salaryCode = null;
            String salaryLayoutName = null;
            String bonusCode = null;
            String bonusLayoutName = null;
            Optional<StatementLayout> salaryLayout = settingMaster.get().getSalaryCode() == null ? Optional.empty() : listStatementLayout.stream().filter(x->x.getStatementCode().equals(settingMaster.get().getSalaryCode())).findFirst();
            if(salaryLayout.isPresent()){
                salaryCode = salaryLayout.get().getStatementCode().v();
                salaryLayoutName = salaryLayout.get().getStatementName().v();
            }

            Optional<StatementLayout> bonusLayout = settingMaster.get().getBonusCode() == null ? Optional.empty() : listStatementLayout.stream().filter(x->x.getStatementCode().equals(settingMaster.get().getBonusCode())).findFirst();
            if(bonusLayout.isPresent()){
                bonusCode = bonusLayout.get().getStatementCode().v();
                bonusLayoutName = bonusLayout.get().getStatementName().v();
            }
            return new StateLinkSetMasterDto(
                    settingMaster.get().getHistoryID(),
                    settingMaster.get().getMasterCode(),
                    information.getSalaryClassificationName().v(),
                    salaryCode,
                    bonusCode,
                    bonusLayoutName,
                    salaryLayoutName
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
