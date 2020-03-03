package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.List;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

public interface StampRecordRepository {

	public void insert(StampRecord stampRecord);

	public void delete(String stampNumber, GeneralDateTime stampDateTime);

	public void update(StampRecord stampRecord);

	public List<StampRecord> get(List<StampNumber> stampNumbers, GeneralDateTime stampDateTime);

	public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period);

}
