package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

public interface StampDakokuRepository {

	/**
	 * [1] insert(打刻)
	 * @param stamp
	 */
	public void insert(Stamp stamp);

	/**
	 * [2] delete(打刻)
	 * @param stampNumber
	 * @param stampDateTime
	 */
	public void delete(String stampNumber, GeneralDateTime stampDateTime);

	/**
	 * [3] update(打刻)
	 * @param stamp
	 */
	public void update(Stamp stamp);

	/**
	 * [4] 取得する
	 * @param stampNumbers
	 * @param stampDateTime
	 * @return
	 */
	public List<Stamp> get(List<StampNumber> stampNumbers, GeneralDate stampDate);

	/**
	 * [5] 打刻カード未登録の打刻データを取得する
	 * @param period
	 * @return
	 */
	public List<Stamp> getStempRcNotResgistNumber(DatePeriod period);
	
	public List<Stamp> getByListCard(List<String> stampNumbers);
	
	public List<Stamp> getByDateperiod(String companyId,DatePeriod period);
	
	public List<Stamp> getByCardAndPeriod(String companyId,List<String> listCard,DatePeriod period);
	
	public List<Stamp> getByDateTimeperiod(String companyId,GeneralDateTime startDate, GeneralDateTime endDate);
}
