package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Setter
public class ApplicationDetailDto {
	
	int appType;
	/** 
	 * 申請名
	 */
	String appName;
	/** 
	 * 事前事後 
	 */
	int prePostAtr;
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
	int reflectionInfo;
	/** 
	 * 承認フェーズ１ 
	 */
	int phase1;
	/** 
	 * 承認フェーズ２ 
	 */
	int phase2;
	/** 
	 * 承認フェーズ３ 
	 */
	int phase3;
	/**
	 *  承認フェーズ４
	 */
	int phase4;
	/** 
	 * 承認フェーズ５ 
	 */
	int phase5;
}
