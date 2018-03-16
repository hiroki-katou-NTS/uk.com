package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.arc.layer.infra.data.JpaRepository;

@Stateless
public class JpaAnnLeaMaxDataRepo extends JpaRepository implements AnnLeaMaxDataRepository{

	@Override
	public Optional<AnnualLeaveMaxData> get(String employeId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
