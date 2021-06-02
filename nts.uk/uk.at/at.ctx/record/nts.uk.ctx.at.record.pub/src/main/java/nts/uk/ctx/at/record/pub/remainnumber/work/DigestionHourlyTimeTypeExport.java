package nts.uk.ctx.at.record.pub.remainnumber.work;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 時間休暇種類及び時間消化
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class DigestionHourlyTimeTypeExport {
	/** 時間消化休暇かどうか */
	private boolean hourlyTimeType;
	/** 時間休暇種類 AppTimeType*/
	private Optional<Integer> appTimeType;

}


