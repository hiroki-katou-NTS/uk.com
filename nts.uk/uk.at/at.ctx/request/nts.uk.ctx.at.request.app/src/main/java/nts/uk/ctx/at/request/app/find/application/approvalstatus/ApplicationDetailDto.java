package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author Anh.BD
 *
 */
@Data
@Setter
@Getter
public class ApplicationDetailDto {
	
	private int appType;
	/** 
	 * 申請名
	 */
	private String appName;
	/** 
	 * 事前事後 
	 */
	private int prePostAtr;
	/** 
	 * 申請日付
	 */
	private String appStartDate;
	
	private String appEndDate;
	/**
	 * 申請内容 
	 */
	private String appContent;
	/** 
	 * 反映状況 
	 */
	private int reflectState;
	
	private List<Integer> approvalStatus;
	
	/**
	 * 申請日
	 */
	private String applicationDate;
	/**
	 * 申請ID
	 */
	private String applicationID;
	
	/**
	 * 申請理由
	 */
	private String applicationReason;
	
	private boolean isDispReason;
	/** 
	 * 承認フェーズ１ 
	 */
	private String phase1;
	/** 
	 * 承認フェーズ２ 
	 */
	private String phase2;
	/** 
	 * 承認フェーズ３ 
	 */
	private String phase3;
	/**
	 *  承認フェーズ４
	 */
	private String phase4;
	/** 
	 * 承認フェーズ５ 
	 */
	private String phase5;
	public ApplicationDetailDto() {
	
	}
	public ApplicationDetailDto(String applicationID, String applicationDate, String applicationReason, int appType, String appName, int prePostAtr, String appStartDate,
			String appEndDate, String appContent, int reflectState, List<Integer> approvalStatus, boolean isDispReason, String phase1,
			String phase2, String phase3, String phase4, String phase5) {
		super();
		this.applicationID = applicationID;
		this.applicationDate = applicationDate;
		this.applicationReason = applicationReason;
		this.appType = appType;
		this.appName = appName;
		this.prePostAtr = prePostAtr;
		this.appStartDate = appStartDate;
		this.appEndDate = appEndDate;
		this.appContent = appContent;
		this.reflectState = reflectState;
		this.approvalStatus = approvalStatus;
		this.isDispReason = isDispReason;
		this.phase1 = Objects.isNull(phase1) ? "" : phase1;
		this.phase2 = Objects.isNull(phase2) ? "" : phase2;
		this.phase3 = Objects.isNull(phase3) ? "" : phase3;
		this.phase4 = Objects.isNull(phase4) ? "" : phase4;
		this.phase5 = Objects.isNull(phase5) ? "" : phase5;
	}
	
}
