package nts.uk.ctx.pr.transfer.dom.emppaymentinfo;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import nts.arc.error.BusinessException;

/**
 * 
 * @author HungTT - 支払方法詳細
 *
 */

@Getter
public class PaymentMethodDetail {

	/**
	 * 利用区分
	 */
	private PaymentUseAtr useAtr;

	/**
	 * 支払方法NO ： １～５
	 */
	private int paymentMethodNo;

	/**
	 * 振込情報
	 */
	private Optional<TransferInfor> transferInfor;

	/**
	 * 支払按分区分
	 */
	private Optional<PaymentProportionAtr> paymentProportionAtr;

	/**
	 * 支払方法
	 */
	private Optional<SalaryPaymentMethod> paymentMethod;

	/**
	 * 支払率
	 */
	private Optional<PaymentRate> paymentRate;

	/**
	 * 支払額
	 */
	private Optional<PaymentAmount> paymentAmount;

	/**
	 * 支給優先順位
	 */
	private Optional<PaymentPriority> paymentPriority;

	public PaymentMethodDetail(boolean useAtr, int paymentMethodNo, TransferInfor transferInfor,
			Integer paymentProportionAtr, Boolean paymentMethodTransfer, Integer paymentRate, Long paymentAmount,
			Integer paymentPriority) {
		super();
		this.useAtr = PaymentUseAtr.of(useAtr);
		this.paymentMethodNo = paymentMethodNo;
		if (useAtr) {
			this.paymentMethod = Optional
					.ofNullable(paymentMethodTransfer == null ? null : SalaryPaymentMethod.of(paymentMethodTransfer));
			if (paymentMethodTransfer)
				this.transferInfor = Optional.ofNullable(transferInfor);
			else
				this.transferInfor = Optional.empty();

			this.paymentProportionAtr = Optional
					.ofNullable(paymentProportionAtr == null ? null : PaymentProportionAtr.of(paymentProportionAtr));
			switch (paymentProportionAtr) {
			case 0: // 定率 - FIXED_RATE
				this.paymentRate = Optional.ofNullable(paymentRate == null ? null : new PaymentRate(paymentRate));
				this.paymentAmount = Optional.empty();
				break;
			case 1: // 定額 - FIXED_AMOUNT
				this.paymentRate = Optional.empty();
				this.paymentAmount = Optional
						.ofNullable(paymentAmount == null ? null : new PaymentAmount(paymentAmount));
				break;
			default: // 全額 - FULL_AMOUNT
				this.paymentRate = Optional.empty();
				this.paymentAmount = Optional.empty();
				break;
			}
			this.paymentPriority = Optional
					.ofNullable(paymentPriority == null ? null : PaymentPriority.of(paymentPriority));
		} else {
			this.transferInfor = Optional.empty();
			this.paymentProportionAtr = Optional.empty();
			this.paymentMethod = Optional.empty();
			this.paymentRate = Optional.empty();
			this.paymentAmount = Optional.empty();
			this.paymentPriority = Optional.empty();
		}
	}

	public static void validate(List<PaymentMethodDetail> listPaymentMethod) {
		/**
		 * 支払按分区分 = 全額 のデータが必ず1件存在する #MsgQ_165
		 *  （存在する範囲は履歴ID＆利用区分 = 利用する 内）
		 */
		if (listPaymentMethod.isEmpty()) {
			throw new BusinessException("MsgQ_165");
		}
		List<PaymentMethodDetail> fullAmountData = listPaymentMethod.stream()
				.filter(p -> p.getPaymentProportionAtr().isPresent()
						&& p.getPaymentProportionAtr().get() == PaymentProportionAtr.FULL_AMOUNT)
				.collect(Collectors.toList());
		if (fullAmountData.size() != 1)
			throw new BusinessException("MsgQ_165");
		/**
		 * 支給優先順位：重複不可 #MsgQ_164
		 *  （重複不可の範囲は同じ履歴ID＆利用区分 = 利用する 内）
		 */
		List<Integer> lstPriority = listPaymentMethod.stream().filter(p -> p.getPaymentPriority().isPresent())
				.map(p -> p.getPaymentPriority().get().value).collect(Collectors.toList());
		Set<Integer> setPriority = new HashSet<>(lstPriority);
		if (lstPriority.size() > setPriority.size())
			throw new BusinessException("MsgQ_164");
		/**
		 * 振込先銀行支店情報、振込先口座番号は重複不可 #MsgQ_166
		 *  （重複不可の範囲は同じ履歴ID＆利用区分 = 利用する 内）
		 */
		List<TransferInfor> lstTransfer = listPaymentMethod.stream().filter(p -> p.getTransferInfor().isPresent())
				.map(p -> p.getTransferInfor().get()).collect(Collectors.toList());
		List<Pair<String, String>> lstDesBankBranchAccountNum = lstTransfer.stream()
				.map(p -> Pair.of(p.getDesBankBranchInfor(), p.getDesAccountNumber().v())).collect(Collectors.toList());
		Set<Pair<String, String>> setDesBankBranchAccountNum = new HashSet<>(lstDesBankBranchAccountNum);
		if (lstDesBankBranchAccountNum.size() > setDesBankBranchAccountNum.size())
			throw new BusinessException("MsgQ_166");
	}

}
