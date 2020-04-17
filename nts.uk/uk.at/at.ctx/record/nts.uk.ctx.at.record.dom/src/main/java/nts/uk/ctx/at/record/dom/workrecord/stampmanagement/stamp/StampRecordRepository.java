package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

public interface StampRecordRepository {

	/**
	 * [1] insert(打刻記録)
	 * @param stampRecord
	 */
	public void insert(StampRecord stampRecord);

	/**
	 * [2] delete(打刻記録)
	 * @param stampNumber
	 * @param stampDateTime
	 */
	public void delete(String stampNumber, GeneralDateTime stampDateTime);

	/**
	 * [3] update(打刻記録)
	 * @param stampRecord
	 */
	public void update(StampRecord stampRecord);

	/**
	 * [4] 取得する
	 * @param stampNumbers
	 * @param stampDateTime
	 * @return
	 */
	public List<StampRecord> get(List<StampNumber> stampNumbers, GeneralDate stampDate);

	/**
	 *  [5] 打刻カード未登録の打刻記録データを取得する
	 * @param period
	 * @return
	 */
	public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period);

}
