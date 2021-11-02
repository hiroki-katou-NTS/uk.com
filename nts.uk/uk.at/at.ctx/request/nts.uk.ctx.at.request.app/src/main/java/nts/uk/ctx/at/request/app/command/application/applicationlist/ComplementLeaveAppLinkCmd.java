package nts.uk.ctx.at.request.app.command.application.applicationlist;

import org.apache.logging.log4j.util.Strings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
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
	 * 申請
	 */
	private ApplicationDto application;
	
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
		complementLeaveAppLink.setComplementLeaveFlg(complementLeaveFlg==null ? null : complementLeaveFlg);
		complementLeaveAppLink.setApplication(application==null ? null : application.toDomain());
		complementLeaveAppLink.setLinkAppID(Strings.isBlank(linkAppID) ? null : linkAppID);
		complementLeaveAppLink.setLinkAppDate(Strings.isBlank(linkAppDate) ? null : GeneralDate.fromString(linkAppDate, "yyyy/MM/dd"));
		return complementLeaveAppLink;
	}
}
