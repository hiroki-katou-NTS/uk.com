package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.PageComment;
import nts.uk.ctx.at.record.dom.stamp.management.PageNo;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageComment;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageLayout;
import nts.uk.ctx.at.record.dom.stamp.management.StampPageName;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
/**
 * 
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class StampPageLayoutCommand {

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

	public StampPageLayout toDomain() {
		PageNo pageNos = new PageNo(pageNo);
		StampPageName stampPageNames = new StampPageName(stampPageName);
		StampPageComment stampPageComments = new StampPageComment(new PageComment(stampPageComment.getPageComment()),
				new ColorCode(stampPageComment.getCommentColor()));
		ButtonLayoutType buttonLayoutTypes = EnumAdaptor.valueOf(buttonLayoutType, ButtonLayoutType.class);
		List<ButtonSettings> lstButtonSets = lstButtonSet.stream().map(x -> ButtonSettingsCommand.toDomain(x)).collect(Collectors.toList());
		return new StampPageLayout(pageNos, stampPageNames, stampPageComments, buttonLayoutTypes, lstButtonSets);
	}

	public StampPageLayoutCommand(int pageNo, String stampPageName, StampPageCommentCommand stampPageComment,
			int buttonLayoutType, List<ButtonSettingsCommand> lstButtonSet) {
		super();
		this.pageNo = pageNo;
		this.stampPageName = stampPageName;
		this.stampPageComment = stampPageComment;
		this.buttonLayoutType = buttonLayoutType;
		this.lstButtonSet = lstButtonSet;
	}
}
