package nts.uk.ctx.at.shared.ws.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anhdt
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindWorkInfoDto {
	private String workTypeCd;
	private String workTimeCd;
}
