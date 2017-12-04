package nts.uk.ctx.at.request.dom.application.overtime.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
