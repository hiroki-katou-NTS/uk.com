package nts.uk.ctx.at.record.dom.remainingnumber.otherholiday;

import java.math.BigDecimal;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.history.DateHistoryItem;

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
	
	// Constants
	private final BigDecimal ZERO = new BigDecimal(0);
	private final BigDecimal ONE = new BigDecimal(1);
	private final BigDecimal AHALF = new BigDecimal(0.5);
	
	public void addOtherHolidayInfo(String cid, PublicHolidayRemain pubHD, ExcessLeaveInfo exLeav, BigDecimal remainNumber, BigDecimal remainLeft){
		
		publicHolidayRemainRepository.add(pubHD);
		excessLeaveInfoRepository.add(exLeav);
		
		String sid = pubHD.getSID();
		// Item IS00366
		if (comDayOffManaDataRepository.getBySid(sid).stream().count() == 0L && leaveManaDataRepository.getBySid(cid, sid).stream().count() == 0L){
			setRemainNumber(cid,sid,remainNumber);
		}
		
		// Item IS00368
		if (substitutionOfHDManaDataRepository.getBysiD(sid).stream().count() == 0L && payoutManagementDataRepository.getSidWithCod(cid,sid).stream().count() == 0L){
			setRemainLeftItem(cid,sid,remainLeft);
		}
		
	}
	
	// Item IS00368
	// 振出振休管理データの初回登録
	private void setRemainLeftItem(String cid, String sid, BigDecimal remainLeft){
		
		// IS00368
		if (remainLeft.compareTo(ZERO) > 0) {
			remainLeftIsBiggerThanZero(cid, sid, remainLeft);
		} else if (remainLeft.compareTo(new BigDecimal(0)) <0){
			remainLeftIsSmallerThanZero(cid, sid, remainLeft);
		}
	}
	
	/**
	 * In case of remain left is bigger than zero
	 * @param cid
	 * @param sid
	 * @param remainLeft
	 */
	private void remainLeftIsBiggerThanZero(String cid, String sid, BigDecimal remainLeft){
		BigDecimal remainLeftTpm = remainLeft;
		String newID = null;
		PayoutManagementData payout = null;
		
		while (remainLeftTpm.compareTo(ONE) >0){
			newID = IdentifierUtil.randomUniqueId();
			if (remainLeftTpm.compareTo(ONE) >= 0){
				/**
				 * 社員ID←パラメータ「社員ID」
					休出日←日付不明=true
					使用期限日←9999/12/31
					振休消化区分←未消化
					未使用日数←1.0日
					法定内外区分←法定外休日
				 */
				payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(), HolidayAtr.NON_STATUTORYHOLIDAY.value, 1d, 1d, DigestionAtr.UNUSED.value);
				
				remainLeftTpm = remainLeftTpm.subtract(ONE);
			} else if (remainLeftTpm.compareTo(AHALF) == 0) {
				payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(), HolidayAtr.NON_STATUTORYHOLIDAY.value, 0.5d, 0.5d, DigestionAtr.UNUSED.value);
				remainLeftTpm = remainLeftTpm.subtract(AHALF);
			}
			payoutManagementDataRepository.create(payout);
		}
	}
	
	/**
	 * In case of remain left is smaller than zero
	 * @param cid
	 * @param sid
	 * @param remainLeft
	 */
	private void remainLeftIsSmallerThanZero(String cid, String sid, BigDecimal remainLeft){
		String newID = null;
		// 振休作成数=-1*パラメータ「振休残数」
		BigDecimal remainLeftTpm = remainLeft.multiply(new BigDecimal(-1));
		SubstitutionOfHDManagementData subOfHD = null;
		while (remainLeftTpm.compareTo(ONE) >0){
			newID = IdentifierUtil.randomUniqueId();
			if (remainLeftTpm.compareTo(ONE) >= 0){
				/**
				 * 社員ID←パラメータ「社員ID」
					休出日←日付不明=true
					使用期限日←9999/12/31
					振休消化区分←未消化
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
	private void setRemainNumber(String cid, String sid, BigDecimal remainNumber){
		// IS00368
		if (remainNumber.compareTo(ZERO) > 0) {
			remainNumberIsMoreThanZero(cid,sid,remainNumber);
		} else if (remainNumber.compareTo(new BigDecimal(0)) <0){
			remainNumberIsSmallerThanZero(cid, sid, remainNumber);
		}
	}
	
	/**
	 * In case of remain number is bigger than zero
	 * @param cid
	 * @param sid
	 * @param remainNumber
	 */
	private void remainNumberIsMoreThanZero(String cid, String sid, BigDecimal remainNumber){
		BigDecimal remainNumberTpm = remainNumber;
		LeaveManagementData leaveMana = null;
		String newID = null;
		// 1日相当時間←指定時間．1日の時間
		// 半日相当時間←指定時間．半日の時間
		// TODO QA
		CompensatoryLeaveComSetting leaveSet = compensLeaveComSetRepository.find(cid);
		int aDay = leaveSet.getCompensatoryOccurrenceSetting().get(0).getTransferSetting().getDesignatedTime().getOneDayTime().v();
		int aHalf = leaveSet.getCompensatoryOccurrenceSetting().get(0).getTransferSetting().getDesignatedTime().getHalfDayTime().v();
		while (remainNumberTpm.compareTo(ONE) >0){
			newID = IdentifierUtil.randomUniqueId();
			if (remainNumberTpm.compareTo(ONE) >= 0){
				leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 1d, 0, 1d, 0, DigestionAtr.UNUSED.value, aDay, aHalf);
				
				remainNumberTpm = remainNumberTpm.subtract(ONE);
			} else if (remainNumberTpm.compareTo(AHALF) == 0) {
				leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 0.5d, 0, 0.5d, 0, DigestionAtr.UNUSED.value, aDay, aHalf);
				remainNumberTpm = remainNumberTpm.subtract(AHALF);
			}
			leaveManaDataRepository.create(leaveMana);
		}
	}
	
	/**
	 * In case of remain number is smaller than zero
	 * @param cid
	 * @param sid
	 * @param remainNumber
	 */
	private void remainNumberIsSmallerThanZero(String cid, String sid, BigDecimal remainNumber){
		String newID = null;
		// 振休作成数=-1*パラメータ「振休残数」
		BigDecimal remainNumberTpm = remainNumber.multiply(new BigDecimal(-1));
		CompensatoryDayOffManaData comDayOff = null;
		
		while (remainNumberTpm.compareTo(ONE) >0){
			newID = IdentifierUtil.randomUniqueId();
			if (remainNumberTpm.compareTo(ONE) >= 0){
				/**
				 * 社員ID←パラメータ「社員ID」
					休出日←日付不明=true
					使用期限日←9999/12/31
					振休消化区分←未消化
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
	 * For Item IS00366
	 * LeaveManagementData
	 * @param sid
	 */
	// TODO Pending QA
	private void getWorkTimeSetting(String cid, String sid){
		Optional<WorkingCondition> workingCond = workingConditionRepository.getBySid(cid, sid);
		if (workingCond.isPresent()){
			DateHistoryItem histItem = workingCond.get().getDateHistoryItem().stream().reduce((first, second) -> second)
					  .orElse(null);
			if (histItem != null){
				String histId = histItem.identifier();
				
				workingConditionItemRepository.getBySidAndHistId(sid, histId);
			}
		}
	}
	
	
	public void updateOtherHolidayInfo(String cid, PublicHolidayRemain pubHD, ExcessLeaveInfo exLeav, BigDecimal remainNumber, BigDecimal remainLeft){
		
		publicHolidayRemainRepository.update(pubHD);
		excessLeaveInfoRepository.update(exLeav);
		
		String sid = pubHD.getSID();
		// Item IS00366
		if (comDayOffManaDataRepository.getBySid(sid).stream().count() == 0L && leaveManaDataRepository.getBySid(cid, sid).stream().count() == 0L){
			setRemainNumber(cid,sid,remainNumber);
		}
		
		// Item IS00368
		if (substitutionOfHDManaDataRepository.getBysiD(sid).stream().count() == 0L && payoutManagementDataRepository.getSidWithCod(cid,sid).stream().count() == 0L){
			setRemainLeftItem(cid,sid,remainLeft);
		}
		
	}
}
