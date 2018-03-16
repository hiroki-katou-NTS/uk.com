package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;

@Stateless
public class JpaAnnLeaGrantRemDataRepo extends JpaRepository implements AnnLeaGrantRemDataRepository{

	@Override
	public Optional<AnnualLeaveGrantRemainingData> get(String employeeId) {
		return Optional.empty();
	}
	
}
