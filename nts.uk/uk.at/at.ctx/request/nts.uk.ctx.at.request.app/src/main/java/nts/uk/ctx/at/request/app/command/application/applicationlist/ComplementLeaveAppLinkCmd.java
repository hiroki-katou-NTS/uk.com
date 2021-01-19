package nts.uk.ctx.at.request.app.command.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ComplementLeaveAppLink;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ComplementLeaveAppLinkCmd {
	/**
	 * 振休振出フラグ
	 */
	private Integer complementLeaveFlg;
	
	/**
	 * 紐付け申請ID
	 */
	private String linkAppID;
	
	/**
	 * 紐付け申請日
	 */
	private String linkAppDate;
	
	public ComplementLeaveAppLink toDomain() {
		ComplementLeaveAppLink complementLeaveAppLink = new ComplementLeaveAppLink();
		complementLeaveAppLink.setComplementLeaveFlg(complementLeaveFlg);
		complementLeaveAppLink.setLinkAppID(linkAppID);
		complementLeaveAppLink.setLinkAppDate(GeneralDate.fromString(linkAppDate, "yyyy/MM/dd"));
		return complementLeaveAppLink;
	}
}
