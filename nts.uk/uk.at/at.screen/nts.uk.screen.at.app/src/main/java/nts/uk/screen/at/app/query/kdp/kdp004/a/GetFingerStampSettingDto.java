package nts.uk.screen.at.app.query.kdp.kdp004.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampResultDisplayDto;

/**
 * 【output】
 * ・共有打刻の打刻設定
 * ・打刻後の実績表示
 * ・ お知らせメッセージ設定
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetFingerStampSettingDto {
	
	// 共有打刻の打刻設定
	private StampSetCommunalDto stampSetting;

	// 打刻後の実績表示
	private StampResultDisplayDto stampResultDisplay;
	
	// お知らせメッセージ設定
	private NoticeSetDto noticeSetDto;

}
