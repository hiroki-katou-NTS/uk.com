package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompany;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisCompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;


/**
* 明細書紐付け履歴（会社）: Finder
*/
@Stateless
public class StateCorrelationHisCompanyFinder
{

    @Inject
    private StateCorrelationHisCompanyRepository finder;

    public List<StateCorrelationHisCompanyDto> getStateCorrelationHisCompanyById(String cid){

        Optional<StateCorrelationHisCompany> stateCorrelationHisCompany = finder.getStateCorrelationHisCompanyById(cid);

        List<StateCorrelationHisCompanyDto> listStateCorrelationHisCompanyDto = new ArrayList<StateCorrelationHisCompanyDto>();
        if(stateCorrelationHisCompany.isPresent()){
            listStateCorrelationHisCompanyDto =  StateCorrelationHisCompanyDto.fromDomain(cid,stateCorrelationHisCompany.get());
        }
        return listStateCorrelationHisCompanyDto;
    }

}
