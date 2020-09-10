package nts.uk.ctx.at.function.infra.repository.attendancerecord.export.setting;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordStandardSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutseal;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.export.setting.KfnmtRptWkAtdOut;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAttendanceRecordStandardSettingRepository extends JpaRepository
		implements AttendanceRecordStandardSettingRepository {

	public static final String GET_STANDARD_SETTING_BY_COMPANY = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId AND ot.itemSelType = :itemSelType";

	private static final String GET_ATD_BY_COMPANY_ID = "SELECT atd FROM KfnmtRptWkAtdOutatd atd"
			+ " WHERE atd.cid = :cid";

	private static final String GET_FRAME_BY_COMPANY_ID = "SELECT atd FROM KfnmtRptWkAtdOutframe frame"
			+ " WHERE frame.cid = :cid";
	
	private static final String GET_SEAL_BY_COMPANY_ID = "SELECT atd FROM KfnmtRptWkAtdOutseal seal"
			+ " WHERE seal.cid = :cid";
	
	private static final String GET_SETTING_BY_COMPANY_AND_CODE = "SELECT ot FROM KfnmtRptWkAtdOut ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.itemSelType = :itemSelType"
			+ "		AND ot.exportCD = :code";

	private JpaAttendanceRecordExportSettingRepository recordExportSettingRepo;

	@Override
	public void add(AttendanceRecordStandardSetting domain) {
		List<KfnmtRptWkAtdOut> entities = domain.getAttendanceRecordExportSettings().stream().map(t -> {
			KfnmtRptWkAtdOut entity = new KfnmtRptWkAtdOut();
//					t.saveToMemento(entity);
			entity.setItemSelType(ItemSelectionType.STANDARD_SETTING.value);
			entity.setLayoutId(UUID.randomUUID().toString());
			entity.setCid(domain.getCid().v());
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void update(AttendanceRecordStandardSetting domain) {

	}

	private static AttendanceRecordStandardSetting toEntity(AttendanceRecordStandardSetting domain) {
		AttendanceRecordStandardSetting entity = new AttendanceRecordStandardSetting();
//		domain.setMemento(entity);
		return entity;
	}

	@Override
	public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String companyId) {

		Map<String, List<KfnmtRptWkAtdOutatd>> mapAtd = this.getLstKfnmtRptWkAtdOutatd(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getLayoutId()));
		Map<String, List<KfnmtRptWkAtdOutframe>> mapFrame = this.getLstKfnmtRptWkAtdOutframe(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));
		List<KfnmtRptWkAtdOut> lstResult = this.queryProxy()
				.query(GET_STANDARD_SETTING_BY_COMPANY, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("itemSelType", ItemSelectionType.STANDARD_SETTING.value).getList().stream().map(t -> {
					t.setLstKfnmtRptWkAtdOutatd(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkAtdOutframe(mapFrame.get(t.getLayoutId()));
					return t;
				}).collect(Collectors.toList());
		if (lstResult.isEmpty()) {
			return Optional.empty();
		}
		AttendanceRecordStandardSetting result = AttendanceRecordStandardSetting
				.createFromMemento(new JpaAttendanceRecordStandardSettingGetMemento(lstResult, companyId,
						ItemSelectionType.STANDARD_SETTING.value));
		return Optional.of(result);
	}

	@Override
	public Optional<AttendanceRecordStandardSetting> findByCompanyIdAndCode(String companyId, String code) {
		Map<String, List<KfnmtRptWkAtdOutatd>> mapAtd = this.getLstKfnmtRptWkAtdOutatd(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getLayoutId()));
		Map<String, List<KfnmtRptWkAtdOutframe>> mapFrame = this.getLstKfnmtRptWkAtdOutframe(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));
		Map<String, List<KfnmtRptWkAtdOutseal>> mapSeal = this.getLstKfnmtRptWkAtdOutseal(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getLayoutId()));
		
		List<KfnmtRptWkAtdOut> lstResult = this.queryProxy()
				.query(GET_SETTING_BY_COMPANY_AND_CODE, KfnmtRptWkAtdOut.class)
				.setParameter("companyId", companyId)
				.setParameter("itemSelType", ItemSelectionType.STANDARD_SETTING.value)
				.setParameter("code", code)
				.getList().stream().map(t -> {
					t.setLstKfnmtRptWkAtdOutatd(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkAtdOutframe(mapFrame.get(t.getLayoutId()));
					t.setLstKfnmtRptWkAtdOutseal(mapSeal.get(t.getLayoutId()));
					return t;
				}).collect(Collectors.toList());
		if (lstResult.isEmpty()) {
			return Optional.empty();
		}
		AttendanceRecordStandardSetting result = AttendanceRecordStandardSetting
				.createFromMemento(new JpaAttendanceRecordStandardSettingGetMemento(lstResult, companyId,
						ItemSelectionType.STANDARD_SETTING.value));
		return Optional.of(result);
	}

	@Override
	public Optional<AttendanceRecordStandardSetting> findByCompanyCodeLayoutId(String companyId, String code,
			String layoutId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the lst kfnmt rpt wk atd outatd.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk atd outatd
	 */
	private List<KfnmtRptWkAtdOutatd> getLstKfnmtRptWkAtdOutatd(String companyId) {
		return this.queryProxy().query(GET_ATD_BY_COMPANY_ID, KfnmtRptWkAtdOutatd.class).setParameter("cid", companyId)
				.getList();
	}

	/**
	 * Gets the lst kfnmt rpt wk atd outframe.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk atd outframe
	 */
	private List<KfnmtRptWkAtdOutframe> getLstKfnmtRptWkAtdOutframe(String companyId) {
		return this.queryProxy().query(GET_FRAME_BY_COMPANY_ID, KfnmtRptWkAtdOutframe.class)
				.setParameter("cid", companyId).getList();
	}
	
	/**
	 * Gets the lst kfnmt rpt wk atd outseal.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk atd outseal
	 */
	private List<KfnmtRptWkAtdOutseal> getLstKfnmtRptWkAtdOutseal(String companyId) {
		return this.queryProxy().query(GET_SEAL_BY_COMPANY_ID, KfnmtRptWkAtdOutseal.class)
				.setParameter("cid", companyId).getList();
	}

}
