package nts.uk.pr.file.infra.comparingsalarybonus;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.ComparingSalaryBonusQueryRepository;
@Stateless
public class JpaComparingSalaryBonusQueryRepository extends JpaRepository implements ComparingSalaryBonusQueryRepository{

}
