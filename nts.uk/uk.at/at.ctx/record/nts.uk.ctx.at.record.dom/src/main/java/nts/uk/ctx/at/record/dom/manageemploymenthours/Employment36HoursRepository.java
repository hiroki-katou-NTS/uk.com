package nts.uk.ctx.at.record.dom.manageemploymenthours;

import java.util.List;
import java.util.Optional;

/**
 * 雇用３６協定時間 IRepository
 */
public interface Employment36HoursRepository {
     void insert(Employment36Hours domain);
     void update(Employment36Hours domain);
     void delete(Employment36Hours domain);
     List<Employment36Hours> getByCid(String cid);
     Optional<Employment36Hours>getByCidAndEmployCode(String cid, String employCode);
}
