package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanhpv
 * 選択可能定年退職コース
 */
@AllArgsConstructor
@Getter
public class EnableRetirePlanCourse extends DomainObject{

	/** 定年退職コースID */
	private long retirePlanCourseId;
	
}
