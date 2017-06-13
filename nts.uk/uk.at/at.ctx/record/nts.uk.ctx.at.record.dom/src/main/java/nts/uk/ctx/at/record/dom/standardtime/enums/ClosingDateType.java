package nts.uk.ctx.at.record.dom.standardtime.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
public enum ClosingDateType {
	// 0:  ‹Î‘Ó‚Ì’÷‚ß“ú‚Æ“¯‚¶
	SAME_AS_CLOSING_DATE(0),
	// 1: ’÷‚ß“ú‚ðŽw’è
	DESIGNATE_CLOSING_DATE(1);

	public final int value;

	public String toName() {
		String name;
		switch (value) {
		case 0:
			name = "‹Î‘Ó‚Ì’÷‚ß“ú‚Æ“¯‚¶";
			break;
		case 1:
			name = "’÷‚ß“ú‚ðŽw’è";
			break;
		default:
			name = "‹Î‘Ó‚Ì’÷‚ß“ú‚Æ“¯‚¶";
			break;
		}
		return name;
	}
}
