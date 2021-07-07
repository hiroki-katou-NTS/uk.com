/**
 * 
 */
package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.MessageTitle;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings.NoticeSet;

@AllArgsConstructor
@Getter
public class NoticeSetDto {
	
	// 会社メッセージ色
	private ColorSettingDto companyColor;
	
	// 会社宛タイトル
	private String companyTitle;
	
	// 個人メッセージ色
	private ColorSettingDto personalColor;
	
	// 職場メッセージ色
	private ColorSettingDto workplaceColor;
	
	// 職場宛タイトル
	private String workplaceTitle;
	
	// 表示区分
	private int displayAtr;

	public NoticeSetDto(NoticeSet domain) {
		this.companyColor = new ColorSettingDto(domain.getCompanyColor());
		this.companyTitle = domain.getCompanyTitle().v();
		this.personalColor = new ColorSettingDto(domain.getPersonalColor());
		this.workplaceColor = new ColorSettingDto(domain.getWorkplaceColor());
		this.workplaceTitle = domain.getWorkplaceTitle().v();
		this.displayAtr = domain.getDisplayAtr().value;
	}
	
	public NoticeSet toDomain() {
		return new NoticeSet(
				this.companyColor.toDomain(),
				new MessageTitle(this.companyTitle), 
				this.personalColor.toDomain(), 
				this.workplaceColor.toDomain(), 
				new MessageTitle(this.workplaceTitle), 
				DoWork.valueOf(this.displayAtr));
	}
	
}
