package nts.uk.ctx.at.request.app.find.application.holidayshipment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.UnOffsetOfAbs;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;

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
	
	public UnOffsetOfAbsDto(AccumulationAbsenceDetail unOffsetOfAbs) {
		super();
		this.absMngId = unOffsetOfAbs.getManageId();
		this.requestDays = unOffsetOfAbs.getNumberOccurren().getDay().v();
		this.unOffSetDays = unOffsetOfAbs.getUnbalanceNumber().getDay().v();
	}
	
	public UnOffsetOfAbs toDomain() {
	    return new UnOffsetOfAbs(
	            absMngId, 
	            requestDays, 
	            unOffSetDays);
	}
}
