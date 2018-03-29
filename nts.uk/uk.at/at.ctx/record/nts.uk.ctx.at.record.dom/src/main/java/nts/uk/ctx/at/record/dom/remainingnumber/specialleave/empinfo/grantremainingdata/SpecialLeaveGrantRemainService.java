package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class SpecialLeaveGrantRemainService {
	
	public String calDayTime(List<SpecialLeaveGrantRemainingData> grantRemains){
		Double result = grantRemains.stream().mapToDouble(item->item.getDetails().getRemainingNumber().getDayNumberOfRemain().v()).sum();
		
		int minutes = grantRemains.stream()
				.filter(i -> i.getDetails().getRemainingNumber().timeOfRemain.isPresent()).mapToInt(i->i.getDetails().getRemainingNumber().getTimeOfRemain().get().minute()).sum();
		int hours = minutes / 60;
		int minute = minutes % 60 *60;
		return result.toString() + "日と " + hours + " : " + minute;
	}

}
