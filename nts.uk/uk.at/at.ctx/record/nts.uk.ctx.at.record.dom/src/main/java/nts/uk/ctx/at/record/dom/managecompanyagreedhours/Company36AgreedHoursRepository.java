package nts.uk.ctx.at.record.dom.managecompanyagreedhours;

import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;

/**
 * 会社３６協定時間 Repository
 */
public interface Company36AgreedHoursRepository {
    void insert(AgreementTimeOfCompany domain);
    void update(AgreementTimeOfCompany domain);
    AgreementTimeOfCompany getByCid(String cid);
}
