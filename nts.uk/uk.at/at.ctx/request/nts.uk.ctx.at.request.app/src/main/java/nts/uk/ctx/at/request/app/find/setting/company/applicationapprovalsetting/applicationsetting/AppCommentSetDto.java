package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppCommentSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.Comment;
import nts.uk.shr.com.color.ColorCode;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppCommentSetDto {
	/**
	 * コメント
	 */
	private String comment;

	/**
	 * 太字
	 */
	private boolean bold;

	/**
	 * 文字色
	 */
	private String ColorCode;

	public static AppCommentSetDto fromDomain(AppCommentSet appCommentSet) {
		return new AppCommentSetDto(appCommentSet.getComment().v(), appCommentSet.isBold(),
				appCommentSet.getColorCode().v());

	}
	public AppCommentSet toDomain() {
		AppCommentSet appCommentSet = new AppCommentSet();
		appCommentSet.setComment(new Comment(comment));
		appCommentSet.setBold(bold);
		appCommentSet.setColorCode(new ColorCode(ColorCode));
		return appCommentSet;
	}
}
