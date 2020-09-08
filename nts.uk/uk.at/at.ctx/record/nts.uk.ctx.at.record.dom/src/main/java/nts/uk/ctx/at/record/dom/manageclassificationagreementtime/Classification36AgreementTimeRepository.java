package nts.uk.ctx.at.record.dom.manageclassificationagreementtime;

import java.util.List;
import java.util.Optional;

public interface Classification36AgreementTimeRepository {
    void insert(Classification36AgreementTime domain);
    void update(Classification36AgreementTime domain);
    void delete(Classification36AgreementTime domain);
    List<Classification36AgreementTime> getByCid(String cid);
    Optional<Classification36AgreementTime> getByCidAndClassificationCode(String cid, String classificationCode);
}
