package nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;

/**
 * @author thanh_nx
 *
 *         勤怠項目のマスタの全部
 */
@Getter
public class AllMasterAttItemImport {

	// 種類
	private TypesMasterRelatedDailyAttendanceItem type;

	private List<MasterAttItemDetailmport> lstDetail;

	public AllMasterAttItemImport(TypesMasterRelatedDailyAttendanceItem type, List<MasterAttItemDetailmport> lstDetail) {
		super();
		this.type = type;
		this.lstDetail = lstDetail;
	}

	// 勤怠項目のマスタ
	@AllArgsConstructor
	@Getter
	public static class MasterAttItemDetailmport {

		// ID
		private String id;

		// コード
		private String code;

		// 名称
		private String name;

	}

}
