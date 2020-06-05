package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnOffsetOfAbs;

@NoArgsConstructor
@Setter
@Getter
public class UnOffsetOfAbsDto {
	/**
	 * 振休データID
	 */
	private String absMngId;
	/**
	 * 必要日数
	 */
	private double requestDays;
	/**
	 * 未相殺日数
	 */
	private double unOffSetDays;
	
	public UnOffsetOfAbsDto(UnOffsetOfAbs unOffsetOfAbs) {
		super();
		this.absMngId = unOffsetOfAbs.getAbsMngId();
		this.requestDays = unOffsetOfAbs.getRequestDays();
		this.unOffSetDays = unOffsetOfAbs.getUnOffSetDays();
	}
}
