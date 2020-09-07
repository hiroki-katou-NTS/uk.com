package nts.uk.ctx.at.record.dom.manageworkplaceagreedhours;

import java.util.List;
import java.util.Optional;

/**
 * 	Repository	 職場３６協定時間
 */
public interface WorkplaceAgreedHoursRepository {
    void insert(WorkplaceAgreedHours domain);
    void update(WorkplaceAgreedHours domain);
    void delete(WorkplaceAgreedHours domain);
    List<WorkplaceAgreedHours> getByListWorkplaceId(List<String> listWorkplaceId);
    Optional<WorkplaceAgreedHours> getByWorkplaceId(String workplaceId);
}
