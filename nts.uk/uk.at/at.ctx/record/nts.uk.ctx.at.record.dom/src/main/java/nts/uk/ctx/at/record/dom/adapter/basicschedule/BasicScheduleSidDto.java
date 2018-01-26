package nts.uk.ctx.at.record.dom.adapter.basicschedule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class BasicScheduleSidDto {

	private String sId;

	private GeneralDate date;

	/** The work type code. */
	private String workTypeCode;

	/** The work time code. */
	private String workTimeCode;

	private List<WorkScheduleSidImport> workScheduleSidImports;

	public BasicScheduleSidDto(String sId, GeneralDate date, String workTypeCode, String workTimeCode,
			List<WorkScheduleSidImport> workScheduleSidImports) {
		super();
		this.sId = sId;
		this.date = date;
		this.workTypeCode = workTypeCode;
		this.workTimeCode = workTimeCode;
		this.workScheduleSidImports = workScheduleSidImports;
	}
}
