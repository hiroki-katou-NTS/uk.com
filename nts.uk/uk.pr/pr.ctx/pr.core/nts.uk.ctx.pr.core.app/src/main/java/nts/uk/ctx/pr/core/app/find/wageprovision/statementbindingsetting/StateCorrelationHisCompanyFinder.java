package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

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
    private StatementLayoutRepository statementLayoutFinder;


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
        List<StatementLayout> listStatementLayout = this.getStatementLayout(cid,startYearMonth);
        if(!listStatementLayout.isEmpty()){
            Optional<StatementLayout> salaryLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().v().equals(stateLinkSettingCompanyDto.getSalaryCode())).findFirst();
            if(salaryLayout.isPresent()){
                salaryCode = salaryLayout.get().getStatementCode().v();
                salaryLayoutName = salaryLayout.get().getStatementName().v();
            }
            Optional<StatementLayout> bonusLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().v().equals(stateLinkSettingCompanyDto.getBonusCode())).findFirst();
            if(bonusLayout.isPresent()){
                bonusCode = bonusLayout.get().getStatementCode().v();
                bonusLayoutName = bonusLayout.get().getStatementName().v();
            }
        }
        return Optional.of(new StatementLayoutDto(hisId,
                salaryCode,
                salaryLayoutName,
                bonusCode,
                bonusLayoutName));

    }

    private  List<StatementLayout> getStatementLayout(String cid, int startYearMonth){
        List<StatementLayout> statementLayout  = statementLayoutFinder.getStatement(cid,startYearMonth);
        return statementLayout;
    }

}
