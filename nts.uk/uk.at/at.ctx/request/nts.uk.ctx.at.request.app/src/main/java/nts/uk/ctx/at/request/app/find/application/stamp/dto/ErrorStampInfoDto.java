package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.stamp.StampAtrOther;
import nts.uk.ctx.at.request.dom.application.stamp.StartEndClassification;
import nts.uk.ctx.at.request.dom.application.stamp.output.ErrorStampInfo;

@AllArgsConstructor
@NoArgsConstructor
//打刻エラー情報
public class ErrorStampInfoDto {
//	打刻分類
	public Integer timeStampAppEnum;
//	打刻枠No
	public Integer stampFrameNo;
//	開始終了区分
	public Integer startEndClassification;
	
	
	public static ErrorStampInfoDto fromDomain(ErrorStampInfo errorStampInfo) {
		return new ErrorStampInfoDto(
				errorStampInfo.getTimeStampAppEnum().value,
				errorStampInfo.getStampFrameNo(),
				errorStampInfo.getStartEndClassification().value);
	}
	
	public ErrorStampInfo toDomain() {
		return new ErrorStampInfo(
				EnumAdaptor.valueOf(timeStampAppEnum, StampAtrOther.class),
				stampFrameNo,
				EnumAdaptor.valueOf(startEndClassification, StartEndClassification.class));
	}
}
