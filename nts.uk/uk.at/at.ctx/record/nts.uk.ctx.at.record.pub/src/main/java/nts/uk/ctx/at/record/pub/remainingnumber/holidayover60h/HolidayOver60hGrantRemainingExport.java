package nts.uk.ctx.at.record.pub.remainingnumber.holidayover60h;

import lombok.*;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hGrantRemaining;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.empinfo.grantremainingdata.HolidayOver60hGrantRemainingData;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayOver60hGrantRemainingExport extends HolidayOver60hGrantRemainingData{

	/** 60H超休不足ダミーフラグ */
	private boolean dummyAtr = false;

	public static HolidayOver60hGrantRemainingExport fromDomain(HolidayOver60hGrantRemaining domain) {
		HolidayOver60hGrantRemainingExport export = new HolidayOver60hGrantRemainingExport();
		export.setLeaveID(domain.getLeaveID());
//		export.setCid(domain.getCid());
		export.setEmployeeId(domain.getEmployeeId());
		export.setGrantDate(domain.getGrantDate());
		export.setDeadline(domain.getDeadline());
		export.setExpirationStatus(domain.getExpirationStatus());
		export.setRegisterType(domain.getRegisterType());
		export.setDetails(domain.getDetails());
		export.setDummyAtr(domain.isDummyAtr());
		return export;
	}
}

