package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.applist.service.content.ComplementLeaveAppLink;

@AllArgsConstructor
@Getter
public class ComplementLeaveAppLinkDto {
	/**
	 * 振休振出フラグ
	 */
	private Integer complementLeaveFlg;
	
	/**
	 * 振休振出同時申請管理
	 */
	private AppHdsubRecDto appHdsubRec;
	
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
	
	public static ComplementLeaveAppLinkDto fromDomain(ComplementLeaveAppLink complementLeaveAppLink) {
		return new ComplementLeaveAppLinkDto(
				complementLeaveAppLink.getComplementLeaveFlg(), 
				complementLeaveAppLink.getAppHdsubRec()==null ? null : AppHdsubRecDto.fromDomain(complementLeaveAppLink.getAppHdsubRec()), 
				complementLeaveAppLink.getApplication()==null ? null : ApplicationDto.fromDomain(complementLeaveAppLink.getApplication()), 
				complementLeaveAppLink.getLinkAppID(), 
				complementLeaveAppLink.getLinkAppDate()==null ? null : complementLeaveAppLink.getLinkAppDate().toString());
	} 
}
