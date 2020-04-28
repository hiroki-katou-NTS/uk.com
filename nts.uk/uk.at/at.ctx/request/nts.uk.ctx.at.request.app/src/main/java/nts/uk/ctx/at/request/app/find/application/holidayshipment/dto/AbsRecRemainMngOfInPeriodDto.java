package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecDetailPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbsRecRemainMngOfInPeriodDto {
	/**
	 * 振出振休明細
	 */
	private List<AbsRecDetailPara> lstAbsRecMng;
	/**
	 * 残日数
	 */
	private double remainDays;
	/**
	 * 未消化日数
	 */
	private double unDigestedDays;
	/**
	 * 発生日数
	 */
	private double occurrenceDays;
	/**
	 * 使用日数
	 */
	private double useDays;
	/**
	 * 繰越日数
	 */
	private double carryForwardDays;
	
	public AbsRecRemainMngOfInPeriodDto(AbsRecRemainMngOfInPeriod domain) {
		super();
		this.lstAbsRecMng = domain.getLstAbsRecMng();
		this.remainDays = domain.getRemainDays();
		this.unDigestedDays = domain.getUnDigestedDays();
		this.occurrenceDays = domain.getOccurrenceDays();
		this.useDays = domain.getUseDays();
		this.carryForwardDays = domain.getCarryForwardDays();
	}
	
	
}
