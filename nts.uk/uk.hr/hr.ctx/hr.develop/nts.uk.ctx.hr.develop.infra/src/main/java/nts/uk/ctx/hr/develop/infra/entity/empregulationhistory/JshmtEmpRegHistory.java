package nts.uk.ctx.hr.develop.infra.entity.empregulationhistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "JSHMT_EMP_REG_HISTORY")
public class JshmtEmpRegHistory extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String cId;

	@Id
	@Column(name = "HIST_ID")
	public String historyId;

	@Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate startDate;
	
	@Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	public GeneralDate endDate;
	
	@Override
	public Object getKey() {
		return historyId;
	}

	public DateHistoryItem toDomain() {
		return new DateHistoryItem(this.historyId, new DatePeriod(this.startDate, this.endDate));
	}

	public JshmtEmpRegHistory(String cId, DateHistoryItem dateHistoryItem) {
		super();
		this.cId = cId;
		this.historyId = dateHistoryItem.identifier();
		this.startDate = dateHistoryItem.start();
		this.endDate = dateHistoryItem.end();
	}
	
}
