package nts.uk.ctx.at.record.app.command.stamp.management;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AudioType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * ボタン詳細設定
 * @author phongtq
 *
 */
@Data
@NoArgsConstructor
public class ButtonSettingsCommand {
	/** ボタン位置NO */
	private int buttonPositionNo;

	/** ボタンの表示設定 */
	private ButtonDisSetCommand buttonDisSet;

	/** ボタン種類 */
	private ButtonTypeCommand buttonType;

	/** 使用区分 */
	private int usrArt;

	/** 音声使用方法 */
	private int audioType;
	
	

	public static ButtonSettings toDomain(ButtonSettingsCommand x) {
		ButtonPositionNo buttonPositionNos = new ButtonPositionNo(x.getButtonPositionNo());

		ColorCode textColors = new ColorCode(x.getButtonDisSet().getButtonNameSet().getTextColor());
		ButtonName buttonNames = new ButtonName(x.getButtonDisSet().getButtonNameSet().getButtonName());
		ButtonNameSet buttonNameSet = new ButtonNameSet(textColors, buttonNames);

		ColorCode backGroundColors = new ColorCode(x.getButtonDisSet().getBackGroundColor());
		ButtonDisSet buttonDisSets = new ButtonDisSet(buttonNameSet, backGroundColors);

		ReservationArt reservationArt = EnumAdaptor.valueOf(x.getButtonType().getReservationArt(), ReservationArt.class);
		GoingOutReason goOutArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getGoOutArt(), GoingOutReason.class);
		SetPreClockArt setPreClockArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getSetPreClockArt(),
				SetPreClockArt.class);
		ChangeClockArt changeClockArt = x.getButtonType().getStampType().getChangeClockArt() != null ? EnumAdaptor.valueOf(x.getButtonType().getStampType().getChangeClockArt(),
				ChangeClockArt.class) : null;
		ChangeCalArt changeCalArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getChangeCalArt(),
				ChangeCalArt.class);
		StampType stampType = new StampType(x.getButtonType().getStampType().isChangeHalfDay(), goOutArt, setPreClockArt,
				changeClockArt, changeCalArt);
		
		ButtonType buttonType = new ButtonType(reservationArt, stampType);

		NotUseAtr usrArts = EnumAdaptor.valueOf(x.getUsrArt(), NotUseAtr.class);

		AudioType audioTypes = EnumAdaptor.valueOf(x.getAudioType(), AudioType.class);

		return new ButtonSettings(buttonPositionNos, buttonDisSets, buttonType, usrArts, audioTypes);
	}



	public ButtonSettingsCommand(int buttonPositionNo, ButtonDisSetCommand buttonDisSet, ButtonTypeCommand buttonType,
			int usrArt, int audioType) {
		super();
		this.buttonPositionNo = buttonPositionNo;
		this.buttonDisSet = buttonDisSet;
		this.buttonType = buttonType;
		this.usrArt = usrArt;
		this.audioType = audioType;
	}
}
