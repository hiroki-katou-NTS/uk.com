package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppStampStandard {
	
	private Integer startTime;
	
	private Integer endTime;
	
	private Integer framNo;
	
	private StampAtrOther stampAtrOther;
	
	public static List<AppStampStandard> toListStandard(AppStamp appStamp) {
		
		List<AppStampStandard> listAppStampStandard = new ArrayList<AppStampStandard>();
//		時刻
		List<TimeStampApp> listTimeStampApp = appStamp.getListTimeStampApp();	
//		時刻の取消
		List<DestinationTimeApp> listDestinationTimeApp = appStamp.getListDestinationTimeApp();
//		時間帯
		List<TimeStampAppOther> listTimeStampAppOther = appStamp.getListTimeStampAppOther();
//		時間帯の取消
		List<DestinationTimeZoneApp> listDestinationTimeZoneApp = appStamp.getListDestinationTimeZoneApp();
		
		if (!CollectionUtil.isEmpty(listTimeStampApp)) {
			listTimeStampApp.stream().forEach(x -> {
				AppStampStandard appStampStandar;
				if (!CollectionUtil.isEmpty(listAppStampStandard)) {
					Optional<AppStampStandard> optional = listAppStampStandard.stream().filter(
							item -> item.getFramNo() == x.getDestinationTimeApp().getEngraveFrameNo())
							.findFirst();
					if (optional.isPresent()) {
						appStampStandar = optional.get();
						if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START) {
							appStampStandar.startTime = x.getTimeOfDay().v();
						} 
						if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END)  {
							appStampStandar.endTime = x.getTimeOfDay().v();
						}
					} else {
						appStampStandar = new AppStampStandard();
						if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START) {
							appStampStandar.startTime = x.getTimeOfDay().v();
						} else {
							appStampStandar.endTime = x.getTimeOfDay().v();
						}
						appStampStandar.framNo = x.getDestinationTimeApp().getEngraveFrameNo();
						// parse enum
						StampAtrOther stampAtr = null;
						if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT) {
							stampAtr = StampAtrOther.ATTEENDENCE_OR_RETIREMENT;
						} else if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.EXTRAORDINARY){
							stampAtr = StampAtrOther.EXTRAORDINARY;
						} else if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.GOOUT_RETURNING) {
							stampAtr = StampAtrOther.GOOUT_RETURNING;
						} else {
							stampAtr = StampAtrOther.CHEERING;
						}
						appStampStandar.stampAtrOther = stampAtr;
						listAppStampStandard.add(appStampStandar);
					}
				} else {
					appStampStandar = new AppStampStandard();
					if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.START) {
						appStampStandar.startTime = x.getTimeOfDay().v();
					} 
					if (x.getDestinationTimeApp().getStartEndClassification() == StartEndClassification.END)  {
						appStampStandar.endTime = x.getTimeOfDay().v();
					}
					appStampStandar.framNo = x.getDestinationTimeApp().getEngraveFrameNo();
					// parse enum
					StampAtrOther stampAtr = null;
					if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT) {
						stampAtr = StampAtrOther.ATTEENDENCE_OR_RETIREMENT;
					} else if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.EXTRAORDINARY){
						stampAtr = StampAtrOther.EXTRAORDINARY;
					} else if (x.getDestinationTimeApp().getTimeStampAppEnum() == TimeStampAppEnum.GOOUT_RETURNING) {
						stampAtr = StampAtrOther.GOOUT_RETURNING;
					} else {
						stampAtr = StampAtrOther.CHEERING;
					}
					appStampStandar.stampAtrOther = stampAtr;
					listAppStampStandard.add(appStampStandar);
				}

				
			});
		}
		
		
		
		if (!CollectionUtil.isEmpty(listDestinationTimeApp)) {
			listDestinationTimeApp.stream().forEach(x -> {
				AppStampStandard appStampStandar =  new AppStampStandard();
				if (!CollectionUtil.isEmpty(listAppStampStandard)) {
					Optional<AppStampStandard> optional = listAppStampStandard.stream()
							.filter(item -> item.framNo== x.getEngraveFrameNo()).findFirst();
					if (optional.isPresent()) {
						optional.get().setFramNo(x.getEngraveFrameNo());
					} else {
						appStampStandar.startTime =  null;
						appStampStandar.endTime = null;
						StampAtrOther stampAtr = null;
						if (x.getTimeStampAppEnum() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT) {
							stampAtr = StampAtrOther.ATTEENDENCE_OR_RETIREMENT;
						} else if (x.getTimeStampAppEnum() == TimeStampAppEnum.EXTRAORDINARY){
							stampAtr = StampAtrOther.EXTRAORDINARY;
						} else if (x.getTimeStampAppEnum() == TimeStampAppEnum.GOOUT_RETURNING) {
							stampAtr = StampAtrOther.GOOUT_RETURNING;
						} else {
							stampAtr = StampAtrOther.CHEERING;
						}
						appStampStandar.stampAtrOther = stampAtr;
						appStampStandar.setFramNo(x.getEngraveFrameNo());
						listAppStampStandard.add(appStampStandar);
					}
				} else {
					
					appStampStandar.startTime =  null;
					appStampStandar.endTime = null;
					StampAtrOther stampAtr = null;
					if (x.getTimeStampAppEnum() == TimeStampAppEnum.ATTEENDENCE_OR_RETIREMENT) {
						stampAtr = StampAtrOther.ATTEENDENCE_OR_RETIREMENT;
					} else if (x.getTimeStampAppEnum() == TimeStampAppEnum.EXTRAORDINARY){
						stampAtr = StampAtrOther.EXTRAORDINARY;
					} else if (x.getTimeStampAppEnum() == TimeStampAppEnum.GOOUT_RETURNING) {
						stampAtr = StampAtrOther.GOOUT_RETURNING;
					} else {
						stampAtr = StampAtrOther.CHEERING;
					}
					appStampStandar.stampAtrOther = stampAtr;
					appStampStandar.setFramNo(x.getEngraveFrameNo());
					listAppStampStandard.add(appStampStandar);
				}
				

			});
		}
		
		
		
		if (!CollectionUtil.isEmpty(listTimeStampAppOther)) {
			listTimeStampAppOther.stream().forEach(item -> {
				AppStampStandard appStampStandard = new AppStampStandard();
				appStampStandard.startTime = item.getTimeZone().getStartTime().v();
				appStampStandard.endTime = item.getTimeZone().getEndTime().v();
				appStampStandard.framNo = item.getDestinationTimeZoneApp().getEngraveFrameNo();
				// parse enum
				StampAtrOther stampAtr = null;
				if (item.getDestinationTimeZoneApp().getTimeZoneStampClassification() == TimeZoneStampClassification.PARENT) {
					stampAtr = StampAtrOther.PARENT;
				} else if (item.getDestinationTimeZoneApp().getTimeZoneStampClassification() == TimeZoneStampClassification.NURSE) {
					stampAtr = StampAtrOther.NURSE;
				} else {
					stampAtr = StampAtrOther.BREAK;
				}
				appStampStandard.stampAtrOther = stampAtr;
				listAppStampStandard.add(appStampStandard);
			});
		}
		
		if (!CollectionUtil.isEmpty(listDestinationTimeZoneApp)) {
			
			listDestinationTimeZoneApp.stream().forEach(item -> {
				AppStampStandard appStampStandard = new AppStampStandard();
				appStampStandard.framNo = item.getEngraveFrameNo();
				// parse enum
				StampAtrOther stampAtr = null;
				if (item.getTimeZoneStampClassification() == TimeZoneStampClassification.PARENT) {
					stampAtr = StampAtrOther.PARENT;
				} else if (item.getTimeZoneStampClassification() == TimeZoneStampClassification.NURSE) {
					stampAtr = StampAtrOther.NURSE;
				} else {
					stampAtr = StampAtrOther.BREAK;
				}
				appStampStandard.stampAtrOther = stampAtr;
				listAppStampStandard.add(appStampStandard);
			});
		}
		
		
		
		
		return listAppStampStandard;
	}

}
