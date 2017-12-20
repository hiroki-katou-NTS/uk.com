package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyAttdItemAuthFinder {
	@Inject
	private DailyAttdItemAuthRepository dailyAttdItemAuthRepository;

	public List<DailyAttdItemAuthDto> getListDailyAttendanceItemAuthority(String authorityId) {
		String companyId = AppContexts.user().companyId();
		List<DailyAttendanceItemAuthority> listDailyAttendanceItemAuthority = this.dailyAttdItemAuthRepository
				.getListDailyAttendanceItemAuthority(authorityId, companyId);
		return listDailyAttendanceItemAuthority.stream().map(c -> toDto(c)).collect(Collectors.toList());
	}

	private DailyAttdItemAuthDto toDto(DailyAttendanceItemAuthority dailyAttendanceItemAuthority) {
		return new DailyAttdItemAuthDto(dailyAttendanceItemAuthority.getAttendanceItemId(),
				dailyAttendanceItemAuthority.getAuthorityId(), dailyAttendanceItemAuthority.isYouCanChangeIt(),
				dailyAttendanceItemAuthority.isCanBeChangedByOthers(), dailyAttendanceItemAuthority.isUse(),
				dailyAttendanceItemAuthority.getUserCanSet() == 1 ? true : false);

	}

}
