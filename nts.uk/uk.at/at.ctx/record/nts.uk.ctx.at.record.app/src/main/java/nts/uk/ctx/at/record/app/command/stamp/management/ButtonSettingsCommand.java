package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.stamp.management.AudioType;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonDisSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonName;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonNameSet;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonSettings;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonType;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeCalArt;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.stamp.management.SetPreClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Data
@AllArgsConstructor
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
		Optional<ButtonName> buttonNames = Optional
				.of(new ButtonName(x.getButtonDisSet().getButtonNameSet().getButtonName().get()));
		ButtonNameSet buttonNameSet = new ButtonNameSet(textColors, buttonNames);

		ColorCode backGroundColors = new ColorCode(x.getButtonDisSet().getBackGroundColor());
		ButtonDisSet buttonDisSets = new ButtonDisSet(buttonNameSet, backGroundColors);

		ReservationArt reservationArt = EnumAdaptor.valueOf(x.getButtonType().getReservationArt(), ReservationArt.class);
		Optional<GoingOutReason> goOutArt = Optional
				.of(EnumAdaptor.valueOf(x.getButtonType().getStampType().getGoOutArt().get(), GoingOutReason.class));
		SetPreClockArt setPreClockArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getSetPreClockArt(),
				SetPreClockArt.class);
		ChangeClockArt changeClockArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getChangeClockArt(),
				ChangeClockArt.class);
		ChangeCalArt changeCalArt = EnumAdaptor.valueOf(x.getButtonType().getStampType().getChangeCalArt(),
				ChangeCalArt.class);
		StampType stampType = new StampType(x.getButtonType().getStampType().isChangeHalfDay(), goOutArt, setPreClockArt,
				changeClockArt, changeCalArt);
		
		ButtonType buttonType = new ButtonType(reservationArt, stampType);

		NotUseAtr usrArts = EnumAdaptor.valueOf(x.getUsrArt(), NotUseAtr.class);

		AudioType audioTypes = EnumAdaptor.valueOf(x.getAudioType(), AudioType.class);

		return new ButtonSettings(buttonPositionNos, buttonDisSets, buttonType, usrArts, audioTypes);
	}
}
