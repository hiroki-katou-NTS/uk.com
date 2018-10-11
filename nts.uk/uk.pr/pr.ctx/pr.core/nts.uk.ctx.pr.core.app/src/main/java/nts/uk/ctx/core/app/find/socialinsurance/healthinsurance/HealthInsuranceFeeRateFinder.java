package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance;

import lombok.val;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.BonusHealthInsuranceRateDto;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceDto;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceFeeRateHistoryDto;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceMonthlyFeeDto;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRateRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistoryRepository;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOffice;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class HealthInsuranceFeeRateFinder {

    @Inject
    private HealthInsuranceFeeRateHistoryRepository healthInsuranceFeeRateHistoryRepository;

    @Inject
    private SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

    @Inject
    private BonusHealthInsuranceRateRepository bonusHealthInsuranceRateRepository;

    @Inject
    private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFeeRepository;

    /**
     * アルゴリズム「選択処理」を実行する
     *
     * @param historyId 履歴ID
     * @return HealthInsuranceDto
     */
    public HealthInsuranceDto getHealthInsuranceByHistoryId(String historyId) {
        val bonusHealthInsuranceRate = bonusHealthInsuranceRateRepository.getBonusHealthInsuranceRateById(historyId);
        val healthInsuranceMonthlyFee = healthInsuranceMonthlyFeeRepository.getHealthInsuranceMonthlyFeeById(historyId);
        return new HealthInsuranceDto(BonusHealthInsuranceRateDto.fromDomain(bonusHealthInsuranceRate), HealthInsuranceMonthlyFeeDto.fromDomain(healthInsuranceMonthlyFee));
    }

    /**
     * アルゴリズム「初期データ取得処理」を実行する
     *
     * @return List<HealthInsuranceFeeRateHistoryDto>
     */
    public List<SocialInsuranceOfficeDto> getHealthInsuranceFeeRateByCompanyId() {
        List<SocialInsuranceOfficeDto> healthDtoList = new ArrayList<>();
        String companyId = AppContexts.user().companyId();
        //ドメインモデル「社会保険事業所」を全て取得する
        List<SocialInsuranceOffice> socialInsuranceOfficeList = this.socialInsuranceOfficeRepository.findByCid(companyId);

        socialInsuranceOfficeList.forEach(office -> {
            Optional<HealthInsuranceFeeRateHistory> healthInsuranceFeeRateHistory = healthInsuranceFeeRateHistoryRepository.getHealthInsuranceFeeRateHistoryByCid(companyId, office.getCode().v());
            healthDtoList.add(new SocialInsuranceOfficeDto(office.getCode().v(), office.getName().v(), HealthInsuranceFeeRateHistoryDto.fromDomain(office.getCode().v(), healthInsuranceFeeRateHistory)));
        });

        return healthDtoList;
    }
}
