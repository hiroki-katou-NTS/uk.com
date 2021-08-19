package nts.uk.ctx.at.function.ac.employment;

import nts.uk.ctx.at.function.dom.adapter.employment.EmpRankInfoAdapter;
import nts.uk.ctx.at.function.dom.adapter.employment.EmpRankInfoImport;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.employeeinfo.EmpRankInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmpRankInfoAdapterImp implements EmpRankInfoAdapter {

    @Inject
    private EmpRankInfoPub empRankInfoPub;


    /**
     * @param List<社員ID>
     * @return List<社員ランク情報Imported>
     */
    @Override
    public List<EmpRankInfoImport> get(List<String> listEmpId) {
        return empRankInfoPub.get(listEmpId).stream()
                .map(x -> new EmpRankInfoImport(
                        x.getEmpId(),
                        x.getRankCode(),
                        x.getRankSymbol()
                )).collect(Collectors.toList());
    }
}
