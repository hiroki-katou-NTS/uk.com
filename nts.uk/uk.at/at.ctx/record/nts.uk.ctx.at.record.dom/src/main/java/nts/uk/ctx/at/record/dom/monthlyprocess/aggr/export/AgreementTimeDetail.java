package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;

/**
 * 36協定時間一覧
 * @author shuichu_ishida
 */
@Getter
public class AgreementTimeDetail {

	/** 社員ID */
	private String employeeId;
	/** 確定情報 */
	private Optional<AgreementTimeOfMonthly> confirmed;
	/** 申請反映後情報 */
	private Optional<AgreementTimeOfMonthly> afterAppReflect;
	/** エラーメッセージ */
	private Optional<String> errorMessage;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 */
	public AgreementTimeDetail(String employeeId){
		
		this.employeeId = employeeId;
		this.confirmed = Optional.empty();
		this.afterAppReflect = Optional.empty();
		this.errorMessage = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param confirmed 確定情報
	 * @param afterAppReflect 申請反映後情報
	 * @param errorMessage エラーメッセージ
	 * @return 36協定時間一覧
	 */
	public static AgreementTimeDetail of(
			String employeeId,
			AgreementTimeOfMonthly confirmed,
			AgreementTimeOfMonthly afterAppReflect,
			String errorMessage){
		
		AgreementTimeDetail domain = new AgreementTimeDetail(employeeId);
		domain.confirmed = Optional.ofNullable(confirmed);
		domain.afterAppReflect = Optional.ofNullable(afterAppReflect);
		domain.errorMessage = Optional.ofNullable(errorMessage);
		return domain;
	}
}
