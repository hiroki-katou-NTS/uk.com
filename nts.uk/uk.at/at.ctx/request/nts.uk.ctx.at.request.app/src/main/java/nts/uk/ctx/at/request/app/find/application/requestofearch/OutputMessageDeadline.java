package nts.uk.ctx.at.request.app.find.application.requestofearch;

import lombok.Value;

@Value
public class OutputMessageDeadline {
	//メッセージ：
	String message;
	//締め切り期限：
	String deadline;
	/**
	 * true: show, false: hidden
	 */
	boolean chkShow;
}
