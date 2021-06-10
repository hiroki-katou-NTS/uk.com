package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

/**
 * 個人利用の打刻設定Repository
 *
 */
public interface StampPageLayoutRepository {
	
	/**
	 * insert(個人利用の打刻設定)
	 * @param stampSettingPerson
	 */
	public void insert(StampSettingPerson stampSettingPerson);
	
}
