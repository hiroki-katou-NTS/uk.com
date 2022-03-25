package nts.uk.screen.at.app.reservation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

@Data
@AllArgsConstructor
public class BentoJoinReservationSetting {

    //BentoSetting
    //予約の運用区別
    public int operationDistinction;

    //bentomenu
    
    public String historyID;

    public GeneralDate startDate;

    public GeneralDate endDate;

    //名前
    public String reservationFrameName1;

    //開始
    public Integer reservationStartTime1;

    //終了
    public Integer reservationEndTime1;

    //名前
    public String reservationFrameName2;

    //開始
    public Integer reservationStartTime2;

    //終了
    public Integer reservationEndTime2;

    public List<BentoDto> bentoDtos;

    public static BentoJoinReservationSetting setData(String historyID, List<ReservationRecTimeZone> reservationRecTimeZoneLst, GeneralDate startDate, GeneralDate endDate, List<Bento> menu){
    	List<BentoDto> bentoDtos = new ArrayList<>();
		for(Bento bento : menu) {
			bentoDtos.add(new BentoDto(
					bento.getFrameNo(), 
					bento.getName().v(), 
					bento.getUnit().v(), 
					bento.getAmount1().v(), 
					bento.getAmount2().v(), 
					bento.getReceptionTimezoneNo().value,
					bento.getWorkLocationCode().map(x -> x.v()).orElse(null), 
					null));
		}
    	
    	ReservationRecTimeZone frame1 = reservationRecTimeZoneLst.stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME1).findAny().orElse(null);
    	ReservationRecTimeZone frame2 = reservationRecTimeZoneLst.stream().filter(x -> x.getFrameNo()==ReservationClosingTimeFrame.FRAME2).findAny().orElse(null);

        return new BentoJoinReservationSetting(
        		OperationDistinction.BY_COMPANY.value,
        		historyID,
        		startDate,
        		endDate,
        		frame1==null ? null : frame1.getReceptionHours().getReceptionName().v(),
				frame1==null ? null : frame1.getReceptionHours().getStartTime().v(),
                frame1==null ? null : frame1.getReceptionHours().getEndTime().v(),
                frame2==null ? null : frame2.getReceptionHours().getReceptionName().v(),
                frame2==null ? null : frame2.getReceptionHours().getStartTime().v(),
                frame2==null ? null : frame2.getReceptionHours().getEndTime().v(),
                bentoDtos
        );
    }
}
