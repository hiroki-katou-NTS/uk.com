package nts.uk.ctx.at.record.app.query.reservation;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;

@Data
public class BentoReservationDetailDto {
	
	private Integer frameNo;
	
	private Integer bentoCount;
	
	private String dateTime;
	
	private boolean autoReservation;

	public BentoReservationDetailDto() {
		super();
	}

	public BentoReservationDetailDto(Integer frameNo, int bentoCount, String dateTime, boolean autoReservation) {
		super();
		this.frameNo = frameNo;
		this.bentoCount = bentoCount;
		this.dateTime = dateTime;
		this.autoReservation = autoReservation;
	}
	
	public static BentoReservationDetailDto fromDomain(BentoReservationDetail domain) {
		return new BentoReservationDetailDto(
		        domain.getFrameNo(), 
		        domain.getBentoCount().v(), 
		        domain.getDateTime().toString(), 
		        domain.isAutoReservation());
	}
}
