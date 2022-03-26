package nts.uk.ctx.at.record.app.command.stamp.management;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AssignmentMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.AudioType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SupportWplSet;
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

	/** 打刻種類 */
	private StampTypeCommand stampType;

	/** 使用区分 */
	private int usrArt;

	/** 音声使用方法 */
	private int audioType;
	
	/** 応援職場設定方法 */
	private Integer supportWplSet;
	
	/** 作業指定方法 */
	private Integer taskChoiceArt;
	
	public ButtonSettings toDomain() {
		return new ButtonSettings(
				new ButtonPositionNo(this.getButtonPositionNo()), 
				NotUseAtr.valueOf(this.usrArt), 
				this.buttonDisSet.toDomain(), 
				this.getStampType().toDomain(), 
				AudioType.valueOf(this.audioType),
				Optional.ofNullable(this.supportWplSet == null? null: SupportWplSet.valueOf(this.supportWplSet)),
				Optional.ofNullable(this.taskChoiceArt == null? null: AssignmentMethod.valueOf(this.taskChoiceArt))
			);
	}



	public ButtonSettingsCommand(int buttonPositionNo, ButtonDisSetCommand buttonDisSet, StampTypeCommand stampType,
			int usrArt, int audioType, Integer supportWplSet) {
		super();
		this.buttonPositionNo = buttonPositionNo;
		this.buttonDisSet = buttonDisSet;
		this.stampType = stampType;
		this.usrArt = usrArt;
		this.audioType = audioType;
		this.supportWplSet = supportWplSet;
	}
}
