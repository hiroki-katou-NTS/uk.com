package nts.uk.ctx.at.record.dom.managecompanyagreedhours;

public interface Company36AgreedHoursRepository {
    void insert(Company36AgreedHours domain);
    void update(Company36AgreedHours domain);
    Company36AgreedHours getByCid(String cid);
}
