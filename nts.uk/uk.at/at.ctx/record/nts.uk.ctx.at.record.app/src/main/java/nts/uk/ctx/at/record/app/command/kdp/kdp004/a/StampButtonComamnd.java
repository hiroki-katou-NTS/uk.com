package nts.uk.ctx.at.record.app.command.kdp.kdp004.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StampButtonComamnd {
	/** ページNO */
	private Integer pageNo;

	/** ボタン位置NO */
	private Integer buttonPositionNo;
}
