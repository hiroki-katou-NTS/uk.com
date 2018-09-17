package nts.uk.ctx.sys.log.app.find.datacorrectionlog;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.sys.log.dom.datacorrectionlog.DataCorrectionLogRepository;
import nts.uk.ctx.sys.log.dom.logbasicinfo.LogBasicInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.content.DataCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.TargetDataType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DataCorrectionLogFinder {

	@Inject
	private DataCorrectionLogRepository correctionLogRepo;

	@Inject
	private LogBasicInfoRepository basicInfoRepo;

	public List<DataCorrectionLogDto> getDataLog(DataCorrectionLogParams params) {
		List<DataCorrectionLogDto> result = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<DataCorrectionLog> listCorrectionLog = new ArrayList<>();
		if (params.getStartYmd() != null && params.getEndYmd() != null && params.getStartYm() != null
				&& params.getEndYm() != null) {
			// ※この分岐は月別修正から呼び出し時のみ - ※ This branch only from the monthly correction to calling
			listCorrectionLog = correctionLogRepo.getAllLogData(convertFuncId(params.getFunctionId()),
					params.getListEmployeeId(), new DatePeriod(params.getStartYmd(), params.getEndYmd()),
					new YearMonthPeriod(new YearMonth(params.getStartYm()), new YearMonth(params.getEndYm())));
		} else if (params.getStartYmd() != null && params.getEndYmd() != null) {
			listCorrectionLog = correctionLogRepo.getAllLogData(convertFuncId(params.getFunctionId()),
					params.getListEmployeeId(), new DatePeriod(params.getStartYmd(), params.getEndYmd()));
		} else if (params.getStartYm() != null && params.getEndYm() != null) {
			listCorrectionLog = correctionLogRepo.getAllLogData(convertFuncId(params.getFunctionId()),
					params.getListEmployeeId(),
					new YearMonthPeriod(new YearMonth(params.getStartYm()), new YearMonth(params.getEndYm())));
		} else {
			listCorrectionLog = correctionLogRepo.getAllLogData(convertFuncId(params.getFunctionId()),
					params.getListEmployeeId(), Year.of(params.getStartY()), Year.of(params.getEndY()));
		}

		if (listCorrectionLog.isEmpty())
			throw new BusinessException("Msg_37");

		for (DataCorrectionLog d : listCorrectionLog) {
			Optional<LogBasicInformation> basicInfo = basicInfoRepo.getLogBasicInfo(companyId, d.getOperationId());
			DataCorrectionLogDto log = new DataCorrectionLogDto(d.getTargetDataKey().getDateKey(),
					d.getTargetUser().getUserName(), d.getCorrectedItem().getName(),
					d.getCorrectedItem().getValueBefore().getViewValue(),
					d.getCorrectedItem().getValueAfter().getViewValue(),
					basicInfo.isPresent() ? basicInfo.get().getUserInfo().getUserName() : null,
					basicInfo.isPresent() ? basicInfo.get().getModifiedDateTime() : null, d.getCorrectionAttr().value,
					d.getTargetUser().getEmployeeId(), d.getShowOrder());
			result.add(log);
		}
		if (params.getDisplayFormat() == 0) { // by date
			Comparator<DataCorrectionLogDto> c = Comparator.comparing(DataCorrectionLogDto::getTargetDate)
					.thenComparing(DataCorrectionLogDto::getEmployeeId)
					.thenComparing(DataCorrectionLogDto::getDisplayOrder)
					.thenComparing(DataCorrectionLogDto::getModifiedDateTime, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(DataCorrectionLogDto::getCorrectionAttr);
			Collections.sort(result, c);
		} else { // by individual
			Comparator<DataCorrectionLogDto> c = Comparator.comparing(DataCorrectionLogDto::getEmployeeId)
					.thenComparing(DataCorrectionLogDto::getTargetDate)
					.thenComparing(DataCorrectionLogDto::getDisplayOrder)
					.thenComparing(DataCorrectionLogDto::getModifiedDateTime, Comparator.nullsLast(Comparator.naturalOrder()))
					.thenComparing(DataCorrectionLogDto::getCorrectionAttr);
			Collections.sort(result, c);
		}
		return result;
	}

	private TargetDataType convertFuncId(int funcId) {
		switch (funcId) {
		case 1:
			return TargetDataType.of(0);
		case 2:
			return TargetDataType.of(1);
		case 3:
			return TargetDataType.of(2);
		case 4:
			return TargetDataType.of(3);
		case 5:
			return TargetDataType.of(6);
		case 6:
			return TargetDataType.of(7);
		case 7:
			return TargetDataType.of(8);
		case 8:
			return TargetDataType.of(9);
		case 9:
			return TargetDataType.of(10);
		default:
			return null;
		}
	}

}
