package nts.uk.ctx.at.shared.app.command.scherec.dailyattdcal.declare;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareHolidayWorkFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareOvertimeFrame;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.DeclareSetRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare.HdwkFrameEachHdAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * Command - 申告設定を登録する
 * 
 * @author NWS
 *
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class RegisterFilingSettingsCommandHandler extends CommandHandler<RegisterFilingSettingsCommand> {

	@Inject
	private DeclareSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<RegisterFilingSettingsCommand> context) {
		RegisterFilingSettingsCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// Step 1: Get 申告設定
		Optional<DeclareSet> optionalDeclareSet = repository.find(companyId);
		// Step 2: Check 申告設定 IS Not Empty => Update
		if (optionalDeclareSet.isPresent()) {
			DeclareSet declareSet = optionalDeclareSet.get();
			declareSet = this.convertCommandToDeclareSet(command, companyId);
			repository.addOrUpdate(declareSet);
			// Step 3: Check 申告設定 IS Empty => Insert
		} else {
			DeclareSet declareSet = new DeclareSet(companyId);
			declareSet = this.convertCommandToDeclareSet(command, companyId);
			repository.addOrUpdate(declareSet);
		}
	}

	/**
	 * Convert dto/command to AggregateRoot
	 * 
	 * @param overtimeWorkFrame
	 * @return DeclareSetDto
	 */
	private DeclareSet convertCommandToDeclareSet(RegisterFilingSettingsCommand declareSetDto, String companyId) {
		// convert DeclareSet to dto
		Integer usageAtr = declareSetDto.getUsageAtr();
		Integer frameSet = declareSetDto.getFrameSet();
		Integer midnightAutoCalc = declareSetDto.getMidnightAutoCalc();

		HdwkFrameEachHdAtr holidayWork = HdwkFrameEachHdAtr.createFromJavaType(
				(declareSetDto.getHolidayWorkFrame() != null
				&& declareSetDto.getHolidayWorkFrame().getHolidayWork() != null)
						? declareSetDto.getHolidayWorkFrame().getHolidayWork().getStatutory()
						: null,
		(declareSetDto.getHolidayWorkFrame() != null
				&& declareSetDto.getHolidayWorkFrame().getHolidayWork() != null)
						? declareSetDto.getHolidayWorkFrame().getHolidayWork().getNotStatutory()
						: null,
		(declareSetDto.getHolidayWorkFrame() != null
				&& declareSetDto.getHolidayWorkFrame().getHolidayWork() != null)
						? declareSetDto.getHolidayWorkFrame().getHolidayWork().getNotStatHoliday()
						: null);

		HdwkFrameEachHdAtr holidayWorkMn = HdwkFrameEachHdAtr.createFromJavaType(
				(declareSetDto.getHolidayWorkFrame() != null
						&& declareSetDto.getHolidayWorkFrame().getHolidayWorkMn() != null)
								? declareSetDto.getHolidayWorkFrame().getHolidayWorkMn().getStatutory()
								: null,
				(declareSetDto.getHolidayWorkFrame() != null
						&& declareSetDto.getHolidayWorkFrame().getHolidayWorkMn() != null)
								? declareSetDto.getHolidayWorkFrame().getHolidayWorkMn().getNotStatutory()
								: null,
				(declareSetDto.getHolidayWorkFrame() != null
						&& declareSetDto.getHolidayWorkFrame().getHolidayWorkMn() != null)
								? declareSetDto.getHolidayWorkFrame().getHolidayWorkMn().getNotStatHoliday()
								: null);
		DeclareHolidayWorkFrame holidayWorkFrame = DeclareHolidayWorkFrame.createFromJavaType(holidayWork,
				holidayWorkMn);

		DeclareOvertimeFrame overtimeFrame = DeclareOvertimeFrame.createFromJavaType(
				declareSetDto.getOvertimeFrame() == null ? null : declareSetDto.getOvertimeFrame().getEarlyOvertime(),
				declareSetDto.getOvertimeFrame() == null ? null : declareSetDto.getOvertimeFrame().getEarlyOvertimeMn(),
				declareSetDto.getOvertimeFrame() == null ? null : declareSetDto.getOvertimeFrame().getOvertime(),
				declareSetDto.getOvertimeFrame() == null ? null : declareSetDto.getOvertimeFrame().getOvertimeMn());
		return DeclareSet.createFromJavaType(companyId, usageAtr, frameSet, midnightAutoCalc, overtimeFrame,
				holidayWorkFrame);
	}

}