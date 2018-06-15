/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.vacation.setting.nursingleave;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ChildNursingRemainExport;
import nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.ShNursingLeaveSettingPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class ShNursingLeaveSettingPubImpl.
 */
@Stateless
public class ShNursingLeaveSettingPubImpl implements ShNursingLeaveSettingPub {
	
	/** The nursing leave setting repository. */
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrChildNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId,
			String employeeId, DatePeriod period, Integer mode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave.
	 * ShNursingLeaveSettingPub#aggrNursingRemainPeriod(java.lang.String,
	 * java.lang.String, nts.uk.shr.com.time.calendar.period.DatePeriod,
	 * java.lang.Integer)
	 */
	@Override
	public ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId, GeneralDate startDate, GeneralDate endDate , Integer mode) {
		ChildNursingRemainExport result = null;
		List<NursingLeaveSetting> listNursingLeaveSetting = this.nursingLeaveSettingRepository.findByCompanyId(companyId);
//		//filter by category
//		NursingCategory nursingCategory = NursingCategory.valueOf(1);
//		listNursingLeaveSetting.stream().filter(e ->{
//			e.getNursingCategory().equals(nursingCategory);
//		});
		if(listNursingLeaveSetting==null ||listNursingLeaveSetting.isEmpty()) {
			result.setIsManage(false);
			return result;
		}else {
			result.setGrantPeriodFlag(false);
			/**
			 * GeneralDate commencementDate = startDate.Year + listNursingLeaveSetting.startMonthDay
			 * vi du : startDate = 2018/5/1, listNursingLeaveSetting.startMonthDay = 4/1 => commencementDate = 2018/4/1
			 * GeneralDate useStartDateBeforeGrant; (1 trong so output cua vi tri hien tai) 利用開始日(付与前)
			 * so sanh: nextCondition =  commencementDate > startDate ? true : false
			 * if(nextCondition){
			 * 		useStartDateBeforeGrant = commencementDate.AddYears(-1) ? 
			 * }else{
			 * 		useStartDateBeforeGrant = commencementDate;
			 * }
			 * GeneralDate endDateUseBeforeGrant = useStartDateBeforeGrant.AddYears(1).AddDays(-1) (1 trong so output cua vi tri hien tai)  利用終了日(付与前)
			 * 
			 * Boolean nextCondition2 =useStartDateBeforeGrant.AddYears(1) <= endDate //Kiểm tra xem có khoản trợ cấp nào trong giai đoạn tổng hợp không
			 * 
			 * if(!nextCondition2){
			 * ket thuc luon
			 * }else{
			 * 	periodGrantFlag = true (1 trong so output cua vi tri hien tai) 期中付与フラグ
			 * 	useStartDateAfterGrant = useStartDateBeforeGrant.AddYears(1); //利用開始日(付与後) (1 trong so output cua vi tri hien tai)
			 * 	endUseAfterGrant = useStartDateAfterGrant.AddYears(1).AddDays(-1) 利用終了日(付与後)  (1 trong so output cua vi tri hien tai)
			 * }
			 * => xong thang nay (利用期間の算出)
			 */
		}
		
		return result;
	}

}
