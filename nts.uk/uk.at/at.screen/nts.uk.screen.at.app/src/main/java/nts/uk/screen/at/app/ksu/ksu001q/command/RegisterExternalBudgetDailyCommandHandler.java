package nts.uk.screen.at.app.ksu.ksu001q.command;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResult;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetActualResultRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetMoneyValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetTimeValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.RegisterExternalBudgetActualResultService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

/**
 * 外部予算日次を登録するHandler
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.予算管理.App.
 *
 * @author thanhlv
 *
 */
@Stateless
@Transactional
public class RegisterExternalBudgetDailyCommandHandler extends CommandHandler<RegisterExternalBudgetDailyCommand> {

	/** The ext budget daily repository. */
	@Inject
	private ExternalBudgetActualResultRepository extBudgetDailyRepository;

	/**
	 * 外部予算日次を登録するHandler
	 *
	 * @param context the context
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterExternalBudgetDailyCommand> context) {

		RegisterExternalBudgetDailyCommand command = context.getCommand();

		List<DateAndValueMap> dateAndValueMap = command.getDateAndValues();

		TargetOrgIdenInfor targetOrg = ("1").equals(command.getUnit())
				? TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(command.getId())
				: TargetOrgIdenInfor.creatIdentifiWorkplace(command.getId());

		RequireImpl require = new RequireImpl(extBudgetDailyRepository);

		String type = command.getType();
		// 登録する(Require, 対象組織識別情報, 外部予算実績項目コード, 外部予算実績受入値年月日, Optional<外部予算実績値>)
		// 登録する(対象組織、項目コード、年月日、外部予算実績値): AtomTask
		switch (type) {
		case "時間":
			for (DateAndValueMap item : dateAndValueMap) {
				Long valueTime = this.convertVal(item.getValue());
				AtomTask atomTask = RegisterExternalBudgetActualResultService.signUp(require, targetOrg,
						new ExternalBudgetCd(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
						Optional.ofNullable(valueTime != null ? new ExternalBudgetTimeValue(valueTime.intValue()) : null));
				transaction.execute(() -> {
					atomTask.run();
				});
			}
			break;
		case "金額":
			for (DateAndValueMap item : dateAndValueMap) {
				AtomTask atomTask = RegisterExternalBudgetActualResultService.signUp(require, targetOrg,
						new ExternalBudgetCd(command.getItemCode()),
						GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"), Optional.ofNullable(
								item.getValue() != "" ? new ExternalBudgetMoneyValue(Integer.parseInt(item.getValue())) : null));
				transaction.execute(() -> {
					atomTask.run();
				});
			}
			break;
		case "人数":
			for (DateAndValueMap item : dateAndValueMap) {
				AtomTask atomTask = RegisterExternalBudgetActualResultService
						.signUp(require, targetOrg, new ExternalBudgetCd(command.getItemCode()),
								GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
								Optional.ofNullable(item.getValue() != ""
										? new ExtBudgetNumberPerson(Integer.parseInt(item.getValue()))
										: null));
				transaction.execute(() -> {
					atomTask.run();
				});
			}
			break;
		case "数値":
			for (DateAndValueMap item : dateAndValueMap) {
				AtomTask atomTask = RegisterExternalBudgetActualResultService
						.signUp(require, targetOrg, new ExternalBudgetCd(command.getItemCode()),
								GeneralDate.fromString(item.getDate(), "yyyy/MM/dd"),
								Optional.ofNullable(item.getValue() != ""
										? new ExtBudgetNumericalVal(Integer.parseInt(item.getValue()))
										: null));
				transaction.execute(() -> {
					atomTask.run();
				});
			}
			break;
		default:
			return;
		}
	}

	/**
	 * Instantiates a new require impl.
	 *
	 * @param extBudgetDailyRepository the ext budget daily repository
	 */
	@AllArgsConstructor
	private class RequireImpl implements RegisterExternalBudgetActualResultService.Require {

		/** The ext budget daily repository. */
		@Inject
		private ExternalBudgetActualResultRepository extBudgetDailyRepository;

		/**
		 * Insert.
		 *
		 * @param actualResult the ext budget daily
		 */
		@Override
		public void insert(ExternalBudgetActualResult actualResult) {
			extBudgetDailyRepository.insert(actualResult);
		}

		/**
		 * Delete.
		 *
		 * @param targetOrg the target org
		 * @param itemCode the item code
		 * @param ymd the ymd
		 */
		@Override
		public void delete(TargetOrgIdenInfor targetOrg, ExternalBudgetCd itemCode, GeneralDate ymd) {
			extBudgetDailyRepository.delete(targetOrg, itemCode, ymd);
		}

	}

	/**
	 * Convert val.
	 *
	 * @param value the value
	 * @return the long
	 */
	private Long convertVal(String value) {
		if (value == null)
			return null;
		String CHARACTER_COLON = ":";
		int numberFirst = 1;

		// not have colon
		if (!value.contains(CHARACTER_COLON)) {
			// it's is number: 0 (mean 00:00 -> 00:59), 1 (mean 01:00 -> 01:59), ... -->
			// #86500
			if (value == "")
				return null;

			return Long.parseLong(value);
		}
		// check number colon character.
		// error when format: hh:mm:ss
		else if (StringUtils.countMatches(value, CHARACTER_COLON) > numberFirst) {
			throw new BusinessException(new RawErrorMessage("Invalid format time of value."));
		}

		// format time of value: 99:00 (hh:mm)
		String[] timeComponents = value.split(CHARACTER_COLON);

		// error when format: hh:
		if (timeComponents.length <= numberFirst) {
			throw new BusinessException(new RawErrorMessage("Invalid format time of value."));
		}

		Integer HOUR = 60;
		Long numberHour = Long.parseLong(timeComponents[0]);
		Long numberMinute = Long.parseLong(timeComponents[1]);
		return numberHour * HOUR + numberMinute;
	}

}
