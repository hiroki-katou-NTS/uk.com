package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlstPtn;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily.KfnmtExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly.KfnmtExtractPeriodMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth.KfnmtAlstPtnDeftmbsmon;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit.KfnmtAlstPtnDeftmcycle;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.yearly.KfnmtAlstPtnDeftmyear;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KFNMT_ALST_PTN_DEFTM")
public class KfnmtAlstPtnDeftm extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtAlstPtnDeftmPK pk;

	@Column(name = "EXTRACTION_ID")
	public String extractionId;

	@Column(name = "EXTRACTION_RANGE")
	public int extractionRange;

	@ManyToOne
	@JoinColumns(
		  { @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false) })
	public KfnmtAlstPtn alarmPatternSet;

	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALST_PTN_CHKCND")
	public List<KfnmtAlstPtnChkcnd> checkConItems;
	
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
	public KfnmtAlstPtnDeftmcycle extractionPerUnit;
	
	
	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_ALST_PTN_DEFTMMON")
	public List<KfnmtExtractPeriodMonth> listExtractPerMonth;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtAlstPtnDeftmyear extractRangeYear;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtAlstPtnDeftmbsmon alstPtnDeftmbsmon ;
	
	
	public KfnmtAlstPtnDeftm(KfnmtAlstPtnDeftmPK pk, String extractionId, int extractionRange,
			List<KfnmtAlstPtnChkcnd> checkConItems, KfnmtExtractionPeriodDaily extractionPeriodDaily) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPeriodDaily = extractionPeriodDaily;
	}
	
	public KfnmtAlstPtnDeftm(KfnmtAlstPtnDeftmPK pk, String extractionId, int extractionRange, List<KfnmtAlstPtnChkcnd> checkConItems,
			KfnmtAlstPtnDeftmcycle extractionPerUnit) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPerUnit = extractionPerUnit;
	}

	public KfnmtAlstPtnDeftm(KfnmtAlstPtnDeftmPK pk, String extractionId, int extractionRange,
			List<KfnmtAlstPtnChkcnd> checkConItems, List<KfnmtExtractPeriodMonth> listExtractPerMonth) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.listExtractPerMonth = listExtractPerMonth;
	}


	public KfnmtAlstPtnDeftm(KfnmtAlstPtnDeftmPK pk, String extractionId, int extractionRange,
			List<KfnmtAlstPtnChkcnd> checkConItems, KfnmtExtractionPeriodDaily extractionPeriodDaily,
			List<KfnmtExtractPeriodMonth> listExtractPerMonth, KfnmtAlstPtnDeftmyear extractRangeYear, KfnmtAlstPtnDeftmbsmon kfnmtAlstPtnDeftmbsmon) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.extractionPeriodDaily = extractionPeriodDaily;
		this.listExtractPerMonth = listExtractPerMonth;
		this.extractRangeYear = extractRangeYear;
		this.alstPtnDeftmbsmon = kfnmtAlstPtnDeftmbsmon;
	}
	
	
	public KfnmtAlstPtnDeftm(KfnmtAlstPtnDeftmPK pk, String extractionId, int extractionRange, List<KfnmtAlstPtnChkcnd> checkConItems) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public CheckCondition toDomain() {

		List<ExtractionRangeBase> extractPeriodList = new ArrayList<>();
		if (this.pk.alarmCategory == AlarmCategory.DAILY.value
				|| this.pk.alarmCategory == AlarmCategory.MAN_HOUR_CHECK.value) {
			extractPeriodList.add(extractionPeriodDaily.toDomain());

		} else if (this.pk.alarmCategory == AlarmCategory.MONTHLY.value) {
			listExtractPerMonth.forEach(e -> {
				if (e.pk.unit == 3)
					extractPeriodList.add(e.toDomain(extractionId, extractionRange));
			});
			
		} else if (this.pk.alarmCategory == AlarmCategory.MULTIPLE_MONTH.value) {
			listExtractPerMonth.forEach(e -> {
				if (e.pk.unit == 3)
					
					extractPeriodList.add(e.toDomain(extractionId, extractionRange));
			});
		} else if (this.pk.alarmCategory == AlarmCategory.SCHEDULE_4WEEK.value) {
			if(extractionPerUnit != null)
			extractPeriodList.add(extractionPerUnit.toDomain());
			
		} else if(this.pk.alarmCategory == AlarmCategory.AGREEMENT.value) {
			if(extractionPeriodDaily != null)
			extractPeriodList.add(extractionPeriodDaily.toDomain());
			
			listExtractPerMonth.forEach(e -> {				
					extractPeriodList.add(e.toDomain(extractionId, extractionRange));
			});
			
			if(extractRangeYear != null)
			extractPeriodList.add(extractRangeYear.toDomain());
			if(alstPtnDeftmbsmon != null)
			// Add アラームリストのパターン設定 既定期間(基準月) to extractPeriodList
			extractPeriodList.add(alstPtnDeftmbsmon.toDomain());
		}

		List<String> checkConList = this.checkConItems.stream().map(c -> c.pk.checkConditionCD)
				.collect(Collectors.toList());
		CheckCondition domain = new CheckCondition(EnumAdaptor.valueOf(this.pk.alarmCategory, AlarmCategory.class),
				checkConList, extractPeriodList);
		return domain;
	}
	
	public static KfnmtAlstPtnDeftm toEntity(CheckCondition domain, String companyId, String alarmPatternCode) {
		
		if (domain.isDaily() || domain.isManHourCheck()) {	
			
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;
			KfnmtAlstPtnDeftm entity = new KfnmtAlstPtnDeftm(
					new KfnmtAlstPtnDeftmPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					extractBase.getExtractionId(), extractBase.getExtractionRange().value,
					domain.getCheckConditionList().stream().map(
							x -> new KfnmtAlstPtnChkcnd(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
							.collect(Collectors.toList()),
					KfnmtExtractionPeriodDaily.toEntity(extractionPeriodDaily));
			return entity;
			
		} else if (domain.isMonthly() || domain.isMultipleMonth()) {		
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			ExtractionPeriodMonth extractionPeriodMonth = (ExtractionPeriodMonth) extractBase;

			KfnmtAlstPtnDeftm entity = new KfnmtAlstPtnDeftm(
					new KfnmtAlstPtnDeftmPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					extractBase.getExtractionId(), extractBase.getExtractionRange().value,
					domain.getCheckConditionList().stream().map(
							x -> new KfnmtAlstPtnChkcnd(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
							.collect(Collectors.toList()),
					Arrays.asList(KfnmtExtractPeriodMonth.toEntity(companyId, alarmPatternCode,
							domain.getAlarmCategory().value, extractionPeriodMonth)));
			return entity;
		} else if (domain.is4W4D()) {

			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);

			ExtractionPeriodUnit extractionPerUnit = (ExtractionPeriodUnit) extractBase;
			KfnmtAlstPtnDeftm entity = new KfnmtAlstPtnDeftm(
					new KfnmtAlstPtnDeftmPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					extractBase.getExtractionId(), extractBase.getExtractionRange().value,
					domain.getCheckConditionList().stream().map(
							x -> new KfnmtAlstPtnChkcnd(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
							.collect(Collectors.toList()),
					KfnmtAlstPtnDeftmcycle.toEntity(extractionPerUnit));
			return entity;			
			
		} else if (domain.isAgrrement()) {
			List<ExtractionPeriodMonth> listMonth = new ArrayList<ExtractionPeriodMonth>();
			ExtractionPeriodDaily extractionPeriodDaily = null;
			AYear extractYear = null ;
			AverageMonth extractAverMonth = null;
			int extractionRange = 0;
			
			for(ExtractionRangeBase extractBase : domain.getExtractPeriodList()) {
				
				if(extractBase instanceof ExtractionPeriodDaily) {
					extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;
					
					
				}else if(extractBase  instanceof ExtractionPeriodMonth) {
					listMonth.add((ExtractionPeriodMonth) extractBase);					
				}else if (extractBase  instanceof AYear){
					extractYear = (AYear) extractBase;
				}else if (extractBase  instanceof AverageMonth){
					extractAverMonth = (AverageMonth) extractBase;
				}
			}
			if(domain.getAlarmCategory().value == 2 ){
				extractionRange = 3;
			} else if (domain.getAlarmCategory().value == 12 ){
				extractionRange = 4;
			} else {
				extractionRange = 0;
			}
			return new KfnmtAlstPtnDeftm(
					new KfnmtAlstPtnDeftmPK(companyId, alarmPatternCode, domain.getAlarmCategory().value),
					extractYear.getExtractionId(), extractionRange,
					domain.getCheckConditionList().stream().map( x -> new KfnmtAlstPtnChkcnd(buildCheckConItemPK(domain, x, companyId, alarmPatternCode))).collect(Collectors.toList()), 
					KfnmtExtractionPeriodDaily.toEntity(extractionPeriodDaily), 
					listMonth.stream().map( e-> KfnmtExtractPeriodMonth.toEntity(companyId, alarmPatternCode,
					domain.getAlarmCategory().value, e) ).collect(Collectors.toList()),
					KfnmtAlstPtnDeftmyear.toEntity(extractYear),
					// Convert to Entity of 複数月平均基準月
					KfnmtAlstPtnDeftmbsmon.toEntity(extractAverMonth));
			
		} else {
			
			return new KfnmtAlstPtnDeftm(
					new KfnmtAlstPtnDeftmPK(companyId, alarmPatternCode, domain.getAlarmCategory().value), "", 0,
					domain.getCheckConditionList().stream().map(
							x -> new KfnmtAlstPtnChkcnd(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
							.collect(Collectors.toList()));
		}


	}
	
	public void fromEntity(KfnmtAlstPtnDeftm entity) {

		if (entity.pk.alarmCategory == AlarmCategory.DAILY.value || entity.pk.alarmCategory == AlarmCategory.MAN_HOUR_CHECK.value) {
			
			if(this.extractionPeriodDaily != null)
			this.extractionPeriodDaily.fromEntity(entity.extractionPeriodDaily);
			
		} else if (entity.pk.alarmCategory == AlarmCategory.MONTHLY.value || 
				entity.pk.alarmCategory == AlarmCategory.MULTIPLE_MONTH.value ) {
			
			this.listExtractPerMonth= new ArrayList<KfnmtExtractPeriodMonth>();
			entity.listExtractPerMonth.forEach(item -> {
				this.listExtractPerMonth.add(item);
			});
		} else if (entity.pk.alarmCategory == AlarmCategory.SCHEDULE_4WEEK.value) {
			
			if(this.extractionPerUnit != null)
			this.extractionPerUnit.fromEntity(entity.extractionPerUnit);
			
		} else if(entity.pk.alarmCategory ==AlarmCategory.AGREEMENT.value) {
			
			if(this.extractionPeriodDaily != null)
			this.extractionPeriodDaily.fromEntity(entity.extractionPeriodDaily);
			
			this.listExtractPerMonth= new ArrayList<KfnmtExtractPeriodMonth>();
			entity.listExtractPerMonth.forEach(item -> {
				this.listExtractPerMonth.add(item);
			});
			
			if(this.extractRangeYear != null)
			this.extractRangeYear.fromEntity(entity.extractRangeYear);
			
			// Convert from Entity of 複数月平均基準月
			if(this.alstPtnDeftmbsmon != null)
			this.alstPtnDeftmbsmon.fromEntity(entity.alstPtnDeftmbsmon);
		}
		
		
		this.extractionRange = entity.extractionRange;
		this.checkConItems.removeIf(item -> !entity.checkConItems.contains(item));
		entity.checkConItems.forEach( item ->{
			if(!this.checkConItems.contains(item)) this.checkConItems.add(item);
		});
	}
	
	private static KfnmtAlstPtnChkcndPK buildCheckConItemPK(CheckCondition domain, String checkConditionCD, String companyId, String alarmPatternCode) {
		return new KfnmtAlstPtnChkcndPK(companyId, alarmPatternCode, domain.getAlarmCategory().value, checkConditionCD);
	}

	
}
