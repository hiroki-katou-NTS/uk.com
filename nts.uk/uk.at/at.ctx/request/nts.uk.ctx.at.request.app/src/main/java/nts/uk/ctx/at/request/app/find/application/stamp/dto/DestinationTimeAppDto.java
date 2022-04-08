package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeApp;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppEnum;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

@AllArgsConstructor
@NoArgsConstructor
//時刻申請の反映先情報
public class DestinationTimeAppDto {
//	打刻分類
	public Integer timeStampAppEnum;
//	打刻枠No
	public Integer engraveFrameNo;
//	開始終了区分
	public Integer startEndClassification;
//	応援勤務枠No	
	public Integer supportWork;
	
	public static DestinationTimeAppDto fromDomain(DestinationTimeApp destinationTimeApp) {
		
		return new DestinationTimeAppDto(
				destinationTimeApp.getTimeStampAppEnum().value,
				destinationTimeApp.getEngraveFrameNo(),
				destinationTimeApp.getStartEndClassification().value,
				destinationTimeApp.getSupportWorkNo().isPresent() ? destinationTimeApp.getSupportWorkNo().get().v() : null);
	}
	
	public DestinationTimeApp toDomain() {
		return new DestinationTimeApp(
				EnumAdaptor.valueOf(timeStampAppEnum, TimeStampAppEnum.class),
				engraveFrameNo,
				EnumAdaptor.valueOf(startEndClassification, StartEndClassification.class),
				supportWork == null ? Optional.empty() : Optional.of(new WorkNo(supportWork)));
	}

}
