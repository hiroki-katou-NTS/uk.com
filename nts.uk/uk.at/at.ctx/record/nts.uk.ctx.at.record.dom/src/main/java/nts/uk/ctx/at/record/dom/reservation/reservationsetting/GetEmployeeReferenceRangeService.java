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
    BentoReservationSettingRepository bentoReservationSettingRepository;

    /**
     * 社員参照範囲を取得する
     * @param cid 会社ID
     * @return 社員参照範囲
     * TODO: Không thấy liên quan gì đến EmployeeReferenceRange
     */

    public Optional<BentoReservationSetting> getReservationConfirmationListStartupInfo(String cid){
        return bentoReservationSettingRepository.findByCId(cid);
    }
}