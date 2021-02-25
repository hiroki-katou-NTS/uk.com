package nts.uk.screen.at.app.reservation;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class BentoMenuSetScreenProcessor {

    @Inject
    private BentoMenuScreenRepository bentoMenuScreenRepository;

    @Inject
    private BentoReservationScreenRepository bentoReservationScreenRepository;

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

        // 1:取得する(会社ID　＝　ログイン会社ID):運用区別
        BentoReservationSettingDto reservationSettingDto = bentoReservationScreenRepository.findDataBentoRervation(companyID);
        if (reservationSettingDto == null){
            throw new BusinessException("Msg_1847");
        }

        // 2:取得する(会社ID＝ログイン会社ID,基準日＝Input．基準日)
        List<BentomenuJoinBentoDto> bentomenuJoinBentoDtos =  bentoMenuScreenRepository.findDataBento(companyID,generalDate,request);
        if (bentomenuJoinBentoDtos.size() == 0){
            throw new BusinessException("Msg_1848");
        }

        return BentoJoinReservationSetting.setData(bentomenuJoinBentoDtos,reservationSettingDto);
    }

    public List<WorkLocationDto> getWorkLocationByCid() {
        String companyID = AppContexts.user().companyId();
        return bentoMenuScreenRepository.findDataWorkLocation(companyID);
    }
}
