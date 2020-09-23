package nts.uk.ctx.at.record.dom.divergence.time;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceInputRequired;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceReasonContent;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/**
 * The Class JpaDivergenceReasonInputMethodServiceImp.
 */
public class DivergenceReasonInputMethodServiceImp implements DivergenceReasonInputMethodService {

	/** The divergence reason input method repo. */
	@Inject
	DivergenceReasonInputMethodRepository divergenceReasonInputMethodRepo;

	/** The divergence reason select repo. */
	@Inject
	DivergenceReasonSelectRepository divergenceReasonSelectRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodService#DetermineLeakageReason(java.lang.
	 * String, nts.arc.time.GeneralDate, java.lang.Integer,
	 * nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode,
	 * nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason,
	 * nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult)
	 */
	@Override
	public JudgmentResult determineLeakageReason(String employeeId, GeneralDate processDate, int divergenceTimeNo,
			DivergenceReasonCode divergenceReasonCode, DivergenceReasonContent divergenceReasonContent,
			JudgmentResult justmentResult) {

		// In case of error or alarm
		if (justmentResult == JudgmentResult.ALARM || justmentResult == JudgmentResult.ALARM) {
			String companyId = AppContexts.user().companyId();
			Optional<DivergenceReasonInputMethod> optionalDivReasonInputMethod = divergenceReasonInputMethodRepo
					.getDivTimeInfo(companyId, divergenceTimeNo);

			// Check DivergenceInputMethod
			if (optionalDivReasonInputMethod.isPresent()) {
				DivergenceReasonInputMethod divReasonInputMethod = optionalDivReasonInputMethod.get();

				boolean reasonInput = divReasonInputMethod.isDivergenceReasonInputed();
				boolean reasonSelect = divReasonInputMethod.isDivergenceReasonSelected();

				// Check reasonInput = true && reasonSelect = true
				if (reasonInput && reasonSelect) {
					// Get DivergenceReasonSelect
					Optional<DivergenceReasonSelect> optionalDivergenceReasonSelect = divergenceReasonSelectRepo
							.findReasonInfo(divergenceTimeNo, companyId, divergenceReasonCode.toString());
					if (optionalDivergenceReasonSelect.isPresent()) {
						// In case DivergenceReasonSelect is present
						DivergenceReasonSelect divergenceReasonSelect = optionalDivergenceReasonSelect.get();

						// Check ReasonRequire
						if (divergenceReasonSelect.getReasonRequired() == DivergenceInputRequired.REQUIRE) {

							return (divergenceReasonSelect.getReason() != null) ? JudgmentResult.NORMAL
									: JudgmentResult.ERROR;
						} else {
							return JudgmentResult.NORMAL;
						}

					}
					return JudgmentResult.ERROR;

				} else if (!reasonInput && reasonSelect) {
					// Get DivergenceReasonSelect
					Optional<DivergenceReasonSelect> optionalDivergenceReasonSelect = divergenceReasonSelectRepo
							.findReasonInfo(divergenceTimeNo, companyId, divergenceReasonCode.toString());
					// return
					return optionalDivergenceReasonSelect.isPresent() ? JudgmentResult.NORMAL : JudgmentResult.ERROR;

				} else if (reasonInput && !reasonSelect) {
					// Get DivergenceReasonSelect
					Optional<DivergenceReasonSelect> optionalDivergenceReasonSelect = divergenceReasonSelectRepo
							.findReasonInfo(divergenceTimeNo, companyId, divergenceReasonCode.toString());

					if (optionalDivergenceReasonSelect.isPresent()) {
						// In case DivergenceReasonSelect is present
						DivergenceReasonSelect divergenceReasonSelect = optionalDivergenceReasonSelect.get();

						// return
						return (divergenceReasonSelect.getReason() != null) ? JudgmentResult.NORMAL
								: JudgmentResult.ERROR;

					}
					return JudgmentResult.ERROR;

				}

			}
		}
		// In case normal
		return JudgmentResult.NORMAL;
	}
}
