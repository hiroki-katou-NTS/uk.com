package nts.uk.ctx.pr.core.ac.employee.employment;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employment.IEmploymentHistoryPub;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmploymentCodeAndPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmploymentHisOfEmployee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class IEmploymentHistoryImple implements IEmploymentHistoryAdapter {
    @Inject
    private IEmploymentHistoryPub mIEmploymentHistoryPub;

    @Override
    public List<EmploymentHisOfEmployee> getEmploymentHisBySid(String sID) {
        List<EmploymentHisOfEmployee> tempArray = new ArrayList<EmploymentHisOfEmployee>();
        mIEmploymentHistoryPub.getEmploymentHisBySid(sID).stream().forEach(i -> {
            tempArray.add(new EmploymentHisOfEmployee(i.getSId(),i.getStartDate(),i.getEndDate(),i.getEmploymentCD()));
        });
        return tempArray;
    }

    @Override
    public List<EmploymentHisExport> getEmploymentHistoryItem(String cid, GeneralDate baseDate) {
        List<EmploymentHisExport> tempArrayRoot = new ArrayList<EmploymentHisExport>();
        mIEmploymentHistoryPub.getEmploymentHistoryItem(cid,baseDate).stream().forEach(i -> {
            ArrayList<EmploymentCodeAndPeriod> tempArray = new ArrayList<EmploymentCodeAndPeriod>();
            i.lstEmpCodeandPeriod.stream().forEach(item ->{
                tempArray.add(new EmploymentCodeAndPeriod(item.getHistoryID(),item.getDatePeriod(),item.getEmploymentCode()));
            });
            tempArrayRoot.add(new EmploymentHisExport(i.getEmployeeId(),tempArray));
        });
        return tempArrayRoot;
    }

    @Override
    public Optional<EmploymentHisExport> getEmploymentHistory(String historyId, String employmentCode) {
        if( mIEmploymentHistoryPub.getEmploymentHistory(historyId,employmentCode).isPresent())
            return Optional.empty();
        return mIEmploymentHistoryPub.getEmploymentHistory(historyId,employmentCode).map(i -> {
            List<EmploymentCodeAndPeriod> tempArray = new ArrayList<EmploymentCodeAndPeriod>();
            i.lstEmpCodeandPeriod.stream().forEach(item ->{
                 tempArray.add(new EmploymentCodeAndPeriod(item.getHistoryID(),item.getDatePeriod(),item.getEmploymentCode()));
            });
            return new EmploymentHisExport(i.getEmployeeId(),tempArray);
        });
    }
}
