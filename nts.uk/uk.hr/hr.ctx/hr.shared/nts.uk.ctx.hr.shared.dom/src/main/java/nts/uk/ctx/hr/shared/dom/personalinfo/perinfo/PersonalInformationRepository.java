package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo;

/**
 * 
 * @author chungnt
 *
 */

public interface PersonalInformationRepository {

	// 個人情報を追加する
	void insert(PersonalInformation domain);
	
	// 個人情報を更新する
	void update(PersonalInformation domain);
	
	// 個人情報を削除する
	void delete(String hisId);
}
