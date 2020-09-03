package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.指定社員リストの打刻カード番号を生成する
 * 
 * @author chungnt
 *
 */

public class GenerateStampCardForEmployees {

	/**
	 * [1] 生成する
	 * 
	 * @param require
	 * @param contractCd       契約コード
	 * @param companyCd        会社コード
	 * @param makeEmbossedCard 打刻カード作成方法
	 * @param targetPersons    対象者リスト
	 * @return 				       打刻カード生成結果リスト
	 */
	public static List<ImprintedCardGenerationResult> generate(Require require, String contractCd, String companyCd,
<<<<<<< HEAD
			MakeEmbossedCard makeEmbossedCard, List<TargetPerson> targetPersons, String companyId, String sid ) {
		
//		List<Optional<String>> stampCards = targetPersons.stream().map(m -> {
//			Optional<String> s = generateEmbossedCardNumber(require, companyCd, makeEmbossedCard,
//					m.getEmployeeCd(), companyId);
//			
//			return s;
//		}).collect(Collectors.toList());
//		
//		if (stampCards.isEmpty()) {
//			throw new BusinessException("Msg_1756");
//		}
		
		List<Optional<String>> stampCards = new ArrayList<Optional<String>>();
		stampCards.add(Optional.of("000000000002        "));
		
		List<ImprintedCardGenerationResult> results = stampCards.stream().map(m -> {
			StampCard card = new StampCard(companyCd,m.get(), sid);
//			Optional<StampCard> duplicateCards = require.getByCardNoAndContractCode(m.get(), contractCd);
			ImprintedCardGenerationResult result = new ImprintedCardGenerationResult(companyCd, card, Optional.of(new StampCard(contractCd, "0123456", sid)));
=======
			MakeEmbossedCard makeEmbossedCard, List<TargetPerson> targetPersons) {
		
		List<ImprintedCardGenerationResult> results = targetPersons.stream().map(m -> {
			Optional<String> stampCard = generateEmbossedCardNumber(require, companyCd, makeEmbossedCard, m.getEmployeeCd());
			
			if(!stampCard.isPresent()) {
				throw new BusinessException("Msg_1756");
			}
			
			StampCard card = new StampCard(companyCd, companyCd, companyCd);
			Optional<StampCard> duplicateCards = require.getByCardNoAndContractCode(stampCard.get(), contractCd);
			ImprintedCardGenerationResult result = new ImprintedCardGenerationResult(companyCd, card, duplicateCards);
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest
			
			return result;
		}).collect(Collectors.toList());

		return results;
	}

	/**
	 * [prv-1] 打刻カード番号を生成する
	 * 
	 * @param require
	 * @param companyCd        会社コード
	 * @param makeEmbossedCard 打刻カード作成方法
	 * @param employeeCd       社員コード
	 * @return 				       打刻カード番号
	 */
	private static Optional<String> generateEmbossedCardNumber(Require require, String companyCd,
<<<<<<< HEAD
			MakeEmbossedCard makeEmbossedCard, String employeeCd, String companyId) {

		StampCardEditing stampCardEditing = require.get(companyId);

		if (makeEmbossedCard.value == makeEmbossedCard.COMPANY_CODE_AND_EMPLOYEE_CODE.value) {

=======
			MakeEmbossedCard makeEmbossedCard, String employeeCd) {
		
		StampCardEditing stampCardEditing = require.get(AppContexts.user().companyId());
		
		if(makeEmbossedCard.value == 1) {
			
>>>>>>> ada8a756ed3... Create GenerateStampCardForEmployeesTest
			return stampCardEditing.createStampCard(companyCd, employeeCd);
		}
		return Optional.of(stampCardEditing.editCardNumber(employeeCd));
	}

	public static interface Require {

		// [R-1] 打刻カード編集を取得する
		StampCardEditing get(String companyId);

		// [R-2] 打刻カードを取得する
		Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd);
	}
}
