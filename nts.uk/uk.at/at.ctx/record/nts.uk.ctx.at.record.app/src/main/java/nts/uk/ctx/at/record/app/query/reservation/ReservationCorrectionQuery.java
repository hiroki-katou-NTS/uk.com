package nts.uk.ctx.at.record.app.query.reservation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhnm
 * UKDesign.UniversalK.オフィス.KMR_予約.KMR003_予約の修正.A:予約の修正.メニュー別OCD.予約の修正を起動する
 *
 */
@Stateless
public class ReservationCorrectionQuery {
    
    @Inject
    public ReservationSettingRepository reservationSettingRepository;

    public ReservationSettingDto getReservationCorrection() {
        // 1.get(ログイン会社ID): 予約設定
        Optional<ReservationSetting> settingOpt = reservationSettingRepository.findByCId(AppContexts.user().companyId());
        
        if (!settingOpt.isPresent()) {
            throw new BusinessException("Msg_2254");
        }
        
        return ReservationSettingDto.fromDomain(settingOpt.get());
    }
}
