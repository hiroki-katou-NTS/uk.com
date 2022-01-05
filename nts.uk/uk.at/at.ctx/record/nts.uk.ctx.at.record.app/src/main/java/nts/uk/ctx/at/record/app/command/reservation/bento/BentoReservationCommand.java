package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;

@Data
public class BentoReservationCommand {
    private String reservationCardNo;
    
    private String reservationDate;
    
    private int closingTimeFrame;
    
    private boolean ordered;
    
    private String workLocationCode;
    
    private List<BentoReservationDetailCommand> listBentoReservationDetail;

    public BentoReservationCommand() {
        super();
    }

    public BentoReservationCommand(String reservationCardNo, boolean ordered, String date, int closingTimeFrame, String workLocationCode,
            List<BentoReservationDetailCommand> listBentoReservationDetail) {
        super();
        this.reservationCardNo = reservationCardNo;
        this.ordered = ordered;
        this.reservationDate = date;
        this.closingTimeFrame = closingTimeFrame;
        this.workLocationCode = workLocationCode;
        this.listBentoReservationDetail = listBentoReservationDetail;
    }
    
    public static BentoReservationCommand fromDomain(BentoReservation domain) {
        return new BentoReservationCommand(
                domain.getRegisterInfor().getReservationCardNo(), 
                domain.isOrdered(), 
                domain.getReservationDate().getDate().toString("yyyy/MM/dd"),
                domain.getReservationDate().getClosingTimeFrame().value, 
                domain.getWorkLocationCode().isPresent() ? domain.getWorkLocationCode().map(WorkLocationCode::v).get() : null,
                domain.getBentoReservationDetails().stream().map(x -> BentoReservationDetailCommand.fromDomain(x)).collect(Collectors.toList())
                );
    }
    
    public BentoReservation toDomain() {
        return new BentoReservation(
                new ReservationRegisterInfo(this.reservationCardNo), 
                new ReservationDate(
                        GeneralDate.fromString(this.reservationDate, "yyyy/MM/dd"), 
                        EnumAdaptor.valueOf(closingTimeFrame, ReservationClosingTimeFrame.class)), 
                ordered, 
                workLocationCode == null ? Optional.empty() : Optional.of(new WorkLocationCode(workLocationCode)), 
                listBentoReservationDetail.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
    }
    
    @Data
    public static class BentoReservationDetailCommand {
        
        private Integer frameNo;
        
        private Integer bentoCount;
        
        private String dateTime;
        
        private boolean autoReservation;

        public BentoReservationDetailCommand() {
            super();
        }

        public BentoReservationDetailCommand(Integer frameNo, int bentoCount, String dateTime, boolean autoReservation) {
            super();
            this.frameNo = frameNo;
            this.bentoCount = bentoCount;
            this.dateTime = dateTime;
            this.autoReservation = autoReservation;
        }
        
        public static BentoReservationDetailCommand fromDomain(BentoReservationDetail domain) {
            return new BentoReservationDetailCommand(
                    domain.getFrameNo(), 
                    domain.getBentoCount().v(), 
                    domain.getDateTime().toString(), 
                    domain.isAutoReservation());
        }
        
        public BentoReservationDetail toDomain() {
            return new BentoReservationDetail(
                    this.frameNo, 
                    GeneralDateTime.fromString(this.dateTime, "yyyy/MM/dd HH:mm:ss"), 
                    this.autoReservation, 
                    new BentoReservationCount(this.bentoCount));
        }
    }
}
