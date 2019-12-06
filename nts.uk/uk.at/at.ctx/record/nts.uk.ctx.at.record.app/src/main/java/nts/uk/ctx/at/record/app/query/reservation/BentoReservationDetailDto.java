package nts.uk.ctx.at.record.app.query.reservation;

import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;

public class BentoReservationDetailDto {
	
	private Integer frameNo;
	
	private Integer bentoCount;
	
	

	public BentoReservationDetailDto() {
		super();
	}

	public BentoReservationDetailDto(Integer frameNo, int bentoCount) {
		super();
		this.frameNo = frameNo;
		this.bentoCount = bentoCount;
	}
	
	public static BentoReservationDetailDto fromDomain(BentoReservationDetail domain) {
		return new BentoReservationDetailDto(domain.getFrameNo(), domain.getBentoCount().v());
	}
}
