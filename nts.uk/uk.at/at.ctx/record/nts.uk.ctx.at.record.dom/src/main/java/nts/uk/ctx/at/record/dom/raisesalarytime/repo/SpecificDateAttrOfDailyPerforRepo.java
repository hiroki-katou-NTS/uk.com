package nts.uk.ctx.at.record.dom.raisesalarytime.repo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;

public interface SpecificDateAttrOfDailyPerforRepo {

	public Optional<SpecificDateAttrOfDailyPerfor> find(String employeeId, GeneralDate baseDate);

	public List<SpecificDateAttrOfDailyPerfor> find(String employeeId, List<GeneralDate> baseDate);
	
	public List<SpecificDateAttrOfDailyPerfor> find(String employeeId);

	public void update(SpecificDateAttrOfDailyPerfor domain);

	public void add(SpecificDateAttrOfDailyPerfor domain);
}
