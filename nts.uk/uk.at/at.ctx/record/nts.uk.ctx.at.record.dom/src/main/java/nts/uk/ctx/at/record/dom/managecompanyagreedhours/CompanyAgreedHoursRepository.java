package nts.uk.ctx.at.record.dom.managecompanyagreedhours;

import java.util.List;
import java.util.Optional;

public interface CompanyAgreedHoursRepository {
    void insert(CompanyAgreedHours domain);
    void update(CompanyAgreedHours domain);
    CompanyAgreedHours getByCid(String cid);
}
