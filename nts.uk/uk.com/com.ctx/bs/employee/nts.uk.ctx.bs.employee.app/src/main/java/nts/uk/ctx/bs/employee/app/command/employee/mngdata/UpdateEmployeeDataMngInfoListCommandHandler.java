package nts.uk.ctx.bs.employee.app.command.employee.mngdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmployeeDataMngInfoListCommandHandler extends CommandHandler<List<UpdateEmployeeDataMngInfoCommand>>
		implements PeregUpdateListCommandHandler<UpdateEmployeeDataMngInfoCommand> {
	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepository;

	@Override
	public String targetCategoryCd() {
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeDataMngInfoCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateEmployeeDataMngInfoCommand>> context) {
		List<UpdateEmployeeDataMngInfoCommand> command = context.getCommand();
		List<String> sidErrorLst = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		// 同じ会社IDかつ、削除状況＝削除していないものは、社員コードは重複してはいけない （#Msg_345#）
		// Map<employeeCode, List<EmployeeDataMngInfo>>
		Map<String, List<EmployeeDataMngInfo>> employeeDataMap = employeeDataMngInfoRepository
				.findByListEmployeeCode(cid,
						command.stream().map(c -> c.getEmployeeCode()).collect(Collectors.toList()))
				.parallelStream().collect(Collectors.groupingBy(c -> c.getEmployeeCode().v()));
		
		List<EmployeeDataMngInfo> domains = command.parallelStream().map(c -> {
			return new EmployeeDataMngInfo(cid, c.getPersonId(), c.getEmployeeId(), c.getEmployeeCode(),
					c.getExternalCode());
		}).collect(Collectors.toList());
		
		command.parallelStream().forEach(c -> {
			List<EmployeeDataMngInfo> empLst = employeeDataMap.get(c.getEmployeeCode());
			if (empLst != null) {
				Optional<EmployeeDataMngInfo> empOpt = empLst.stream()
						.filter(emp -> !emp.getEmployeeId().equals(c.getEmployeeId())).findFirst();
				if (empOpt.isPresent()) {
					sidErrorLst.add(c.getEmployeeId());
					domains.remove(empOpt.get());
				}
			}

		});


		
		this.employeeDataMngInfoRepository.updateAll(domains);
		
		if (sidErrorLst.size() > 0) {
			throw new MyCustomizeException("Msg_345", sidErrorLst);
		}

	}

}
