package nts.uk.ctx.at.record.dom.monthlyaggrmethod;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.flex.AggrSettingMonthlyOfFlx;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfIrg;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular.LegalAggrSetOfReg;

/**
 * 職場月別実績集計設定
 * @author shuichu_ishida
 */
@Getter
public class AggrSettingMonthlyForWorkplace extends AggregateRoot {

	/** 会社ID */
	private final String companyId;
	/** 職場ID */
	private final String workplaceId;
	
	/** 通常勤務の法定内集計設定 */
	private LegalAggrSetOfReg regularWork;
	/** 変形労働時間勤務の法定内集計設定 */
	private LegalAggrSetOfIrg irregularWork;
	/** フレックス時間勤務の月の集計設定 */
	private AggrSettingMonthlyOfFlx flexWork;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 */
	public AggrSettingMonthlyForWorkplace(String companyId, String workplaceId){
		
		super();
		this.companyId = companyId;
		this.workplaceId = workplaceId;
		this.regularWork = new LegalAggrSetOfReg();
		this.irregularWork = new LegalAggrSetOfIrg();
		this.flexWork = new AggrSettingMonthlyOfFlx();
	}

	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param workplaceId 職場ID
	 * @param legalAggrSetOfReg 通常勤務の法定内集計設定
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 * @param aggrSettingMonthlyOfFlx フレックス時間勤務の月の集計設定
	 * @return 職場月別実績集計設定
	 */
	public static AggrSettingMonthlyForWorkplace of(
			String companyId, String workplaceId,
			LegalAggrSetOfReg legalAggrSetOfReg,
			LegalAggrSetOfIrg legalAggrSetOfIrg,
			AggrSettingMonthlyOfFlx aggrSettingMonthlyOfFlx){
		
		val domain = new AggrSettingMonthlyForWorkplace(companyId, workplaceId);
		domain.regularWork = legalAggrSetOfReg;
		domain.irregularWork = legalAggrSetOfIrg;
		domain.flexWork = aggrSettingMonthlyOfFlx;
		return domain;
	}
}
