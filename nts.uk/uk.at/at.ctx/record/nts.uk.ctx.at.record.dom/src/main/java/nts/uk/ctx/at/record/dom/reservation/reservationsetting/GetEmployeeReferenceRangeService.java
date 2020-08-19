package nts.uk.ctx.at.record.dom.reservation.reservationsetting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 社員参照範囲を取得する
 *
 * @author 3si - Dang Huu Khai
 *
 */
@Stateless
public class GetEmployeeReferenceRangeService {

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepository;

    /**
     * 社員参照範囲を取得する
     * @param cid 会社ID
     * @return 社員参照範囲
     */
    public Optional<BentoReservationSetting> getBentoReservationSetting(String cid){
        Optional<BentoReservationSetting> optBentoReservationSetting = bentoReservationSettingRepository.findByCId(cid);
        return optBentoReservationSetting;
    }

    public static interface Require {

        /**
         * 場所名を取得する
         * @param cid 会社ID
         * @return 職場
         */
        Optional<String> getWorkplaceName(String cid);

        /**
         * 勤務場所を取得する
         * @param cid 会社ID
         * @return 勤務場所
         */
        Optional<String> getWorkpLocation(String cid);
    }
}