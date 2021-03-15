package nts.uk.cnv.dom.td.alteration.summary;

import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;

/**
 * orutaサマリ
 * @author ai_muto
 *
 */
public class AltarationSummary {
	private String alterId;
	private GeneralDateTime time;
	private TableIdInfo tableIdInfo;
	private DevelopmentState state;
	private AlterationMetaData metaData;
	private String featureId;
}
