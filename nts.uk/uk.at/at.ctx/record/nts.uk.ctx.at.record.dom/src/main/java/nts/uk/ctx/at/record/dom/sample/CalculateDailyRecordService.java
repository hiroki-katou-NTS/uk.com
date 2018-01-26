//package nts.uk.ctx.at.record.dom.sample;
//
//import java.util.function.Function;
//
//public class CalculateDailyRecordService {
//
//	public void calculate(
//			DailyRecord dailyRecord,
//			Function<DailyRecord, DailyRecordToAttendanceItemConverter> converterBulder) {
//		
//		/* calculate daily record ... */
//		
//		DailyRecordToAttendanceItemConverter converter = converterBulder.apply(dailyRecord);
//		converter.convert("1");
//	}
//	
//	public interface DailyRecordToAttendanceItemConverter {
//		
//		Object convert(String attendanceItemId);
//	}
//	
//	public static class DailyRecord {
//		
//	}
//}
