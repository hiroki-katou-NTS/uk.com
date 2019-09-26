package nts.uk.ctx.pereg.dom.person.setting.validatecheck;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 個人情報整合性チェックカテゴリ
 * @author yennth
 *
 */
@Getter
public class PerInfoValidateCheckCategory extends AggregateRoot{
	/**
	 * カテゴリコード
	 */
	private CategoryCode categoryCd;
	/**
	 * 契約コード
	 */
	private ContractCd contractCd;
	/**
	 * スケジュール管理必要
	 */
	private NotUseAtr scheduleMngReq;
	/**
	 * 年調管理必要
	 */
	private NotUseAtr yearMngReq;
	/**
	 * 日別実績管理必要
	 */
	private NotUseAtr dailyActualMngReq;
	/**
	 * 月別実績管理必要
	 */
	private NotUseAtr monthActualMngReq;
	/**
	 * 月額算定管理必要
	 */
	private NotUseAtr monthCalcMngReq;
	/**
	 * 給与管理管理必要
	 */
	private NotUseAtr payMngReq;
	/**
	 * 賞与管理必要
	 */
	private NotUseAtr bonusMngReq;
	/**
	 * 就業システム必須
	 */
	private NotUseAtr jobSysReq;
	/**
	 * 給与システム必須
	 */
	private NotUseAtr paySysReq;
	/**
	 * 人事システム必須
	 */
	private NotUseAtr humanSysReq;
	
	
	public static PerInfoValidateCheckCategory createFromJavaType(String categoryCd, String contractCd, int scheduleMngReq, int yearMngReq,
			int dailyActualMngReq, int monthActualMngReq, int monthCalcMngReq, int payMngReq,
			int bonusMngReq, int jobSysReq, int paySysReq, int humanSysReq) {

		return new PerInfoValidateCheckCategory(new CategoryCode(categoryCd),
				new ContractCd(contractCd),
				EnumAdaptor.valueOf(scheduleMngReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(yearMngReq, NotUseAtr.class),
				EnumAdaptor.valueOf(dailyActualMngReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(monthActualMngReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(monthCalcMngReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(payMngReq, NotUseAtr.class),
				EnumAdaptor.valueOf(bonusMngReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(jobSysReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(paySysReq, NotUseAtr.class), 
				EnumAdaptor.valueOf(humanSysReq, NotUseAtr.class));

	}


	public PerInfoValidateCheckCategory(CategoryCode categoryCd, ContractCd contractCd, NotUseAtr scheduleMngReq,
			NotUseAtr yearMngReq, NotUseAtr dailyActualMngReq, NotUseAtr monthActualMngReq, NotUseAtr monthCalcMngReq,
			NotUseAtr payMngReq, NotUseAtr bonusMngReq, NotUseAtr jobSysReq, NotUseAtr paySysReq,
			NotUseAtr humanSysReq) {
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
