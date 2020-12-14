package nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;

@Getter
public class ControlOfMonthlyDto {
	/**会社ID*/
	private String companyId;
	
	/**勤怠項目ID*/
	private int itemMonthlyId;
	
	/**日別実績のヘッダ背景色*/
	private String headerBgColorOfMonthlyPer;

	/**時間項目の入力単位*/
	private BigDecimal inputUnitOfTimeItem;
	
	public ControlOfMonthlyDto(String companyId, int itemMonthlyId, String headerBgColorOfMonthlyPer, BigDecimal inputUnitOfTimeItem) {
		super();
		this.companyId = companyId;
		this.itemMonthlyId = itemMonthlyId;
		this.headerBgColorOfMonthlyPer = headerBgColorOfMonthlyPer;
		this.inputUnitOfTimeItem = inputUnitOfTimeItem;
	}
	
	public static ControlOfMonthlyDto fromDomain(ControlOfMonthlyItems domain) {
		return new ControlOfMonthlyDto(
				domain.getCompanyId(),
				domain.getItemMonthlyId(),
				!domain.getHeaderBgColorOfMonthlyPer().isPresent() ?null:domain.getHeaderBgColorOfMonthlyPer().get().v(),
				!domain.getInputUnitOfTimeItem().isPresent() ? null: domain.getInputUnitOfTimeItem().get()
				);
	}
}
