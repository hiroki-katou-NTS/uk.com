package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementNameLayoutHistDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHist;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け履歴（会社）: Finder
*/
@Stateless
public class StateCorrelationHisCompanyFinder {

    @Inject
    private StateCorrelationHisCompanyRepository stateCorrelationHisCompanyRepository;

    @Inject
    private StatementLayoutRepository statementLayoutRepository;

    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepository;


    public List<StateCorrelationHisCompanyDto> getStateCorrelationHisCompanyById(String cid){

        Optional<StateCorrelationHisCompany> stateCorrelationHisCompany = stateCorrelationHisCompanyRepository.getStateCorrelationHisCompanyById(cid);

        List<StateCorrelationHisCompanyDto> listStateCorrelationHisCompanyDto = new ArrayList<StateCorrelationHisCompanyDto>();
        if(stateCorrelationHisCompany.isPresent()){
            listStateCorrelationHisCompanyDto =  StateCorrelationHisCompanyDto.fromDomain(cid,stateCorrelationHisCompany.get());
        }
        return listStateCorrelationHisCompanyDto;
    }


    public Optional<StatementLayoutDto> getStateLinkSettingCompanyById(String cid, String hisId, int startYearMonth){
        Optional<StateLinkSettingCompany> stateLinkSettingCompany =  stateCorrelationHisCompanyRepository.getStateLinkSettingCompanyById(cid,hisId);
        StateLinkSettingCompanyDto stateLinkSettingCompanyDto;
        String salaryCode = null;
        String salaryLayoutName = null;
        String bonusCode = null;
        String bonusLayoutName = null;
        if(!stateLinkSettingCompany.isPresent()){
            return Optional.empty();
        }

        stateLinkSettingCompanyDto = StateLinkSettingCompanyDto.fromDomain(stateLinkSettingCompany.get());
        List<StatementNameLayoutHistDto> listStatementLayout = this.getAllStatementLayoutHis(startYearMonth);
        Optional<StatementNameLayoutHistDto> salaryLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getSalaryCode())).findFirst();
        if(salaryLayout.isPresent()){
            salaryCode = salaryLayout.get().getStatementCode();
            salaryLayoutName = salaryLayout.get().getStatementName();
        }
        Optional<StatementNameLayoutHistDto> bonusLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().equals(stateLinkSettingCompanyDto.getBonusCode())).findFirst();
        if(bonusLayout.isPresent()){
            bonusCode = bonusLayout.get().getStatementCode();
            bonusLayoutName = bonusLayout.get().getStatementName();
        }

        return Optional.of(new StatementLayoutDto(hisId,
                salaryCode,
                salaryLayoutName,
                bonusCode,
                bonusLayoutName));

    }

    public List<StatementNameLayoutHistDto> getAllStatementLayoutHis(int startYearMonth) {
        String cid = AppContexts.user().companyId();
        List<StatementNameLayoutHistDto> result = new ArrayList<>();
        List<StatementLayoutHist> listStatementLayoutHistory = statementLayoutHistRepository.getAllStatementLayoutHistByCid(cid, startYearMonth);
        List<StatementLayout> listStatementLayout = statementLayoutRepository.getStatementLayoutByCId(cid);
        listStatementLayoutHistory.forEach(item -> {
            result.add(new StatementNameLayoutHistDto(item.getCid(), item.getStatementCode().v(),
                    listStatementLayout.stream().filter(elementToSearch -> elementToSearch.getStatementCode().v().equals(item.getStatementCode().v())).findFirst().get().getStatementName().v(),
                    item.getHistory().get(0).identifier(),
                    item.getHistory().get(0).start().v(),
                    item.getHistory().get(0).end().v()
            ));
        });
        return result;
    }

}
