package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.InterimMngParamCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

/**
 * @author ThanhNX
 *
 */
@Getter
public class AbsRecMngInPeriodRefactParamInput extends InterimMngParamCommon {

	/** 上書き用の暫定管理データ */
	private List<InterimAbsMng> useAbsMng;

	/**
	 * 上書き用の暫定振出管理データ
	 */
	private List<InterimRecMng> useRecMng;
	/**
	 * 前回振休の集計結果
	 */
	private Optional<CompenLeaveAggrResult> optBeforeResult;

	public AbsRecMngInPeriodRefactParamInput(String cid, String sid, DatePeriod dateData, GeneralDate screenDisplayDate,
			boolean mode, boolean replaceChk, List<InterimAbsMng> useAbsMng, List<InterimRemain> interimMng,
			List<InterimRecMng> useRecMng, Optional<CompenLeaveAggrResult> optBeforeResult,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate, FixedManagementDataMonth fixManaDataMonth) {
		super(cid, sid, dateData, mode, screenDisplayDate, replaceChk, interimMng, creatorAtr, processDate, fixManaDataMonth);
		this.useAbsMng = useAbsMng;
		this.useRecMng = useRecMng;
		this.optBeforeResult = optBeforeResult;
	}

}
