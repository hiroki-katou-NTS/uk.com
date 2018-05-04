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
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily.KfnmtExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly.KfnmtExtractPeriodMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit.KfnmtExtractionPerUnit;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.yearly.KfnmtExtractRangeYear;
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

	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_CHECK_CON_ITEM")
	public List<KfnmtCheckConItem> checkConItems;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtExtractionPeriodDaily extractionPeriodDaily;

	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtExtractionPerUnit extractionPerUnit;
	
	
	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public List<KfnmtExtractPeriodMonth> listExtractPerMonth;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtExtractRangeYear extractRangeYear;
	
	
	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange,
			List<KfnmtCheckConItem> checkConItems, KfnmtExtractionPeriodDaily extractionPeriodDaily) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPeriodDaily = extractionPeriodDaily;
	}
	
	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange, List<KfnmtCheckConItem> checkConItems,
			KfnmtExtractionPerUnit extractionPerUnit) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPerUnit = extractionPerUnit;
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public CheckCondition toDomain() {
		
		ExtractionRangeBase extractPeriod=null;
		if(this.extractionRange ==ExtractionRange.PERIOD.value) {
			extractPeriod = extractionPeriodDaily.toDomain();
		}else if(this.extractionRange ==ExtractionRange.WEEK.value) {
			extractPeriod = extractionPerUnit.toDomain();
		}
		
		List<String> checkConList = this.checkConItems.stream().map( c -> c.pk.checkConditionCD).collect(Collectors.toList());
		CheckCondition domain = new CheckCondition(EnumAdaptor.valueOf(this.pk.alarmCategory, AlarmCategory.class), 
				checkConList, extractPeriod);
		return domain;
	}
	
	public static KfnmtCheckCondition toEntity(CheckCondition domain, String companyId, String alarmPatternCode) {
		if (domain.getExtractPeriod().getExtractionRange() == ExtractionRange.PERIOD)
		{
			ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) domain.getExtractPeriod();
			KfnmtCheckCondition entity = new KfnmtCheckCondition(
					new KfnmtCheckConditionPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					domain.getExtractPeriod().getExtractionId(), domain.getExtractPeriod().getExtractionRange().value, 
					domain.getCheckConditionList().stream().map(
							x-> new KfnmtCheckConItem(buildCheckConItemPK(domain, x, companyId, alarmPatternCode))).collect(Collectors.toList()),
					KfnmtExtractionPeriodDaily.toEntity(extractionPeriodDaily));
			return entity;
			
		} else if(domain.getExtractPeriod().getExtractionRange() == ExtractionRange.WEEK) {
			
			ExtractionPeriodUnit extractionPerUnit = (ExtractionPeriodUnit) domain.getExtractPeriod();
			KfnmtCheckCondition entity = new KfnmtCheckCondition(
					new KfnmtCheckConditionPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					domain.getExtractPeriod().getExtractionId(), domain.getExtractPeriod().getExtractionRange().value, 
					domain.getCheckConditionList().stream().map(
							x-> new KfnmtCheckConItem(buildCheckConItemPK(domain, x, companyId, alarmPatternCode))).collect(Collectors.toList()),
					KfnmtExtractionPerUnit.toEntity(extractionPerUnit));
			return entity;
		}else {
			return null;
		}
		
	}
	
	public void fromEntity(KfnmtCheckCondition entity) {
		
		if(entity.extractionRange == ExtractionRange.PERIOD.value){ 
			this.extractionPeriodDaily.fromEntity(entity.extractionPeriodDaily);
		}else if(entity.extractionRange == ExtractionRange.WEEK.value) {
			this.extractionPerUnit.fromEntity(entity.extractionPerUnit);
		}
		this.extractionRange = entity.extractionRange;
		this.checkConItems.removeIf(item -> !entity.checkConItems.contains(item));
		entity.checkConItems.forEach( item ->{
			if(!this.checkConItems.contains(item)) this.checkConItems.add(item);
		});
	}
	
	private static KfnmtCheckConItemPK buildCheckConItemPK(CheckCondition domain, String checkConditionCD, String companyId, String alarmPatternCode) {
		return new KfnmtCheckConItemPK(companyId, alarmPatternCode, domain.getAlarmCategory().value, checkConditionCD);
	}




		
}
