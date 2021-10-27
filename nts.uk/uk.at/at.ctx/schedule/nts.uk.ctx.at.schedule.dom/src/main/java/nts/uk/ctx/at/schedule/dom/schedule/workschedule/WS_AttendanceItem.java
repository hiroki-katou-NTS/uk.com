package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;

import lombok.val;

public enum WS_AttendanceItem {
	
	WorkType( 28 , "勤務種類コード"),
	WorkTime( 29 , "就業時間帯コード"),
	
	StartTime1( 31, "出勤時刻1"),
	EndTime1( 34, "退勤時刻1"),
	
	StartTime2( 41, "出勤時刻1"),
	EndTime2( 44, "退勤時刻1"),
	
	StartBreakTime1( 157, "休憩開始時刻1"),
	EndBreakTime1( 159, "休憩開始時刻1"),
	
	StartBreakTime2( 163, "休憩開始時刻2"),
	EndBreakTime2( 165, "休憩開始時刻2"),
	
	StartBreakTime3( 169, "休憩開始時刻3"),
	EndBreakTime3( 171, "休憩開始時刻3"),
	
	StartBreakTime4( 175, "休憩開始時刻4"),
	EndBreakTime4( 177, "休憩開始時刻4"),
	
	StartBreakTime5( 181, "休憩開始時刻5"),
	EndBreakTime5( 183, "休憩開始時刻5"),
	
	StartBreakTime6( 187, "休憩開始時刻6"),
	EndBreakTime6( 189, "休憩開始時刻6"),
	
	StartBreakTime7( 193, "休憩開始時刻7"),
	EndBreakTime7( 195, "休憩開始時刻7"),
	
	StartBreakTime8( 199, "休憩開始時刻8"),
	EndBreakTime8( 201, "休憩開始時刻8"),
	
	StartBreakTime9( 205, "休憩開始時刻9"),
	EndBreakTime9( 207, "休憩開始時刻9"),
	
	StartBreakTime10( 211, "休憩開始時刻10"),
	EndBreakTime10( 213, "休憩開始時刻10"),
	
	GoStraight( 859, "休憩開始時刻10"),
	BackStraight( 860, "休憩開始時刻10");
	
	public final int ID;

	public final String name;
	
	private WS_AttendanceItem(int id, String name) {
		this.ID = id;
		this.name = name;
	}
	
	private final static WS_AttendanceItem[] values = WS_AttendanceItem.values();
	
	public static WS_AttendanceItem valueOf(int ID) {

		// Find value
		for (WS_AttendanceItem val : WS_AttendanceItem.values) {
			if (val.ID == ID) {
				return val;
			}
		}
		
		throw new IllegalArgumentException(String.format("not found: %d of %s", ID, WS_AttendanceItem.class.getName()));
	}
	
	/**
	 * 休憩時刻であるか
	 * @param attendanceId 勤怠項目ID
	 * @return
	 */
	public static boolean isBreakTime(int attendanceId) {
		val enumValue = WS_AttendanceItem.valueOf(attendanceId);
		switch (enumValue) {
		case StartBreakTime1:
		case StartBreakTime2:
		case StartBreakTime3:
		case StartBreakTime4:
		case StartBreakTime5:
		case StartBreakTime6:
		case StartBreakTime7:
		case StartBreakTime8:
		case StartBreakTime9:
		case StartBreakTime10:
		case EndBreakTime1:
		case EndBreakTime2:
		case EndBreakTime3:
		case EndBreakTime4:
		case EndBreakTime5:
		case EndBreakTime6:
		case EndBreakTime7:
		case EndBreakTime8:
		case EndBreakTime9:
		case EndBreakTime10:
			return true;
		default:
			return false;
		}
	}
	
	public static List<WS_AttendanceItem> getBreakTimeItemWithSize(int breakTimeSize) {
		List<WS_AttendanceItem> breakTimeItems = new ArrayList<>();
		if ( breakTimeSize >= 1) {
			breakTimeItems.add(StartBreakTime1);
			breakTimeItems.add(EndBreakTime1);
		}
		if ( breakTimeSize >= 2) {
			breakTimeItems.add(StartBreakTime2);
			breakTimeItems.add(EndBreakTime2);
		}
		if ( breakTimeSize >= 3) {
			breakTimeItems.add(StartBreakTime3);
			breakTimeItems.add(EndBreakTime3);
		}
		if ( breakTimeSize >= 4) {
			breakTimeItems.add(StartBreakTime4);
			breakTimeItems.add(EndBreakTime4);
		}
		if ( breakTimeSize >= 5) {
			breakTimeItems.add(StartBreakTime5);
			breakTimeItems.add(EndBreakTime5);
		}
		if ( breakTimeSize >= 6) {
			breakTimeItems.add(StartBreakTime6);
			breakTimeItems.add(EndBreakTime6);
		}
		if ( breakTimeSize >= 7) {
			breakTimeItems.add(StartBreakTime7);
			breakTimeItems.add(EndBreakTime7);
		}
		if ( breakTimeSize >= 8) {
			breakTimeItems.add(StartBreakTime8);
			breakTimeItems.add(EndBreakTime8);
		}
		if ( breakTimeSize >= 9) {
			breakTimeItems.add(StartBreakTime9);
			breakTimeItems.add(EndBreakTime9);
		}
		if ( breakTimeSize >= 10) {
			breakTimeItems.add(StartBreakTime10);
			breakTimeItems.add(EndBreakTime10);
		}
		
		return breakTimeItems;
	}

}
