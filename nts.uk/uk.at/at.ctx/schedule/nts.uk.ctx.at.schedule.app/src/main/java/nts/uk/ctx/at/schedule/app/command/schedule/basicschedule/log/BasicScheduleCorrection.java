package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrection;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataKey;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;

@RequiredArgsConstructor

public class BasicScheduleCorrection implements DataCorrection{
	
	/** 修正区分*/
	@Getter
	private final CorrectionAttr correctionAttr;
	
	/** 項目情報*/
	@Getter
	private final ItemInfo correctedItem;
	
	/** 備考*/
	@Getter
	private final String remark;
	
	/** 対象データKEY情報*/
	
	private final GeneralDate date;
	
	/** 並び順*/
	@Getter
	private final int showOrder;
	
	/** 対象ユーザ*/
	@Getter
	private final UserInfo targetUser;
	
	/** 対象データ種類*/
	@Override
	public TargetDataType getTargetDataType() {
		return TargetDataType.SCHEDULE;
	}
	/** 対象データKEY情報*/
	@Override
	public TargetDataKey getTargetDataKey() {
		return TargetDataKey.of(this.date);
	}


}
