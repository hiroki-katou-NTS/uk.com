package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.app.find.stamp.management.ButtonSettingsDto;
import nts.uk.ctx.at.record.app.find.stamp.management.StampTypeDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;

@AllArgsConstructor
@Getter
public class StampInfoDto {

	/**
	 * 打刻する方法
	 */
	private final RelieveDto relieve;

	/**
	 * 打刻場所情報
	 */
	private final StampLocationInforDto locationInfor;
	/**
	 * dựa vào 表示アイコン※[2]参照
	 */
	private int buttonValueType;

	private RefectActualResultDto refActualResult;
	
	/**
	 * dựa vào 時刻変更区分
	 */
	private int changeClockArt;

	public static StampInfoDto fromDomain(Stamp stamp) {

		return new StampInfoDto(RelieveDto.fromDomain(stamp.getRelieve()),
				StampLocationInforDto.fromDomain(stamp.getLocationInfor()),
				ButtonSettingsDto.toButtonValueType(StampTypeDto.fromDomain(stamp.getType())),
				RefectActualResultDto.fromDomain(stamp.getRefActualResults()),
				stamp.getType().getChangeClockArt().value);
	}

}
