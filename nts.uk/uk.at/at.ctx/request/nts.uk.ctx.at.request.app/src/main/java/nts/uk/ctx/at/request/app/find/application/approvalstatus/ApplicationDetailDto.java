package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ApplicationDetailDto {
	
	int appType;
	/** 
	 * 申請名
	 */
	String appName;
	/** 
	 * 事前事後 
	 */
	boolean prePostAtr;
	/** 
	 * 申請日付
	 */
	GeneralDate appStartDate;
	
	GeneralDate appEndDate;
	/**
	 * 申請内容 
	 */
	String appContent;
	/** 
	 * 反映状況 
	 */
	int reflectState;
	
	List<Integer> approvalStatus; 
	
	/** 
	 * 承認フェーズ１ 
	 */
	String phase1;
	/** 
	 * 承認フェーズ２ 
	 */
	String phase2;
	/** 
	 * 承認フェーズ３ 
	 */
	String phase3;
	/**
	 *  承認フェーズ４
	 */
	String phase4;
	/** 
	 * 承認フェーズ５ 
	 */
	String phase5;
}
