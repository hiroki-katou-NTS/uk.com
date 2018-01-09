package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.ConExtractedDaily;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_DAILY_ALARM_CON")
public class KrcmtDailyAlarmCondition extends UkJpaEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DAILY_ALARM_CON_ID")
	public String dailyAlarmConID;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "CATEGORY_CODE")
	public String code;

	@Column(name = "CATEGORY")
	public int category;

	@Column(name = "ERROR_ALARM_CHECK_ID")
	public String errorAlarmCode;

	@Column(name = "CON_EXTRACTED_DAILY")
	public int conExtractedDaily;
	
	@Column(name = "ADD_APPLICATION")
	public int addApplication;
	
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY_CODE", referencedColumnName = "CD", insertable = false, updatable = false)
	})
	public KfnmtAlarmCheckConditionCategory condition;

	public KrcmtDailyAlarmCondition(String dailyAlarmConID, String companyId, String code, int category,
			String errorAlarmCode, int conExtractedDaily, int addApplication) {
		super();
		this.dailyAlarmConID = dailyAlarmConID;
		this.companyId = companyId;
		this.code = code;
		this.category = category;
		this.errorAlarmCode = errorAlarmCode;
		this.conExtractedDaily = conExtractedDaily;
		this.addApplication = addApplication;
	}
	
	@Override
	protected Object getKey() {
		return dailyAlarmConID;
	}

	public static KrcmtDailyAlarmCondition toEntity(String companyId,AlarmCheckConditionCode code ,AlarmCategory category, DailyAlarmCondition domain) {
		return new KrcmtDailyAlarmCondition(
					domain.getDailyAlarmConID(),
					companyId,
					code.v(),
					category.value,
					domain.getErrorAlarmCode(),
					domain.getConExtractedDaily().value,
					domain.isAddApplication()?1:0
				);
	}
	
	public DailyAlarmCondition toDomain() {
		return new DailyAlarmCondition(
				this.dailyAlarmConID,
				EnumAdaptor.valueOf(this.conExtractedDaily, ConExtractedDaily.class) ,
				
				this.addApplication == 1?true:false,
				this.errorAlarmCode
				);
	}

}
