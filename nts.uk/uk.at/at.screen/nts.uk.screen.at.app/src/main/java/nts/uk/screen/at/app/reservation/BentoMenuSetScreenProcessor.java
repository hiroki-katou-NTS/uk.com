package nts.uk.screen.at.app.reservation;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class BentoMenuSetScreenProcessor {

    @Inject
    private BentoMenuScreenRepository bentoMenuScreenRepository;

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;
    
    @Inject
    private ReservationSettingRepository reservationSettingRepository;
    
    @Inject
    private BentoMenuRepository bentoMenuRepository;
    
    @Inject
    private IBentoMenuHistoryRepository iBentoMenuHistoryRepository;

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
        GeneralDate generalDate = GeneralDate.max();
        
        // 1: 会社IDと予約の運用区別によって予約受付時間帯を取得する
        List<ReservationRecTimeZone> reservationRecTimeZoneLst = reservationSettingRepository.getReservationRecTimeZoneByOpDist(companyID, 0);
        
        // 2: 取得する
        Optional<BentoMenuHistory> opBentoMenuHistory = iBentoMenuHistoryRepository.findByCompanyId(companyID);
        String histID = Strings.EMPTY;
        GeneralDate startDate = null;
        GeneralDate endDate = null;
        if(opBentoMenuHistory.isPresent()) {
        	Optional<DateHistoryItem> opDateHistoryItem = opBentoMenuHistory.get().getHistoryItems().stream().filter(x -> x.contains(generalDate)).findAny();
        	if(opDateHistoryItem.isPresent()) {
        		histID = opDateHistoryItem.get().identifier();
        		startDate = opDateHistoryItem.get().start();
        		endDate = opDateHistoryItem.get().end();
        	}
        }
        
        // 2.1: 弁当メニューを取得
        BentoMenu bentoMenu = bentoMenuRepository.getBentoMenuByHistId(companyID, histID);

        return BentoJoinReservationSetting.setData(reservationRecTimeZoneLst, startDate, endDate, bentoMenu);
    }

    public List<WorkLocationDto> getWorkLocationByContr() {
        String contract = AppContexts.user().contractCode();
        return bentoMenuScreenRepository.findDataWorkLocation(contract);
    }
}
