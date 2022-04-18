package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master;

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
public class AllMasterAttItem {

	// 種類
	private TypesMasterRelatedDailyAttendanceItem type;

	//勤怠項目のマスタ
	private List<MasterAttItemDetail> lstDetail;

	public AllMasterAttItem(TypesMasterRelatedDailyAttendanceItem type, List<MasterAttItemDetail> lstDetail) {
		super();
		this.type = type;
		this.lstDetail = lstDetail;
	}

	// 勤怠項目のマスタ
	@AllArgsConstructor
	@Getter
	public static class MasterAttItemDetail {

		// ID
		private String id;

		// コード
		private String code;

		// 名称
		private String name;

	}

}
