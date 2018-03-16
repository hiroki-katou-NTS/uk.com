package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;

@Stateless
public class JpaRervLeaGrantRemDataRepo extends JpaRepository implements RervLeaGrantRemDataRepository{

	@Override
	public Optional<ReserveLeaveGrantRemainingData> get(String employeeId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
