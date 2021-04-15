package nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting;

import java.util.Optional;

/**
 * @author thanhpv
 * @name 作業変更可能期間設定Repository
 */
public interface WorkChangeablePeriodSettingRepository {

	/**
	 * @name [1] Insert(作業変更可能期間設定)
	 */
	void insert(WorkChangeablePeriodSetting domain);
	
	/**
	 * @name [2] Delete(作業変更可能期間設定)
	 */
	void delete(WorkChangeablePeriodSetting domain);
	
	/**
	 * @name [3] Update(作業変更可能期間設定)
	 */
	void update(WorkChangeablePeriodSetting domain);
	
	/**
	 * @name [4] Get
	 * @Description 作業変更可能期間設定を取得する
	 * @input companyId 会社ID
	 * @output Optional<作業変更可能期間設定>
	 */
	Optional<WorkChangeablePeriodSetting> get(String companyId);
}
