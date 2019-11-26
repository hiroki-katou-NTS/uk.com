package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCom;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisComRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetCom;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayout;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
* 明細書紐付け履歴（会社）: Finder
*/
@Stateless
public class StateCorreHisComFinder {

    @Inject
    private StateCorreHisComRepository stateCorreHisComRepository;

    @Inject
    private StatementLayoutRepository statementLayoutFinder;


    public List<StateCorreHisComDto> getStateCorrelationHisCompanyById(String cid){

        Optional<StateCorreHisCom> stateCorrelationHisCompany = stateCorreHisComRepository.getStateCorrelationHisCompanyById(cid);

        List<StateCorreHisComDto> listStateCorreHisComDto = new ArrayList<StateCorreHisComDto>();
        if(stateCorrelationHisCompany.isPresent()){
            listStateCorreHisComDto =  StateCorreHisComDto.fromDomain(cid,stateCorrelationHisCompany.get());
        }
        return listStateCorreHisComDto;
    }


    public Optional<StatementLayoutDto> getStateLinkSettingCompanyById(String cid, String hisId, int startYearMonth){
        Optional<StateLinkSetCom> stateLinkSettingCompany =  stateCorreHisComRepository.getStateLinkSettingCompanyById(cid,hisId);
        StateLinkSetComDto stateLinkSetComDto;
        String salaryCode = null;
        String salaryLayoutName = null;
        String bonusCode = null;
        String bonusLayoutName = null;
        if(!stateLinkSettingCompany.isPresent()){
            return Optional.empty();
        }

        stateLinkSetComDto = StateLinkSetComDto.fromDomain(stateLinkSettingCompany.get());
        List<StatementLayout> listStatementLayout = this.getStatementLayout(cid,startYearMonth);
        if(!listStatementLayout.isEmpty()){
            Optional<StatementLayout> salaryLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().v().equals(stateLinkSetComDto.getSalaryCode())).findFirst();
            if(salaryLayout.isPresent()){
                salaryCode = salaryLayout.get().getStatementCode().v();
                salaryLayoutName = salaryLayout.get().getStatementName().v();
            }
            Optional<StatementLayout> bonusLayout = listStatementLayout.stream().filter(o -> o.getStatementCode().v().equals(stateLinkSetComDto.getBonusCode())).findFirst();
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
