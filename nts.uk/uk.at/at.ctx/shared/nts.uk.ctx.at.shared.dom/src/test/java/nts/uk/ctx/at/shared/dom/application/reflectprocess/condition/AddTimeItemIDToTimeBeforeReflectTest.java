package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.AttendanceBeforeApplicationReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.common.ReflectApplicationHelper;

public class AddTimeItemIDToTimeBeforeReflectTest {

	/*
	 * テストしたい内容
	 * 
	 * 
	 * →反映前の勤怠を作る
	 * 
	 * 　「申請反映前の勤怠」データが利用可能
	 * 
	 * 　「申請反映前の勤怠」データは利用できません
	 * 
	 * 準備するデータ
	 * 
	 * →勤怠項目ID
	 * 
	 * →「申請反映前の勤怠」データが利用可能
	 */
	@Test
	public void test() {

		List<AttendanceBeforeApplicationReflect> lstAtt = new ArrayList<>();
		lstAtt.add(new AttendanceBeforeApplicationReflect(1,// 勤怠項目ID
				"a", //値
				Optional.empty()));
		DailyRecordOfApplication domaindaily = ReflectApplicationHelper.createDailyRecord(lstAtt);

		AddTimeItemIDToTimeBeforeReflect.addTime(domaindaily, Arrays.asList(1, 2));
		
		assertThat(domaindaily.getAttendanceBeforeReflect())
		.extracting(x -> x.getAttendanceId(), x -> x.getValue(), x -> x.getEditState().orElse(null))
		.containsExactly(Tuple.tuple(1, 
														"",
														null), 
									Tuple.tuple(2, "", null));
	}

}
