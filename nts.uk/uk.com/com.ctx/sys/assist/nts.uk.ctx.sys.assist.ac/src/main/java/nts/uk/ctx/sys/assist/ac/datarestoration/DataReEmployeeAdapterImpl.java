package nts.uk.ctx.sys.assist.ac.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.EmployeeDataMngInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.sys.assist.dom.datarestoration.EmployeeDataReInfoImport;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataReEmployeeAdapter;

@Stateless
public class DataReEmployeeAdapterImpl implements DataReEmployeeAdapter {
     
    @Inject
    private SyEmployeePub syEmployeePub;

    @Override
    public Optional<EmployeeDataReInfoImport> getSdataMngInfo(String sid) {
        Optional<EmployeeDataMngInfoExport> importData = this.syEmployeePub.getSdataMngInfo(sid);
        if (importData.isPresent()) {
            return Optional.of(new EmployeeDataReInfoImport(importData.get().getCompanyId(),
                    importData.get().getPersonId(), importData.get().getEmployeeId(),
                    importData.get().getEmployeeCode(), importData.get().getDeleteDateTemporary(),
                    importData.get().getRemoveReason(), importData.get().getExternalCode()));
        }
        return Optional.empty();
    }
}