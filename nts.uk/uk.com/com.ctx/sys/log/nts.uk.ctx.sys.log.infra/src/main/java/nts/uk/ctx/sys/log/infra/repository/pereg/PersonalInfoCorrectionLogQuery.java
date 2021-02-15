package nts.uk.ctx.sys.log.infra.repository.pereg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.sys.log.infra.entity.pereg.SrcdtCtgCorrectionLog;
import nts.uk.ctx.sys.log.infra.entity.pereg.SrcdtDataHistoryLog;
import nts.uk.ctx.sys.log.infra.entity.pereg.SrcdtItemInfoLog;
import nts.uk.ctx.sys.log.infra.entity.pereg.SrcdtPerCorrectionLog;

@Getter
@Setter
@AllArgsConstructor
public class PersonalInfoCorrectionLogQuery {
	private String perCorrectionLogID;

	private SrcdtPerCorrectionLog srcdtPerCorrectionLog;

	private SrcdtCtgCorrectionLog srcdtCtgCorrectionLog;

	private SrcdtDataHistoryLog srcdtDataHistoryLog;

	private SrcdtItemInfoLog srcdtItemInfoLog;
}
