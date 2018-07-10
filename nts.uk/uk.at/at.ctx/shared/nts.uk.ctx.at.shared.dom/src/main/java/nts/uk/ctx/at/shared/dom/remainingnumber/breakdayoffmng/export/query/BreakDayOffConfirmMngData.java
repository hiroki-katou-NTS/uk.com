package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BreakDayOffConfirmMngData {
	/**
	 * 休出代休紐付け管理
	 */
	private List<LeaveComDayOffManagement> typingMngData;
	/**
	 * 休出管理データ
	 */
	private List<LeaveManagementData> breakMngData;
	/**
	 * 代休管理データ
	 */
	private List<CompensatoryDayOffManaData> dayOffData;
}
