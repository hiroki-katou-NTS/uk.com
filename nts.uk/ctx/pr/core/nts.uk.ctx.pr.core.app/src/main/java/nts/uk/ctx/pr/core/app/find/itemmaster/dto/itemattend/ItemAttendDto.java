package nts.uk.ctx.pr.core.app.find.itemmaster.dto.itemattend;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttend;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemAttendDto {
	public int avePayAtr;
	public int itemAtr;
	public int errRangeLowAtr;
	public BigDecimal errRangeLow;
	public int errRangeHighAtr;
	public BigDecimal errRangeHigh;
	public int alRangeLowAtr;
	public BigDecimal alRangeLow;
	public int alRangeHighAtr;
	public BigDecimal alRangeHigh;
	public int workDaysScopeAtr;
	public String memo;

	public static ItemAttendDto fromDomain(ItemAttend domain) {

		return new ItemAttendDto(domain.getAvePayAtr().value, domain.getItemAtr().value,
				domain.getErrRangeLowAtr().value, domain.getErrRangeLow().v(), domain.getAlRangeHighAtr().value,
				domain.getErrRangeHigh().v(), domain.getAlRangeLowAtr().value, domain.getAlRangeLow().v(),
				domain.getAlRangeHighAtr().value, domain.getAlRangeHigh().v(), domain.getWorkDaysScopeAtr().value,
				domain.getMemo().v());
	}

}
