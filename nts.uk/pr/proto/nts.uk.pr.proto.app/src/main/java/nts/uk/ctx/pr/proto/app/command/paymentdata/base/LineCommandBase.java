package nts.uk.ctx.pr.proto.app.command.paymentdata.base;

import java.util.List;

import lombok.Value;

@Value
public class LineCommandBase {

	/**
	 * 行
	 */
	int linePosition;

	List<DetailItemCommandBase> details;

	public static LineCommandBase fromDomain(int linePosition, List<DetailItemCommandBase> details) {
		return new LineCommandBase(linePosition, details);
	}
}
