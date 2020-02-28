package nts.uk.ctx.pr.core.dom.emprsdttaxinfo.amountinfo;

import java.util.List;
import java.util.Optional;

/**
 * 社員住民税納付額情報
 */
public interface EmployeeResidentTaxPayAmountInfoRepository {
    Optional<EmployeeResidentTaxPayAmountInfo> getEmpRsdtTaxPayAmountInfoById(String sid, int year);

    List<EmployeeResidentTaxPayAmountInfo> getListEmpRsdtTaxPayAmountInfo(List<String> listSId, int year);

    void add(EmployeeResidentTaxPayAmountInfo domain);

    void addAll(List<EmployeeResidentTaxPayAmountInfo> domains);

    void update(EmployeeResidentTaxPayAmountInfo domain);

    void updateAll(List<EmployeeResidentTaxPayAmountInfo> domains);

    void remove(String sid, int year);

    void removeAll(List<String> sids, int year);
}
