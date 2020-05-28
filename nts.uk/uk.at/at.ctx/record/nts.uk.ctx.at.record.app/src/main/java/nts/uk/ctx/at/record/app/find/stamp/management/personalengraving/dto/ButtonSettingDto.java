package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;

/**
 * @author anhdt
 *
 */
@Data
public class ButtonSettingDto {
	private Integer btnPositionNo;
	private String btnName;
	private String btnTextColor;
	private String btnBackGroundColor;
	
	private Integer btnReservationArt;
	private boolean changeHalfDay;
	private Integer goOutArt;
	private Integer setPreClockArt;
	private Integer changeClockArt;
	private Integer changeCalArt;
	
	private Integer usrArt;
	private Integer audioType;
	
	public ButtonSettingDto(ButtonSettings btnSet) {
		this.btnPositionNo = btnSet.getButtonPositionNo().v();
		
		ButtonDisSet btnDisSet = btnSet.getButtonDisSet();
		ButtonNameSet btnNameSet = btnDisSet.getButtonNameSet();
		this.btnName = btnNameSet.getButtonName().isPresent() ? btnNameSet.getButtonName().get().v() : null;
		this.btnTextColor = btnNameSet.getTextColor().v();
		this.btnBackGroundColor = btnDisSet.getBackGroundColor().v();
		
		ButtonType btnType = btnSet.getButtonType();
		this.btnReservationArt = btnType.getReservationArt().value;
		
		Optional<StampType> oStampType = btnType.getStampType();
		if(oStampType.isPresent()) {
			StampType stampType = oStampType.get();
			this.changeHalfDay = stampType.getChangeHalfDay() == null ? null : stampType.getChangeHalfDay();
			this.goOutArt = stampType.getGoOutArt().isPresent() ? stampType.getGoOutArt().get().value : null;
			this.setPreClockArt = stampType.getSetPreClockArt() == null ? null : stampType.getSetPreClockArt().value;
			this.changeClockArt = stampType.getChangeClockArt() == null ? null : stampType.getChangeClockArt().value;
			this.changeCalArt = stampType.getChangeCalArt() == null ? null : stampType.getChangeCalArt().value;
		}
		
		this.usrArt = btnSet.getUsrArt().value;
		this.audioType = btnSet.getAudioType().value;
		
	}
}
