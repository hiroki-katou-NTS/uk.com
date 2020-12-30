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

	private boolean isDisplay;

	private String address;
}
