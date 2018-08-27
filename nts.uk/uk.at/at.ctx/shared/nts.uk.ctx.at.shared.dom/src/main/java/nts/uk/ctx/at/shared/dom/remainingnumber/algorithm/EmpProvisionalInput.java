package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 社員リスト
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmpProvisionalInput {
	
	/**
	 * 社員ID
	 */
	private String employeeID;
	
	/**
	 * 年月日(List)
	 */
	private List<GeneralDate> dateLst;
	
}
