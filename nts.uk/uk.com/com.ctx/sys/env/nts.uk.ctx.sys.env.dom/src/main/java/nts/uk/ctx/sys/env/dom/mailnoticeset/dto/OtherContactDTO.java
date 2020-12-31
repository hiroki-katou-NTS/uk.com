package nts.uk.ctx.sys.env.dom.mailnoticeset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nws-ducnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtherContactDTO {
	private int no;

	// 在席照会に表示するか
	private boolean isDisplay;

	private String address;
}
