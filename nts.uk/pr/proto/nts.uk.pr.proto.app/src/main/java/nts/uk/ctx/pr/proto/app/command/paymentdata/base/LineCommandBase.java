package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineCommandBase {

	/**
	 * 行
	 */
	int linePosition;

	List<DetailItemCommandBase> details;
}
