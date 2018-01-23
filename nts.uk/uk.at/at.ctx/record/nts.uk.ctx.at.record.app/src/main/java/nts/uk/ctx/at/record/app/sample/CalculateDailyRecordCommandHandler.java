//package nts.uk.ctx.at.record.app.sample;
//
//import nts.uk.ctx.at.record.dom.sample.CalculateDailyRecordService;
//
//public class CalculateDailyRecordCommandHandler {
//
//	@Inject
//	private CalculateDailyRecordService calculate;
//	
//	public void handle() {
//		
//		DailyRecord dailyRecord = this.repository.find();
//		
//		this.calculate.calculate(dailyRecord, dr -> new ConverterImpl(dr));
//	}
//	
//	
//	public static class ConverterImpl implements CalculateDailyRecordService.DailyRecordToAttendanceItemConverter {
//
//		private DailyRecordDto dto;
//		
//		public ConverterImpl(CalculateDailyRecordService.DailyRecord dailyRecord) {
//			
//		}
//		
//		@Override
//		public Object convert(String attendanceItemId) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
//}
