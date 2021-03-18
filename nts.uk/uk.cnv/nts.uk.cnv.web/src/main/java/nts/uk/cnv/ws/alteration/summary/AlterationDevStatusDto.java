package nts.uk.cnv.ws.alteration.summary;

import lombok.Value;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;

/**
 * orutaの開発状況
 */
@Value
public class AlterationDevStatusDto {
	String id;
	String tableName;
	String comment;
	String designer;
	DevelopmentState status; 
}
