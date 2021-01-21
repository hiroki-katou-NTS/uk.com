package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class BentoJoinReservationSetting {

    //BentoSetting
    //予約の運用区別
    public int operationDistinction;

    //bentomenu

    public GeneralDate startDate;

    public GeneralDate endDate;

    //名前
    public String reservationFrameName1;

    //開始
    public Integer reservationStartTime1;

    //終了
    public int reservationEndTime1;

    //名前
    public String reservationFrameName2;

    //開始
    public Integer reservationStartTime2;

    //終了
    public Integer reservationEndTime2;

    public List<BentoDto> bentoDtos;

    public static BentoJoinReservationSetting setData(List<BentomenuJoinBentoDto> bentomenuJoinBentoDtos, BentoReservationSettingDto bentoReservationSettingDto){
        if (bentomenuJoinBentoDtos == null || CollectionUtil.isEmpty(bentomenuJoinBentoDtos)) return null;
        List<BentoDto> bentoDtos = new ArrayList<>();

//        List<BentomenuJoinBentoDto> result = bentoReservationSettingDto.operationDistinction == 0 ?
//                bentomenuJoinBentoDtos.stream().filter(x -> x.workLocationCode == null).collect(Collectors.toList()) :
//                bentomenuJoinBentoDtos.stream().filter(x -> x.workLocationCode != null).collect(Collectors.toList());

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
