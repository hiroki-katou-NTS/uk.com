package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
/**
 * 日別のコンバーターを作成する
 * Sử dụng để từ shared gọi sang
 * @author phongtq
 *
 */
@Stateless
public class ConvertDailyRecordToAd implements DailyRecordConverter{
	@Inject
	private AttendanceItemConvertFactory factory;
	@Override
	public DailyRecordToAttendanceItemConverter createDailyConverter() {
		DailyRecordToAttendanceItemConverter converter = factory.createDailyConverter();
		return converter;
	}
	
}
