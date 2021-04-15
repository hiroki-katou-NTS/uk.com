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
public class ReasonTimeChangeSharedImport implements DomainObject {
	
	//時刻変更手段 enum TimeChangeMeans
	@Setter
	private int timeChangeMeans;
	
	//打刻方法 enum EngravingMethod
	private Integer engravingMethod;

	public ReasonTimeChangeSharedImport(int timeChangeMeans, Integer engravingMethod) {
		super();
		this.timeChangeMeans = timeChangeMeans;
		this.engravingMethod = engravingMethod;
	}
	
	

}
