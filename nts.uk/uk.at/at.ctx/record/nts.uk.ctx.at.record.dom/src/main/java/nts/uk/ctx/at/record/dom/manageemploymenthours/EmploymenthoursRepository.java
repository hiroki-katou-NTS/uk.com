package nts.uk.ctx.at.record.dom.manageemploymenthours;

import java.util.List;
import java.util.Optional;

/**
 * 雇用３６協定時間 IRepository
 */
public interface EmploymenthoursRepository {
     void insert(Employmenthours domain);
     void update(Employmenthours domain);
     void delete(Employmenthours domain);
     List<Employmenthours> getByCid(String cid);
     Optional<Employmenthours>getByCidAndEmployCode(String cid, String employCode);
}
