package nts.uk.ctx.at.record.dom.manageworkplaceagreedhours;

import java.util.List;
import java.util.Optional;

/**
 * 	Repository	 職場３６協定時間
 */
public interface Workplace36AgreedHoursRepository {
    void insert(Workplace36AgreedHours domain);
    void update(Workplace36AgreedHours domain);
    void delete(Workplace36AgreedHours domain);
    List<Workplace36AgreedHours> getByListWorkplaceId(List<String> listWorkplaceId);
    Optional<Workplace36AgreedHours> getByWorkplaceId(String workplaceId);
}
