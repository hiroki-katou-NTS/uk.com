package nts.uk.screen.at.app.query.ksu.ksu001q.command;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.budget.performance.domainservice.RegisterExtBudgetDailyService;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetActItemCode;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDaily;
import nts.uk.ctx.at.schedule.dom.workschedule.budgetcontrol.budgetperformance.ExtBudgetDailyRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

/**
 * 外部予算日次を登録するHandler
 * 
 * @author thanhlv
 *
 */
@Stateless
@Transactional
public class RegisterExternalBudgetDailyCommandHandler extends CommandHandler<RegisterExternalBudgetDailyCommand> {

	@Inject
	private ExtBudgetDailyRepository extBudgetDailyRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterExternalBudgetDailyCommand> context) {

		RegisterExternalBudgetDailyCommand command = context.getCommand();

		List<DateAndValueMap> dateAndValueMap = command.getDateAndValues();

		String workplaceId = null;
		String workplaceGroupId = null;

		if (command.getUnit().equals("1")) {
			workplaceId = null;
			workplaceGroupId = command.getId();
		}
		if (command.getUnit().equals("0")) {
			workplaceId = command.getId();
			workplaceGroupId = null;
		}

		// 外部予算日次を登録する
		TargetOrgIdenInfor targetOrg = new TargetOrgIdenInfor(
				TargetOrganizationUnit.valueOf(Integer.parseInt(command.getUnit())), Optional.ofNullable(workplaceId), Optional.ofNullable(workplaceGroupId));

		RequireImpl require = new RequireImpl(extBudgetDailyRepository);

		String type = command.getType();
		// 登録する(Require, 対象組織識別情報, 外部予算実績項目コード, 外部予算実績受入値年月日, Optional<外部予算実績値>)
		// 登録する(対象組織、項目コード、年月日、外部予算実績値): AtomTask
		if (type.equals("時間")) {
			for (DateAndValueMap item : dateAndValueMap) {
				RegisterExtBudgetDailyService.signUp(require, targetOrg,
						new ExtBudgetActItemCode(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
						Optional.ofNullable(new ExtBudgetTime(Integer.parseInt(item.getValue()))));
			}
		}
		if (type.equals("金額")) {
			for (DateAndValueMap item : dateAndValueMap) {
				RegisterExtBudgetDailyService.signUp(require, targetOrg,
						new ExtBudgetActItemCode(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
						Optional.ofNullable(new ExtBudgetMoney(Integer.parseInt(item.getValue()))));
			}
		}
		if (type.equals("人数")) {
			for (DateAndValueMap item : dateAndValueMap) {
				RegisterExtBudgetDailyService.signUp(require, targetOrg,
						new ExtBudgetActItemCode(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
						Optional.ofNullable(new ExtBudgetNumberPerson(Integer.parseInt(item.getValue()))));
			}
		}
		if (type.equals("数値")) {
			for (DateAndValueMap item : dateAndValueMap) {
				RegisterExtBudgetDailyService.signUp(require, targetOrg,
						new ExtBudgetActItemCode(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
						Optional.ofNullable(new ExtBudgetNumericalVal(Integer.parseInt(item.getValue()))));
			}
		}
	}

	@AllArgsConstructor
	private class RequireImpl implements RegisterExtBudgetDailyService.Require {

		private final ExtBudgetDailyRepository extBudgetDailyRepository;

		@Override
		public void insert(ExtBudgetDaily extBudgetDaily) {
			extBudgetDailyRepository.insert(extBudgetDaily);

		}

		@Override
		public void delete(TargetOrgIdenInfor targetOrg, ExtBudgetActItemCode itemCode, GeneralDate ymd) {
			extBudgetDailyRepository.delete(targetOrg, itemCode, ymd);
		}

	}

}
