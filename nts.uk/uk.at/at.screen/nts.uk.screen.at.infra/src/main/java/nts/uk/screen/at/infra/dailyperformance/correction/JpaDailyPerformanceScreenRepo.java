/**
 * 2:14:20 PM Aug 21, 2017
 */
package nts.uk.screen.at.infra.dailyperformance.correction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import entity.employeeinfo.BsymtEmployee;
import entity.person.info.BpsmtPerson;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessFormatSheet;
import nts.uk.ctx.at.record.infra.entity.dailyperformanceformat.KrcmtBusinessTypeDaily;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KrcdtSyainDpErList;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.KwrmtErAlWorkRecord;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KrcmtDailyAttendanceItem;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstControlOfAttendanceItems;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattendanceitem.KshstDailyServiceTypeControl;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave.KalmtAnnualPaidLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours.KshstCom60hVacation;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst.KsvstComSubstVacation;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosure;
import nts.uk.ctx.bs.employee.infra.entity.classification.CclmtClassification;
import nts.uk.ctx.bs.employee.infra.entity.employment.BsymtEmployment;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceInfo;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.Com60HVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.CompensLeaveComDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItemControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPBusinessTypeControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.SubstVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.YearHolidaySettingDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class JpaDailyPerformanceScreenRepo extends JpaRepository implements DailyPerformanceScreenRepo {

	private final static String SEL_BUSINESS_TYPE;

	private final static String SEL_FORMAT_DP_CORRECTION;

	private final static String SEL_CLOSURE;

	private final static String SEL_EMPLOYMENT_BY_CLOSURE;

	private final static String SEL_WORKPLACE;

	private final static String SEL_CLASSIFICATION = "SELECT c FROM CclmtClassification c WHERE c.cclmtClassificationPK.cid = :companyId";

	private final static String SEL_EMPLOYEE;

	private final static String SEL_PERSON = "SELECT p FROM BpsmtPerson p WHERE p.bpsmtPersonPk.pId IN :lstPersonId";

	private final static String SEL_DP_TYPE_CONTROL;

	private final static String SEL_ATTENDANCE_ITEM;

	private final static String SEL_ATTENDANCE_ITEM_CONTROL = "SELECT c FROM KshstControlOfAttendanceItems c WHERE c.kshstControlOfAttendanceItemsPK.attandanceTimeId IN :lstItem";

	private final static String SEL_DP_ERROR_EMPLOYEE;

	private final static String SEL_ERROR_SETTING;

	private final static String SEL_FORMAT_SHEET;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT b.krcmtBusinessTypeSyainPK.businessTypeCode");
		builderString.append(" FROM KrcmtBusinessTypeSyain b");
		builderString.append(" WHERE b.krcmtBusinessTypeSyainPK.sId IN :lstSID");
		builderString.append(" AND b.krcmtBusinessTypeSyainPK.startYmd <= :endYmd");
		builderString.append(" AND b.krcmtBusinessTypeSyainPK.endYmd >= :startYmd");
		builderString.append(" ORDER BY b.krcmtBusinessTypeSyainPK.businessTypeCode ASC");
		SEL_BUSINESS_TYPE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT b");
		builderString.append(" FROM KrcmtBusinessTypeDaily b INNER JOIN");
		builderString.append(" KrcstBusinessTypeSorted s");
		builderString.append(" WHERE s.krcstBusinessTypeSortedPK.companyId = :companyId");
		builderString.append(
				" AND b.krcmtBusinessTypeDailyPK.attendanceItemId = s.krcstBusinessTypeSortedPK.attendanceItemId");
		builderString.append(" AND b.krcmtBusinessTypeDailyPK.companyId = :companyId");
		builderString.append(" AND b.krcmtBusinessTypeDailyPK.businessTypeCode IN :lstBusinessTypeCode ");
		builderString.append(" ORDER BY s.order ASC, b.krcmtBusinessTypeDailyPK.attendanceItemId ASC");
		SEL_FORMAT_DP_CORRECTION = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT closure FROM KclmtClosure closure JOIN ");
		builderString.append("BsymtEmployment emp JOIN ");
		builderString.append("KmnmtAffiliEmploymentHist hist ");
		builderString.append("WHERE hist.kmnmtEmploymentHistPK.empId = :sId ");
		builderString.append("AND hist.kmnmtEmploymentHistPK.strD <= :baseDate AND hist.endD >= :baseDate ");
		builderString.append("AND emp.bsymtEmploymentPK.cid = :companyId ");
		builderString.append("AND emp.bsymtEmploymentPK.code = hist.kmnmtEmploymentHistPK.emptcd ");
		builderString.append("AND closure.kclmtClosurePK.cid = :companyId ");
		builderString.append("AND closure.kclmtClosurePK.closureId = emp.workClosureId");
		SEL_CLOSURE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e FROM BsymtEmployment e WHERE e.bsymtEmploymentPK.cid = :companyId");
		SEL_EMPLOYMENT_BY_CLOSURE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT w FROM BsymtWorkplaceInfo w JOIN ");
		builderString.append("KmnmtAffiliWorkplaceHist a ");
		builderString.append("WHERE a.kmnmtAffiliWorkplaceHistPK.empId = :sId ");
		builderString.append("AND a.kmnmtAffiliWorkplaceHistPK.strD <= :baseDate ");
		builderString.append("AND a.endD >= :baseDate ");
		builderString.append("AND w.bsymtWorkplaceInfoPK.wkpid = a.kmnmtAffiliWorkplaceHistPK.wkpId ");
		builderString.append("AND w.bsymtWorkplaceHist.strD <= :baseDate ");
		builderString.append("AND w.bsymtWorkplaceHist.endD >= :baseDate");
		SEL_WORKPLACE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT DISTINCT s FROM BsymtEmployee s ");
		// builderString.append("JOIN KmnmtAffiliClassificationHist c ");
		// builderString.append("JOIN KmnmtAffiliEmploymentHist e ");
		// builderString.append("JOIN KmnmtAffiliJobTitleHist j ");
		builderString.append("JOIN KmnmtAffiliWorkplaceHist w ");
		builderString.append("WHERE w.kmnmtAffiliWorkplaceHistPK.wkpId IN :lstWkp ");
		// builderString.append("AND e.kmnmtEmploymentHistPK.emptcd IN :lstEmp
		// ");
		// builderString.append("AND j.kmnmtJobTitleHistPK.jobId IN :lstJob ");
		// builderString.append("AND c.kmnmtClassificationHistPK.clscd IN
		// :lstClas ");
		builderString.append("AND s.bsymtEmployeePk.sId = w.kmnmtAffiliWorkplaceHistPK.empId ");
		// builderString.append("OR s.bsymtEmployeePk.sId =
		// e.kmnmtEmploymentHistPK.empId ");
		// builderString.append("OR s.bsymtEmployeePk.sId =
		// j.kmnmtJobTitleHistPK.empId ");
		// builderString.append("OR s.bsymtEmployeePk.sId =
		// c.kmnmtClassificationHistPK.empId ");
		SEL_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT c FROM KshstDailyServiceTypeControl c ");
		builderString.append("WHERE c.kshstDailyServiceTypeControlPK.businessTypeCode IN :lstBusinessType ");
		builderString.append("AND c.kshstDailyServiceTypeControlPK.attendanceItemId IN :lstItem ");
		builderString.append("AND c.use = 1");
		SEL_DP_TYPE_CONTROL = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT i FROM KrcmtDailyAttendanceItem i ");
		builderString.append("WHERE i.krcmtDailyAttendanceItemPK.companyId = :companyId ");
		builderString.append("AND i.krcmtDailyAttendanceItemPK.attendanceItemId IN :lstItem");
		SEL_ATTENDANCE_ITEM = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT e FROM KrcdtSyainDpErList e ");
		builderString.append("WHERE e.krcdtSyainDpErListPK.processingDate IN :lstDate ");
		builderString.append("AND e.krcdtSyainDpErListPK.employeeId IN :lstEmployee");
		SEL_DP_ERROR_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT s FROM KwrmtErAlWorkRecord s");
		builderString.append(" WHERE s.kwrmtErAlWorkRecordPK.companyId = :companyId ");
		builderString.append("AND s.kwrmtErAlWorkRecordPK.errorAlarmCode IN :lstCode");
		SEL_ERROR_SETTING = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT s FROM KrcmtBusinessFormatSheet s ");
		builderString.append("WHERE s.krcmtBusinessFormatSheetPK.companyId = :companyId ");
		builderString.append("AND s.krcmtBusinessFormatSheetPK.businessTypeCode IN :lstBusinessTypeCode");
		SEL_FORMAT_SHEET = builderString.toString();
	}

	@Override
	public ClosureDto getClosure(String sId, GeneralDate baseDate) {
		Optional<KclmtClosure> entity = this.queryProxy().query(SEL_CLOSURE, KclmtClosure.class)
				.setParameter("companyId", AppContexts.user().companyId()).setParameter("sId", sId)
				.setParameter("baseDate", baseDate).getSingle();
		if (entity.isPresent()) {
			return entity.map(c -> {
				return new ClosureDto(c.getKclmtClosurePK().getCid(), c.getKclmtClosurePK().getClosureId(),
						c.getUseClass() == 1 ? true : false, c.getClosureMonth());
			}).get();
		}
		return null;
	}

	@Override
	public YearHolidaySettingDto getYearHolidaySetting() {
		Optional<KalmtAnnualPaidLeave> entity = this.queryProxy().find(AppContexts.user().companyId(),
				KalmtAnnualPaidLeave.class);
		if (entity.isPresent()) {
			return new YearHolidaySettingDto(entity.get().getCid(), entity.get().getManageAtr() == 1 ? true : false,
					entity.get().getPermitAtr() == 1 ? true : false, entity.get().getPermitAtr());
		}
		return null;
	}

	@Override
	public SubstVacationDto getSubstVacationDto() {
		Optional<KsvstComSubstVacation> entity = this.queryProxy().find(AppContexts.user().companyId(),
				KsvstComSubstVacation.class);
		if (entity.isPresent()) {
			return new SubstVacationDto(entity.get().getCid(), entity.get().getIsManage() == 1 ? true : false,
					entity.get().getExpirationDateSet(), entity.get().getAllowPrepaidLeave() == 1 ? true : false);
		}
		return null;
	}

	@Override
	public CompensLeaveComDto getCompensLeaveComDto() {
		Optional<KclmtCompensLeaveCom> entity = this.queryProxy().find(AppContexts.user().companyId(),
				KclmtCompensLeaveCom.class);
		if (entity.isPresent()) {
			return new CompensLeaveComDto(entity.get().getCid(), entity.get().getManageAtr() == 1 ? true : false);
		}
		return null;
	}

	@Override
	public Com60HVacationDto getCom60HVacationDto() {
		Optional<KshstCom60hVacation> entity = this.queryProxy().find(AppContexts.user().companyId(),
				KshstCom60hVacation.class);
		if (entity.isPresent()) {
			return new Com60HVacationDto(entity.get().getCid(), entity.get().getManageDistinct() == 1 ? true : false,
					entity.get().getSixtyHourExtra(), entity.get().getTimeDigestTive());
		}
		return null;
	}

	@Override
	public List<String> getListJobTitle(DateRange dateRange) {
		return null;
	}

	@Override
	public List<String> getListEmployment() {
		return this.queryProxy().query(SEL_EMPLOYMENT_BY_CLOSURE, BsymtEmployment.class)
				.setParameter("companyId", AppContexts.user().companyId()).getList().stream().map(e -> {
					return e.getBsymtEmploymentPK().getCode();
				}).collect(Collectors.toList());
	}

	@Override
	public Map<String, String> getListWorkplace(String employeeId, DateRange dateRange) {
		Map<String, String> lstWkp = new HashMap<>();
		this.queryProxy().query(SEL_WORKPLACE, BsymtWorkplaceInfo.class).setParameter("sId", employeeId)
				.setParameter("baseDate", dateRange.getEndDate()).getList().stream().forEach(w -> {
					lstWkp.put(w.getBsymtWorkplaceInfoPK().getWkpid(), w.getWkpName());
				});
		return lstWkp;
	}

	@Override
	public List<String> getListClassification() {
		return this.queryProxy().query(SEL_CLASSIFICATION, CclmtClassification.class)
				.setParameter("companyId", AppContexts.user().companyId()).getList().stream().map(c -> {
					return c.getCclmtClassificationPK().getCode();
				}).collect(Collectors.toList());
	}

	@Override
	public List<DailyPerformanceEmployeeDto> getListEmployee(List<String> lstJobTitle, List<String> lstEmployment,
			Map<String, String> lstWorkplace, List<String> lstClassification) {
		List<BsymtEmployee> lstEmployee = this.queryProxy().query(SEL_EMPLOYEE, BsymtEmployee.class)
				.setParameter("lstWkp", lstWorkplace.keySet().stream().collect(Collectors.toList())).getList();
		List<BpsmtPerson> lstPerson = this.queryProxy().query(SEL_PERSON, BpsmtPerson.class)
				.setParameter("lstPersonId", lstEmployee.stream().map((employee) -> {
					return employee.personId;
				}).collect(Collectors.toList())).getList();
		return lstEmployee.stream().map((employee) -> {
			for (BpsmtPerson person : lstPerson) {
				if (person.bpsmtPersonPk.pId.equals(employee.personId)) {
					return new DailyPerformanceEmployeeDto(employee.bsymtEmployeePk.sId, employee.employeeCode,
							person.personName, lstWorkplace.values().stream().findFirst().get(), "", false);
				}
			}
			return new DailyPerformanceEmployeeDto(employee.bsymtEmployeePk.sId, employee.employeeCode, "",
					lstWorkplace.values().stream().findFirst().get(), "", false);
		}).collect(Collectors.toList());
	}

	@Override
	public List<String> getListBusinessType(List<String> lstEmployee, DateRange dateRange) {
		return this.queryProxy().query(SEL_BUSINESS_TYPE, String.class).setParameter("lstSID", lstEmployee)
				.setParameter("startYmd", dateRange.getStartDate()).setParameter("endYmd", dateRange.getEndDate())
				.getList();
	}

	@Override
	public List<FormatDPCorrectionDto> getListFormatDPCorrection(List<String> lstBusinessType) {
		return this.queryProxy().query(SEL_FORMAT_DP_CORRECTION, KrcmtBusinessTypeDaily.class)
				.setParameter("companyId", AppContexts.user().companyId())
				.setParameter("lstBusinessTypeCode", lstBusinessType).getList().stream()
				.map(f -> new FormatDPCorrectionDto(f.krcmtBusinessTypeDailyPK.companyId,
						f.krcmtBusinessTypeDailyPK.businessTypeCode, f.krcmtBusinessTypeDailyPK.attendanceItemId,
						String.valueOf(f.krcmtBusinessTypeDailyPK.sheetNo), f.order, f.columnWidth.intValue()))
				.distinct().collect(Collectors.toList());
	}

	@Override
	public List<DPBusinessTypeControl> getListBusinessTypeControl(List<String> lstBusinessType,
			List<Integer> lstAttendanceItem) {
		return this.queryProxy().query(SEL_DP_TYPE_CONTROL, KshstDailyServiceTypeControl.class)
				.setParameter("lstBusinessType", lstBusinessType).setParameter("lstItem", lstAttendanceItem).getList()
				.stream().map(c -> {
					return new DPBusinessTypeControl(c.kshstDailyServiceTypeControlPK.businessTypeCode,
							c.kshstDailyServiceTypeControlPK.attendanceItemId.intValue(),
							c.use.intValue() == 1 ? true : false, c.canBeChangedByOthers.intValue() == 1 ? true : false,
							c.youCanChangeIt.intValue() == 1 ? true : false);
				}).collect(Collectors.toList());
	}

	@Override
	public List<DPAttendanceItem> getListAttendanceItem(List<Integer> lstAttendanceItem) {
		return this.queryProxy().query(SEL_ATTENDANCE_ITEM, KrcmtDailyAttendanceItem.class)
				.setParameter("companyId", AppContexts.user().companyId()).setParameter("lstItem", lstAttendanceItem)
				.getList().stream().map(i -> {
					return new DPAttendanceItem(i.krcmtDailyAttendanceItemPK.attendanceItemId, i.attendanceItemName,
							i.displayNumber.intValue(), i.userCanSet.intValue() == 1 ? true : false,
							i.nameLineFeedPosition.intValue(), i.dailyAttendanceAtr.intValue());
				}).collect(Collectors.toList());
	}

	@Override
	public List<DPAttendanceItemControl> getListAttendanceItemControl(List<Integer> lstAttendanceItem) {
		return this.queryProxy().query(SEL_ATTENDANCE_ITEM_CONTROL, KshstControlOfAttendanceItems.class)
				.setParameter("lstItem", lstAttendanceItem).getList().stream().map(c -> {
					return new DPAttendanceItemControl(c.kshstControlOfAttendanceItemsPK.attandanceTimeId.intValue(),
							c.inputUnitOfTimeItem.intValue(), c.headerBackgroundColorOfDailyPerformance,
							c.nameLineFeedPosition.intValue());
				}).collect(Collectors.toList());
	}

	@Override
	public List<DPErrorDto> getListDPError(DateRange dateRange, List<String> lstEmployee) {
		return this.queryProxy().query(SEL_DP_ERROR_EMPLOYEE, KrcdtSyainDpErList.class)
				.setParameter("lstDate", dateRange.toListDate()).setParameter("lstEmployee", lstEmployee).getList()
				.stream().map(e -> {
					return new DPErrorDto(e.krcdtSyainDpErListPK.errorCode, "", e.krcdtSyainDpErListPK.employeeId,
							e.krcdtSyainDpErListPK.processingDate, e.attendanceItemId.intValue(),
							e.errorCancelable.intValue() == 1 ? true : false);
				}).collect(Collectors.toList());
	}

	@Override
	public List<DPErrorSettingDto> getErrorSetting(List<String> listErrorCode) {
		return this.queryProxy().query(SEL_ERROR_SETTING, KwrmtErAlWorkRecord.class)
				.setParameter("companyId", AppContexts.user().companyId()).setParameter("lstCode", listErrorCode)
				.getList().stream().map(s -> {
					return new DPErrorSettingDto(s.kwrmtErAlWorkRecordPK.companyId,
							s.kwrmtErAlWorkRecordPK.errorAlarmCode, s.errorAlarmName,
							s.fixedAtr.intValue() == 1 ? true : false, s.useAtr.intValue() == 1 ? true : false,
							s.typeAtr.intValue(), s.messageDisplay, s.boldAtr.intValue() == 1 ? true : false,
							s.messageColor, s.cancelableAtr.intValue() == 1 ? true : false,
							s.errorDisplayItem.intValue());
				}).collect(Collectors.toList());
	}

	@Override
	public List<DPSheetDto> getFormatSheets(List<String> lstBusinessType) {
		return this.queryProxy().query(SEL_FORMAT_SHEET, KrcmtBusinessFormatSheet.class)
				.setParameter("companyId", AppContexts.user().companyId())
				.setParameter("lstBusinessTypeCode", lstBusinessType).getList().stream().map(s -> {
					return new DPSheetDto(String.valueOf(s.krcmtBusinessFormatSheetPK.sheetNo), s.sheetName);
				}).collect(Collectors.toList());
	}

}
