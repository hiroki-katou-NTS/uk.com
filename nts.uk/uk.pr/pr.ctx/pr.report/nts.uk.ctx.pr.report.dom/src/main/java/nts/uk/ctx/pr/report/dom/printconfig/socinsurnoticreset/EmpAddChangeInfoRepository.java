package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import java.util.List;

/**
* 社員住所変更届情報
*/
public interface EmpAddChangeInfoRepository {
    List<EmpAddChangeInfo> getListEmpAddChange(List<String> empIds);
}
