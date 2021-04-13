package nts.uk.ctx.at.record.pub.worklocation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 勤務場所名称Export
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLocationExportNew {
	/**
	 * 場所CD
	 */
	private String workLocationCD;
	/**
	 * 場所名
	 */
	private String workLocationName;

}
