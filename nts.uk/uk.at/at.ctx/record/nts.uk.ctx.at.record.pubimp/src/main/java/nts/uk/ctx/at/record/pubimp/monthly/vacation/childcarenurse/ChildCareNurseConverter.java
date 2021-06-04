package nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.TempChildCareNurseManagementExport;
import nts.uk.ctx.at.record.pub.remainnumber.work.DigestionHourlyTimeTypeExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;

public class ChildCareNurseConverter {

	public static TempChildCareNurseManagement toDomain(TempChildCareNurseManagementExport export) {
		return new TempChildCareNurseManagement(
				toDomain(export.getUsedNumber()),
				export.getAppTimeType().map(mapper->toDomain(mapper)));
	}

	public static ChildCareNurseUsedNumber toDomain(ChildCareNurseUsedNumberExport export) {
		return ChildCareNurseUsedNumber.of(
				new DayNumberOfUse(export.getUsedDays()),
				export.getUsedTime().isPresent() ? Optional.of(new TimeOfUse(export.getUsedTime().get())) : Optional.empty()
				);
	}

	public static DigestionHourlyTimeType toDomain(DigestionHourlyTimeTypeExport export) {

		DigestionHourlyTimeType domain
			= DigestionHourlyTimeType.of(
					export.isHourlyTimeType(),
					export.getAppTimeType().map(mapper->EnumAdaptor.valueOf(mapper, AppTimeType.class)));
		return domain;
	}
}
