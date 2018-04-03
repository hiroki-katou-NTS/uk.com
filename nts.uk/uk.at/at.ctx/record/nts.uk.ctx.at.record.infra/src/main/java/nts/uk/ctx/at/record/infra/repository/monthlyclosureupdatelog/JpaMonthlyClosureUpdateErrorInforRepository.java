package nts.uk.ctx.at.record.infra.repository.monthlyclosureupdatelog;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseUpdErrInf;
import nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog.KrcdtMcloseUpdErrInfPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaMonthlyClosureUpdateErrorInforRepository extends JpaRepository
		implements MonthlyClosureUpdateErrorInforRepository {

	@Override
	public void add(MonthlyClosureUpdateErrorInfor domain) {
		this.commandProxy().insert(KrcdtMcloseUpdErrInf.fromDomain(domain));
	}

	@Override
	public Optional<MonthlyClosureUpdateErrorInfor> getById(String monthlyClosureUpdateLogId, String employeeId) {
		Optional<KrcdtMcloseUpdErrInf> optEntity = this.queryProxy()
				.find(new KrcdtMcloseUpdErrInfPk(employeeId, monthlyClosureUpdateLogId), KrcdtMcloseUpdErrInf.class);
		if (optEntity.isPresent())
			return Optional.of(optEntity.get().toDomain());
		else
			return Optional.empty();
	}

}
