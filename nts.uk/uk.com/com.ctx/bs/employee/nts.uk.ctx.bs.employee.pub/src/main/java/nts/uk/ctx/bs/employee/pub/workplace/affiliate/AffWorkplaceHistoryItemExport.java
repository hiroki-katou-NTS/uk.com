package nts.uk.ctx.bs.employee.pub.workplace.affiliate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * 所属職場履歴項目Exported
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class AffWorkplaceHistoryItemExport {

	/** The history Id. */
	// 履歴ID
	private final String historyId;

	/** The Employee Id. */
	// 社員ID
	private final String employeeId;

	/** The workplaceId. */
	// 職場ID
	private final String  workplaceId;

	/** The normalWorkplaceCode. */
	// 通常職場コード
	private final String  normalWorkplaceId;

	private final Optional<String> workLocationCode;

}
