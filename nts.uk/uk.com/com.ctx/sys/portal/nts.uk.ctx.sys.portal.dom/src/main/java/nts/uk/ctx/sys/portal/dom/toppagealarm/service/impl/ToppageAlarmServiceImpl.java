package nts.uk.ctx.sys.portal.dom.toppagealarm.service.impl;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.portal.dom.toppagealarm.AlarmClassification;
import nts.uk.ctx.sys.portal.dom.toppagealarm.DisplayAtr;
import nts.uk.ctx.sys.portal.dom.toppagealarm.ToppageAlarmData;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.ToppageAlarmService;
import nts.uk.ctx.sys.portal.dom.toppagealarm.service.ToppageCheckResult;

@Stateless
public class ToppageAlarmServiceImpl implements ToppageAlarmService {

	@Override
	public ToppageCheckResult isAlarmExisted(Require require, String companyId, AlarmClassification alarmCls, String idenKey,
			String sId, DisplayAtr dispAtr) {
		// $トップページアラームデータ = require.取得する(会社ID, アラーム分類, 表示社員ID, 識別キー, 表示社員区分)
		Optional<ToppageAlarmData> oDomain = require.getOne(companyId, alarmCls, idenKey, sId, dispAtr);
		if (oDomain.isPresent()) {
			// if $トップページアラームデータ.isPresent
			return ToppageCheckResult.builder()
					.isExisted(true)
					.occurrenceDateTime(oDomain.map(ToppageAlarmData::getOccurrenceDateTime))
					.build();
		} else {
			// else
			return ToppageCheckResult.builder()
					.isExisted(false)
					.build();
		}
	}

}
