package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordFreeSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.shr.com.context.AppContexts;

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
	
	
	private static final String GET_STANDARD_SETTING_WITH_LAYOUTID = GET_STANDARD_SETTING_BY_CODE + " AND out.layoutId = :layoutId";
			
	private static final String GET_FREE_SETTING_BY_CODE = GET_STANDARD_SETTING_BY_CODE + " AND out.sid = :employeeId";
	
	private static final String GET_FREE_SETTING_WITH_LAYOUTID = GET_FREE_SETTING_BY_CODE + " AND out.layoutId = :layoutId";

	private static final String GET_SETTING_BY_LAYOUT_ID = "SELECT out FROM KfnmtRptWkAtdOut out"
			+ " WHERE out.layoutId = :layoutId";
	
	private static final String GET_STANDARD_SETTING = "SELECT out FROM KfnmtRptWkAtdOut out"
			+ " WHERE out.cid = :companyId"
			+ " AND out.itemSelType = :selectionType";
	private static final String GET_FREE_SETTING = GET_STANDARD_SETTING + " AND out.sid = :employeeId";
	private static final String GET_SEAL = "SELECT seal FROM KfnmtRptWkAtdOutseal seal WHERE seal.cid = :cid AND seal.layoutId = :layoutId ORDER BY seal.sealOrder ASC";
	
	@Override
	public Optional<AttendanceRecordExportSetting> findByCode(ItemSelectionType selectionType
			, String companyId
			, Optional<String> employeeId
			, String code) {
		
		// パラメータ．項目選択種類をチェックする
		if (selectionType == ItemSelectionType.STANDARD_SETTING) {
			
			// ドメインモデル「出勤簿の出力項目定型設定」を取得する
			return this.queryProxy()
					.query(GET_STANDARD_SETTING_BY_CODE, KfnmtRptWkAtdOut.class)
					.setParameter("companyId", companyId)
					.setParameter("selectionType", selectionType.value)
					.setParameter("code", code)
					.getSingle(t -> new AttendanceRecordExportSetting(t));
		}
		
		if (selectionType == ItemSelectionType.FREE_SETTING && employeeId.isPresent()) {
			
			// ドメインモデル「出勤簿の出力項目自由設定」を取得する 
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
	public void save(AttendanceRecordStandardSetting domain) {
		for (AttendanceRecordExportSetting subDomain : domain.getAttendanceRecordExportSettings()) {
			KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
			entity.setContractCd(AppContexts.user().contractCode());
			entity.setCid(domain.getCid().v());
			entity.setItemSelType(domain.getItemSelectionType().value);
			subDomain.saveToMemento(entity);
			this.deleteSealStamp(domain.getCid().v(), subDomain.getLayoutId());
			
			Optional<AttendanceRecordExportSetting> ares = this.findByLayoutId(subDomain.getLayoutId());
			if (ares.isPresent()) {
				// update
				this.commandProxy().update(entity);
			} else {
				// insert
				this.commandProxy().insert(entity);
			}
		}
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
	public Optional<AttendanceRecordStandardSetting> getStandardWithLayoutId(String companyId, String layoutId, String exportCD) {
		List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut = this.queryProxy()
				.query(GET_STANDARD_SETTING_WITH_LAYOUTID, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("selectionType", ItemSelectionType.STANDARD_SETTING.value)
				.setParameter("layoutId", layoutId)
				.setParameter("code", exportCD)
				.getList();
		
		if (kfnmtRptWkAtdOut.isEmpty()) {
			return Optional.empty();
		}
		
		JpaAttendanceRecordStandardSettingGetMemento mementoGetter = new JpaAttendanceRecordStandardSettingGetMemento(
				kfnmtRptWkAtdOut, companyId, ItemSelectionType.STANDARD_SETTING.value);
		
		return Optional.of(AttendanceRecordStandardSetting.createFromMemento(mementoGetter));
	}
	
	@Override
	public void save(AttendanceRecordFreeSetting domain) {
		for (AttendanceRecordExportSetting subDomain : domain.getAttendanceRecordExportSettings()) {
			KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
			entity.setContractCd(AppContexts.user().contractCode());
			entity.setCid(domain.getCid().v());
			entity.setItemSelType(domain.getItemSelectionType().value);
			entity.setSid(domain.getEmployeeId().v());
			subDomain.saveToMemento(entity);
			this.deleteSealStamp(domain.getCid().v(), subDomain.getLayoutId());
			
			Optional<AttendanceRecordExportSetting> ares = this.findByLayoutId(subDomain.getLayoutId());
			if (ares.isPresent()) {
				// update
				this.commandProxy().update(entity);
			} else {
				// insert
				this.commandProxy().insert(entity);
			}
			
		}
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
	
	@Override
	public Optional<AttendanceRecordFreeSetting> getFreeWithLayoutId(String companyId,
			String employeeId, String layoutId, String exportCD) {
		List<KfnmtRptWkAtdOut> kfnmtRptWkAtdOut = this.queryProxy()
				.query(GET_FREE_SETTING_WITH_LAYOUTID, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("selectionType", ItemSelectionType.FREE_SETTING.value)
				.setParameter("employeeId", employeeId)
				.setParameter("layoutId", layoutId)
				.setParameter("code", exportCD)
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
	public List<String> getSealStamp(String companyId, String layoutId) {
		if (!StringUtil.isNullOrEmpty(companyId, true)) {
			return this.findAllSealColumn(companyId, layoutId).stream()
					.map(t -> t.getSealStampName())
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public void deleteAttendanceRecExpSet(AttendanceRecordExportSetting domain) {
		this.commandProxy().remove(KfnmtRptWkAtdOut.class, domain.getLayoutId());
	}
	
	/**
	 * Delete seal stamp.
	 *
	 * @param companyId the company id
	 * @param layoutId the layout id
	 */
	private void deleteSealStamp(String companyId, String layoutId) {
		// Delete seal Stamp list
		List<KfnmtRptWkAtdOutseal> sealStampList = this.findAllSealColumn(companyId, layoutId);
		this.commandProxy().removeAll(sealStampList);
		this.getEntityManager().flush();
	}
	
	/**
	 * Find all seal column.
	 *
	 * @param companyId the company id
	 * @param layoutId the layout id
	 * @return the list
	 */
	private List<KfnmtRptWkAtdOutseal> findAllSealColumn(String companyId, String layoutId) {
		// query data and return
		return this.queryProxy().query(GET_SEAL, KfnmtRptWkAtdOutseal.class)
				.setParameter("cid", companyId)
				.setParameter("layoutId", layoutId)
				.getList();
	}
}
