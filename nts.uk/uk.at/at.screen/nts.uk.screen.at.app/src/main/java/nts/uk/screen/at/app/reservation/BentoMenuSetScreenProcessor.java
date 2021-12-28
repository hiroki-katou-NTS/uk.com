package nts.uk.screen.at.app.reservation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BentoMenuSetScreenProcessor {

    @Inject
    private BentoMenuScreenRepository bentoMenuScreenRepository;

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private BentoMenuHistRepository bentoMenuHistRepository;

    public BentoMenuJoinBentoSettingDto findDataBentoMenu() {
        String companyID = AppContexts.user().companyId();
        GeneralDate generalDate = GeneralDate.max();

        // 2:取得する(会社ID＝ログイン会社ID,終了日＝’9999/12/31’)
        BentoMenuDto bentoMenuDto = bentoMenuScreenRepository.findDataBentoMenu(companyID,generalDate);

        // 1:get(会社ID)
        BentoReservationSettingDto reservationSettingDto = bentoReservationScreenRepository.findDataBentoRervation(companyID);

        return BentoMenuJoinBentoSettingDto.setData(bentoMenuDto,reservationSettingDto);
    }

    public BentoJoinReservationSetting getBentoMenuByHist(BentoRequest request) {
        String companyID = AppContexts.user().companyId();
        
        // 1: 会社IDと予約の運用区別によって予約受付時間帯を取得する
        List<ReservationRecTimeZone> reservationRecTimeZoneLst = reservationSettingRepository.getReservationRecTimeZoneByOpDist(companyID, 0);
        if(CollectionUtil.isEmpty(reservationRecTimeZoneLst)) {
        	throw new BusinessException("Msg_3259");
        }
        
        // 2: 取得する
        Optional<BentoMenuHistory> opBentoMenuHistory = bentoMenuHistRepository.findByCompanyDate(companyID, GeneralDate.fromString(request.getDate(), "yyyy/MM/dd"));
        if(!opBentoMenuHistory.isPresent()) {
        	throw new BusinessException("Msg_3260");
        }

        return BentoJoinReservationSetting.setData(
        		opBentoMenuHistory.get().getHistoryID(),
        		reservationRecTimeZoneLst, 
        		opBentoMenuHistory.get().getHistoryItem().start(), 
        		opBentoMenuHistory.get().getHistoryItem().end(), 
        		opBentoMenuHistory.get().getMenu());
    }

    public List<WorkLocationDto> getWorkLocationByContr() {
        String contract = AppContexts.user().contractCode();
        return bentoMenuScreenRepository.findDataWorkLocation(contract);
    }
}
