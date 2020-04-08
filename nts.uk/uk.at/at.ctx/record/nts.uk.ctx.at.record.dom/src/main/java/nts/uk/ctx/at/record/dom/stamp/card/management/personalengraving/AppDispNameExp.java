package nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author anhdt
 *
 */
@Builder
@AllArgsConstructor
@Data
public class AppDispNameExp {

	// 会社ID
	private String companyId;
	// 申請種類
	private int appType;
	// 表示名
	private String dispName;

}
