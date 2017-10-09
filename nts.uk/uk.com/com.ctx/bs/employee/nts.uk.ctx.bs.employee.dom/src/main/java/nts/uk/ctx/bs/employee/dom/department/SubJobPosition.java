package nts.uk.ctx.bs.employee.dom.department;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 職務職位（兼務）
 * @author xuan vinh
 * */
@Getter
@AllArgsConstructor
public class SubJobPosition {
	/**職務職位ID（兼務） sub job position id*/
	private String subJobPosId;
	/**所属部門ID affiliation department id*/
	private String affiDeptId;
	/**役職ID job title id*/
	private String jobTitleId;
	/**Start date*/
	private Date startDate;
	/**End date*/
	private Date endDate;
}
