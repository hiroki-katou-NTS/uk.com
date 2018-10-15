package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * @author thanhnx 月の本人確認を登録する
 */
@Stateless
public class RegisterConfirmationMonth {

	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;

	public void registerConfirmationMonth(ParamRegisterConfirmMonth param) {
		String companyId = AppContexts.user().companyId();
		param.getSelfConfirm().stream().forEach(data -> {
			if (data.isSelfConfirm()) {
				Optional<ConfirmationMonth> confirmMonthOpt = confirmationMonthRepository.findByKey(companyId,
						// fix bug 101936
						data.getEmployeeID(), ClosureId.valueOf(param.getClosureId()), new ClosureDate(param.getClosureDate().getClosureDay().v(), param.getClosureDate().getLastDayOfMonth()), param.getYearMonth());
				if (!confirmMonthOpt.isPresent()) {
					confirmationMonthRepository.insert(new ConfirmationMonth(new CompanyId(companyId),
							data.getEmployeeID(), ClosureId.valueOf(param.getClosureId()),
							// fix bug 101936
							new ClosureDate(param.getClosureDate().getClosureDay().v(),  param.getClosureDate().getLastDayOfMonth()), param.getYearMonth(), param.getIndentifyYmd()));
				}
			} else {
				// fix bug 101936
				confirmationMonthRepository.delete(companyId, data.getEmployeeID(), param.getClosureId(), param.getClosureDate().getClosureDay().v(),param.getClosureDate().getLastDayOfMonth(), param.getYearMonth().v());
			}
		});
	}
}
