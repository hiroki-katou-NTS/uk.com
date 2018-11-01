package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutHistRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
/**
* 明細書レイアウト履歴: Finder
*/
public class StatementLayoutHistFinder
{

    @Inject
    private StatementLayoutHistRepository finder;

    public List<StatementLayoutHistDto> getAllStatementLayoutHist(){
        return null;
        /*bởi vì chưa ai làm cạ*/

//        EmpInsurHis empInsurHis = empInsurHisRepository.getEmpInsurHisByCid(cId);
//        List<EmpInsurHisDto> empInsurHisDto = new ArrayList<EmpInsurHisDto>();
//        if (empInsurHis.getHistory() != null && !empInsurHis.getHistory().isEmpty()) {
//            empInsurHisDto = EmpInsurHisDto.fromDomain(empInsurHis);
//        }
//        return empInsurHisDto;
    }

}
