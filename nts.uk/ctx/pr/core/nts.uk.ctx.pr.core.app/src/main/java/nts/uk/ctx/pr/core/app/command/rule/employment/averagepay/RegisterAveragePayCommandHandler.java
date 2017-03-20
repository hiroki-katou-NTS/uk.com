package nts.uk.ctx.pr.core.app.command.rule.employment.averagepay;

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
import nts.uk.ctx.pr.core.dom.itemmaster.AvePayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
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
			x.setAvePayAttribute(AvePayAtr.Object);
			this.itemSalaryRespository.update(x);
		});
		if (command.getAttendDayGettingSet() == 1) {
			List<ItemAttend> itemAttends = this.itemAttendRespository.findAll(companyCode);
			List<ItemAttend> itemSelectedAttends = itemAttends.stream()
					.filter(x -> command.getAttendSelectedCode().contains(x.getItemCode()))
					.collect(Collectors.toList());
			itemSelectedAttends.forEach(x -> {
				x.setAvePayAttribute(AvePayAtr.Object);
				this.itemAttendRespository.update(x);
			});
		}
	}

}
