package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import lombok.AllArgsConstructor;

/**
 * @author thanhnx
 * するしない区分
 */
@AllArgsConstructor
@SuppressWarnings("unused")
public enum DoWork {
	    // しない
		NOTUSE(0, "しない"),

		/** The pattern schedule. */
		// する 
		USE(1, "する");
		
		public final int code;

		public final String name;
		
		private final static DoWork[] values = DoWork.values();
}
