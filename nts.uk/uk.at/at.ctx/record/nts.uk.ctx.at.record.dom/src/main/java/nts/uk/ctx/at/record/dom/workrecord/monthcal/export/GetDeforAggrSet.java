package nts.uk.ctx.at.record.dom.workrecord.monthcal.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.DeforWorkTimeAggrSet;

/**
 * 集計設定の取得（変形労働）
 * @author shuichu_ishida
 */
public interface GetDeforAggrSet {

	/**
	 * 集計設定の取得（変形労働）
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 変形労働時間勤務の法定内集計設定
	 */
	Optional<DeforWorkTimeAggrSet> get(String companyId, String employmentCd,
			String employeeId, GeneralDate criteriaDate);
}
