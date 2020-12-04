package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.recruitmentapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayshipment.TypeApplicationHolidays;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.KrqdtAppRecAbsPK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @domain 振出申請
 * @author ThanhPV
 */
@Entity
@Table(name = "KRQDT_APP_RECRUITMENT")
@NoArgsConstructor
public class KrqdtAppRecruitment extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtAppRecAbsPK pK;
	
	@Column(name = "WORK_TYPE_CD")
	public String workTypeCd;
	
	@Column(name = "WORK_TIME_CD")
	public String workTimeCd;
	
	@Column(name = "WORK_TIME_START1")
	public Integer workTimeStart1;
	
	@Column(name = "WORK_TIME_END1")
	public Integer workTimeEnd1;
	
	@Column(name = "WORK_TIME_START2")
	public Integer workTimeStart2;
	
	@Column(name = "WORK_TIME_END2")
	public Integer workTimeEnd2;

	@Override
	protected Object getKey() {
		return pK;
	}
	
	@PrePersist
    private void setInsertingContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}

	public KrqdtAppRecruitment(RecruitmentApp domain) {
		super();
		this.pK.cId = AppContexts.user().companyId();
		this.pK.appID = domain.getAppID();
		this.workTypeCd = domain.getWorkInformation().getWorkTypeCode().v();
		this.workTimeCd = domain.getWorkInformation().getWorkTimeCode().v();
		this.workTimeStart1 = domain.getWorkTime(new WorkNo(1)).get().getTimeZone().getStartTime().v();
		this.workTimeEnd1 = domain.getWorkTime(new WorkNo(1)).get().getTimeZone().getEndTime().v();
		this.workTimeStart2 = domain.getWorkTime(new WorkNo(2)).isPresent() ? domain.getWorkTime(new WorkNo(2)).get().getTimeZone().getStartTime().v():null;
		this.workTimeEnd2 = domain.getWorkTime(new WorkNo(2)).isPresent() ? domain.getWorkTime(new WorkNo(2)).get().getTimeZone().getEndTime().v():null;
	}
	
	public RecruitmentApp toDomain(Application application) {
		return new RecruitmentApp(
				new WorkInformation(this.workTypeCd, this.workTimeCd), 
				this.getWorkingHours(),
				TypeApplicationHolidays.Rec,
				application);
	}
	
	private List<TimeZoneWithWorkNo> getWorkingHours() {
		List<TimeZoneWithWorkNo> result = new ArrayList<>(); 
		result.add(new TimeZoneWithWorkNo(1, this.workTimeStart1, this.workTimeEnd1));
		if(this.workTimeStart2 != null && this.workTimeEnd2 != null) {
			result.add(new TimeZoneWithWorkNo(2, this.workTimeStart2, this.workTimeEnd2));
		}
		return result;
	}

}
