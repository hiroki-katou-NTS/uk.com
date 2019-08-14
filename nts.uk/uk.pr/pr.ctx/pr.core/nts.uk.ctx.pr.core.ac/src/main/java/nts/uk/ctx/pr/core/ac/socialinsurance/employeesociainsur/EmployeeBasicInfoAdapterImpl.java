package nts.uk.ctx.pr.core.ac.socialinsurance.employeesociainsur;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.pr.shared.dom.adapter.socialinsurance.employeesociainsur.person.EmployeeBasicInfo;
import nts.uk.ctx.pr.shared.dom.adapter.socialinsurance.employeesociainsur.person.EmployeeBasicInfoAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmployeeBasicInfoAdapterImpl implements EmployeeBasicInfoAdapter{

    @Inject
    IPersonInfoPub persion;

    @Override
    public EmployeeBasicInfo getPersonInfo(String sID) {
        PersonInfoExport person = persion.getPersonInfo(sID);

        return new EmployeeBasicInfo(person.getPid(),
                person.getBusinessName(),
                person.getEntryDate(),
                person.getGender(),
                person.getBirthDay(),
                person.getEmployeeId(),
                person.getEmployeeCode(),
                person.getRetiredDate());
    }
}
