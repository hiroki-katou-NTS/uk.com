package nts.uk.ctx.at.shared.dom.remainingnumber.otherholiday;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 休出管理データの初回登録
 * 
 * @author lanlt
 *
 */
@Stateless
public class OtherHolidayInfoService {

	@Inject
	private PublicHolidayRemainRepository publicHolidayRemainRepository;

	@Inject
	private ExcessLeaveInfoRepository excessLeaveInfoRepository;

	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;

	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;

	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;

	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;

	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;

	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;

	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	// Constants
	private static final BigDecimal ZERO = new BigDecimal(0);
	private static final BigDecimal ONE = new BigDecimal(1);
	private static final BigDecimal AHALF = new BigDecimal(0.5);

	public void addOtherHolidayInfo(String cid, PublicHolidayRemain pubHD, ExcessLeaveInfo exLeav,
			BigDecimal remainNumber, BigDecimal remainLeft) {

		publicHolidayRemainRepository.add(pubHD);
		excessLeaveInfoRepository.add(exLeav);

		String sid = pubHD.getSID();
		if (checkEnableLeaveMan(sid)) {
			if (remainNumber != null) {
				setRemainNumber(cid, sid, remainNumber);
			}
		}

		// Item IS00368
		if (checkEnablePayout(sid)) {
			if (remainLeft != null) {
				setRemainLeftItem(cid, sid, remainLeft);
			}
		}

	}

	// Item IS00368
	// 振出振休管理データの初回登録
	private void setRemainLeftItem(String cid, String sid, BigDecimal remainLeft) {

		// IS00368
		if (remainLeft.compareTo(ZERO) > 0) {
			remainLeftIsBiggerThanZero(cid, sid, remainLeft);
		} else if (remainLeft.compareTo(ZERO) < 0) {
			remainLeftIsSmallerThanZero(cid, sid, remainLeft);
		}
	}

	/**
	 * In case of remain left is bigger than zero
	 * 
	 * @param cid
	 * @param sid
	 * @param remainLeft
	 */
	private void remainLeftIsBiggerThanZero(String cid, String sid, BigDecimal remainLeft) {
		BigDecimal remainLeftTpm = remainLeft;
		String newID = null;
		PayoutManagementData payout = null;

		while (remainLeftTpm.compareTo(ZERO) > 0) {
			newID = IdentifierUtil.randomUniqueId();
			if (remainLeftTpm.compareTo(ONE) >= 0) {
				/**
				 * 社員ID←パラメータ「社員ID」 休出日←日付不明=true 使用期限日←9999/12/31 振休消化区分←未消化 未使用日数←1.0日
				 * 法定内外区分←法定外休日
				 */
				payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(),
						HolidayAtr.NON_STATUTORYHOLIDAY.value, 1d, 1d, DigestionAtr.UNUSED.value);

				remainLeftTpm = remainLeftTpm.subtract(ONE);
			} else if (remainLeftTpm.compareTo(AHALF) == 0) {
				payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(),
						HolidayAtr.NON_STATUTORYHOLIDAY.value, 0.5d, 0.5d, DigestionAtr.UNUSED.value);
				remainLeftTpm = remainLeftTpm.subtract(AHALF);
			}
			payoutManagementDataRepository.create(payout);
		}
	}

	/**
	 * In case of remain left is smaller than zero
	 * 
	 * @param cid
	 * @param sid
	 * @param remainLeft
	 */
	private void remainLeftIsSmallerThanZero(String cid, String sid, BigDecimal remainLeft) {
		String newID = null;
		// 振休作成数=-1*パラメータ「振休残数」
		BigDecimal remainLeftTpm = remainLeft.multiply(new BigDecimal(-1));
		SubstitutionOfHDManagementData subOfHD = null;
		while (remainLeftTpm.compareTo(ZERO) > 0) {
			newID = IdentifierUtil.randomUniqueId();
			if (remainLeftTpm.compareTo(ONE) >= 0) {
				/**
				 * 社員ID←パラメータ「社員ID」 休出日←日付不明=true 使用期限日←9999/12/31 振休消化区分←未消化
				 */
				subOfHD = new SubstitutionOfHDManagementData(newID, cid, sid, true, null, 1d, 1d);
				remainLeftTpm = remainLeftTpm.subtract(ONE);
			} else if (remainLeftTpm.compareTo(AHALF) == 0) {
				subOfHD = new SubstitutionOfHDManagementData(newID, cid, sid, true, null, 0.5d, 0.5d);
				remainLeftTpm = remainLeftTpm.subtract(AHALF);
			}
			substitutionOfHDManaDataRepository.create(subOfHD);
		}
	}

	// Item IS00366
	// 休出代休管理データの初回登録
	private void setRemainNumber(String cid, String sid, BigDecimal remainNumber) {
		// IS00368
		if (remainNumber.compareTo(ZERO) > 0) {
			remainNumberIsMoreThanZero(cid, sid, remainNumber);
		} else if (remainNumber.compareTo(ZERO) < 0) {
			remainNumberIsSmallerThanZero(cid, sid, remainNumber);
		}
	}

	/**
	 * In case of remain number is bigger than zero
	 * 
	 * @param cid
	 * @param sid
	 * @param remainNumber
	 */
	private void remainNumberIsMoreThanZero(String cid, String sid, BigDecimal remainNumber) {
		BigDecimal remainNumberTpm = remainNumber;
		LeaveManagementData leaveMana = null;
		String newID = null;
		// 1日相当時間←指定時間．1日の時間
		// 半日相当時間←指定時間．半日の時間
		// TODO QA
		DesignatedTime commonSet = getWorkTimeSetting(cid, sid);

		int aDay = commonSet.getOneDayTime().v();
		int aHalf = commonSet.getHalfDayTime().v();
		while (remainNumberTpm.compareTo(ZERO) > 0) {
			newID = IdentifierUtil.randomUniqueId();
			if (remainNumberTpm.compareTo(ONE) >= 0) {
				leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 1d, 0, 1d, 0,
						DigestionAtr.UNUSED.value, aDay, aHalf);

				remainNumberTpm = remainNumberTpm.subtract(ONE);
			} else if (remainNumberTpm.compareTo(AHALF) == 0) {
				leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 0.5d, 0, 0.5d, 0,
						DigestionAtr.UNUSED.value, aDay, aHalf);
				remainNumberTpm = remainNumberTpm.subtract(AHALF);
			}
			leaveManaDataRepository.create(leaveMana);
		}
	}

	/**
	 * In case of remain number is smaller than zero
	 * 
	 * @param cid
	 * @param sid
	 * @param remainNumber
	 */
	private void remainNumberIsSmallerThanZero(String cid, String sid, BigDecimal remainNumber) {
		String newID = null;
		// 振休作成数=-1*パラメータ「振休残数」
		BigDecimal remainNumberTpm = remainNumber.multiply(new BigDecimal(-1));
		CompensatoryDayOffManaData comDayOff = null;

		while (remainNumberTpm.compareTo(ZERO) > 0) {
			newID = IdentifierUtil.randomUniqueId();
			if (remainNumberTpm.compareTo(ONE) >= 0) {
				/**
				 * 社員ID←パラメータ「社員ID」 休出日←日付不明=true 使用期限日←9999/12/31 振休消化区分←未消化
				 */
				comDayOff = new CompensatoryDayOffManaData(newID, cid, sid, true, null, 1d, 0, 1d, 0);
				remainNumberTpm = remainNumberTpm.subtract(ONE);
			} else if (remainNumberTpm.compareTo(AHALF) == 0) {
				comDayOff = new CompensatoryDayOffManaData(newID, cid, sid, true, null, 0.5d, 0, 0.5d, 0);
				remainNumberTpm = remainNumberTpm.subtract(AHALF);
			}
			comDayOffManaDataRepository.create(comDayOff);
		}
	}

	/**
	 * For Item IS00366 LeaveManagementData
	 * 
	 * @param sid
	 * @return 指定時間
	 */
	// TODO Pending QA
	private DesignatedTime getWorkTimeSetting(String cid, String sid) {

		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));

		Optional<WorkingCondition> workingCond = workingConditionRepository.getBySid(cid, sid);
		if (!workingCond.isPresent() || workingCond.get().getDateHistoryItem().isEmpty()) {
			return result;
		}
		DateHistoryItem histItem = workingCond.get().getDateHistoryItem()
				.get(workingCond.get().getDateHistoryItem().size() - 1);
		Optional<WorkingConditionItem> workingCondItem = workingConditionItemRepository.getBySidAndHistId(sid,
				histItem.identifier());
		if (!workingCondItem.isPresent()) {
			return result;
		}
		// 労働条件項目．区分別勤務．休日出勤時．就業時間帯コードの就業時間帯
		Optional<WorkTimeCode> workTimeCD = workingCondItem.get().getWorkCategory().getHolidayWork().getWorkTimeCode();

		if (!workTimeCD.isPresent()) {
			return getCompanySet(cid);
		}
		Optional<WorkTimeSetting> workTimeSet = workTimeSettingRepository.findByCode(cid, workTimeCD.get().v());

		if (!workTimeSet.isPresent()) {
			return getCompanySet(cid);
		}

		// Flexible time
		if (workTimeSet.get().getWorkTimeDivision().getWorkTimeDailyAtr().isFlex()) {
			getFlexTime(cid, workTimeCD.get().v());
		} else {
			switch (workTimeSet.get().getWorkTimeDivision().getWorkTimeMethodSet()) {
			case FIXED_WORK:
				return getFixedWork(cid, workTimeCD.get().v());
			case FLOW_WORK:
				return getFlowWork(cid, workTimeCD.get().v());
			case DIFFTIME_WORK:
				return getDiffTimeWork(cid, workTimeCD.get().v());
			default:
				break;
			}

		}

		return result;
	}
	
	/**
	 * Get flexible time
	 * 
	 * @param cid
	 * @param workTimeCD
	 * @return
	 */
	private DesignatedTime getFlexTime(String cid, String workTimeCD) {
		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));
		Optional<FlexWorkSetting> flexWork = flexWorkSettingRepository.find(cid, workTimeCD);
		if (!flexWork.isPresent()) {
			return result;
		}
		Optional<WorkTimezoneOtherSubHolTimeSet> subHolTimeSet = flexWork.get().getCommonSetting().getSubHolTimeSet()
				.stream().filter(i -> i.getOriginAtr().value == CompensatoryOccurrenceDivision.WorkDayOffTime.value
						&& (i.getSubHolTimeSet().isUseDivision() == true))
				.findFirst();
		if (subHolTimeSet.isPresent()) {
			if (subHolTimeSet.get().getSubHolTimeSet().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
				return subHolTimeSet.get().getSubHolTimeSet().getDesignatedTime();
			} else {
				return new DesignatedTime(subHolTimeSet.get().getSubHolTimeSet().getCertainTime(), new OneDayTime(0));
			}
		}
		return getCompanySet(cid);
	}

	/**
	 * 会社別代休時間設定
	 * 
	 * @param cid
	 * @return
	 */
	private DesignatedTime getCompanySet(String cid) {
		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));

		CompensatoryLeaveComSetting comSet = compensLeaveComSetRepository.find(cid);
		if (comSet != null) {
			Optional<CompensatoryOccurrenceSetting> comCurrentSetOptional = comSet.getCompensatoryOccurrenceSetting().stream()
					.filter(i -> i.getOccurrenceType().value == CompensatoryOccurrenceDivision.WorkDayOffTime.value)
					.findFirst();
			if (comCurrentSetOptional.isPresent()) {
				CompensatoryOccurrenceSetting comCurrentSet = comCurrentSetOptional.get();
				if(comCurrentSet.getTransferSetting().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
					return comCurrentSet.getTransferSetting().getDesignatedTime();
				}else {
					return new  DesignatedTime(comCurrentSet.getTransferSetting().getCertainTime(), new OneDayTime(0));
				}
			}
		}
		return result;
	}

	/**
	 * ドメインモデル「固定勤務設定」を取得
	 * 
	 * @param cid
	 * @param workTimeCD
	 * @return
	 */
	private DesignatedTime getFixedWork(String cid, String workTimeCD) {
		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));
		Optional<FixedWorkSetting> fixedWork = fixedWorkSettingRepository.findByKey(cid, workTimeCD);
		if (!fixedWork.isPresent()) {
			return result;
		}
		Optional<WorkTimezoneOtherSubHolTimeSet> workTimeOptional = fixedWork.get().getCommonSetting()
				.getSubHolTimeSet().stream()
				.filter(i -> i.getOriginAtr().value == CompensatoryOccurrenceDivision.WorkDayOffTime.value
						&& (i.getSubHolTimeSet().isUseDivision() == true))
				.findFirst();

		if (!workTimeOptional.isPresent()) {
			return getCompanySet(cid);
		}

		WorkTimezoneOtherSubHolTimeSet workTime = workTimeOptional.get();
		if (workTime.getSubHolTimeSet().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
			return workTime.getSubHolTimeSet().getDesignatedTime();
		} else {
			return new DesignatedTime(workTime.getSubHolTimeSet().getCertainTime(), new OneDayTime(0));
		}
	}

	/**
	 * ドメインモデル「流動勤務設定」を取得
	 * 
	 * @param cid
	 * @param workTimeCD
	 * @return
	 */
	private DesignatedTime getFlowWork(String cid, String workTimeCD) {
		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));
		Optional<FlowWorkSetting> flowWork = flowWorkSettingRepository.find(cid, workTimeCD);
		if (!flowWork.isPresent()) {
			return result;
		}
		Optional<WorkTimezoneOtherSubHolTimeSet> workTimeOptional = flowWork.get().getCommonSetting().getSubHolTimeSet()
				.stream().filter(i -> i.getOriginAtr().value == CompensatoryOccurrenceDivision.WorkDayOffTime.value
						&& (i.getSubHolTimeSet().isUseDivision() == true))
				.findFirst();

		if (!workTimeOptional.isPresent()) {
			return getCompanySet(cid);
		}
		WorkTimezoneOtherSubHolTimeSet workTime = workTimeOptional.get();
		if (workTime.getSubHolTimeSet().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
			return workTime.getSubHolTimeSet().getDesignatedTime();
		} else {
			return new DesignatedTime(workTime.getSubHolTimeSet().getCertainTime(), new OneDayTime(0));
		}
	}

	/**
	 * ドメインモデル「時差勤務設定」を取得
	 * 
	 * @param cid
	 * @param workTimeCD
	 * @return
	 */
	private DesignatedTime getDiffTimeWork(String cid, String workTimeCD) {
		DesignatedTime result = new DesignatedTime(new OneDayTime(0), new OneDayTime(0));
		Optional<DiffTimeWorkSetting> diffTime = diffTimeWorkSettingRepository.find(cid, workTimeCD);
		if (!diffTime.isPresent()) {
			return result;
		}
		Optional<WorkTimezoneOtherSubHolTimeSet> workTimeOptional = diffTime.get().getCommonSet().getSubHolTimeSet()
				.stream().filter(i -> i.getOriginAtr().value == CompensatoryOccurrenceDivision.WorkDayOffTime.value
						&& (i.getSubHolTimeSet().isUseDivision() == true))
				.findFirst();

		if (!workTimeOptional.isPresent()) {
			return getCompanySet(cid);
		}
		WorkTimezoneOtherSubHolTimeSet workTime = workTimeOptional.get();
		if (workTime.getSubHolTimeSet().getSubHolTransferSetAtr().isSpecifiedTimeSubHol()) {
			return workTime.getSubHolTimeSet().getDesignatedTime();
		} else {
			return new DesignatedTime(workTime.getSubHolTimeSet().getCertainTime(), new OneDayTime(0));
		}
	}

	public void updateOtherHolidayInfo(String cid, PublicHolidayRemain pubHD, ExcessLeaveInfo exLeav,
			BigDecimal remainNumber, BigDecimal remainLeft) {

		publicHolidayRemainRepository.update(pubHD);
		excessLeaveInfoRepository.update(exLeav);

		String sid = pubHD.getSID();
		// Item IS00366
		if (checkEnableLeaveMan(sid)) {
			if (remainNumber != null) {
				setRemainNumber(cid, sid, remainNumber);
			}
		}

		// Item IS00368
		if (checkEnablePayout(sid)) {
			if (remainLeft != null) {
				setRemainLeftItem(cid, sid, remainLeft);
			}
		}

	}

	/**
	 * IS00366 check Enable
	 * 
	 * @param cid
	 * @param sid
	 * @return
	 */
	public boolean checkEnableLeaveMan(String sid) {
		String cid = AppContexts.user().companyId();
		if (comDayOffManaDataRepository.getBySid(cid, sid).isEmpty()
				&& leaveManaDataRepository.getBySid(cid, sid).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * IS00368 check Enable
	 * 
	 * @param sid
	 * @return
	 */
	public boolean checkEnablePayout(String sid) {
		String cid = AppContexts.user().companyId();
		if (substitutionOfHDManaDataRepository.getBysiD(cid, sid).isEmpty()
				&& payoutManagementDataRepository.getSid(cid, sid).isEmpty()) {
			return true;
		}
		return false;
	}
}
