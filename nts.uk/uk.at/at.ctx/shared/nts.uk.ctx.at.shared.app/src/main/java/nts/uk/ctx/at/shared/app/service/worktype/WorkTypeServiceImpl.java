package nts.uk.ctx.at.shared.app.service.worktype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;
import nts.uk.ctx.at.shared.dom.worktype.CloseAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeServiceImpl implements WorkTypeService {
	
	@Inject
	private WorkTypeRepository workTypeRepository;

	@Override
	public boolean isExistingCloseAtr(WorkType workType, WorkTypeCommandBase workTypeCommandBase) {
		String companyId = AppContexts.user().companyId();
		if (!workType.isOneDay()) {
			return false;
		}
		// 追加する勤務の分類＝『休業』の場合
		if (workType.getDailyWork().getClassification().equals(WorkTypeClassification.Closure)) {
			// ドメインモデル「勤務種類」の勤務種類_休業区分をチェックする
			List<CloseAtr> existingData = this.workTypeRepository.findByCompanyId(companyId).stream()
					.filter(data -> data.getWorkTypeSetByAtr(WorkAtr.OneDay).isPresent()
							&& data.getDailyWork().getClassification().equals(WorkTypeClassification.Closure)
							&& !data.getWorkTypeCode().equals(workType.getWorkTypeCode()))
					.map(data -> data.getWorkTypeSetByAtr(WorkAtr.OneDay).get().getCloseAtr())
					.collect(Collectors.toList());
			// 更新する勤務種類の休業区分が他の既存の勤務種類の休業区分と重複する場合
			return existingData.contains(EnumAdaptor.valueOf(workTypeCommandBase.getOneDay().getCloseAtr(), CloseAtr.class));
		}
		return false;
	}

}
