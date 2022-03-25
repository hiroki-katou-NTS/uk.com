package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.wage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.service.GetPeriodByDailyDataExistsService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.DailyPeriodRecordPub;

/**
* @author sakuratani
* 
* 			日別実績データが存在する期間を取得するPubImpl
*
*/
@Stateless
public class DailyPeriodRecordPubImpl implements DailyPeriodRecordPub {

	@Inject
	private WorkInformationRepository repo;

	@Override
	public Optional<DatePeriod> get(String employeeId, DatePeriod period) {
		RequireImpl impl = new RequireImpl(repo);
		return GetPeriodByDailyDataExistsService.get(impl, employeeId, period);
	}

	@AllArgsConstructor
	public class RequireImpl implements GetPeriodByDailyDataExistsService.Require {

		private WorkInformationRepository repo;

		@Override
		public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod period) {
			return repo.findByPeriodOrderByYmd(employeeId, period);
		}

	}
}