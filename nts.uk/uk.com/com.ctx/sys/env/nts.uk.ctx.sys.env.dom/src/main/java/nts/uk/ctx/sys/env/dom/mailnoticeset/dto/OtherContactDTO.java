package nts.uk.ctx.sys.env.dom.mailnoticeset.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author nws-ducnt
 *
 */
@Data
@Builder
public class OtherContactDTO {
	private int no;

	// 在席照会に表示するか
	private boolean isDisplay;

	private String address;
}
