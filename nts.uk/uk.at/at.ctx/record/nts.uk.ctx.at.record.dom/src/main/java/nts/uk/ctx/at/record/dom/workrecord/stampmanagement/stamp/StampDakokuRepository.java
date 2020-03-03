package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.List;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;

public interface StampDakokuRepository {

	public void insert(Stamp stamp);

	public void delete(String stampNumber, GeneralDateTime stampDateTime);

	public void update(Stamp stamp);

	public List<Stamp> get(List<StampNumber> stampNumbers, GeneralDateTime stampDateTime);

	public List<Stamp> getStempRcNotResgistNumber(DatePeriod period);
}
