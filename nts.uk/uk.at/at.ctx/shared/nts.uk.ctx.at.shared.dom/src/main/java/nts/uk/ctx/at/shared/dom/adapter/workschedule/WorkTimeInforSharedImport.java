package nts.uk.ctx.at.shared.dom.adapter.workschedule;

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
public class WorkTimeInforSharedImport implements DomainObject {
	//時刻変更理由
	private ReasonTimeChangeSharedImport reasonTimeChange;

	@Setter
	//時刻 TimeWithDayAttr
	private Integer timeWithDay;
	public WorkTimeInforSharedImport(ReasonTimeChangeSharedImport reasonTimeChange, Integer timeWithDay) {
		super();
		this.reasonTimeChange = reasonTimeChange;
		this.timeWithDay = timeWithDay;
	}
	

}
