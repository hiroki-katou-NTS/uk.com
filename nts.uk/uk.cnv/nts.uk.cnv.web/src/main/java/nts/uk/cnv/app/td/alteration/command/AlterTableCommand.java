package nts.uk.cnv.app.td.alteration.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.app.td.schema.prospect.TableProspectDto;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;

@Data
@NoArgsConstructor
public class AlterTableCommand {
	
	String featureId;
	String userName;
	String comment;
	
	TableProspectDto tableDesign;
	
	public boolean isNewTable() {
		return tableDesign.getId() == null;
	}
	
	public AlterationMetaData getMetaData() {
		return new AlterationMetaData(userName, comment);
	}
}
