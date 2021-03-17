package nts.uk.cnv.app.td.schema.prospect;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nts.uk.cnv.app.td.schema.tabledesign.TableDesignDto;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TableProspectDto extends TableDesignDto {

	String lastAlterId;
	
	public TableProspectDto(TableProspect d) {
		super(d);
		lastAlterId = d.getLastAlterId();
	}
	
	public TableProspect toDomain() {
		return new TableProspect(lastAlterId, super.toDomain());
	}
}
