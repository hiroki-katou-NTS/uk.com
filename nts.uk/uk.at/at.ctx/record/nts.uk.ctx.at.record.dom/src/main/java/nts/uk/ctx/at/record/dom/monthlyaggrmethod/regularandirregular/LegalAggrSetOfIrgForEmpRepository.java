package nts.uk.ctx.at.record.dom.monthlyaggrmethod.regularandirregular;

/**
 * リポジトリ：雇用の変形労働時間勤務の法定内集計設定
 * @author shuichu_ishida
 */
public interface LegalAggrSetOfIrgForEmpRepository {

	/**
	 * 追加
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 */
	void insert(String companyId, String employmentCd, LegalAggrSetOfIrg legalAggrSetOfIrg);
	
	/**
	 * 更新
	 * @param companyId 会社ID
	 * @param employmentCd 雇用コード
	 * @param legalAggrSetOfIrg 変形労働時間勤務の法定内集計設定
	 */
	void update(String companyId, String employmentCd, LegalAggrSetOfIrg legalAggrSetOfIrg);
}
