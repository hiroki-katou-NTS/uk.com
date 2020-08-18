package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo.GetPersonInfoHRInput;

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
	
	// 個人情報を取得する
	List<PersonalInformation> get(GetPersonInfoHRInput input);
	
	List<PersonalInformation> getDispatchedInfos(String contractCd, String cId, GeneralDate baseDate);
	
	List<PersonalInformation> getDispatchedInfoByStr10s(String contractCd, String cId, GeneralDate baseDate);
	
	List<PersonalInformation> getLstPersonInfoByCIdSIdsWorkId (String cId, List<String> sids, int workId, GeneralDate baseDate);
	
	List<PersonalInformation> getLstPersonInfoByQualificationIds(String cId, List<String> qualificationIds, int workId, GeneralDate baseDate);
}