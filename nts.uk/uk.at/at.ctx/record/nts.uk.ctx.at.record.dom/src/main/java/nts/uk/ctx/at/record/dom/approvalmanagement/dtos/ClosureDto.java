/**
 * 8:55:59 AM Mar 12, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class ClosureDto {
	public int closureId;
	public String closureName;
	public int currentYearMonth;
}
