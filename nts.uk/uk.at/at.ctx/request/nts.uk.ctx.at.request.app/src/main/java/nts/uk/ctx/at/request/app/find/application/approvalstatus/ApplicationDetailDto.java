package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

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
	private boolean prePostAtr;
	/** 
	 * 申請日付
	 */
	private GeneralDate appStartDate;
	
	private GeneralDate appEndDate;
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
	public ApplicationDetailDto(int appType, String appName, boolean prePostAtr, GeneralDate appStartDate,
			GeneralDate appEndDate, String appContent, int reflectState, List<Integer> approvalStatus, String phase1,
			String phase2, String phase3, String phase4, String phase5) {
		super();
		this.appType = appType;
		this.appName = appName;
		this.prePostAtr = prePostAtr;
		this.appStartDate = appStartDate;
		this.appEndDate = appEndDate;
		this.appContent = appContent;
		this.reflectState = reflectState;
		this.approvalStatus = approvalStatus;
		this.phase1 = Objects.isNull(phase1) ? "" : phase1;
		this.phase2 = Objects.isNull(phase2) ? "" : phase2;
		this.phase3 = Objects.isNull(phase3) ? "" : phase3;
		this.phase4 = Objects.isNull(phase4) ? "" : phase4;
		this.phase5 = Objects.isNull(phase5) ? "" : phase5;
	}
	
}
