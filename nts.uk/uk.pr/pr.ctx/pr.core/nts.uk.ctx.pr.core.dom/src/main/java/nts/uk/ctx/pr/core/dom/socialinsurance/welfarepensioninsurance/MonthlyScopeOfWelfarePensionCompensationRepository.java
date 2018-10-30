package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import java.util.Optional;

/**
 * 厚生年金報酬月額範囲
 */
public interface MonthlyScopeOfWelfarePensionCompensationRepository {
	
	Optional<MonthlyScopeOfWelfarePensionCompensation> getMonthlyScopeOfWelfarePensionCompensationByStartYearMonth(int startYearMonth);
	
}
