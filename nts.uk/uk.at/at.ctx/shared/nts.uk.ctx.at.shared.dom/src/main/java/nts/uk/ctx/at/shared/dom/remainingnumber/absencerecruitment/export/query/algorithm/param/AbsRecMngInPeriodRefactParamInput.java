package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.InterimMngParamCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

/**
 * @author ThanhNX
 *
 */
@Getter
public class AbsRecMngInPeriodRefactParamInput extends InterimMngParamCommon {

	private Optional<CompenLeaveAggrResult> optBeforeResult;
	
	/** 上書き用の暫定振休管理データ */
	public List<InterimAbsMng> getUseAbsMng() {
		return this.getInterimMng().stream().filter(x -> x.getRemainType() == RemainType.PAUSE).map(x -> (InterimAbsMng) x)
				.collect(Collectors.toList());
	}

	/**
	 * 上書き用の暫定振出管理データ
	 */
	public List<InterimRecMng> getUseRecMng() {
		return this.getInterimMng().stream().filter(x -> x.getRemainType() == RemainType.PICKINGUP)
				.map(x -> (InterimRecMng) x).collect(Collectors.toList());
	}

	public AbsRecMngInPeriodRefactParamInput(String cid, String sid, DatePeriod dateData, GeneralDate screenDisplayDate,
			boolean mode, boolean replaceChk, List<InterimAbsMng> useAbsMng, List<InterimRemain> interimMng,
			List<InterimRecMng> useRecMng, Optional<CompenLeaveAggrResult> optBeforeResult,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate, FixedManagementDataMonth fixManaDataMonth) {
		this(cid, sid, dateData, screenDisplayDate, mode, replaceChk, createFull(useAbsMng, useRecMng, interimMng), optBeforeResult, creatorAtr, processDate, fixManaDataMonth);
	}

	public AbsRecMngInPeriodRefactParamInput(String cid, String sid, DatePeriod dateData, GeneralDate screenDisplayDate,
			boolean mode, boolean replaceChk, List<InterimRemain> interimMng,
			Optional<CompenLeaveAggrResult> optBeforeResult,
			Optional<CreateAtr> creatorAtr, Optional<DatePeriod> processDate, FixedManagementDataMonth fixManaDataMonth) {
		super(cid, sid, dateData, mode, screenDisplayDate, replaceChk, interimMng, creatorAtr, processDate, fixManaDataMonth);
		this.optBeforeResult = optBeforeResult;
	}
	
	private static List<InterimRemain> createFull(List<InterimAbsMng> useAbsMng, List<InterimRecMng> useRecMng,
			List<InterimRemain> lstCommon) {
		List<InterimRemain> lstAll = new ArrayList<>();
		lstAll.addAll(useAbsMng.stream().map(x -> {
			val common = lstCommon.stream().filter(c -> c.getRemainManaID().equals(x.getRemainManaID())).findFirst().orElse(null);
			return createFullInterimAbs(x, common);
		}).collect(Collectors.toList()));
		
		lstAll.addAll(useRecMng.stream().map(x -> {
			val common = lstCommon.stream().filter(c -> c.getRemainManaID().equals(x.getRemainManaID())).findFirst().orElse(null);
			return createFullInterimRec(x, common);
		}).collect(Collectors.toList()));
		return lstAll;
	}
	
	private static InterimAbsMng createFullInterimAbs(InterimAbsMng absMng, InterimRemain common) {
		return new InterimAbsMng(common.getRemainManaID(), common.getSID(), common.getYmd(), common.getCreatorAtr(),
				common.getRemainType(), absMng.getRequeiredDays(), absMng.getUnOffsetDays());
	}

	private static InterimRecMng createFullInterimRec(InterimRecMng recMng, InterimRemain common) {
		return new InterimRecMng(common.getRemainManaID(), common.getSID(), common.getYmd(), common.getCreatorAtr(),
				common.getRemainType(), recMng.getExpirationDate(), recMng.getOccurrenceDays(), recMng.getUnUsedDays());
	}
	
}
