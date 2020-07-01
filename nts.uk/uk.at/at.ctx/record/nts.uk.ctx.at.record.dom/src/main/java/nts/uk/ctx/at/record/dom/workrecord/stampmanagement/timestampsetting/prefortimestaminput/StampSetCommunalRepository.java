package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

/**
 * RP: 共有打刻の打刻設定Repository
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.共有打刻の打刻設定Repository
 * @author chungnt
 *
 */
public interface StampSetCommunalRepository {
	
	/**
	 * 	[1]  insert(共有打刻の打刻設定)
	 * @param domain
	 */
	public void insert(StampSetCommunal domain);

	/**
	 * 	[2]  save(共有打刻の打刻設定)
	 * @param domain
	 */
	public void save(StampSetCommunal domain);

	/**
	 * 	[3]  取得する
	 * @param comppanyID
	 * @return
	 */
	public Optional<StampSetCommunal> gets(String comppanyID);

}
