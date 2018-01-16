package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;

/**
 * 月別実績集計設定
 * @author shuichu_ishida
 */
@Getter
public class AggrSettingMonthly {

	/** 通常勤務の法定内集計設定 */
	private LegalAggrSetOfReg regularWork;
	/** 変形労働時間勤務の法定内集計設定 */
	private LegalAggrSetOfIrg irregularWork;
	/** フレックス時間勤務の月の集計設定 */
	private AggrSettingMonthlyOfFlx flexWork;

	/**
	 * コンストラクタ
	 */
	public AggrSettingMonthly(){
		this.regularWork = new LegalAggrSetOfReg();
		this.irregularWork = new LegalAggrSetOfIrg();
		this.flexWork = new AggrSettingMonthlyOfFlx();
	}
	
	/**
	 * ファクトリー
	 * @param legalAggrSetOfReg 通常勤務の法定内集計設定
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 * @param aggrSettingMonthlyOfFlx フレックス時間勤務の月の集計設定
	 * @return 月別実績集計設定
	 */
	public static AggrSettingMonthly of(LegalAggrSetOfReg legalAggrSetOfReg,
			LegalAggrSetOfIrg legalAggrSetOfIrg, AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx){
		
		val domain = new AggrSettingMonthly();
		domain.regularWork = legalAggrSetOfReg;
		domain.irregularWork = legalAggrSetOfIrg;
		domain.flexWork = aggrSettingMonthlyOfFlx;
		return domain;
	}
}
