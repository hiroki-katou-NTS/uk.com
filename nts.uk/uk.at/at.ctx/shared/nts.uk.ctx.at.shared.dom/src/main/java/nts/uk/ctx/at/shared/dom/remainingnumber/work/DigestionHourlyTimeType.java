package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;


/**
 * 時間休暇種類及び時間消化
 * @author yuri_tamakoshi
 */

@Getter
@Setter
public class DigestionHourlyTimeType {
	/** 時間消化休暇かどうか */
	private boolean hourlyTimeType;
	/** 時間休暇種類 */
	private Optional<AppTimeType> appTimeType;

	/**
	 * コンストラクタ
	 */
	public DigestionHourlyTimeType(){
		this.hourlyTimeType = false;
		this.appTimeType = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param hourlyTimeType　時間消化休暇かどうか
	 * @param appTimeType　時間休暇種類
	 * @return 時間休暇種類及び時間消化
	*/
	public static DigestionHourlyTimeType of(
			boolean hourlyTimeType,
			Optional<AppTimeType> appTimeType){

		DigestionHourlyTimeType domain = new DigestionHourlyTimeType();
		domain.hourlyTimeType = hourlyTimeType;
		domain.appTimeType = appTimeType;
		return domain;
	}

}
