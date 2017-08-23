package nts.uk.ctx.at.schedule.app.find.shift.total.times.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalSubjectsSetMemento;


/**
 * The Class TotalSubjectsDto.
 */
@Getter
@Setter
public class TotalSubjectsDto implements TotalSubjectsSetMemento{
	/** The work type code. */
	//勤務種類コード
	private String workTypeCode;
	
	/** The sift code. */
	//就業時間帯コード
	private int workTypeAtr;

	/**
	 * Instantiates a new summary list dto.
	 *
	 * @param workTypeCode the work type code
	 * @param workingCode the working code
	 */
	public TotalSubjectsDto(String workTypeCode, int workTypeAtr) {
		this.workTypeCode = workTypeCode;
		this.workTypeAtr = workTypeAtr;
	}
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.SummaryListSetMamento#setWorkTypeCode(nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode setWorkTypeCode) {
			this.workTypeCode = setWorkTypeCode.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.SummaryListSetMamento#setSiftCode(nts.uk.ctx.at.shared.dom.worktime.SiftCode)
	 */
	@Override
	public void setWorkTypeAtr(Integer setWorkTypeAtr) {
		this.workTypeAtr = setWorkTypeAtr;
	}
}
