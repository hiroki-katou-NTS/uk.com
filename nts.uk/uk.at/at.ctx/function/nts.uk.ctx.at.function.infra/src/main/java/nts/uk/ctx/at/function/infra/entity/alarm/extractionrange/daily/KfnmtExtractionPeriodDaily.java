package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.Month;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity 抽出期間（日単位）
 */
@Data
@Entity
@Table(name = "KFNMT_EXTRACT_PER_DAILY")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KfnmtExtractionPeriodDaily extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtExtractionPeriodDailyPK kfnmtExtractionPeriodDailyPK;

	/**
	 * Gets primary key of entity.
	 *
	 * @return the primary key
	 */
	@Override
	protected Object getKey() {
		return this.kfnmtExtractionPeriodDailyPK;
	}

	@Column(name = "STR_SPECIFY")
	public int strSpecify;

	@Column(name = "STR_PREVIOUS_DAY", nullable = true)
	public Integer strPreviousDay;

	@Column(name = "STR_MAKE_TO_DAY", nullable = true)
	public Integer strMakeToDay;

	@Column(name = "STR_DAY", nullable = true)
	public Integer strDay;

	@Column(name = "STR_PREVIOUS_MONTH", nullable = true)
	public Integer strPreviousMonth;

	@Column(name = "STR_CURRENT_MONTH", nullable = true)
	public Integer strCurrentMonth;

	@Column(name = "STR_MONTH", nullable = true)
	public Integer strMonth;

	@Column(name = "END_SPECIFY")
	public int endSpecify;

	@Column(name = "END_PREVIOUS_DAY", nullable = true)
	public Integer endPreviousDay;

	@Column(name = "END_MAKE_TO_DAY", nullable = true)
	public Integer endMakeToDay;

	@Column(name = "END_DAY", nullable = true)
	public Integer endDay;

	@Column(name = "END_PREVIOUS_MONTH", nullable = true)
	public Integer endPreviousMonth;

	@Column(name = "END_CURRENT_MONTH", nullable = true)
	public Integer endCurrentMonth;

	@Column(name = "END_MONTH", nullable = true)
	public Integer endMonth;

	@OneToOne(mappedBy = "extractionPeriodDaily", orphanRemoval = true)
	public KfnmtCheckCondition checkCondition;

	/**
	 * To domain.
	 *
	 * @return the <code>ExtractionPeriodDaily</code>
	 */
	public ExtractionPeriodDaily toDomain() {
		// StartDate
		StartDate startDate = new StartDate(this.strSpecify);
		StartSpecify startSpecify = EnumAdaptor.valueOf(this.strSpecify, StartSpecify.class);
		switch (startSpecify) {
			case DAYS:
				startDate.setStartDays(EnumAdaptor.valueOf(this.strPreviousDay, PreviousClassification.class),
									   this.strDay,
									   this.strMakeToDay != 0);
				break;
			case MONTH:
				startDate.setStartMonth(EnumAdaptor.valueOf(this.strPreviousMonth, PreviousClassification.class),
										this.strMonth,
										strCurrentMonth != 0);
				break;
		}

		// EndDate
		EndDate endDate = new EndDate(this.endSpecify);
		EndSpecify endSpecify = EnumAdaptor.valueOf(this.endSpecify, EndSpecify.class);
		switch (endSpecify) {
			case DAYS:
				endDate.setEndDay(EnumAdaptor.valueOf(this.endPreviousDay, PreviousClassification.class),
								  this.endDay,
								  this.endMakeToDay != 0);
				break;
			case MONTH:
				endDate.setEndMonth(EnumAdaptor.valueOf(this.endPreviousMonth, PreviousClassification.class),
									   this.endMonth,
									   this.endCurrentMonth != 0);
				break;
		}

		return new ExtractionPeriodDaily(this.kfnmtExtractionPeriodDailyPK.extractionId,
										 this.kfnmtExtractionPeriodDailyPK.extractionRange,
										 startDate,
										 endDate);
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain <code>ExtractionPeriodDaily</code>
	 * @return the entity <code>KfnmtExtractionPeriodDaily</code>
	 */
	public static KfnmtExtractionPeriodDaily createFromDomain(ExtractionPeriodDaily domain) {
		return new KfnmtExtractionPeriodDaily(domain.getExtractionId(),
											  domain.getExtractionRange().value,
											  domain.getStartDate(),
											  domain.getEndDate());
	}

	/**
	 * Instantiates a new entity <code>KfnmtExtractionPeriodDaily</code>.
	 *
	 * @param extractionId    the extraction id
	 * @param extractionRange the extraction range
	 * @param startDate       the start date
	 * @param endDate         the end date
	 */
	public KfnmtExtractionPeriodDaily(String extractionId, int extractionRange, StartDate startDate, EndDate endDate) {
		this.kfnmtExtractionPeriodDailyPK = new KfnmtExtractionPeriodDailyPK(extractionId, extractionRange);
		// set start date
		StartSpecify startSpecify = EnumAdaptor.valueOf(startDate.getStartSpecify().value, StartSpecify.class);
		this.strSpecify = startDate.getStartSpecify().value;
		switch (startSpecify) {
			case DAYS:
				this.strPreviousDay = startDate.getStartDays()
											   .map(days -> days.getDayPrevious().value)
											   .orElse(null);
				this.strMakeToDay = startDate.getStartDays()
											 .map(days -> days.isToday() ? 1 : 0)
											 .orElse(null);
				this.strDay = startDate.getStartDays()
									   .map(days -> days.getDay().v())
									   .orElse(null);
				break;
			case MONTH:
				this.strPreviousMonth = startDate.getStartMonth()
												 .map(month -> month.getMonthPrevious().value)
												 .orElse(null);
				this.strCurrentMonth = startDate.getStartMonth()
												.map(month -> month.isCurrentMonth() ? 1 : 0)
												.orElse(null);
				this.strMonth = startDate.getStartMonth()
										 .map(Month::getMonth)
										 .orElse(null);
				break;
		}

		// set end date
		EndSpecify endSpecify = EnumAdaptor.valueOf(endDate.getEndSpecify().value, EndSpecify.class);
		this.endSpecify = endDate.getEndSpecify().value;
		switch (endSpecify) {
			case DAYS:
				this.endPreviousDay = endDate.getEndDays()
											 .map(days -> days.getDayPrevious().value)
											 .orElse(null);
				this.endMakeToDay = endDate.getEndDays()
										   .map(days -> days.isToday() ? 1 : 0)
										   .orElse(null);
				this.endDay = endDate.getEndDays()
									 .map(days -> days.getDay().v())
									 .orElse(null);
				break;
			case MONTH:
				this.endPreviousMonth = endDate.getEndMonth()
											   .map(month -> month.getMonthPrevious().value)
											   .orElse(null);
				this.endCurrentMonth = endDate.getEndMonth()
											  .map(month -> month.isCurrentMonth() ? 1 : 0)
											  .orElse(null);
				this.endMonth = endDate.getEndMonth()
									   .map(Month::getMonth)
									   .orElse(null);
				break;
		}
	}

	/**
	 * Clone from other entity.
	 *
	 * @param newEntity the new entity
	 */
	public void cloneFromOtherEntity(KfnmtExtractionPeriodDaily newEntity) {
		this.strSpecify = newEntity.strSpecify;
		this.strPreviousDay = newEntity.strPreviousDay;
		this.strMakeToDay = newEntity.strMakeToDay;
		this.strDay = newEntity.strDay;
		this.strPreviousMonth = newEntity.strPreviousMonth;
		this.strCurrentMonth = newEntity.strCurrentMonth;
		this.strMonth = newEntity.strMonth;
		this.endSpecify = newEntity.endSpecify;
		this.endPreviousDay = newEntity.endPreviousDay;
		this.endMakeToDay = newEntity.endMakeToDay;
		this.endDay = newEntity.endDay;
		this.endPreviousMonth = newEntity.endPreviousMonth;
		this.endCurrentMonth = newEntity.endCurrentMonth;
		this.endMonth = newEntity.endMonth;
	}

}
