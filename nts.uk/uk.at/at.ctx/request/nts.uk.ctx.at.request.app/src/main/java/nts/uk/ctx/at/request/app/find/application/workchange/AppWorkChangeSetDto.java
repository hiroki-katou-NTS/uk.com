package nts.uk.ctx.at.request.app.find.application.workchange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.AppCommentSetDto;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.AppWorkChangeSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange.InitDisplayWorktimeAtr;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppWorkChangeSetDto {
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * コメント1
	 */
	private AppCommentSetDto comment1;
	
	/**
	 * コメント2
	 */
	private AppCommentSetDto comment2;
	
	/**
	 * 勤務時間の初期表示
	 */
	private int initDisplayWorktimeAtr;
	
	
	public static AppWorkChangeSetDto fromDomain(AppWorkChangeSet appWorkChangeSet) {
		return new AppWorkChangeSetDto(
				appWorkChangeSet.getCompanyID(), 
				AppCommentSetDto.fromDomain(appWorkChangeSet.getComment1()), 
				AppCommentSetDto.fromDomain(appWorkChangeSet.getComment2()), 
				appWorkChangeSet.getInitDisplayWorktimeAtr().value);
	}
	public AppWorkChangeSet toDomain() {
		AppWorkChangeSet appWorkChangeSet = new AppWorkChangeSet();
		appWorkChangeSet.setCompanyID(companyID);
		appWorkChangeSet.setComment1(comment1.toDomain());
		appWorkChangeSet.setComment2(comment2.toDomain());
		appWorkChangeSet.setInitDisplayWorktimeAtr(EnumAdaptor.valueOf(initDisplayWorktimeAtr, InitDisplayWorktimeAtr.class));
		return appWorkChangeSet; 
	}
}
