package nts.uk.ctx.at.shared.app.command.remainingnumber.rervleagrtremnum;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum.AddAnnLeaGrantRemnNumPeregCommand;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.GrantRemainRegisterType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
@Stateless
public class AddResvLeaCommandPeregHandler extends CommandHandlerWithResult<AddResvLeaRemainPeregCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<AddResvLeaRemainPeregCommand>{
	@Inject
	private RervLeaGrantRemDataRepository resvLeaRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00038";
	}

	@Override
	public Class<?> commandClass() {
		return AddResvLeaRemainPeregCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddResvLeaRemainPeregCommand> context) {
		AddResvLeaRemainPeregCommand c = context.getCommand();
		String annLeavId = IdentifierUtil.randomUniqueId();
		boolean check = validate(c.getGrantDate(), c.getDeadline(), c.getGrantDays(),
				c.getUseDays(), c.getRemainingDays() , c.grantDateItemName , c.deadlineDateItemName);
		if (check) {
			ReserveLeaveGrantRemainingData data = ReserveLeaveGrantRemainingData.createFromJavaType(annLeavId,
					c.getEmployeeId(), c.getGrantDate(), c.getDeadline(),
					c.getExpirationStatus() == null ? 1 : c.getExpirationStatus().intValue(),
					GrantRemainRegisterType.MANUAL.value,
					c.getGrantDays() == null ? 0d : c.getGrantDays().doubleValue(),
					c.getUseDays() == null ? 0d : c.getUseDays().doubleValue(),
					c.getOverLimitDays() == null ? 0d : c.getOverLimitDays().doubleValue(),
					c.getRemainingDays() == null ? 0d : c.getRemainingDays().doubleValue());
			resvLeaRepo.add(data, AppContexts.user().companyId());
		}
		return new PeregAddCommandResult(annLeavId);
	}
	
	
	
	public static boolean validate(GeneralDate grantDate, GeneralDate deadlineDate,
			BigDecimal grantDays, BigDecimal usedDays, BigDecimal remainDays , String grantDateItemName, String  deadlineDateItemName) {
		if (grantDate == null && deadlineDate == null && grantDays == null && usedDays == null && remainDays == null)
			return false;

		if (grantDays != null || usedDays != null || remainDays != null) {
			if (deadlineDate == null || grantDate == null) {
				if (grantDate == null) {
					throw new BusinessException("Msg_925", grantDateItemName);
				}
				if (deadlineDate == null) {
					throw new BusinessException("Msg_925", deadlineDateItemName);
				}
			}
		}
		if (grantDate == null && deadlineDate != null) {
			throw new BusinessException("Msg_925", grantDateItemName);
		}
		if (deadlineDate == null && grantDate != null) {
			throw new BusinessException("Msg_925", deadlineDateItemName);
		}
		if (grantDate != null && deadlineDate != null) {
			// 付与日＞使用期限の場合はエラー #Msg_1023
			if (grantDate.compareTo(deadlineDate) > 0) {
				throw new BusinessException("Msg_1023");
			}
		}
		return true;
	}
}
