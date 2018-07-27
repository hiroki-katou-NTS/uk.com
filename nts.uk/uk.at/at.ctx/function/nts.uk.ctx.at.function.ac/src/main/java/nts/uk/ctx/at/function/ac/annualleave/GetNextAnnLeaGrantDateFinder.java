package nts.uk.ctx.at.function.ac.annualleave;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.annualleave.GetNextAnnLeaGrantDateAdapter;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.GetNextAnnLeaGrantDate;

@Stateless
public class GetNextAnnLeaGrantDateFinder implements  GetNextAnnLeaGrantDateAdapter {
	/**
	 * 次回年休付与年月日を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 次回年休付与年月日
	 * RequestList369
	 */
	@Inject
	private GetNextAnnLeaGrantDate getNextAnnLeaGrantDate;

	@Override
	public Optional<GeneralDate> algorithm(String companyId, String employeeId) {
		Optional<GeneralDate> algorithm = getNextAnnLeaGrantDate.algorithm(companyId, employeeId);
		return algorithm;
	}
}
