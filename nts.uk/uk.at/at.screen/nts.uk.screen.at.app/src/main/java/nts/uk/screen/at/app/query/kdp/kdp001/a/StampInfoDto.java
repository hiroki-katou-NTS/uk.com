package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;

@AllArgsConstructor
public class StampInfoDto {

	@Getter
	/**
	 * 打刻する方法
	 */
	private final RelieveDto relieve;

	/**
	 * 打刻場所情報
	 */
	@Getter
	private final StampLocationInforDto locationInfor;

	public static StampInfoDto fromDomain(Stamp stamp) {
		return new StampInfoDto(RelieveDto.fromDomain(stamp.getRelieve()),
				StampLocationInforDto.fromDomain(stamp.getLocationInfor()));
	}

}
