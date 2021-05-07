package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 時間休暇種類及び時間消化
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class DigestionHourlyTimeTypeImport {
	/** 時間消化休暇かどうか */
	private boolean hourlyTimeType;
	/** 時間休暇種類 AppTimeType*/
	private Optional<Integer> appTimeType;

}