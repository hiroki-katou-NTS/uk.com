package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayGrantRepository;

/**
 * 
 * @author TanLV
 *
 */

@Stateless
public class JpaYearHolidayGrantRepository extends JpaRepository implements YearHolidayGrantRepository {

}
