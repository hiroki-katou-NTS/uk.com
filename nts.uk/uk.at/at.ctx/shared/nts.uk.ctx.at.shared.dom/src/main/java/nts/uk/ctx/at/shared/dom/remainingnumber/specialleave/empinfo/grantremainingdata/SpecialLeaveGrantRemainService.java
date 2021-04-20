package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.LeaveExpirationStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@Stateless
public class SpecialLeaveGrantRemainService {
	@Inject
	private SpecialLeaveGrantRepository specialLeaveGrantRepo;
	
	public String calDayTime(String sid, int specialCD) {
		
		List<SpecialLeaveGrantRemainingData> grantRemains = specialLeaveGrantRepo.getAllByExpStatus(sid, specialCD,
				LeaveExpirationStatus.AVAILABLE.value);

		// Total day
		Double result = grantRemains.stream()
				.mapToDouble(item -> item.getDetails().getRemainingNumber().getDays().v()).sum();
		
		// Total time
		// TODO No268特別休暇の利用制御
		ManageDistinct specialTimeManager = ManageDistinct.NO;
		
		if (specialTimeManager == ManageDistinct.NO){
			return result.toString() + "日";
		}
		
		return result.toString() + "日と　"+ calTime(grantRemains);
	}
	
	/**
	 * viết hàm tính toán cho một list sid , cid, specialCD
	 * @param cid
	 * @param sid
	 * @param specialCD
	 * @return
	 * @author lanlt
	 */
	public Map<String, String> calDayTime(String cid, List<String> sids, int specialCD) {
		Map<String, String> result = new HashMap<>();
		 Map<String, List<SpecialLeaveGrantRemainingData>> grantRemains = specialLeaveGrantRepo.getAllByExpStatus(cid, sids, specialCD,
				LeaveExpirationStatus.AVAILABLE.value).stream().collect(Collectors.groupingBy( c  -> c.getEmployeeId()));
		 if(grantRemains.size() < sids.size()) {
			 for(String sid: sids) {
				 List<SpecialLeaveGrantRemainingData> grantRemain = grantRemains.get(sid);
				 if(CollectionUtil.isEmpty(grantRemain)) {
					 grantRemains.put(sid, new ArrayList<>());
				 }
			 }
		 }
			// Total time
			// TODO No268特別休暇の利用制御
			ManageDistinct specialTimeManager = ManageDistinct.NO;
			boolean isNo = specialTimeManager == ManageDistinct.NO;
		for (Map.Entry<String, List<SpecialLeaveGrantRemainingData>> entry : grantRemains.entrySet()) {
			String key = entry.getKey();
			// Total day
			Double totalDay = entry.getValue().stream()
					.mapToDouble(item -> item.getDetails().getRemainingNumber().getDays().v()).sum();
			result.put(key,
					isNo == true ? (totalDay.toString() + "日") : (totalDay.toString() + "日と　" + calTime(entry.getValue())));

		}
		 return result;
	}
	/**
	 *  No268特別休暇の利用制御
	 * @param grantRemains
	 * @return
	 */
	private String calTime(List<SpecialLeaveGrantRemainingData> grantRemains){
		
		List<SpecialLeaveGrantRemainingData> grantRemainsTemp = grantRemains.stream()
				.filter(i -> i.getDetails().getRemainingNumber().getMinutes().isPresent()).collect(Collectors.toList());

		Integer minute = grantRemainsTemp.stream().mapToInt(i -> {
			LeaveRemainingTime timeOfRemain =  i.getDetails().getRemainingNumber().getMinutes().get();
			return (timeOfRemain.valueAsMinutes());
		}).sum();
		
		Integer hours = minute / 60 ;
		minute = minute % 60;
		
		return  ((hours == 0 && minute <0) ? ("-" + hours) : hours ) + ":" + (Math.abs(minute) < 10 ? ("0" + Math.abs(minute)) : (Math.abs(minute) + ""));
	}

}
