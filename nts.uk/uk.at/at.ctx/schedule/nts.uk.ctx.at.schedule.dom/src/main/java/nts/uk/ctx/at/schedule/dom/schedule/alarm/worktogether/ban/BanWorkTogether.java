package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
/**
 * 同時出勤禁止
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤禁止
 * @author lan_lt
 *
 */
@AllArgsConstructor
@Getter
public class BanWorkTogether implements DomainAggregate{
	/** 対象組織 */
	private final TargetOrgIdenInfor targetOrg;
	
	/** コード */
	private final BanWorkTogetherCode code;
	
	/** 名称 */
	private BanWorkTogetherName name;
	
	/** 適用する時間帯 */
	private final ApplicableTimeZoneCls applicableTimeZoneCls;
	
	/** 禁止する社員の組み合わせ */
	private List<String> empBanWorkTogetherLst;
	
	/** 許容する人数 */
	private Integer upperLimit;
	
	/**
	 * 終日を指定して作成する
	 * @param targetOrg 対象組織
	 * @param code コード
	 * @param name 名称 
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @param upperLimit 許容する人数
	 * @return
	 */
	public static BanWorkTogether createBySpecifyingAllDay(TargetOrgIdenInfor targetOrg, 
			BanWorkTogetherCode code,
			BanWorkTogetherName name,
			List<String> empBanWorkTogetherLst,
			Integer upperLimit) {
		
		return create(targetOrg, code, 
				name, ApplicableTimeZoneCls.ALLDAY,
				empBanWorkTogetherLst, upperLimit);
	}
	

	/**
	 * 夜勤時間帯を指定して作成する
	 * @param targetOrg 対象組織
	 * @param code コード
	 * @param name 名称
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @param upperLimit 許容する人数
	 * @return
	 */
	public static BanWorkTogether createByNightShift(TargetOrgIdenInfor targetOrg, 
			BanWorkTogetherCode code,
			BanWorkTogetherName name,
			List<String> empBanWorkTogetherLst,
			Integer upperLimit) {
		
		return  create(targetOrg, code, 
				name, ApplicableTimeZoneCls.NIGHTSHIFT,
				empBanWorkTogetherLst, upperLimit);
	}
	
	private static BanWorkTogether create(TargetOrgIdenInfor targetOrg, 
			BanWorkTogetherCode code,
			BanWorkTogetherName name,
			ApplicableTimeZoneCls timeZoneCls,
			List<String> empBanWorkTogetherLst,
			Integer upperLimit) {
		
		if(empBanWorkTogetherLst.size() < 2) {
			throw new BusinessException("Msg_1875");
		}
		
		if(empBanWorkTogetherLst.size() <= upperLimit) {
			throw new BusinessException("Msg_1787");
		}
		
		return new BanWorkTogether(targetOrg, code, 
				name, timeZoneCls,
				empBanWorkTogetherLst, upperLimit);
	}
	
}
