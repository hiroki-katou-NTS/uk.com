package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;

/**
 * The Class JpaAttendanceRecordExportSettingRepo.
 *
 * @author nws-lienptk
 */
@Stateless
public class JpaAttendanceRecordExportSettingRepo extends JpaRepository
		implements AttendanceRecordFreeSettingRepository, AttendanceRecordStandardSettingRepository, AttendanceRecordExportSettingRepository {
	
	private static final String GET_STANDARD_SETTING_BY_CODE = "SELECT out FROM KfnmtRptWkAtdOut out"
			+ " WHERE out.cid = :companyId"
			+ " AND out.itemSelType = :selectionType"
			+ " AND out.exportCD = :code";
	private static final String GET_FREE_SETTING_BY_CODE = GET_STANDARD_SETTING_BY_CODE + " AND out.sid = :employeeId";

	private static final String GET_SETTING_BY_LAYOUT_ID = "SELECT out FROM KfnmtRptWkAtdOut out"
			+ " WHERE out.layoutId = :layoutId";
	
	private static final String GET_STANDARD_SETTING = "SELECT out FROM KfnmtRptWkAtdOut out"
			+ " WHERE out.cid = :companyId"
			+ " AND out.itemSelType = :selectionType";
	private static final String GET_FREE_SETTING = GET_STANDARD_SETTING + " AND out.sid = :employeeId";

	@Override
	public Optional<AttendanceRecordExportSetting> findByCode(ItemSelectionType selectionType
			, String companyId
			, Optional<String> employeeId
			, String code) {
		if (selectionType == ItemSelectionType.STANDARD_SETTING) {
			return this.queryProxy()
					.query(GET_STANDARD_SETTING_BY_CODE, KfnmtRptWkAtdOut.class)
					.setParameter("companyId", companyId)
					.setParameter("selectionType", selectionType.value)
					.setParameter("code", code)
					.getSingle(t -> new AttendanceRecordExportSetting(t));
		}
		
		if (selectionType == ItemSelectionType.FREE_SETTING && employeeId.isPresent()) {
			return this.queryProxy()
					.query(GET_FREE_SETTING_BY_CODE, KfnmtRptWkAtdOut.class)
					.setParameter("companyId", companyId)
					.setParameter("selectionType", selectionType.value)
					.setParameter("code", code)
					.setParameter("employeeId", employeeId.get())
					.getSingle(t -> new AttendanceRecordExportSetting(t));
		}

		return Optional.empty();
	}

	@Override
	public Optional<AttendanceRecordExportSetting> findByLayoutId(String layoutId) {
		return this.queryProxy()
				.query(GET_SETTING_BY_LAYOUT_ID, KfnmtRptWkAtdOut.class)
				.setParameter("layoutId", layoutId)
				.getSingle(t -> new AttendanceRecordExportSetting(t));
	}

	@Override
	public void add(AttendanceRecordStandardSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AttendanceRecordStandardSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String companyId) {
		List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut = this.queryProxy()
				.query(GET_STANDARD_SETTING, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("selectionType", ItemSelectionType.STANDARD_SETTING.value)
				.getList();

		if (kfnmtRptWkAtdOut.isEmpty()) {
			return Optional.empty();
		}

		JpaAttendanceRecordStandardSettingGetMemento mementoGetter = new JpaAttendanceRecordStandardSettingGetMemento(
				kfnmtRptWkAtdOut, companyId, ItemSelectionType.STANDARD_SETTING.value);
		return Optional.of(AttendanceRecordStandardSetting.createFromMemento(mementoGetter));
		
	}

	@Override
	public void add(AttendanceRecordFreeSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AttendanceRecordFreeSetting domain) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<AttendanceRecordFreeSetting> getOutputItemsByCompnayAndEmployee(String companyId,
			String employeeId) {
		List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut = this.queryProxy()
				.query(GET_FREE_SETTING, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("selectionType", ItemSelectionType.FREE_SETTING.value)
				.setParameter("employeeId", employeeId)
				.getList();

		if (kfnmtRptWkAtdOut.isEmpty()) {
			return Optional.empty();
		}

		JpaAttendanceRecordFreeSettingGetMemento mementoGetter = new JpaAttendanceRecordFreeSettingGetMemento(
				kfnmtRptWkAtdOut, companyId, employeeId, ItemSelectionType.FREE_SETTING.value);
		return Optional.of(AttendanceRecordFreeSetting.createFromMemento(mementoGetter));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.function.dom.attendancerecord.export.setting.
	 * AttendanceRecordExportSettingRepository#getSealStamp(java.lang.String,
	 * long)
	 */
	@Override
	public List<String> getSealStamp(String companyId, long code) {
		return new ArrayList<>();
	}

	@Override
	public void deleteAttendanceRecExpSet(AttendanceRecordExportSetting domain) {
		// TODO Auto-generated method stub
		
	}

}
