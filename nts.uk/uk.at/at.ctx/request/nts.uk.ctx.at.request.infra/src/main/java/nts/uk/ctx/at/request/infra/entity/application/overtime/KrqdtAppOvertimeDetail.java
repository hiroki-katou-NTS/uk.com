package nts.uk.ctx.at.request.infra.entity.application.overtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeAnnual;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitAverage;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitMonth;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimitPerMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * Refactor5
 * @author hoangnd
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQDT_APP_OVERTIME_DETAIL")
public class KrqdtAppOvertimeDetail extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrqdtAppOvertimeDetailPk appOvertimeDetailPk;
	
	@Column(name = "APPLICATION_TIME")
	public Integer applicationTime_detail;
	
	@Column(name = "YEAR_MONTH")
	public Integer year_month;
	
	@Column(name = "ACTUAL_TIME")
	public Integer actualTime;
	
	@Column(name = "LIMIT_ERROR_TIME")
	public Integer limitErrorTime;
	
	@Column(name = "LIMIT_ALARM_TIME")
	public Integer limitAlarmTime;
	
	@Column(name = "EXCEPTION_LIMIT_ERROR_TIME")
	public Integer excLimitErrorTime;
	
	@Column(name = "EXCEPTION_LIMIT_ALARM_TIME")
	public Integer excLimitAlarmTime;
	
	@Column(name = "NUM_OF_YEAR36_OVER")
	public Integer numOfYear36Over;
	
	@Column(name = "ACTUAL_TIME_YEAR")
	public Integer actualTimeYear;
	
	@Column(name = "LIMIT_TIME_YEAR")
	public Integer limitTimeYear;
	
	@Column(name = "REG_APPLICATION_TIME")
	public Integer regApplicationTime;
	
	@Column(name = "REG_ACTUAL_TIME")
	public Integer refActualTime;
	
	@Column(name = "REG_LIMIT_TIME")
	public Integer regLimitTime;
	
	@Column(name = "REG_LIMIT_TIME_MULTI")
	public Integer regLimitTimeMulti;
	
	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "APP_ID", referencedColumnName = "APP_ID") })
	public KrqdtAppOverTime appOvertime;
	
	
	@OneToMany(targetEntity = KrqdtAppOverTimeDetM.class, mappedBy = "appOvertimeDetail", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_OVERTIME_DET_M")
	public List<KrqdtAppOverTimeDetM> KrqdtAppOverTimeDetM;
	
	@Override
	protected Object getKey() {
		return null;
	}
	
	public AppOvertimeDetail toDomain() {
		if (getKey() == null) return null;
		AppOvertimeDetail appOvertimeDetail = new AppOvertimeDetail();
		appOvertimeDetail.setCid(appOvertimeDetailPk.cid);
		appOvertimeDetail.setAppId(appOvertimeDetailPk.appId);
		
		if(year_month != null) {
			YearMonth yearMonth = new YearMonth(year_month);
			appOvertimeDetail.setYearMonth(yearMonth);
		}
		
		Time36Agree time36Agree = new Time36Agree();
		if (applicationTime_detail != null) {
			time36Agree.setApplicationTime(new AttendanceTimeMonth(applicationTime_detail));			
		}
		Time36AgreeMonth agreeMonth = new Time36AgreeMonth();
		agreeMonth.setActualTime(actualTime);
		agreeMonth.setLimitErrorTime(limitErrorTime);
		agreeMonth.setLimitAlarmTime(limitAlarmTime);
		if (excLimitErrorTime != null) {
			agreeMonth.setExceptionLimitErrorTime(excLimitErrorTime);			
		}
		if (excLimitAlarmTime != null) {
			agreeMonth.setExceptionLimitAlarmTime(excLimitAlarmTime);			
		}
		agreeMonth.setNumOfYear36Over(numOfYear36Over);
		time36Agree.setAgreeMonth(agreeMonth);
		Time36AgreeAnnual agreeAnnual = new Time36AgreeAnnual();
		agreeAnnual.setActualTime(new AttendanceTimeYear(actualTimeYear));
		agreeAnnual.setLimitTime(new AgreementOneYearTime(limitTimeYear));
		time36Agree.setAgreeAnnual(agreeAnnual);
		appOvertimeDetail.setTime36Agree(time36Agree);
		
		Time36AgreeUpperLimit time36AgreeUpperLimit = new Time36AgreeUpperLimit();
		time36AgreeUpperLimit.setApplicationTime(new AttendanceTimeMonth(regApplicationTime));
		Time36AgreeUpperLimitMonth agreeUpperLimitMonth = new Time36AgreeUpperLimitMonth();
		agreeUpperLimitMonth.updateOverTime(refActualTime);
		agreeUpperLimitMonth.updateUpperLimitTime(regLimitTime);
		time36AgreeUpperLimit.setAgreeUpperLimitMonth(agreeUpperLimitMonth);
		
		Time36AgreeUpperLimitAverage agreeUpperLimitAverage = new Time36AgreeUpperLimitAverage();
		List<Time36AgreeUpperLimitPerMonth> averageTimeLst = new ArrayList<Time36AgreeUpperLimitPerMonth>();
		if (!CollectionUtil.isEmpty(KrqdtAppOverTimeDetM)) {
			averageTimeLst = KrqdtAppOverTimeDetM.stream()
								.map(x -> x.toDomain())
								.collect(Collectors.toList());
		}
		agreeUpperLimitAverage.setAverageTimeLst(averageTimeLst);
		agreeUpperLimitAverage.setUpperLimitTime(new AgreementOneMonthTime(regLimitTimeMulti));
		return appOvertimeDetail;
	}

}
