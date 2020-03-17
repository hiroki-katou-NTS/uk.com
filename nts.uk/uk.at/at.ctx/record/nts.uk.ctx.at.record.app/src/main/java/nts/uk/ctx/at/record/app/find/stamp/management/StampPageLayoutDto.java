package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.command.stamp.management.ButtonDisSetCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.ButtonNameSetCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.ButtonSettingsCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.ButtonTypeCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.StampPageCommentCommand;
import nts.uk.ctx.at.record.app.command.stamp.management.StampTypeCommand;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;

@Data
@AllArgsConstructor
public class StampPageLayoutDto {

	/** ページNO */
	private int pageNo;

	/** ページ名 */
	private String stampPageName;

	/** ページコメント */
	private StampPageCommentCommand stampPageComment;

	/** ボタン配置タイプ */
	private int buttonLayoutType;

	/** ボタン詳細設定リスト */
	private List<ButtonSettingsCommand> lstButtonSet;

	public static StampPageLayoutDto fromDomain(StampPageLayout pageLayout) {
		StampPageCommentCommand stampPageComments = new StampPageCommentCommand(pageLayout.getStampPageComment().getPageComment().v(), pageLayout.getStampPageComment().getCommentColor().v());
		List<ButtonSettingsCommand> lstButtonSets = pageLayout.getLstButtonSet().stream().map(x->{
			ButtonNameSetCommand buttonNameSet = new ButtonNameSetCommand(x.getButtonDisSet().getButtonNameSet().getTextColor().v(), 
					x.getButtonDisSet().getButtonNameSet().getButtonName() == null ? null : x.getButtonDisSet().getButtonNameSet().getButtonName().get().v());
			ButtonDisSetCommand buttonDisSet = new ButtonDisSetCommand(buttonNameSet, x.getButtonDisSet().getBackGroundColor().v());
			
			StampTypeCommand stampType = new StampTypeCommand(x.getButtonType().getStampType().isChangeHalfDay(),
					x.getButtonType().getStampType().getGoOutArt() == null ? null : x.getButtonType().getStampType().getGoOutArt().get().value,
					x.getButtonType().getStampType().getSetPreClockArt().value,
					x.getButtonType().getStampType().getChangeClockArt().value,
					x.getButtonType().getStampType().getChangeCalArt().value);
			
			ButtonTypeCommand buttonType = new ButtonTypeCommand(x.getButtonType().getReservationArt().value, stampType);
			return new ButtonSettingsCommand(x.getButtonPositionNo().v(),
					buttonDisSet,
					buttonType,
					x.getUsrArt().value,
					x.getAudioType().value);
		}).collect(Collectors.toList());
		
		return new StampPageLayoutDto(
				pageLayout.getPageNo().v(), 
				pageLayout.getStampPageName().v(), 
				stampPageComments, 
				pageLayout.getButtonLayoutType().value, 
				lstButtonSets);
	}
}
