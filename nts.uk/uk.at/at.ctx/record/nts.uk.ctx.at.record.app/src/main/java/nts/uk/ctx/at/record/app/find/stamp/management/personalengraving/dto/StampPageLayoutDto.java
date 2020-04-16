package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageComment;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;

/**
 * @author anhdt
 *
 */
@Data
public class StampPageLayoutDto {
	private Integer pageNo;
	private String stampPageName;
	private String stampPageComment;
	private String stampPageCommentColor;
	private Integer buttonLayoutType;
	List<ButtonSettingDto> buttonSettings = new ArrayList<>();
	
	public StampPageLayoutDto(StampPageLayout layout) {
		this.pageNo = layout.getPageNo().v();
		this.stampPageName = layout.getStampPageName().v();
		
		StampPageComment pageComment = layout.getStampPageComment();
		this.stampPageComment = pageComment.getPageComment().v();
		this.stampPageCommentColor = pageComment.getCommentColor().v();
		this.buttonLayoutType = layout.getButtonLayoutType().value;
		
		List<ButtonSettings> btnSets = layout.getLstButtonSet();
		
		btnSets.sort((b1, b2) -> b1.getButtonPositionNo().v().compareTo(b2.getButtonPositionNo().v()));
		for (ButtonSettings btnSet : btnSets) {
			this.buttonSettings.add(new ButtonSettingDto(btnSet));
		}
		
	}
}
