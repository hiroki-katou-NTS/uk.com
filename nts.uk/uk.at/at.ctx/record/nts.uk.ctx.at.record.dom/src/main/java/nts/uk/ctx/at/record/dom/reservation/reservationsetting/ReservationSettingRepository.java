package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import java.util.List;
import java.util.Optional;

public interface ReservationSettingRepository {

    /**
     * Find all BentoReservationSetting
     * [1]  会社IDによって予約設定を取得する
     * @param companyId
     * @return
     */
    Optional<ReservationSetting> findByCId(String companyId);

    public void add(ReservationSetting bentoReservationSetting);

    public void update(ReservationSetting bentoReservationSetting);
    
    /**
     * 	[2]  会社IDと予約の運用区別によって予約受付時間帯を取得する
     * @param companyID 会社ID
     * @param frameNo 予約の運用区別
     * @return
     */
    List<ReservationRecTimeZone> getReservationRecTimeZoneByOpDist(String companyID, int operationDistinction);
    
    /**
     * 	[3]  会社IDと予約の運用区別によって予約設定を取得する
     * @param companyID 会社ID
     * @param frameNo 予約の運用区別
     * @return
     */
    ReservationSetting getReservationSettingByOpDist(String companyID, int operationDistinction);
    
    /**
     * [4]  会社ID、枠NOと予約の運用区別によって予約受付時間帯を取得する
     * @param companyID 会社ID
     * @param frameNo 枠NO
     * @param operationDistinction 予約の運用区別
     * @return
     */
    ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo, int operationDistinction);

}
