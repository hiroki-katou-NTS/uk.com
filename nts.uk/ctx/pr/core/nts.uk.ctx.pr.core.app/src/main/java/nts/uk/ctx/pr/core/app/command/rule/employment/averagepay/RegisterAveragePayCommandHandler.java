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
public class RegisterAveragePayCommandHandler extends CommandHandler<RegisterAveragePayCommand> {

	@Inject
	private AveragePayRepository averagePayRepository;

	@Inject
	private ItemSalaryRespository itemSalaryRespository;

	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<RegisterAveragePayCommand> context) {
		RegisterAveragePayCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();

		Optional<AveragePay> avepay = this.averagePayRepository.findByCompanyCode(companyCode);

		if (avepay.isPresent()) {
			throw new BusinessException("Register Fail");
		}
		AveragePay averagePay = new AveragePay(new CompanyCode(companyCode),
				EnumAdaptor.valueOf(command.getAttendDayGettingSet(), AttendDayGettingSet.class),
				new ExceptionPayRate(command.getExceptionPayRate()),
				EnumAdaptor.valueOf(command.getRoundDigitSet(), RoundDigitSet.class),
				EnumAdaptor.valueOf(command.getRoundTimingSet(), RoundTimingSet.class));
		averagePayRepository.register(averagePay);
		List<ItemSalary> itemSalarys = this.itemSalaryRespository.findAll(companyCode);
		List<ItemSalary> itemSelectedSalarys = itemSalarys.stream()
				.filter(x -> command.getSalarySelectedCode().contains(x.getItemCode())).collect(Collectors.toList());
		itemSelectedSalarys.forEach(x -> {
			this.itemSalaryRespository.update(new ItemSalary(new CompanyCode(companyCode), x.itemCode, x.taxAtr,
					x.socialInsAtr, x.laborInsAtr, x.fixPayAtr, x.applyForAllEmpFlg, x.applyForMonthlyPayEmp,
					x.applyForDaymonthlyPayEmp, x.applyForDaylyPayEmp, x.applyForHourlyPayEmp,
					EnumAdaptor.valueOf(1, ApplyFor.class), x.errRangeLowAtr, x.errRangeLow, x.errRangeHighAtr,
					x.errRangeHigh, x.alRangeLowAtr, x.alRangeLow, x.alRangeHighAtr, x.alRangeHigh, x.memo,
					x.limitMnyAtr, x.limitMnyRefItemCd, x.limitMny));
		});
		if (command.getAttendDayGettingSet() == 1) {
			List<ItemAttend> itemAttends = this.itemAttendRespository.findAll(companyCode);
			List<ItemAttend> itemSelectedAttends = itemAttends.stream()
					.filter(x -> command.getAttendSelectedCode().contains(x.getItemCode()))
					.collect(Collectors.toList());
			itemSelectedAttends.forEach(x -> {
				this.itemAttendRespository.update(companyCode,
						new ItemAttend(new CompanyCode(companyCode), x.getItemCode(),
								EnumAdaptor.valueOf(1, AvePayAtr.class), x.getItemAtr(), x.getErrRangeLowAtr(),
								x.getErrRangeLow(), x.getErrRangeHighAtr(), x.getErrRangeHigh(), x.getAlRangeLowAtr(),
								x.getAlRangeLow(), x.getAlRangeHighAtr(), x.getAlRangeHigh(), x.getWorkDaysScopeAtr(),
								x.getMemo()));
			});
		}
	}

}
