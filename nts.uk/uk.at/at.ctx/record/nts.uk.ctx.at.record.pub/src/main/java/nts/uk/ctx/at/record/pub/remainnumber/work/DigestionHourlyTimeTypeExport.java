package nts.uk.ctx.at.record.pub.remainnumber.work;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

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

	/**
	 * domainへ変換
	 * @return
	 */
	public DigestionHourlyTimeType toDomain() {
		DigestionHourlyTimeType domain
			= DigestionHourlyTimeType.of(
					hourlyTimeType,
					appTimeType.map(mapper->EnumAdaptor.valueOf(mapper, AppTimeType.class)));
		return domain;
	}
}


