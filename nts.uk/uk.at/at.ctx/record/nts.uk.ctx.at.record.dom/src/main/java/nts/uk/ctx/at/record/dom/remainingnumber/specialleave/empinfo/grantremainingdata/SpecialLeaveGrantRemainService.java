package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.base.LeaveExpirationStatus;

@Stateless
public class SpecialLeaveGrantRemainService {
	@Inject 
	private SpecialLeaveGrantRepository specialLeaveGrantRepo;
	
	public String calDayTime(String sid, int specialCD){
		List<SpecialLeaveGrantRemainingData> grantRemains = specialLeaveGrantRepo.getAllByExpStatus(sid, specialCD, LeaveExpirationStatus.AVAILABLE.value);
		
		Double result = grantRemains.stream().mapToDouble(item->item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
		List<SpecialLeaveGrantRemainingData> grantRemainsTemp = grantRemains.stream()
				.filter(i -> i.getDetails().getRemainingNumber().timeOfRemain.isPresent()).collect(Collectors.toList());
		
		int minute = grantRemainsTemp.stream().mapToInt(i->i.getDetails().getRemainingNumber().getTimeOfRemain().get().minute()).sum();
		
		int hours = grantRemainsTemp.stream().mapToInt(i->i.getDetails().getRemainingNumber().getTimeOfRemain().get().hour()).sum();
		hours = Math.abs(minute) / 60 + hours;
		minute = Math.abs(minute) % 60;
		
		return result.toString() + "日と　" + (hours) + ":" + (minute < 10 ? ("0"+ minute) : (minute + "")) ;
	}

}
