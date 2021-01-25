package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ComplementLeaveAppLink;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class CompLeaveAppDataOutput {
	/**
	 * 申請内容
	 */
	private String content;
	
	/**
	 * 振休振出申請紐付け
	 */
	private ComplementLeaveAppLink complementLeaveAppLink;
}
