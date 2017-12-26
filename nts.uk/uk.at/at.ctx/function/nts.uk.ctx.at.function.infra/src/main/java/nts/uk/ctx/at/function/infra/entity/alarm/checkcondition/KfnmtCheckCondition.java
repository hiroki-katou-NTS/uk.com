package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily.KfnmtExtractionPeriodDaily;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_CHECK_CONDITION")
public class KfnmtCheckCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtCheckConditionPK pk;

	@Column(name = "EXTRACTION_ID")
	public String extractionId;

	@Column(name = "EXTRACTION_RANGE")
	public int extractionRange;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false) })
	public KfnmtAlarmPatternSet alarmPatternSet;

	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL)
	@JoinTable(name = "KFNMT_CHECK_CON_ITEM")
	public List<KfnmtCheckConItem> checkConItems;
	
	@OneToOne(mappedBy="checkCondition", cascade = CascadeType.ALL, optional=true)
	@JoinTable(name = "KFNMT_EXTRACT_PER_DAILY")
	public KfnmtExtractionPeriodDaily extractionPeriodDaily;

	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange,
			List<KfnmtCheckConItem> checkConItems, KfnmtExtractionPeriodDaily extractionPeriodDaily) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPeriodDaily = extractionPeriodDaily;
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public CheckCondition toDomain() {
		List<String> checkConList = this.checkConItems.stream().map( c -> c.pk.checkConditionCD).collect(Collectors.toList());
		CheckCondition domain = new CheckCondition(this.pk.alarmPatternCD , this.pk.companyID, EnumAdaptor.valueOf(this.pk.alarmCategory, AlarmCategory.class), 
				checkConList, extractionPeriodDaily.toDomain());
		return domain;
	}
	
	public static KfnmtCheckCondition toEntity(CheckCondition domain) {
		if (domain.getExtractPeriod().getExtractionRange() == ExtractionRange.PERIOD)
		{
			ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) domain.getExtractPeriod();
			KfnmtCheckCondition entity = new KfnmtCheckCondition(
					new KfnmtCheckConditionPK(domain.getCompanyID(), domain.getAlarmPatternCD().v(), domain.getAlarmCategory().value),
					domain.getExtractPeriod().getExtractionId(), domain.getExtractPeriod().getExtractionRange().value, 
					domain.getCheckConditionList().stream().map(
							x-> new KfnmtCheckConItem(buildCheckConItemPK(domain, x))).collect(Collectors.toList()),
					KfnmtExtractionPeriodDaily.toEntity(extractionPeriodDaily));
			return entity;
		} else {
			return null;
		}
		
	}
	
	private static KfnmtCheckConItemPK buildCheckConItemPK(CheckCondition domain, String checkConditionCD) {
		return new KfnmtCheckConItemPK(domain.getCompanyID(), domain.getAlarmPatternCD().v(), domain.getAlarmCategory().value, checkConditionCD);
	}


		
}
