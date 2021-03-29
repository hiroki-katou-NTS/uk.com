package nts.uk.cnv.dom.td.alteration.summary;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

/**
 * orutaサマリ
 * @author ai_muto
 *
 */
@Value
public class AlterationSummary {
	private String alterId;
	private GeneralDateTime time;
	private String tableId;
	private DevelopmentStatus state;
	private AlterationMetaData metaData;
	private String featureId;
}
