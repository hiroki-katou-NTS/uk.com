package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;

@Data
public class BentoReservationDto {
    
    private String reservationCardNo;
	
    private String reservationDate;
    
    private int closingTimeFrame;
	
    private boolean ordered;
	
	private String workLocationCode;
	
	private List<BentoReservationDetailDto> listBentoReservationDetail;

	public BentoReservationDto() {
		super();
	}

	public BentoReservationDto(String reservationCardNo, boolean ordered, String date, int closingTimeFrame, String workLocationCode,
			List<BentoReservationDetailDto> listBentoReservationDetail) {
		super();
		this.reservationCardNo = reservationCardNo;
		this.ordered = ordered;
		this.reservationDate = date;
		this.closingTimeFrame = closingTimeFrame;
		this.workLocationCode = workLocationCode;
		this.listBentoReservationDetail = listBentoReservationDetail;
	}
	
	public static BentoReservationDto fromDomain(BentoReservation domain) {
		return new BentoReservationDto(
		        domain.getRegisterInfor().getReservationCardNo(), 
		        domain.isOrdered(), 
		        domain.getReservationDate().getDate().toString("yyyy/MM/dd"),
		        domain.getReservationDate().getClosingTimeFrame().value, 
		        domain.getWorkLocationCode().isPresent() ? domain.getWorkLocationCode().map(WorkLocationCode::v).get() : null,
				domain.getBentoReservationDetails().stream().map(x -> BentoReservationDetailDto.fromDomain(x)).collect(Collectors.toList())
				);
	}
	
}
