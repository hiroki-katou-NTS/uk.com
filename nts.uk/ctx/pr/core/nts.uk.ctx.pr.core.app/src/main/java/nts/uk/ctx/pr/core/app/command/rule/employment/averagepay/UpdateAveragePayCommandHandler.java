package nts.uk.ctx.pr.core.app.command.rule.employment.averagepay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ApplyFor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AttendDayGettingSet;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePay;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.AveragePayRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.ExceptionPayRate;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.RoundDigitSet;
import nts.uk.ctx.pr.core.dom.rule.employment.averagepay.RoundTimingSet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@RequestScoped
public class UpdateAveragePayCommandHandler extends CommandHandler<UpdateAveragePayCommand> {
	@Inject
	private AveragePayRepository averagePayRepository;

	@Inject
	private ItemSalaryRespository itemSalaryRespository;

	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<UpdateAveragePayCommand> context) {
		UpdateAveragePayCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();
		Optional<AveragePay> avepay = this.averagePayRepository.findByCompanyCode(companyCode);
		if (!avepay.isPresent()) {
			throw new BusinessException("Update Fail");
		}
		AveragePay averagePay = new AveragePay(new CompanyCode(companyCode),
				EnumAdaptor.valueOf(command.getAttendDayGettingSet(), AttendDayGettingSet.class),
				new ExceptionPayRate(command.getExceptionPayRate()),
				EnumAdaptor.valueOf(command.getRoundDigitSet(), RoundDigitSet.class),
				EnumAdaptor.valueOf(command.getRoundTimingSet(), RoundTimingSet.class));
		averagePayRepository.update(averagePay);

		List<ItemSalary> itemSalarys = this.itemSalaryRespository.findAll(companyCode);

		// item salary selected
		List<ItemSalary> itemSelectedSalarys = itemSalarys.stream()
				.filter(x -> command.getSalarySelectedCode().contains(x.getItemCode().v())).collect(Collectors.toList());

		// item salary not selected
		List<ItemSalary> itemUnselectedSalarys = itemSalarys.stream()
				.filter(x -> !command.getSalarySelectedCode().contains(x.getItemCode().v())).collect(Collectors.toList());
		itemSelectedSalarys.forEach(x -> {
			this.itemSalaryRespository.update(new ItemSalary(x.getCompanyCode(), x.getItemCode(), x.getTaxAtr(),
					x.getSocialInsAtr(), x.getLaborInsAtr(), x.getFixPayAtr(), x.getApplyForAllEmpFlg(),
					x.getApplyForMonthlyPayEmp(), x.getApplyForDaymonthlyPayEmp(), x.getApplyForDaylyPayEmp(),
					x.getApplyForHourlyPayEmp(), EnumAdaptor.valueOf(1, ApplyFor.class), x.getErrRangeLowAtr(),
					x.getErrRangeLow(), x.getErrRangeHighAtr(), x.getErrRangeHigh(), x.getAlRangeLowAtr(),
					x.getAlRangeLow(), x.getAlRangeHighAtr(), x.getAlRangeHigh(), x.getMemo(), x.getLimitMnyAtr(),
					x.getLimitMnyRefItemCd(), x.getLimitMny()));
		});
		itemUnselectedSalarys.forEach(x -> {
			this.itemSalaryRespository.update(new ItemSalary(x.getCompanyCode(), x.getItemCode(), x.getTaxAtr(),
					x.getSocialInsAtr(), x.getLaborInsAtr(), x.getFixPayAtr(), x.getApplyForAllEmpFlg(),
					x.getApplyForMonthlyPayEmp(), x.getApplyForDaymonthlyPayEmp(), x.getApplyForDaylyPayEmp(),
					x.getApplyForHourlyPayEmp(), EnumAdaptor.valueOf(0, ApplyFor.class), x.getErrRangeLowAtr(),
					x.getErrRangeLow(), x.getErrRangeHighAtr(), x.getErrRangeHigh(), x.getAlRangeLowAtr(),
					x.getAlRangeLow(), x.getAlRangeHighAtr(), x.getAlRangeHigh(), x.getMemo(), x.getLimitMnyAtr(),
					x.getLimitMnyRefItemCd(), x.getLimitMny()));
		});
		if (command.getAttendDayGettingSet() == 1) {
			List<ItemAttend> itemAttends = this.itemAttendRespository.findAll(companyCode);
			List<ItemAttend> itemSelectedAttends = itemAttends.stream()
					.filter(x -> command.getAttendSelectedCode().contains(x.getItemCode()))
					.collect(Collectors.toList());
			List<ItemAttend> itemUnselectedAttends = itemAttends.stream()
					.filter(x -> !command.getAttendSelectedCode().contains(x.getItemCode()))
					.collect(Collectors.toList());
			itemSelectedAttends.forEach(x -> {
				this.itemAttendRespository.update(
						new ItemAttend(x.getCompanyCode(), x.getItemCode(), EnumAdaptor.valueOf(1, AvePayAtr.class),
								x.getItemAtr(), x.getErrRangeLowAtr(), x.getErrRangeLow(), x.getErrRangeHighAtr(),
								x.getErrRangeHigh(), x.getAlRangeLowAtr(), x.getAlRangeLow(), x.getAlRangeHighAtr(),
								x.getAlRangeHigh(), x.getWorkDaysScopeAtr(), x.getMemo()));
			});
			itemUnselectedAttends.forEach(x -> {
				this.itemAttendRespository.update(
						new ItemAttend(x.getCompanyCode(), x.getItemCode(), EnumAdaptor.valueOf(0, AvePayAtr.class),
								x.getItemAtr(), x.getErrRangeLowAtr(), x.getErrRangeLow(), x.getErrRangeHighAtr(),
								x.getErrRangeHigh(), x.getAlRangeLowAtr(), x.getAlRangeLow(), x.getAlRangeHighAtr(),
								x.getAlRangeHigh(), x.getWorkDaysScopeAtr(), x.getMemo()));
			});
		}
	}

}
