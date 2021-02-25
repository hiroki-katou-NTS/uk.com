package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;

/**
 * Repository 出力条件設定
 */
public interface OutputPeriodSettingRepository {
	
	/**
	 * Find OutputPeriodSetting by id
	 * @param cid
	 * @param conditionSetCd
	 * @return
	 */
	Optional<OutputPeriodSetting> findById(String cid, String conditionSetCd);
	
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void add(OutputPeriodSetting domain);

	/**
	 * Update OutputPeriodSetting
	 * @param domain
	 */
    void update(String cid, String conditionSetCd, OutputPeriodSetting domain);

}
