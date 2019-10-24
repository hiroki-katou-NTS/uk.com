package nts.uk.ctx.pereg.app.find.person.setting.checkdata;

import lombok.Value;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;

@Value
public class PerInfoValidChkCtgDto {
	/**
	 * カテゴリコード
	 */
	private String categoryCd;
	/**
	 * 契約コード
	 */
	private String contractCd;
	/**
	 * スケジュール管理必要
	 */
	private int scheduleMngReq;
	/**
	 * 年調管理必要
	 */
	private int yearMngReq;
	/**
	 * 日別実績管理必要
	 */
	private int dailyActualMngReq;
	/**
	 * 月別実績管理必要
	 */
	private int monthActualMngReq;
	/**
	 * 月額算定管理必要
	 */
	private int monthCalcMngReq;
	/**
	 * 給与管理管理必要
	 */
	private int payMngReq;
	/**
	 * 賞与管理必要
	 */
	private int bonusMngReq;
	/**
	 * 就業システム必須
	 */
	private int jobSysReq;/**
	 * 給与システム必須
	 */
	private int paySysReq;/**
	 * 人事システム必須
	 */
	private int humanSysReq;
	
	
	/**
	 * convert from domain to dto
	 * @param domain
	 * @return
	 * @author yennth
	 */
	public static PerInfoValidChkCtgDto fromDomain(PerInfoValidateCheckCategory domain){
		return new PerInfoValidChkCtgDto(domain.getCategoryCd().v(), domain.getContractCd().v(), 
				domain.getScheduleMngReq().value, domain.getYearMngReq().value, 
				domain.getDailyActualMngReq().value, domain.getMonthActualMngReq().value, 
				domain.getMonthCalcMngReq().value, domain.getPayMngReq().value, 
				domain.getBonusMngReq().value, domain.getJobSysReq().value, 
				domain.getPaySysReq().value, domain.getHumanSysReq().value);
	}


	public PerInfoValidChkCtgDto(String categoryCd, String contractCd, int scheduleMngReq, int yearMngReq,
			int dailyActualMngReq, int monthActualMngReq, int monthCalcMngReq, int payMngReq, int bonusMngReq,
			int jobSysReq, int paySysReq, int humanSysReq) {
		super();
		this.categoryCd = categoryCd;
		this.contractCd = contractCd;
		this.scheduleMngReq = scheduleMngReq;
		this.yearMngReq = yearMngReq;
		this.dailyActualMngReq = dailyActualMngReq;
		this.monthActualMngReq = monthActualMngReq;
		this.monthCalcMngReq = monthCalcMngReq;
		this.payMngReq = payMngReq;
		this.bonusMngReq = bonusMngReq;
		this.jobSysReq = jobSysReq;
		this.paySysReq = paySysReq;
		this.humanSysReq = humanSysReq;
	}
}
