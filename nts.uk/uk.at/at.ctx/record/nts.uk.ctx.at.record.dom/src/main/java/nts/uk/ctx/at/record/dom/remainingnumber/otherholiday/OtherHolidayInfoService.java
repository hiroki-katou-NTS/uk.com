package nts.uk.ctx.at.record.dom.remainingnumber.otherholiday;

import java.math.BigDecimal;

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
	private void setRemainLeftItem(String cid, String sid, BigDecimal remainLeft){
		BigDecimal remainLeftTpm = remainLeft;
		
		String newID = null;
		
		// IS00368
		if (remainLeft.compareTo(ZERO) > 0) {
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
		} else if (remainLeft.compareTo(new BigDecimal(0)) <0){
			
			// 振休作成数=-1*パラメータ「振休残数」
			remainLeftTpm = remainLeftTpm.multiply(new BigDecimal(-1));
			
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
	}
	
	// Item IS00366
	private void setRemainNumber(String cid, String sid, BigDecimal remainNumber){
		BigDecimal remainNumberTpm = remainNumber;
		
		String newID = null;
		
		// IS00368
		if (remainNumber.compareTo(ZERO) > 0) {
			LeaveManagementData leaveMana = null;
			// 1日相当時間←指定時間．1日の時間
			// 半日相当時間←指定時間．半日の時間
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
		} else if (remainNumber.compareTo(new BigDecimal(0)) <0){
			
			// 振休作成数=-1*パラメータ「振休残数」
			remainNumberTpm = remainNumberTpm.multiply(new BigDecimal(-1));
			
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
	}
}
