package nts.uk.file.at.app.export.dailyschedule.totalsum;

import java.util.List;

import lombok.Data;

/**
 * 職場計行
 * @author HoangNDH
 *
 */
@Data
public class WorkplaceTotal {
	// 職場ID
	private String workplaceId;
	// 職場合算値
	private List<TotalValue> totalWorkplaceValue;
}
