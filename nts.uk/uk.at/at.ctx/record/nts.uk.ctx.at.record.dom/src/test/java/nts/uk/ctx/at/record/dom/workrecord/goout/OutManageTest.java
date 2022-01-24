package nts.uk.ctx.at.record.dom.workrecord.goout;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

public class OutManageTest {

	/**
	 * test [1] 外出に対応する日次の勤怠項目を絞り込む
	 */
	@Test
	public void testGetDaiLyAttendanceID() {
		List<Integer> listAttdId = new ArrayList<>();
		OutManage outManage = new OutManage("companyID", new MaxGoOut(4), GoingOutReason.PRIVATE); // dummy
		listAttdId = outManage.getDaiLyAttendanceID();
		assertThat(listAttdId).extracting(d -> d).containsExactly(86, 87, 88, 1196, 1197, 1216, 1217, 90, 91, 93, 94,
				95, 1198, 1199, 1218, 1219, 97, 98, 100, 101, 102, 1200, 1201, 1220, 1221, 104, 105, 107, 108, 109,
				1202, 1203, 1222, 1223, 111, 112, 114, 115, 116, 1204, 1205, 1224, 1225, 118, 119, 121, 122, 123, 1206,
				1207, 1226, 1227, 125, 126, 128, 129, 130, 1208, 1209, 1228, 1229, 132, 133, 135, 136, 137, 1210, 1211,
				1230, 1231, 139, 140, 142, 143, 144, 1212, 1213, 1232, 1233, 146, 147, 149, 150, 151, 1214, 1215, 1234,
				1235, 153, 154);

	}

	/**
	 * test [2] 利用できない日次の勤怠項目を取得する
	 */
	@Test
	public void testGetDaiLyAttendanceIDNotAvailable() {
		List<Integer> listAttdId = new ArrayList<>();
		// 最大使用回数 = 4
		OutManage outManage = new OutManage("companyID", new MaxGoOut(4), GoingOutReason.PRIVATE);
		listAttdId = outManage.getDaiLyAttendanceIDNotAvailable();
		assertThat(listAttdId).extracting(d -> d).containsExactly(114, 115, 116, 1204, 1205, 1224, 1225, 118, 119, 121,
				122, 123, 1206, 1207, 1226, 1227, 125, 126, 128, 129, 130, 1208, 1209, 1228, 1229, 132, 133, 135, 136,
				137, 1210, 1211, 1230, 1231, 139, 140, 142, 143, 144, 1212, 1213, 1232, 1233, 146, 147, 149, 150, 151,
				1214, 1215, 1234, 1235, 153, 154);
		
		// 最大使用回数 = 10
		outManage = new OutManage("companyID", new MaxGoOut(10), GoingOutReason.PRIVATE);
		listAttdId = outManage.getDaiLyAttendanceIDNotAvailable();
		assertThat(listAttdId.isEmpty()).isTrue();

	}

}
