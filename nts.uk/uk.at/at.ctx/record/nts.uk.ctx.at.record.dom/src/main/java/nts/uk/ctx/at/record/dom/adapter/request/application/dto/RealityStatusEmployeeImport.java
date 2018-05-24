package nts.uk.ctx.at.record.dom.adapter.request.application.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class RealityStatusEmployeeImport {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 開始日
	 */
	private GeneralDate startDate;
	/**
	 * 終了日
	 */
	private GeneralDate endDate;
}
