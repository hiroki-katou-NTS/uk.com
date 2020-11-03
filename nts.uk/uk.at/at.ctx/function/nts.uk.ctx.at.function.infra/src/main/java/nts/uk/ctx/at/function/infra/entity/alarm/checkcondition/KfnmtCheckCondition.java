package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.mutilmonth.AverageMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.periodunit.ExtractionPeriodUnit;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.year.AYear;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily.KfnmtExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly.KfnmtExtractPeriodMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth.KfnmtAlstPtnDeftmbsmon;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit.KfnmtExtractionPerUnit;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.yearly.KfnmtExtractRangeYear;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "KFNMT_CHECK_CONDITION")
@EqualsAndHashCode(callSuper = true)
public class KfnmtCheckCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KfnmtCheckConditionPK pk;

	@Column(name = "EXTRACTION_ID")
	private String extractionId;

	@Column(name = "EXTRACTION_RANGE")
	private int extractionRange;

	@ManyToOne
	@JoinColumns(
			{@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
					@JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false)})
	public KfnmtAlarmPatternSet alarmPatternSet;

	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_CHECK_CON_ITEM")
	public List<KfnmtCheckConItem> checkConItems;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
			@JoinColumn(name = "EXTRACTION_ID", referencedColumnName = "EXTRACTION_ID", insertable = false, updatable = false),
			@JoinColumn(name = "EXTRACTION_RANGE", referencedColumnName = "EXTRACTION_RANGE", insertable = false, updatable = false)
	})
	public KfnmtExtractionPeriodDaily extractionPeriodDaily;


	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
			@JoinColumn(name = "EXTRACTION_ID", referencedColumnName = "EXTRACTION_ID", insertable = false, updatable = false),
			@JoinColumn(name = "EXTRACTION_RANGE", referencedColumnName = "EXTRACTION_RANGE", insertable = false, updatable = false)
	})
	public KfnmtExtractionPerUnit extractionPerUnit;


	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_EXTRACT_PER_MONTH")
	public List<KfnmtExtractPeriodMonth> listExtractPerMonth;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
			@JoinColumn(name = "EXTRACTION_ID", referencedColumnName = "EXTRACTION_ID", insertable = false, updatable = false),
			@JoinColumn(name = "EXTRACTION_RANGE", referencedColumnName = "EXTRACTION_RANGE", insertable = false, updatable = false)
	})
	public KfnmtExtractRangeYear extractRangeYear;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
			@JoinColumn(name = "EXTRACTION_ID", referencedColumnName = "EXTRACTION_ID", insertable = false, updatable = false),
			@JoinColumn(name = "EXTRACTION_RANGE", referencedColumnName = "EXTRACTION_RANGE", insertable = false, updatable = false)
	})
	public KfnmtAlstPtnDeftmbsmon alstPtnDeftmbsmon;


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

	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange,
	                           List<KfnmtCheckConItem> checkConItems, List<KfnmtExtractPeriodMonth> listExtractPerMonth) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
		this.listExtractPerMonth = listExtractPerMonth;
	}


	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange,
	                           List<KfnmtCheckConItem> checkConItems, KfnmtExtractionPeriodDaily extractionPeriodDaily,
	                           List<KfnmtExtractPeriodMonth> listExtractPerMonth, KfnmtExtractRangeYear extractRangeYear, KfnmtAlstPtnDeftmbsmon kfnmtAlstPtnDeftmbsmon) {
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


	public KfnmtCheckCondition(KfnmtCheckConditionPK pk, String extractionId, int extractionRange, List<KfnmtCheckConItem> checkConItems) {
		super();
		this.pk = pk;
		this.extractionId = extractionId;
		this.extractionRange = extractionRange;
		this.checkConItems = checkConItems;
	}

	/**
	 * No args constructor.
	 */
	protected KfnmtCheckCondition() {
	}

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public CheckCondition toDomain() {

		List<ExtractionRangeBase> extractPeriodList = new ArrayList<>();
		if (this.pk.alarmCategory == AlarmCategory.DAILY.value
				|| this.pk.alarmCategory == AlarmCategory.MAN_HOUR_CHECK.value) {
			extractPeriodList.add(this.extractionPeriodDaily.toDomain());

		} else if (this.pk.alarmCategory == AlarmCategory.MONTHLY.value) {
			this.listExtractPerMonth.forEach(e -> {
				if (e.pk.unit == 3) {
					extractPeriodList.add(e.toDomain(this.extractionId, this.extractionRange));
				}
			});

		} else if (this.pk.alarmCategory == AlarmCategory.MULTIPLE_MONTH.value) {
			this.listExtractPerMonth.forEach(e -> {
				if (e.pk.unit == 3) {
					extractPeriodList.add(e.toDomain(this.extractionId, this.extractionRange));
				}
			});

		} else if (this.pk.alarmCategory == AlarmCategory.SCHEDULE_4WEEK.value) {
			if (this.extractionPerUnit != null) {
				extractPeriodList.add(this.extractionPerUnit.toDomain());
			}

		} else if (this.pk.alarmCategory == AlarmCategory.AGREEMENT.value) {
			if (extractionPeriodDaily != null)
				extractPeriodList.add(extractionPeriodDaily.toDomain());

			listExtractPerMonth.forEach(e -> {
				extractPeriodList.add(e.toDomain(extractionId, extractionRange));
			});

			if (extractRangeYear != null)
				extractPeriodList.add(extractRangeYear.toDomain());
			if (alstPtnDeftmbsmon != null)
				// Add アラームリストのパターン設定 既定期間(基準月) to extractPeriodList
				extractPeriodList.add(alstPtnDeftmbsmon.toDomain());
		}

		List<AlarmCheckConditionCode> checkConList = this.checkConItems
				.stream()
				.map(c -> new AlarmCheckConditionCode(c.pk.checkConditionCD))
				.collect(Collectors.toList());
		return new CheckCondition(EnumAdaptor.valueOf(this.pk.alarmCategory, AlarmCategory.class),
				checkConList,
				extractPeriodList,
				Optional.empty());
	}

	public static KfnmtCheckCondition createFromDomain(CheckCondition domain, String companyId, String alarmPatternCode) {
		KfnmtCheckConditionPK kfnmtCheckConditionPK = new KfnmtCheckConditionPK(companyId, alarmPatternCode, domain.getAlarmCategory().value);
		if (domain.isDaily() || domain.isManHourCheck()) {
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			ExtractionPeriodDaily extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;
			List<KfnmtCheckConItem> kfnmtCheckConItemList = domain.getCheckConditionList()
																  .stream()
																  .map(checkConCD -> new KfnmtCheckConItem(buildCheckConItemPK(domain,
																															   checkConCD,
																															   companyId,
																															   alarmPatternCode)))
																  .collect(Collectors.toList());
			return new KfnmtCheckCondition(kfnmtCheckConditionPK,
										   extractBase.getExtractionId(),
										   extractBase.getExtractionRange().value,
										   kfnmtCheckConItemList,
										   KfnmtExtractionPeriodDaily.createFromDomain(extractionPeriodDaily));
		} else if (domain.isMonthly() || domain.isMultipleMonth()) {
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			ExtractionPeriodMonth extractionPeriodMonth = (ExtractionPeriodMonth) extractBase;
			List<KfnmtCheckConItem> kfnmtCheckConItemList = domain.getCheckConditionList()
																  .stream()
																  .map(checkConditionCD -> new KfnmtCheckConItem(
																									buildCheckConItemPK(domain,
																														checkConditionCD,
																														companyId,
																														alarmPatternCode)))
																  .collect(Collectors.toList());
			return new KfnmtCheckCondition(kfnmtCheckConditionPK,
										   extractBase.getExtractionId(),
										   extractBase.getExtractionRange().value,
										   kfnmtCheckConItemList,
										   Collections.singletonList(KfnmtExtractPeriodMonth.toEntity(companyId,
																									  alarmPatternCode,
																									  domain.getAlarmCategory().value,
																									  extractionPeriodMonth)));
		} else if (domain.is4W4D()) {
			ExtractionRangeBase extractBase = domain.getExtractPeriodList().get(0);
			ExtractionPeriodUnit extractionPerUnit = (ExtractionPeriodUnit) extractBase;
			return new KfnmtCheckCondition(kfnmtCheckConditionPK,
										   extractBase.getExtractionId(),
										   extractBase.getExtractionRange().value,
					domain.getCheckConditionList()
						  .stream().map(
										x -> new KfnmtCheckConItem(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
						  .collect(Collectors.toList()),
					KfnmtExtractionPerUnit.toEntity(extractionPerUnit));
		} else if (domain.isAgrrement()) {
			List<ExtractionPeriodMonth> listMonth = new ArrayList<ExtractionPeriodMonth>();
			ExtractionPeriodDaily extractionPeriodDaily = null;
			AYear extractYear = null;
			AverageMonth extractAverMonth = null;
			int extractionRange = 0;

			for (ExtractionRangeBase extractBase : domain.getExtractPeriodList()) {

				if (extractBase instanceof ExtractionPeriodDaily) {
					extractionPeriodDaily = (ExtractionPeriodDaily) extractBase;


				} else if (extractBase instanceof ExtractionPeriodMonth) {
					listMonth.add((ExtractionPeriodMonth) extractBase);
				} else if (extractBase instanceof AYear) {
					extractYear = (AYear) extractBase;
				} else if (extractBase instanceof AverageMonth) {
					extractAverMonth = (AverageMonth) extractBase;
				}
			}
			if (domain.getAlarmCategory().value == 2) {
				extractionRange = 3;
			} else if (domain.getAlarmCategory().value == 12) {
				extractionRange = 4;
			} else {
				extractionRange = 0;
			}
			return new KfnmtCheckCondition(
					kfnmtCheckConditionPK,
					extractYear.getExtractionId(), extractionRange,
					domain.getCheckConditionList().stream().map(x -> new KfnmtCheckConItem(buildCheckConItemPK(domain, x, companyId, alarmPatternCode))).collect(Collectors.toList()),
					KfnmtExtractionPeriodDaily.createFromDomain(extractionPeriodDaily),
					listMonth.stream().map(e -> KfnmtExtractPeriodMonth.toEntity(companyId, alarmPatternCode,
							domain.getAlarmCategory().value, e)).collect(Collectors.toList()),
					KfnmtExtractRangeYear.toEntity(extractYear),
					// Convert to Entity of 複数月平均基準月
					KfnmtAlstPtnDeftmbsmon.toEntity(extractAverMonth));

		}
		return new KfnmtCheckCondition(
				kfnmtCheckConditionPK, "", 0,
				domain.getCheckConditionList().stream().map(
						x -> new KfnmtCheckConItem(buildCheckConItemPK(domain, x, companyId, alarmPatternCode)))
						.collect(Collectors.toList()));
	}

	public void fromEntity(KfnmtCheckCondition entity) {

		if (entity.pk.alarmCategory == AlarmCategory.DAILY.value || entity.pk.alarmCategory == AlarmCategory.MAN_HOUR_CHECK.value) {

			if (this.extractionPeriodDaily != null)
				this.extractionPeriodDaily.cloneFromOtherEntity(entity.extractionPeriodDaily);

		} else if (entity.pk.alarmCategory == AlarmCategory.MONTHLY.value ||
				entity.pk.alarmCategory == AlarmCategory.MULTIPLE_MONTH.value) {

			this.listExtractPerMonth = new ArrayList<KfnmtExtractPeriodMonth>();
			entity.listExtractPerMonth.forEach(item -> {
				this.listExtractPerMonth.add(item);
			});
		} else if (entity.pk.alarmCategory == AlarmCategory.SCHEDULE_4WEEK.value) {

			if (this.extractionPerUnit != null)
				this.extractionPerUnit.fromEntity(entity.extractionPerUnit);

		} else if (entity.pk.alarmCategory == AlarmCategory.AGREEMENT.value) {

			if (this.extractionPeriodDaily != null)
				this.extractionPeriodDaily.cloneFromOtherEntity(entity.extractionPeriodDaily);

			this.listExtractPerMonth = new ArrayList<KfnmtExtractPeriodMonth>();
			entity.listExtractPerMonth.forEach(item -> {
				this.listExtractPerMonth.add(item);
			});

			if (this.extractRangeYear != null)
				this.extractRangeYear.fromEntity(entity.extractRangeYear);

			// Convert from Entity of 複数月平均基準月
			if (this.alstPtnDeftmbsmon != null)
				this.alstPtnDeftmbsmon.fromEntity(entity.alstPtnDeftmbsmon);
		}


		this.extractionRange = entity.extractionRange;
		this.checkConItems.removeIf(item -> !entity.checkConItems.contains(item));
		entity.checkConItems.forEach(item -> {
			if (!this.checkConItems.contains(item)) this.checkConItems.add(item);
		});
	}

	private static KfnmtCheckConItemPK buildCheckConItemPK(CheckCondition domain, String checkConditionCD, String companyId, String alarmPatternCode) {
		return new KfnmtCheckConItemPK(companyId, alarmPatternCode, domain.getAlarmCategory().value, checkConditionCD);
	}


}
