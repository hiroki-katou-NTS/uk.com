package nts.uk.ctx.at.function.infra.entity.alarmworkplace.condition;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.infra.entity.alarm.KfnmtAlarmPatternSet;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckConItem;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtCheckConditionPK;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.daily.KfnmtExtractionPeriodDaily;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly.KfnmtExtractPeriodMonth;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.mutilmonth.KfnmtAlstPtnDeftmbsmon;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.periodunit.KfnmtExtractionPerUnit;
import nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.yearly.KfnmtExtractRangeYear;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.alarmpatternworkplace.KfnmtALstWkpPtn;
import nts.uk.ctx.at.function.infra.entity.alarmworkplace.singlemonth.KfnmtAssignNumofMon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNMT_WKPCHECK_CONDITION")
public class KfnmtWkpCheckCondition extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfnmtWkpCheckConditionPK pk;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@ManyToOne
	@JoinColumns(
		{ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "ALARM_PATTERN_CD", referencedColumnName = "ALARM_PATTERN_CD", insertable = false, updatable = false) })
	public KfnmtALstWkpPtn kfnmtALstWkpPtn;

	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_PTN_MAP_CAT")
	public List<KfnmtPtnMapCat> checkConItems;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="ALARM_PATTERN_CD", referencedColumnName="ALARM_PATTERN_CD", insertable = false, updatable = false),
		@JoinColumn(name="CATEGORY", referencedColumnName="CATEGORY", insertable = false, updatable = false) })
	public KfnmtAssignNumofMon kfnmtAssignNumofMon;


	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtExtractionPerUnit extractionPerUnit;


	@OneToMany(mappedBy = "checkCondition", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KFNMT_EXTRACT_PER_MONTH")
	public List<KfnmtExtractPeriodMonth> listExtractPerMonth;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtExtractRangeYear extractRangeYear;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumns({
		@JoinColumn(name="EXTRACTION_ID", referencedColumnName="EXTRACTION_ID", insertable=false, updatable=false),
		@JoinColumn(name="EXTRACTION_RANGE", referencedColumnName="EXTRACTION_RANGE", insertable=false, updatable=false)
	})
	public KfnmtAlstPtnDeftmbsmon alstPtnDeftmbsmon ;

	
}
