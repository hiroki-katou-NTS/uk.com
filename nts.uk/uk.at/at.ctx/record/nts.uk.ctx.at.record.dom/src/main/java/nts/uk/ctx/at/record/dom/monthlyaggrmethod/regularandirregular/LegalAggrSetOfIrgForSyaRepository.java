package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：社員の変形労働時間勤務の法定内集計設定
 * @author shuichu_ishida
 */
public interface LegalAggrSetOfIrgForSyaRepository {
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 */
	void update(String companyId, String employeeId, LegalAggrSetOfIrg legalAggrSetOfIrg);
}
