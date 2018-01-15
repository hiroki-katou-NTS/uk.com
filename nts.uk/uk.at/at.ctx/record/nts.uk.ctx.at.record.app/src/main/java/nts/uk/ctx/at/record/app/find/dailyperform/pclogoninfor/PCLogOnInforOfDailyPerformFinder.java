package nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.common.TimeStampDto;
import nts.uk.ctx.at.record.app.find.dailyperform.pclogoninfor.dto.PCLogOnInforOfDailyPerformDto;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

/** 日別実績のPCログオン情報 Finder */
@Stateless
public class PCLogOnInforOfDailyPerformFinder extends FinderFacade {

	@Inject
	private PCLogOnInfoOfDailyRepo repo;
	
	@SuppressWarnings("unchecked")
	@Override
	public PCLogOnInforOfDailyPerformDto find(String employeeId, GeneralDate baseDate) {
		PCLogOnInforOfDailyPerformDto dto = new PCLogOnInforOfDailyPerformDto();
		PCLogOnInfoOfDaily domain = this.repo.find(employeeId, baseDate).orElse(null);
		if (domain != null) {
			dto.setLogonTime(ConvertHelper.mapTo(domain.getLogOnInfo(),
					(c) -> new TimeSheetDto(c.getWorkNo().v(),
							new TimeStampDto(c.getLogOn().getTimeWithDay().valueAsMinutes(),
									c.getLogOn().getAfterRoundingTime().valueAsMinutes(),
									c.getLogOn().getLocationCode().v(),
									c.getLogOn().getStampSourceInfo().value),
							new TimeStampDto(c.getLogOff().getTimeWithDay().valueAsMinutes(),
									c.getLogOff().getAfterRoundingTime().valueAsMinutes(),
									c.getLogOff().getLocationCode().v(),
									c.getLogOff().getStampSourceInfo().value),
							0
					)));
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
		}
		return dto;
	}
}
