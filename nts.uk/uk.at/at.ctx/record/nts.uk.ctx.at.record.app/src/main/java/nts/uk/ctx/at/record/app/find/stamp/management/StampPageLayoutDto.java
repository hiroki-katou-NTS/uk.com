package nts.uk.ctx.at.record.app.find.stamp.management;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;

/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class StampPageLayoutDto {

	/** ページNO */
	private int pageNo;

	/** ページ名 */
	private String stampPageName;

	/** ページコメント */
	private StampPageCommentDto stampPageComment;

	/** ボタン配置タイプ */
	private int buttonLayoutType;

	/** ボタン詳細設定リスト */
	private List<ButtonSettingsDto> lstButtonSet;

	public static StampPageLayoutDto fromDomain(StampPageLayout domain) {
		return new StampPageLayoutDto(
				domain.getPageNo().v(), 
				domain.getStampPageName().v(), 
				StampPageCommentDto.fromDomain(domain.getStampPageComment()),
				domain.getButtonLayoutType().value, 
				domain.getLstButtonSet().stream().map(c->ButtonSettingsDto.fromDomain(c)).collect(Collectors.toList()));
	}
}
