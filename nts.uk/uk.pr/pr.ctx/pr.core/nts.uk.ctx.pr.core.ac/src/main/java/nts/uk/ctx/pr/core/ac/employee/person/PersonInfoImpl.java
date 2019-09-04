package nts.uk.ctx.pr.core.ac.employee.person;

import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfomationAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfoExportAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PersonInfoImpl implements PersonInfomationAdapter {

    @Inject
    private IPersonInfoPub repo;

    @Override
    public PersonInfoExportAdapter getPersonInfo(String sID) {
        PersonInfoExport domain = repo.getPersonInfo(sID);
        return new PersonInfoExportAdapter(
                domain.getPid(),
                domain.getBusinessName(),
                domain.getEntryDate(),
                domain.getGender(),
                domain.getBirthDay(),
                domain.getEmployeeId(),
                domain.getEmployeeCode(),
                domain.getRetiredDate()
        );
    }
}
