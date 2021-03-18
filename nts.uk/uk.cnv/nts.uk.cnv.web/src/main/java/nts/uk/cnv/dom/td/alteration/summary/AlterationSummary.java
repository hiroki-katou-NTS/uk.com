package nts.uk.cnv.dom.td.alteration.summary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.schema.TableIdentity;

/**
 * orutaサマリ
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class AlterationSummary {
	private String alterId;
	private GeneralDateTime time;
	private TableIdentity tableIdentity;
	private DevelopmentState state;
	private AlterationMetaData metaData;
	private String featureId;
}
