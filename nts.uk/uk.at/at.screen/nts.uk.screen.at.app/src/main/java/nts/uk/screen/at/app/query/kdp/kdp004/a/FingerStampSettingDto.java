package nts.uk.screen.at.app.query.kdp.kdp004.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampResultDisplayDto;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FingerStampSettingDto {
	// 共有打刻の打刻設定
	private StampSetCommunalDto stampSetCommunal;

	// 打刻後の実績表示
	private StampResultDisplayDto stampResultDisplay;

}
