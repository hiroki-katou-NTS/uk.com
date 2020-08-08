package nts.uk.ctx.hr.shared.dom.personalinfo.personalinformation;

import java.util.List;

/**
 * 
 * @author chungnt
 *
 */

public interface PersonalInformationRepository {

	// 個人情報を追加する
	void insert(List<PersonalInformation> domains);
	
	// 個人情報を更新する
	void update(List<PersonalInformation> domains);
}
