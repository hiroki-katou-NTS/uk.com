/**
 * 10:58:31 AM Aug 22, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@AllArgsConstructor
public class ClosureDto {
	private String companyId;
	private Integer closureId;
	private Integer useAtr;
	private Integer closureMonth;
	private String sid;
	public ClosureDto(String companyId, Integer closureId, Integer useAtr, Integer closureMonth){
		this.companyId = companyId;
		this.closureId = closureId;
		this.useAtr = useAtr;
		this.closureMonth = closureMonth;
	}
}

