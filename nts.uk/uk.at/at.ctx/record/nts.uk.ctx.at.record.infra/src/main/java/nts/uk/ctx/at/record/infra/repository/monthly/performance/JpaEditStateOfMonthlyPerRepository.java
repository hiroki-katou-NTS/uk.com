package nts.uk.ctx.at.record.infra.repository.monthly.performance;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.performance.EditStateOfMonthlyPerRepository;

@Stateless
public class JpaEditStateOfMonthlyPerRepository extends JpaRepository implements EditStateOfMonthlyPerRepository{

}
