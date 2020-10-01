package nts.uk.ctx.at.record.dom.adapter.workschedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;

/**
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class WorkTimeInformationImport implements DomainObject {
	//時刻変更理由
	private ReasonTimeChangeImport reasonTimeChange;

	@Setter
	//時刻 TimeWithDayAttr
	private Integer timeWithDay;
	public WorkTimeInformationImport(ReasonTimeChangeImport reasonTimeChange, Integer timeWithDay) {
		super();
		this.reasonTimeChange = reasonTimeChange;
		this.timeWithDay = timeWithDay;
	}
	

}
