package nts.uk.ctx.at.function.dom.workledgeroutputitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 印刷する勤怠項目
 *
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class AttendanceItemToPrint extends ValueObject {

	// 勤怠項目ID
	private int attendanceId;

	// 順位
	private int ranking;

	/**
     * @param rankingList		List<順位>
	 * @param attendanceIdList	List<勤怠項目ID>
	 * @return List<印刷する勤怠項目>
	 */
	public static List<AttendanceItemToPrint> createList(List<Integer> rankingList, List<Integer> attendanceIdList) {
		if (CollectionUtil.isEmpty(attendanceIdList) || CollectionUtil.isEmpty(rankingList)) {
			return Collections.emptyList();// TODO or throw some Exception?
		}

		if (attendanceIdList.size() != rankingList.size()) {
			// TODO throw some Exception?
		}

		List<AttendanceItemToPrint> attendanceList = new ArrayList<>();
		for (int i =0; i < attendanceIdList.size(); i++) {
			attendanceList.add(new AttendanceItemToPrint(attendanceIdList.get(i), rankingList.get(i)));
		}

		return attendanceList;
	}
}
