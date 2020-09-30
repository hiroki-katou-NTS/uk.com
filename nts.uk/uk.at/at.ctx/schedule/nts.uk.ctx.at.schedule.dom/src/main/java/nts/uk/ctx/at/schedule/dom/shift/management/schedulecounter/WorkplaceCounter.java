package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@AllArgsConstructor
@Getter
public class WorkplaceCounter implements DomainAggregate{
	
	private Map<WorkplaceCounterCategory, NotUseAtr> categories;

}
