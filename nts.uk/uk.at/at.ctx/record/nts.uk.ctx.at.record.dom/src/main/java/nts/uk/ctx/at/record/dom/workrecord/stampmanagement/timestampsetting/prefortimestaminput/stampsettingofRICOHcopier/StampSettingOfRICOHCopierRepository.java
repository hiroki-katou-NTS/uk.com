/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier;

import java.util.Optional;

/**
 * @author ThanhPV
 * RICOH複写機の打刻設定Repository
 */
public interface StampSettingOfRICOHCopierRepository {

	/**
	 * [1]  insert(RICOH複写機の打刻設定)
	 */
	public void insert(StampSettingOfRICOHCopier stampSettingOfRICOHCopier);

	/**
	 * [2]  update(RICOH複写機の打刻設定)
	 */
	public void update(StampSettingOfRICOHCopier stampSettingOfRICOHCopier);
	
	/**
	 * [3]  get
	 */
	public Optional<StampSettingOfRICOHCopier> get(String cid);

}
