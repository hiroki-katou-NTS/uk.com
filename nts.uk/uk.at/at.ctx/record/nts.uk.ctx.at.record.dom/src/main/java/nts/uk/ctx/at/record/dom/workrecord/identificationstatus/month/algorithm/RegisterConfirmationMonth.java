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
						data.getEmployeeID(), ClosureId.valueOf(param.getClosureId()), new Day(param.getClosureDay()), param.getYearMonth());
				if (!confirmMonthOpt.isPresent()) {
					confirmationMonthRepository.insert(new ConfirmationMonth(new CompanyId(companyId),
							data.getEmployeeID(), ClosureId.valueOf(param.getClosureId()),
							new Day(param.getClosureDay()), param.getYearMonth(), param.getIndentifyYmd()));
				}
			} else {
				confirmationMonthRepository.delete(companyId, data.getEmployeeID(), param.getClosureId(), param.getClosureDay(), param.getYearMonth().v());
			}
		});
	}
}
