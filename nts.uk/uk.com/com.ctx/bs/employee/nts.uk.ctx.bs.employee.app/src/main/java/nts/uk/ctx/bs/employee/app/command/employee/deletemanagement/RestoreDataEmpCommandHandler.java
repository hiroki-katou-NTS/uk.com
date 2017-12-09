package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.bs.employee.dom.deleteempmanagement.DeleteEmpRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeCode;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.BusinessName;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonNameGroup;

@Stateless
@Transactional
public class RestoreDataEmpCommandHandler extends CommandHandler<EmployeeDeleteToRestoreCommand> {

	@Inject
	PersonRepository personRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Override
	protected void handle(CommandHandlerContext<EmployeeDeleteToRestoreCommand> context) {

		EmployeeDeleteToRestoreCommand command = context.getCommand();

		if (command != null) {
			List<EmployeeDataMngInfo> listEmpData = empDataMngRepo.findByEmployeeId(command.getId());

			if (!listEmpData.isEmpty()) {
				EmployeeDataMngInfo empInfo = listEmpData.get(0);
				GeneralDateTime currentDatetime = GeneralDateTime.legacyDateTime(new Date());
				empInfo.setEmployeeCode(new EmployeeCode(command.getNewCode().toString()));
				empInfo.setDeleteDateTemporary(currentDatetime);

				empDataMngRepo.updateRemoveReason(empInfo);

				// get Person
				Person person = personRepo.getByPersonId(empInfo.getPersonId()).get();
				PersonNameGroup nameGroup = person.getPersonNameGroup();
				nameGroup.setBusinessName(new BusinessName(command.getNewName()));
				person.setPersonNameGroup(nameGroup);
				personRepo.update(person);
			}
		}
	}

}
