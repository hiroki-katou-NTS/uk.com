package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import lombok.Data;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;

/**
 * @author anhdt
 *
 */
@Data
public class StampRecordDto {

	private String stampNumber;

	private String stampDate;
	
	private String stampTime;
	
	private String stampHow;

	private boolean stampArt;

	private Integer revervationAtr;

	private Integer empInfoTerCode;
	
	private String timeStampType;

	public StampRecordDto(StampRecord stampRecord, Stamp stamp) {
		this.stampNumber = stampRecord.getStampNumber().v();
		this.stampDate = stampRecord.getStampDateTime().toString("yyyy/MM/dd");
		this.stampTime =stampRecord.getStampDateTime().toString("HH:mm");
		this.stampHow =  getCorrectTimeString(stamp != null ? stamp.getRelieve().getStampMeans() : null);
		this.stampArt = stampRecord.isStampArt();
		this.revervationAtr = stampRecord.getRevervationAtr().value;
		this.empInfoTerCode = stampRecord.getEmpInfoTerCode().isPresent() ? stampRecord.getEmpInfoTerCode().get().v()
				: null;
	}

	public String getCorrectTimeString(StampMeans mean) {
				
		if(mean == null) {
			return " ";
		}
		
		switch (mean) {
			case NAME_SELECTION:
				return I18NText.getText("KDP002_120");
			case FINGER_AUTHC:
				return I18NText.getText("KDP002_120");
			case IC_CARD:
				return I18NText.getText("KDP002_120");
			case INDIVITION:
				return I18NText.getText("KDP002_120");
			case PORTAL:
				return I18NText.getText("KDP002_120");
			case SMART_PHONE:
				return I18NText.getText("KDP002_121");
			case TIME_CLOCK:
				return I18NText.getText("KDP002_122");
			case TEXT:
				return I18NText.getText("KDP002_120");
			case RICOH_COPIER:
				return I18NText.getText("KDP002_120");
			default:
				break;
		}

		return " ";
	}

}
