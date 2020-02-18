package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;

@Data
public class BentoReservationDto {
	
	private boolean ordered;
	
	private GeneralDate date;
	
	private int reservationClosingTimeFrame;
	
	private List<BentoReservationDetailDto> listBentoReservationDetail;

	public BentoReservationDto() {
		super();
	}

	public BentoReservationDto(boolean ordered, GeneralDate date, int reservationClosingTimeFrame,
			List<BentoReservationDetailDto> listBentoReservationDetail) {
		super();
		this.ordered = ordered;
		this.date = date;
		this.reservationClosingTimeFrame = reservationClosingTimeFrame;
		this.listBentoReservationDetail = listBentoReservationDetail;
	}
	
	public static BentoReservationDto fromDomain(BentoReservation domain) {
		return new BentoReservationDto(domain.isOrdered(), domain.getReservationDate().getDate(),
				domain.getReservationDate().getClosingTimeFrame().value, 
				domain.getBentoReservationDetails().stream().map(x -> BentoReservationDetailDto.fromDomain(x)).collect(Collectors.toList())
				);
	}
	
}
