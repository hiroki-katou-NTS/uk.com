package nts.uk.ctx.pr.core.dom.emprsdttaxinfo;

import java.util.Optional;
import java.util.List;

/**
 * 社員住民税納付先情報
 */
public interface EmployeeResidentTaxPayeeInfoRepository {

    List<EmployeeResidentTaxPayeeInfo> getAllEmployeeResidentTaxPayeeInfo();

    Optional<EmployeeResidentTaxPayeeInfo> getEmployeeResidentTaxPayeeInfoById(String sid, String histId);

    void add(EmployeeResidentTaxPayeeInfo domain);

    void update(EmployeeResidentTaxPayeeInfo domain);

    void remove(String sid, String histId);

}
