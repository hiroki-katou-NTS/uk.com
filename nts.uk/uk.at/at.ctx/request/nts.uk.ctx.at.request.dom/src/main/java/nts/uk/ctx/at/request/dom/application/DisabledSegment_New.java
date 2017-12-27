package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * するしない区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum DisabledSegment_New {
	
	// しない
	NOTTODO(0, "しない"),
	
	// する
	TODO(1, "する");
	
	public final Integer value;
	
	public final String name;
}
