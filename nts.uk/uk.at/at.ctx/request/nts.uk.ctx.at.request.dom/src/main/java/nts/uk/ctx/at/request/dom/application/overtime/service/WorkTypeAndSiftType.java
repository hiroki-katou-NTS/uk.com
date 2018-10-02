package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeAndSiftType {
	/**
	 * workType
	 */
	private WorkTypeOvertime workType;
	/**
	 * siftType
	 */
	private SiftType siftType;
	
	/**
	 * 控除時間帯(丸め付き)
	 */
	List<DeductionTime>  breakTimes;
}
