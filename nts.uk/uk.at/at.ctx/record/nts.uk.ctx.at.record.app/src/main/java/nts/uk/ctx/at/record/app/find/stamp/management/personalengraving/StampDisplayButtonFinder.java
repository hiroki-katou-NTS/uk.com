package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampToSuppressDto;

/**
 * @author anhdt 
 * 
 *  打刻入力(個人)の打刻ボタンを抑制の表示をする
 */
public class StampDisplayButtonFinder {
	
	@Inject
	private StampSettingsEmbossFinder settingFinder;
	
	public StampToSuppressDto getStampDisplayButton(String employeeId) {
		return new StampToSuppressDto(settingFinder.getStampToSuppress(employeeId));
	}
}
