package nts.uk.ctx.at.record.infra.entity.workmanagement.workinitselectset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskCode;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskInitialSel;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskInitialSelHist;
import nts.uk.ctx.at.record.dom.workmanagement.workinitselectset.TaskItem;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;
@Entity
@NoArgsConstructor

@Table(name="KRCMT_TASK_INITIAL_SEL_HIST")
public class KrcmtTaskInitialSelHist extends ContractCompanyUkJpaEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcmtTaskInitialSelHistPk pk;
	
	/** 終了日 **/
	@Basic(optional = false)
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	/** 作業コード1 **/
	@Basic(optional = false)
	@Column(name = "TASK_CD_1")
	public String taskCd1;
	
	/** 作業コード2 **/
	@Basic(optional = false)
	@Column(name = "TASK_CD_2")
	public String taskCd2;
	
	/** 作業コード3 **/
	@Basic(optional = false)
	@Column(name = "TASK_CD_3")
	public String taskCd3;
	
	/** 作業コード4 **/
	@Basic(optional = false)
	@Column(name = "TASK_CD_4")
	public String taskCd4;
	
	/** 作業コード5 **/
	@Basic(optional = false)
	@Column(name = "TASK_CD_5")
	public String taskCd5;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcmtTaskInitialSelHist(KrcmtTaskInitialSelHistPk pk, GeneralDate endDate, String taskCd1, String taskCd2,
			String taskCd3, String taskCd4, String taskCd5) {
		super();
		this.pk = pk;
		this.endDate = endDate;
		this.taskCd1 = taskCd1;
		this.taskCd2 = taskCd2;
		this.taskCd3 = taskCd3;
		this.taskCd4 = taskCd4;
		this.taskCd5 = taskCd5;
	}
	
	public static List<KrcmtTaskInitialSelHist> toEntity(TaskInitialSelHist domain){		
		return domain.getLstHistory().stream().map(c -> new KrcmtTaskInitialSelHist(
				new KrcmtTaskInitialSelHistPk(domain.getEmpId(), c.getDatePeriod().start()),
				c.getDatePeriod().end(),
				c.getTaskItem().getOtpWorkCode1().isPresent() ? c.getTaskItem().getOtpWorkCode1().get().v() : "",
				c.getTaskItem().getOtpWorkCode2().isPresent() ? c.getTaskItem().getOtpWorkCode2().get().v() : "",
				c.getTaskItem().getOtpWorkCode3().isPresent() ? c.getTaskItem().getOtpWorkCode3().get().v() : "",
				c.getTaskItem().getOtpWorkCode4().isPresent() ? c.getTaskItem().getOtpWorkCode4().get().v() : "",
				c.getTaskItem().getOtpWorkCode5().isPresent() ? c.getTaskItem().getOtpWorkCode5().get().v() : "")).collect(Collectors.toList());
	}
}
