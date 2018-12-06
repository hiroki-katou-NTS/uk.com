package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationInformationRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableContentRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableQualification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class WageTableQualificationFinder {

    @Inject
    private QualificationInformationRepository qualificationInformationRepository;

    @Inject
    private WageTableContentRepository wageTableContentRepository;


    /**
     * アルゴリズム「E画面_賃金テーブル作成処理」を実施
     *
     * @param historyId 履歴ID
     * @return 資格_賃金テーブル作成
     */
    public List<WageTableQualificationDto> createWageTableQualification(String historyId) {
        List<QualificationInformation> qualificationInformation = this.qualificationInformationRepository.getQualificationGroupSettingByCompanyID();

        if (qualificationInformation.isEmpty()) return Collections.emptyList();

        //ドメインモデル「賃金テーブル内容」を取得する
        List<WageTableQualification> wageTableContent = wageTableContentRepository.getWageTableQualification(historyId);

        List<WageTableQualification> wageTableContentDefault = wageTableContentRepository.getDefaultWageTableQualification();
        if (wageTableContent.isEmpty())
            return wageTableContentDefault.stream().map(WageTableQualificationDto::fromDomain).collect(Collectors.toList());

        return wageTableContent.stream().map(WageTableQualificationDto::fromDomain).collect(Collectors.toList());
    }
}