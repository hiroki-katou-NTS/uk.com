package nts.uk.ctx.at.record.dom.workinformation.service.updateworkinfo;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class UpdateWorkInfoOfDailyPerforServiceImpl implements UpdateWorkInfoOfDailyPerforService {

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public void updateWorkInfoOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {

		// 就業時間帯を補正する
		// 実績の勤務種類を取得
		WorkTypeCode workTypeCode = workInfoOfDailyPerformance.getRecordInfo().getWorkTypeCode();

		Optional<WorkType> workType = this.workTypeRepository.findByPK(companyId, workTypeCode.v());

		if (workType.isPresent()) {
			WorkTypeClassification oneDay = workType.get().getDailyWork().getOneDay();
			if (oneDay == WorkTypeClassification.Holiday || oneDay == WorkTypeClassification.Pause
					|| oneDay == WorkTypeClassification.ContinuousWork
					|| oneDay == WorkTypeClassification.LeaveOfAbsence || oneDay == WorkTypeClassification.Closure) {
				WorkInformation recordWorkInformation = new WorkInformation(null,
						workInfoOfDailyPerformance.getRecordInfo().getWorkTypeCode().v());
				workInfoOfDailyPerformance.setRecordInfo(recordWorkInformation);
			}
		}
	}

}
