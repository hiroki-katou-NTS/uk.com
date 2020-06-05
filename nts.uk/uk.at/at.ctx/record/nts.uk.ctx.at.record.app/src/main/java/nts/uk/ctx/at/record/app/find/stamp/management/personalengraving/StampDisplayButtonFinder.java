package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampToSuppress;

/**
 * @author anhdt 
 * 
   *  打刻入力(個人)の打刻ボタンを抑制の表示をする
 */
@Stateless
public class StampDisplayButtonFinder {
	
	@Inject
	private StampSettingsEmbossFinder settingFinder;
	
	public StampToSuppress getStampDisplayButton(String employeeId) {
		return settingFinder.getStampToSuppress(employeeId);
	}
}
