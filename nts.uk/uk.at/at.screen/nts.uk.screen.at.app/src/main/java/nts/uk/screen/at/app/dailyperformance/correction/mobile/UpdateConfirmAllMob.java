package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.screen.at.app.dailymodify.command.DailyModifyRCommandFacade;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemCheckBox;

@Stateless
public class UpdateConfirmAllMob {
	@Inject
	private DailyModifyRCommandFacade dailyModifyRCommandFacade;

	public void confirmAll(List<DPItemCheckBox> dataCheckSign, List<DailyRecordDto> dailyRecordDtos) {
		if (GeneralDate.today().before(dataCheckSign.get(0).getDate())) {
			// 確認
			if (dataCheckSign.get(0).isValue()) {
				throw new BusinessException("Msg_1545");
			// 解除`
			} else {
				throw new BusinessException("Msg_1545");
			}
			
		} else {
			// insert sign
			dailyModifyRCommandFacade.insertSign(dataCheckSign, dailyRecordDtos, dailyRecordDtos, new HashSet<>());
		}
	}
}
