package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckDataEmployeeServicesImp implements CheckDataEmployeeServices {

	@Inject
	private RegulationInfoEmployeeRepository regulationInfoEmployeeRepo;

	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public <C> void manager(CheckDataFromUI excuteCommand, AsyncCommandHandlerContext<C> async) {

		// 実行状態 初期設定
		val dataSetter = async.getDataSetter();
		dataSetter.setData("aggCreateCount", 0);
		dataSetter.setData("aggCreateStatus", ExecutionStatusCps013.PROCESSING.name);
		List<EmployeeResultDto> listEmp = this.findEmployeesInfo(excuteCommand);
		List<ErrorInfoCPS013> listError = new ArrayList<>();
		for (int i = 0; i < listEmp.size(); i++) {
			System.out.println("==== t");
			dataSetter.updateData("aggCreateCount", i + 1);
			if (i % 10 == 0) {
				ErrorInfoCPS013 error = new ErrorInfoCPS013(listEmp.get(i).sid, "categoryId",
						listEmp.get(i).employeeCode, listEmp.get(i).bussinessName, "check personInfo", "categoryName",
						"error" + i);
				listError.add(error);
			}
		}
		for (int i = 0; i < listError.size(); i++) {
			dataSetter.setData("employeeId" + i, listError.get(i).employeeId);
			dataSetter.setData("categoryId" + i, listError.get(i).categoryId);
			dataSetter.setData("employeeCode" + i, listError.get(i).employeeCode);
			dataSetter.setData("bussinessName" + i, listError.get(i).bussinessName);
			dataSetter.setData("clsCategoryCheck" + i, listError.get(i).clsCategoryCheck);
			dataSetter.setData("categoryName" + i, listError.get(i).categoryName);
			dataSetter.setData("error" + i, listError.get(i).error);
		}
	}

	// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
	private List<EmployeeResultDto> findEmployeesInfo(CheckDataFromUI query) {
		EmpQueryDto queryDto = new EmpQueryDto();
		GeneralDateTime baseDate = GeneralDateTime.fromString(query.getDateTime() + TIME_DAY_START, DATE_TIME_FORMAT);
		return this.regulationInfoEmployeeRepo.find(AppContexts.user().companyId(), queryDto.toQueryModel(baseDate))
				.stream().map(model -> this.toEmployeeDto(model)).collect(Collectors.toList());
	}

	private EmployeeResultDto toEmployeeDto(RegulationInfoEmployee model) {
		return new EmployeeResultDto(model.getEmployeeID(), model.getEmployeeCode(), model.getName().orElse(""));
	}

}
