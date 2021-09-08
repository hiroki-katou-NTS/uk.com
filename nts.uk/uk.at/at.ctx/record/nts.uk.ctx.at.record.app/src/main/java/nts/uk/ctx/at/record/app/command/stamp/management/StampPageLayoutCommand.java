package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
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
	
	/** 0:共有打刻, 1:個人利用, 2:ICカード, 3:スマホ打刻, 5:RICOH打刻 */
	private int stampMeans;

	public StampPageLayout toDomain() {
		PageNo pageNos = new PageNo(pageNo);
		StampPageName stampPageNames = new StampPageName(stampPageName);
		StampPageComment stampPageComments = stampPageComment.toDomain();
		ButtonLayoutType buttonLayoutTypes = EnumAdaptor.valueOf(buttonLayoutType, ButtonLayoutType.class);
		List<ButtonSettings> lstButtonSets = lstButtonSet.stream().map(x -> x.toDomain()).collect(Collectors.toList());
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
