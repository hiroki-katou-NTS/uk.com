package nts.uk.ctx.at.record.dom.manageclassificationagreementtime;

import nts.uk.ctx.at.shared.dom.standardtime.AgreementTimeOfClassification;

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
    Optional<AgreementTimeOfClassification> getByCidAndClassificationCode(String cid, String classificationCode);
}
