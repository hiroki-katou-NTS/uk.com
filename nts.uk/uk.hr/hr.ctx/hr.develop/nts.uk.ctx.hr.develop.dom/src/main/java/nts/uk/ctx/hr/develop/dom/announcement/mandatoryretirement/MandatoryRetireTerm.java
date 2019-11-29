package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author thanhpv
 * 定年退職条件
 */
@AllArgsConstructor
@Getter
public class MandatoryRetireTerm extends DomainObject{

	/** 共通項目ID  */
	private String empCommonMasterItemId;
	
	/** 定年有無 */
	private boolean usageFlg;
	
	/** 選択可能定年退職コース */
	private List<EnableRetirePlanCourse> enableRetirePlanCourse;
	
}
