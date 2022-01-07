package nts.uk.file.at.infra.vacation.set.specialleave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.LeaveCountedAsWorkDaysType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCount;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.WorkDaysNumberOnLeaveCountRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.specialleave.SpecialLeaveSettingRepository;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaSpecialLeaveRepository implements SpecialLeaveSettingRepository {

	private static final int LEAVE_TYPE = 3;

	@Inject
	private WorkDaysNumberOnLeaveCountRepository repository;
	
	@Inject
	private TimeSpecialLeaveMngSetRepository repositorySpecial;

	@Override
	public List<MasterData> getWorkDaysNumberOnLeaveCount(String cid) {
		WorkDaysNumberOnLeaveCount domain = this.repository.findByCid(cid);
		Optional<LeaveCountedAsWorkDaysType> optLeave = domain.getCountedLeaveList().stream()
				.filter(data -> data.value == LEAVE_TYPE).findAny();
		 List<MasterData> dataMaster = new ArrayList<>();
		Optional<TimeSpecialLeaveManagementSetting> domainSpecialLeave = this.repositorySpecial.findByCompany(cid);
		String isCounting = optLeave.isPresent() ? "管理する" : "管理しない";
		String checkValue = domainSpecialLeave.map(data -> data.getTimeVacationDigestUnit().getManage().equals(ManageDistinct.NO) ? "" : data.getTimeVacationDigestUnit().getDigestUnit().nameId)
				.orElse("");
		dataMaster.add(this.buildARow(TextResource.localize("KMF001_350"), TextResource.localize("KMF001_351"), isCounting, false));
		dataMaster.add(this.buildARow(TextResource.localize("KMF001_356"), TextResource.localize("KMF001_357"), domainSpecialLeave.map(data -> data.getTimeVacationDigestUnit().getManage().nameId)
				.orElse(""), false));
		dataMaster.add(this.buildARow("", TextResource.localize("KMF001_211"), checkValue, true));
		return dataMaster;
	}
	
    private MasterData buildARow(String value1, String value2, String value3, Boolean checkRight) {
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(EmployeeSystemImpl.KMF001_166, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_166)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_B01, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_B01)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_167, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_167)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(checkRight ? ColumnTextAlign.RIGHT : ColumnTextAlign.LEFT))
                .build());
        return MasterData.builder().rowData(data).build();
    }
}
