package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.KrcmtAnnLeaBasicInfo;

@Stateless
public class JpaAnnLeaEmpBasicInfoRepo extends JpaRepository implements AnnLeaEmpBasicInfoRepository {

	@Override
	public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
		Optional<KrcmtAnnLeaBasicInfo> entityOpt = this.queryProxy().find(employeeId, KrcmtAnnLeaBasicInfo.class);
		if (entityOpt.isPresent()) {
			KrcmtAnnLeaBasicInfo ent = entityOpt.get();
			return Optional.of(AnnualLeaveEmpBasicInfo.createFromJavaType(ent.employeeId, ent.workDaysPerYear,
					ent.workDaysBeforeIntro, ent.grantTableCode, ent.grantStandardDate));
		}
		return Optional.empty();
	}

}
