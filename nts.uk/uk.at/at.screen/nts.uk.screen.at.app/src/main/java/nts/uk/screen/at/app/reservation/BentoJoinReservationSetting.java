package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BentoJoinReservationSetting {

    //BentoSetting
    //予約の運用区別
    public int operationDistinction;

    //bentomenu

    public GeneralDate startDate;

    public GeneralDate endDate;

    public String reservationFrameName1;

    public int reservationStartTime1;

    public int reservationEndTime1;

    public String reservationFrameName2;

    public int reservationStartTime2;

    public int reservationEndTime2;

    public List<BentoDto> bentoDtos;

    public static BentoJoinReservationSetting setData(List<BentomenuJoinBentoDto> bentomenuJoinBentoDtos, BentoReservationSettingDto bentoReservationSettingDto){
        if (bentomenuJoinBentoDtos == null || CollectionUtil.isEmpty(bentomenuJoinBentoDtos)) return null;
        List<BentoDto> bentoDtos = new ArrayList<>();

        for(BentomenuJoinBentoDto x : bentomenuJoinBentoDtos){
            bentoDtos.add(new BentoDto(
                    x.getFrameNo(),
                    x.getBentoName(),
                    x.getUnitName(),
                    x.getPrice1(),
                    x.getPrice2(),
                    x.isReservationAtr1(),
                    x.isReservationAtr2(),
                    x.getWorkLocationCode(),
                    x.getWorkLocationName()
            ));
        }

        val dto = bentomenuJoinBentoDtos.get(0);
        return new BentoJoinReservationSetting(
                bentoReservationSettingDto.getOperationDistinction(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getReservationFrameName1(),
                dto.getReservationStartTime1(),
                dto.getReservationEndTime1(),
                dto.getReservationFrameName2(),
                dto.getReservationStartTime2(),
                dto.getReservationEndTime2(),
                bentoDtos
        );
    }
}
