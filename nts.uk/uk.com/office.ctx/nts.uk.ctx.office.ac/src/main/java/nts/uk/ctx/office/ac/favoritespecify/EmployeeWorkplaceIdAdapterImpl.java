package nts.uk.ctx.office.ac.favoritespecify;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.AffAtWorkplaceExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeWorkplaceIdAdapter;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeWorkplaceIdAdapterImpl implements EmployeeWorkplaceIdAdapter {
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public Map<String, String> getWorkplaceId(List<String> sIds, GeneralDate baseDate) {
		// ＄所属職場 ＝ RQ.社員ID（List）と基準日から所属職場IDを取得(社員IDリスト、基準日) //List<所属職場履歴項目>
		List<AffAtWorkplaceExport> listAffWorkplace = this.workplacePub.findBySIdAndBaseDate(sIds, baseDate).stream()
				.filter(value -> sIds.contains(value.getEmployeeId())).collect(Collectors.toList());
		if (listAffWorkplace.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, String> map = listAffWorkplace.stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getWorkplaceId()));
		return map;
	}

}
