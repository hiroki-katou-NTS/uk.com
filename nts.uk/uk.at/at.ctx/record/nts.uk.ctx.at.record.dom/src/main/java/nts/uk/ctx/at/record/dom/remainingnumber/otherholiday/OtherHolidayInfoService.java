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
					 */
					payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(), HolidayAtr.STATUTORY_HOLIDAYS.value, 1d, 0d, DigestionAtr.UNUSED.value);
					
					remainLeftTpm = remainLeftTpm.subtract(ONE);
				} else if (remainLeftTpm.compareTo(AHALF) == 0) {
					payout = new PayoutManagementData(newID, cid, sid, true, null, GeneralDate.max(), HolidayAtr.STATUTORY_HOLIDAYS.value, 0.5d, 0d, DigestionAtr.UNUSED.value);
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
			while (remainNumberTpm.compareTo(ONE) >0){
				newID = IdentifierUtil.randomUniqueId();
				if (remainNumberTpm.compareTo(ONE) >= 0){
//					leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 1d, occurredTimes, unUsedDays, unUsedTimes, DigestionAtr.UNUSED, equivalentADay, equivalentHalfDay);
					
					remainNumberTpm = remainNumberTpm.subtract(ONE);
				} else if (remainNumberTpm.compareTo(AHALF) == 0) {
//					leaveMana = new LeaveManagementData(newID, cid, sid, true, null, GeneralDate.max(), 0.5d, occurredTimes, unUsedDays, unUsedTimes, DigestionAtr.UNUSED, equivalentADay, equivalentHalfDay);
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
//					comDayOff = new CompensatoryDayOffManaData(newID, cid, sid, true, null, 1d, time, 1d, remainTimes);
					remainNumberTpm = remainNumberTpm.subtract(ONE);
				} else if (remainNumberTpm.compareTo(AHALF) == 0) {
//					comDayOff = new CompensatoryDayOffManaData(newID, cid, sid, true, null, 0.5d, time, 0.5d, remainTimes);
					remainNumberTpm = remainNumberTpm.subtract(AHALF);
				}
				comDayOffManaDataRepository.create(comDayOff);
			}
		}
	}
}
