package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.UpdateCommandHealth;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;

@Stateless
public class HealthInsuCommandHandler extends CommandHandlerWithResult<UpdateCommandHealth, List<String>> {
	
	@Inject
	private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;
	
	
	@Override
	protected List<String> handle(CommandHandlerContext<UpdateCommandHealth> context) {
		
		List<String> response = new ArrayList<>();
		UpdateCommandHealth command = context.getCommand();
		// ドメインモデル「健康保険月額保険料額」を更新する
		Optional<HealthInsuranceMonthlyFee> data = healthInsuranceMonthlyFeeRepository
				.getHealthInsuranceMonthlyFeeById(command.getHistoryId());
		if(data.isPresent() && command.getCusDataDtos().get(0).getEmBasicInsurancePremium() != null) {
			List<HealthInsurancePerGradeFee> dataUpdate = command.getCusDataDtos().stream().map( x -> x.fromCommandToDomain()).collect(Collectors.toList());
			data.get().updateGradeFee(dataUpdate);
			List<HealthInsurancePerGradeFee> checkData = data.get().getHealthInsurancePerGradeFee();
			if(checkData.isEmpty()) {
				healthInsuranceMonthlyFeeRepository.insertGraFee(data.get());
			} else {
				healthInsuranceMonthlyFeeRepository.updateGraFee(data.get());
			}
		}
		response.add("Msg_15");
		return response;

	}

}
