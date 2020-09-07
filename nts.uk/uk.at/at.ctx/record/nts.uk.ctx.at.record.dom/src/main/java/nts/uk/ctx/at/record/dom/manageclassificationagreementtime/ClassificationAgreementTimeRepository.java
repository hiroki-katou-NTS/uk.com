package nts.uk.ctx.at.record.dom.manageclassificationagreementtime;

import java.util.List;
import java.util.Optional;

public interface ClassificationAgreementTimeRepository {
    void insert(ClassificationAgreementTime domain);
    void update(ClassificationAgreementTime domain);
    void delete(ClassificationAgreementTime domain);
    List<ClassificationAgreementTime> getByCid(String cid);
    Optional<ClassificationAgreementTime> getByCidAndClassificationCode(String cid, String classificationCode);
}
