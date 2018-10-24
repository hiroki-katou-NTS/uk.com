package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.CusWelfarePensionDto;
import nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto.UpdateCommandWelfare;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.*;

@Stateless
public class WelfarePensionStandardMonthlyFeeCommand extends CommandHandlerWithResult<UpdateCommandWelfare, List<String>> {

    @Inject
    private EmployeesPensionMonthlyInsuranceFeeRepository employeesPensionMonthlyInsuranceFeeRepository;

    @Inject
    private WelfarePensionInsuranceClassificationRepository welfarePensionInsuranceClassificationRepository;

    @Override
    protected List<String> handle(CommandHandlerContext<UpdateCommandWelfare> context) {

        List<String> response = new ArrayList<>();
        UpdateCommandWelfare command = context.getCommand();
        Optional<WelfarePensionInsuranceClassification> data = welfarePensionInsuranceClassificationRepository.getWelfarePensionInsuranceClassificationById(command.getHistoryId());
        // ドメインモデル「厚生年金保険月額保険料額」を更新する
        Optional<EmployeesPensionMonthlyInsuranceFee> employeesPension = employeesPensionMonthlyInsuranceFeeRepository
                .getEmployeesPensionMonthlyInsuranceFeeByHistoryId(command.getHistoryId());
        if (employeesPension.isPresent()) {
            List<GradeWelfarePensionInsurancePremium> dataUpdate = command.getCusWelfarePensions().stream()
                    .map(x -> x.fromToDomain(data,x)).collect(Collectors.toList());
            employeesPension.get().updateGradeList(dataUpdate);
            List<GradeWelfarePensionInsurancePremium> datacheck = employeesPension.get().getPensionInsurancePremium();
            if (datacheck.isEmpty()) {
                employeesPensionMonthlyInsuranceFeeRepository.insertWelfarePension(employeesPension.get());
            } else {
                employeesPensionMonthlyInsuranceFeeRepository.updateWelfarePension(employeesPension.get());
            }
        }
        response.add("Msg_15");
        return response;
    }

}
