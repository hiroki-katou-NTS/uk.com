package nts.uk.ctx.at.record.pub.dailyperform.master;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         勤怠項目のマスタの全部
 */
@Getter
public class AllMasterAttItemExport {

	// 種類
	private int type;

	private List<MasterAttItemDetailExport> lstDetail;

	public AllMasterAttItemExport(int type, List<MasterAttItemDetailExport> lstDetail) {
		super();
		this.type = type;
		this.lstDetail = lstDetail;
	}

	// 勤怠項目のマスタ
	@AllArgsConstructor
	@Getter
	public static class MasterAttItemDetailExport {

		// ID
		private String id;

		// コード
		private String code;

		// 名称
		private String name;

	}

}
