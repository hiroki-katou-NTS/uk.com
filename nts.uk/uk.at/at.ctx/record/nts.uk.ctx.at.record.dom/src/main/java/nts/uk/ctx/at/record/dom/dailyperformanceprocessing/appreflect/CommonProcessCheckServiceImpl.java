package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfoRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CommonProcessCheckServiceImpl implements CommonProcessCheckService{

	@Inject
	private ErrMessageInfoRepository errMessInfo;

	@Override
	public void createLogError(String sid, GeneralDate ymd, String excLogId) {
		ErrMessageInfo errMes = new ErrMessageInfo(sid, 
				excLogId,
				new ErrMessageResource("024"),
				EnumAdaptor.valueOf(2, ExecutionContent.class),
				ymd,
				new ErrMessageContent(TextResource.localize("Msg_1541")));
		this.errMessInfo.add(errMes);
	}
}
