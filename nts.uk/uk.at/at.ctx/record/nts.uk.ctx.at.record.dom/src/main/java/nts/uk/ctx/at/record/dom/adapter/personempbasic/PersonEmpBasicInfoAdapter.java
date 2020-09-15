package nts.uk.ctx.at.record.dom.adapter.personempbasic;

import java.util.List;

public interface PersonEmpBasicInfoAdapter {

    List<PersonEmpBasicInfoDto> getPerEmpBasicInfo(List<String> employeeIds);

}
