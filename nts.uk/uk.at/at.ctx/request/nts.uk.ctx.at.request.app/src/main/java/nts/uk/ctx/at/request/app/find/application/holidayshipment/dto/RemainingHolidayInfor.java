package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;

@Getter
public class RemainingHolidayInfor {

	/**
	 * 振出振休明細
	 */
	private List<AbsRecDetailParaDto> lstAbsRecMng;
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
	@Setter
	private GeneralDate closestDueDate;

	public RemainingHolidayInfor(AbsRecRemainMngOfInPeriod domain) {
		this.lstAbsRecMng = domain.getLstAbsRecMng().stream().map(c->new AbsRecDetailParaDto(c)).collect(Collectors.toList());
		this.remainDays = domain.getRemainDays();
		this.unDigestedDays = domain.getUnDigestedDays();
		this.occurrenceDays = domain.getOccurrenceDays();
		this.useDays = domain.getUseDays();
		this.carryForwardDays = domain.getCarryForwardDays();
	}

}
