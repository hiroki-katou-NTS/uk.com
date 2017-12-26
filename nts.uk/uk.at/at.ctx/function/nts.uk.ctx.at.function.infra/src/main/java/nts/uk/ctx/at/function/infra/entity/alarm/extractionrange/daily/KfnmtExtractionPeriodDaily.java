package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_EXTRACT_PER_DAILY")
@NoArgsConstructor

public class KfnmtExtractionPeriodDaily extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtExtractionPeriodDailyPK kfnmtExtractionPeriodDailyPK;

	@Override
	protected Object getKey() {
		return kfnmtExtractionPeriodDailyPK;
	}

	@Column(name = "STR_SPECIFY")
	public int strSpecify;

	@Column(name = "STR_PREVIOUS_DAY")
	public int strPreviousDay;

	@Column(name = "STR_MAKE_TO_DAY")
	public int strMakeToDay;

	@Column(name = "STR_DAY")
	public int strDay;

	@Column(name = "STR_PREVIOUS_MONTH")
	public int strPreviousMonth;

	@Column(name = "STR_CURRENT_MONTH")
	public int strCurrentMonth;

	@Column(name = "STR_MONTH")
	public int strMonth;

	@Column(name = "END_SPECIFY")
	public int endSpecify;

	@Column(name = "END_PREVIOUS_DAY")
	public int endPreviousDay;

	@Column(name = "END_MAKE_TO_DAY")
	public int endMakeToDay;

	@Column(name = "END_DAY")
	public int endDay;

	@Column(name = "END_PREVIOUS_MONTH")
	public int endPreviousMonth;

	@Column(name = "END_CURRENT_MONTH")
	public int endCurrentMonth;

	@Column(name = "END_MONTH")
	public int endMonth;

	@OneToOne(mappedBy = "extractionPeriodDaily")
	public KfnmtCheckCondition checkCondition;

	public ExtractionPeriodDaily toDomain() {
		// StartDate
		StartDate startDate = new StartDate(this.strSpecify);
		StartSpecify prevClass = EnumAdaptor.valueOf(strSpecify, StartSpecify.class);
		if (prevClass == StartSpecify.DAYS) {
			startDate.setStartDay(EnumAdaptor.valueOf(strPreviousDay, PreviousClassification.class), strDay, strMakeToDay == 0 ? false : true);
		} else if (prevClass == StartSpecify.MONTH) {
			startDate.setStartMonth(EnumAdaptor.valueOf(strPreviousMonth, PreviousClassification.class), strMonth, strCurrentMonth == 0 ? false : true);
		}
		
		// EndDate
		EndDate endDate = new EndDate(this.endSpecify, this.endPreviousMonth, this.endMonth,
				this.endCurrentMonth == 1 ? true : false, this.endPreviousDay, this.endDay,
				this.endMakeToDay == 1 ? true : false);
		
		
		ExtractionPeriodDaily periodDaily = new ExtractionPeriodDaily(this.kfnmtExtractionPeriodDailyPK.extractionId,
				this.kfnmtExtractionPeriodDailyPK.extractionRange, startDate, endDate);
		return periodDaily;
	}

	public static KfnmtExtractionPeriodDaily toEntity(ExtractionPeriodDaily domain) {
		

		return null;
	}

	public KfnmtExtractionPeriodDaily(String extractionId, int extractionRange, int strSpecify, int strPreviousDay,
			int strMakeToDay, int strDay, int strPreviousMonth, int strCurrentMonth, int strMonth, int endSpecify,
			int endPreviousDay, int endMakeToDay, int endDay, int endPreviousMonth, int endCurrentMonth, int endMonth) {
		super();
		this.kfnmtExtractionPeriodDailyPK = new KfnmtExtractionPeriodDailyPK(extractionId, extractionRange);
		this.strSpecify = strSpecify;
		this.strPreviousDay = strPreviousDay;
		this.strMakeToDay = strMakeToDay;
		this.strDay = strDay;
		this.strPreviousMonth = strPreviousMonth;
		this.strCurrentMonth = strCurrentMonth;
		this.strMonth = strMonth;
		this.endSpecify = endSpecify;
		this.endPreviousDay = endPreviousDay;
		this.endMakeToDay = endMakeToDay;
		this.endDay = endDay;
		this.endPreviousMonth = endPreviousMonth;
		this.endCurrentMonth = endCurrentMonth;
		this.endMonth = endMonth;
	}

}
