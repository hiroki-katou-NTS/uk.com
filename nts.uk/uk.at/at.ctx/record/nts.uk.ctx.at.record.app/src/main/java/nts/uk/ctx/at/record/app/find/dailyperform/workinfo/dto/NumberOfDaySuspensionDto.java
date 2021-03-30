package nts.uk.ctx.at.record.app.find.dailyperform.workinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.FuriClassifi;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.NumberOfDaySuspension;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;

/**
 * @author thanh_nx
 *
 *         振休振出として扱う日数
 */
@AllArgsConstructor
@Data
public class NumberOfDaySuspensionDto {

	//振休振出日数
	private Double days;

	//振休振出区分
	private int classifiction;
	
	public static NumberOfDaySuspensionDto from(NumberOfDaySuspension domain) {
		return new NumberOfDaySuspensionDto(domain.getDays().v(), domain.getClassifiction().value);
	}

	public NumberOfDaySuspension toDomain() {
		return new NumberOfDaySuspension(new UsedDays(days),
				EnumAdaptor.valueOf(this.classifiction, FuriClassifi.class));
	}

	@Override
	protected NumberOfDaySuspensionDto clone() {
		return new NumberOfDaySuspensionDto(days, classifiction);
	}
}
