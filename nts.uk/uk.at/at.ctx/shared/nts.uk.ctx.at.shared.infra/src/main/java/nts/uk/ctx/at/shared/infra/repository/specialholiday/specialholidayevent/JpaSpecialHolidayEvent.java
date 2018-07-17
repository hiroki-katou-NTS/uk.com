package nts.uk.ctx.at.shared.infra.repository.specialholiday.specialholidayevent;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEventRepository;

@Stateless
public class JpaSpecialHolidayEvent extends JpaRepository implements SpecialHolidayEventRepository {

}
