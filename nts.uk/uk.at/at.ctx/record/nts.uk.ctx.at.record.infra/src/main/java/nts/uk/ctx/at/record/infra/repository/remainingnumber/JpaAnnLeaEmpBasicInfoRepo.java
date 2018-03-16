package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;

@Stateless
public class JpaAnnLeaEmpBasicInfoRepo extends JpaRepository implements AnnLeaEmpBasicInfoRepository{

	@Override
	public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
