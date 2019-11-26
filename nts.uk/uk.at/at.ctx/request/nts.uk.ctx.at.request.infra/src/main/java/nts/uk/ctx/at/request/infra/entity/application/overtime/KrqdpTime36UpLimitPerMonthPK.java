package nts.uk.ctx.at.request.infra.entity.application.overtime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrqdpTime36UpLimitPerMonthPK {
	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String cID;

	/**
	 * 申請ID
	 */
	@Column(name = "APP_ID")
	public String appID;
	
	/*
	 * 期間
	 */
	@Column(name = "START_YM")
	public Integer periodYearStart;
	
	@Column(name = "END_YM")
	public Integer periodYearEnd;
	
}
