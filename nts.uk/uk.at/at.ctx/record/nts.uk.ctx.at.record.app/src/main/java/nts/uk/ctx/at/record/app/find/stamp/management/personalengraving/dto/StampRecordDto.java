package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;

/**
 * @author anhdt
 *
 */
@Data
public class StampRecordDto {
	
	private  String stampNumber;

	private  String stampDateTime;

	private  boolean stampArt;

	private  Integer revervationAtr;
	
	private  Integer empInfoTerCode;
	
	public StampRecordDto(StampRecord stampRecord) {
		this.stampNumber = stampRecord.getStampNumber().v();
		this.stampDateTime = stampRecord.getStampDateTime().toString();
		this.stampArt = stampRecord.isStampArt();
		this.revervationAtr = stampRecord.getRevervationAtr().value;
		this.empInfoTerCode = stampRecord.getEmpInfoTerCode().isPresent() ? stampRecord.getEmpInfoTerCode().get().v() : null;
	}

}
