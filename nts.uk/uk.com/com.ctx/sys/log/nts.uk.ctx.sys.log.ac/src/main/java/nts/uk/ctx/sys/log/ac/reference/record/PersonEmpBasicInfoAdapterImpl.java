package nts.uk.ctx.sys.log.ac.reference.record;

import java.util.Arrays;
import java.util.List;

/**
 *  author: thuongtv
 */

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoAdapter;
import nts.uk.ctx.sys.log.dom.reference.PersonEmpBasicInfoImport;

@Stateless
public class PersonEmpBasicInfoAdapterImpl implements PersonEmpBasicInfoAdapter {

	@Inject
	private PersonEmpBasicInfoPub personEmpBasicInfoPub;

	@Override
	public PersonEmpBasicInfoImport getPersonEmpBasicInfoByEmpId(String empId) {
		List<String> employeeIds = Arrays.asList(empId);
		List<PersonEmpBasicInfoDto> lstPerson = personEmpBasicInfoPub.getPerEmpBasicInfo(employeeIds);

		if (!CollectionUtil.isEmpty(lstPerson)) {
			PersonEmpBasicInfoDto object = lstPerson.get(0);
			PersonEmpBasicInfoImport personEmpBasicInfoImport = new PersonEmpBasicInfoImport(object.getPersonId(),
					object.getEmployeeId(), object.getBusinessName(), object.getGender(), object.getBirthday(),
					object.getEmployeeCode(), object.getJobEntryDate(), object.getRetirementDate());
			return personEmpBasicInfoImport;
		}
		return null;
	}

}
