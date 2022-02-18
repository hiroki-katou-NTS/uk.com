package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.shr.com.license.option.OptionLicense;


/**
 * 応援の運用設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援の運用設定.応援の運用設定
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class SupportOperationSetting implements DomainAggregate{
	
	/** 利用するか **/
	private boolean isUsed;
	
	/** 応援先が応援者を指定できるか **/
	private boolean supportDestinationCanSpecifySupporter;
	
	/** 一日の最大応援回数 **/
	private MaximumNumberOfSupport maxNumberOfSupportOfDay;
	
	 /**
		 * 	[1] 応援に対応する日次の勤怠項目を取得する
		 * @return
		 */
		public List<Integer> getDaiLyAttendanceIdByWork() {
			List<SupportFrameNo> supportFrameNoLst = new ArrayList<>();
			for (int i = 1; i <= 20; i++) {
				supportFrameNoLst.add(new SupportFrameNo(i));
			}
			return GetAttendanceItemIdService.getAttendanceItemIds(supportFrameNoLst);
		}
		
		/**
		 *  [2] 利用できない日次の勤怠項目を取得する
		 * @return
		 */
		public List<Integer> getDaiLyAttendanceIdNotAvailable(Require require) {
			if(!this.canUsedSupport(require)) {
				return this.getDaiLyAttendanceIdByWork();
			}
			if(this.maxNumberOfSupportOfDay.v() >=20)
				return new ArrayList<>();
			List<SupportFrameNo> supportFrameNoLst = new ArrayList<>();
			for (int i =this.maxNumberOfSupportOfDay.v() + 1; i <= 20; i++) {
				supportFrameNoLst.add(new SupportFrameNo(i));
			}
			return GetAttendanceItemIdService.getAttendanceItemIds(supportFrameNoLst);
		}
	    
	    /**
	     * [3] 実績で作業を利用できるか
	     * @param require
	     */
	    public boolean canUsedSupport(Require require) {
	    	OptionLicense optionLicense = require.getOptionLicense();
	    	if(!optionLicense.attendance().workload() ||
	    	    !optionLicense.attendance().schedule().medical() ) { //医療のoptionがtrue固定の為、一旦ここでFalse固定でチェックする
	    		return false;
	    	}
	    	return this.isUsed;
		}
	    
	    public static interface Require {
	    	/**
	    	 * 
	    	 * AppContexts.optionLicense();
	    	 * @return
	    	 */
	    	OptionLicense getOptionLicense();
		}

}
