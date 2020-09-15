package nts.uk.ctx.at.record.ac.personempbasic;

import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class PersonBasicInfoAdapterImpl implements PersonEmpBasicInfoAdapter {
    @Inject
    private  PersonEmpBasicInfoPub personEmpBasicInfoPub;
    @Override
    public List<PersonEmpBasicInfoDto> getPerEmpBasicInfo(List<String> employeeIds) {
        return personEmpBasicInfoPub.getPerEmpBasicInfo(employeeIds).stream().map(e-> new PersonEmpBasicInfoDto())
                .collect(Collectors.toList());
    }
}
