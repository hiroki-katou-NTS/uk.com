package nts.uk.ctx.at.record.dom.divergence.time.history;

import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * The Interface CompanyDivergenceReferenceTimeService.
 */
public interface CompanyDivergenceReferenceTimeService {

	/**
	 * Check divergence time.
	 *
	 * @param userId the user id
	 * @param companyId the company id
	 * @param processDate the process date
	 * @param divergenceTimeNo the divergence time no
	 * @param DivergenceTimeOccurred the divergence time occurred
	 */
	//乖離時間をチェックする
	public JudgmentResultDetermineRefTime CheckDivergenceTime(String userId, String companyId, GeneralDate processDate, int divergenceTimeNo, AttendanceTime DivergenceTimeOccurred );
}
