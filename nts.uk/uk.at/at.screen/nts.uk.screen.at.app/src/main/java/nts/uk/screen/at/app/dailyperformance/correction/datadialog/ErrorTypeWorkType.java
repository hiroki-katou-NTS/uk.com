package nts.uk.screen.at.app.dailyperformance.correction.datadialog;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.ReasonGoOut;

@AllArgsConstructor
@SuppressWarnings("unused")
public enum ErrorTypeWorkType {

		MASTER(0, "GET_ALL"),
		
		GROUP(1, "GROUP"),

		NO_GROUP(2, "NOT_GROUP");

		public final int code;
		
		public final String name;
		
		private final static ReasonGoOut[] values = ReasonGoOut.values();
}
