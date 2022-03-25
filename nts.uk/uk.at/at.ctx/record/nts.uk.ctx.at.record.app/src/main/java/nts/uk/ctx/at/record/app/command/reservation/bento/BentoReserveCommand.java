package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

@Value
public class BentoReserveCommand {

	private String workLocationCode;

	private String date;
	
	private int closingTimeFrameNo;

	private List<BentoReservationDetailCommand> details;
	
	public Map<Integer, BentoReservationCount> getFrame1Bentos() {
		return details.stream().filter(x -> x.getClosingTimeFrame()==ReservationClosingTimeFrame.FRAME1)
				.collect(Collectors.toMap(BentoReservationDetailCommand::getFrameNo, BentoReservationDetailCommand::getBentoCount));
	}
	
	public Map<Integer, BentoReservationCount> getFrame2Bentos() {
		return details.stream().filter(x -> x.getClosingTimeFrame()==ReservationClosingTimeFrame.FRAME2)
				.collect(Collectors.toMap(BentoReservationDetailCommand::getFrameNo, BentoReservationDetailCommand::getBentoCount));
	}
	
	@Value
	public static class BentoReservationDetailCommand {
		
		private int closingTimeFrame;
		
		private int frameNo;
		
		private int bentoCount;

		public ReservationClosingTimeFrame getClosingTimeFrame() {
			return EnumAdaptor.valueOf(closingTimeFrame, ReservationClosingTimeFrame.class);
		}
		
		public BentoReservationCount getBentoCount() {
			BentoReservationCount bentoReservationCount = new BentoReservationCount(bentoCount);
			bentoReservationCount.validate();
			return bentoReservationCount;
		}
	}
	
}
