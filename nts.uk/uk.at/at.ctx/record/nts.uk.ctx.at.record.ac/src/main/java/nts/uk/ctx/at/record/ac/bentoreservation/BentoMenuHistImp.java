package nts.uk.ctx.at.record.ac.bentoreservation;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class BentoMenuHistImp implements BentomenuAdapter {

    @Inject
    private SyWorkplacePub syWorkplacePub;

    @Override
    public Optional<SWkpHistExport> findBySid(String employeeId, GeneralDate baseDate) {
        return syWorkplacePub.findBySid(employeeId,baseDate)
                .map(x -> new SWkpHistExport(x.getDateRange(), x.getEmployeeId(), x.getWorkplaceId(),
                        x.getWorkplaceCode(), x.getWorkplaceName(), x.getWkpDisplayName()));
    }
}
