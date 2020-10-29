package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;


import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import java.util.List;
import java.util.Optional;
/**
 * 	Repository	 分類３６協定時間
 */
public interface Classification36AgreementTimeRepository {
    void insert(AgreementTimeOfClassification domain);
    void update(AgreementTimeOfClassification domain);
    void delete(AgreementTimeOfClassification domain);
    List<AgreementTimeOfClassification> getByCid(String cid);
    List<AgreementTimeOfClassification> find(String cid,String classificationCode);
    List<AgreementTimeOfClassification> findCidAndLstCd(String cid,List<String> classificationCodes);
    Optional<AgreementTimeOfClassification> getByCidAndClassificationCode(String cid, String classificationCode,LaborSystemtAtr laborSystemAtr);
}
