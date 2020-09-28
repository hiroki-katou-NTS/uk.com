package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

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
	
	/** 同時出勤禁止コード */
	private final BanWorkTogetherCode banWorkTogetherCode;
	
	/** 同時出勤禁止名称 */
	private BanWorkTogetherName banWorkTogetherName;
	
	/** 適用する時間帯 */
	private final ApplicableTimeZoneCls applicableTimeZoneCls;
	
	/** 許容する人数 */
	private MaxOfNumberEmployeeTogether upperLimit;
	
	/** 禁止する社員の組み合わせ */
	private List<String> empBanWorkTogetherLst;
	
	/**
	 * 終日を指定して作成する
	 * @param targetOrg 対象組織
	 * @param banWorkTogetherCode 同時出勤禁止コード
	 * @param banWorkTogetherName 同時出勤禁止名称 
	 * @param upperLimit 許容する人数
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @return
	 */
	public static BanWorkTogether createBySpecifyingAllDay(TargetOrgIdenInfor targetOrg, 
			BanWorkTogetherCode banWorkTogetherCode,
			BanWorkTogetherName banWorkTogetherName,
			MaxOfNumberEmployeeTogether upperLimit,
			List<String> empBanWorkTogetherLst
			) {
		
		if(empBanWorkTogetherLst.isEmpty() || empBanWorkTogetherLst.size() <= 1) {
			throw new BusinessException("Msg_1875");
		}
		
		if(empBanWorkTogetherLst.size() < upperLimit.v()) {
			throw new BusinessException("Msg_1787");
		}
		
		return new BanWorkTogether(targetOrg, banWorkTogetherCode, 
				banWorkTogetherName, ApplicableTimeZoneCls.ALLDAY,
				upperLimit, empBanWorkTogetherLst);
	}
	

	/**
	 * 夜勤時間帯を指定して作成する
	 * @param targetOrg 対象組織
	 * @param banWorkTogetherCode 同時出勤禁止コード
	 * @param banWorkTogetherName 同時出勤禁止名称 
	 * @param upperLimit 許容する人数
	 * @param empBanWorkTogetherLst 禁止する社員の組み合わせ
	 * @return
	 */
	public static BanWorkTogether createByNightShift(TargetOrgIdenInfor targetOrg, 
			BanWorkTogetherCode banWorkTogetherCode,
			BanWorkTogetherName banWorkTogetherName,
			MaxOfNumberEmployeeTogether upperLimit,
			List<String> empBanWorkTogetherLst) {
		
		if(empBanWorkTogetherLst.isEmpty() || empBanWorkTogetherLst.size() <= 1) {
			throw new BusinessException("Msg_1875");
		}
		
		if(empBanWorkTogetherLst.size() < upperLimit.v()) {
			throw new BusinessException("Msg_1787");
		}
		
		return new BanWorkTogether(targetOrg, banWorkTogetherCode, 
				banWorkTogetherName, ApplicableTimeZoneCls.NIGHTSHIFT,
				upperLimit,	empBanWorkTogetherLst);
	}
	
}
