package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.Optional;
import java.util.List;

/**
* 雇用保険番号情報
*/
public interface EmpInsNumInfoRepository{
    Optional<EmpInsNumInfo> getEmpInsNumInfoById(String cid, String sid,String hisId);
    List<EmpInsNumInfo> getByCidAndHistIds(String cid, List<String> histIds);
}
