package nts.uk.ctx.hr.shared.app.databeforereflecting.command;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;

@Stateless
public class AlgorithmPreCheck {
	// ten thuat toan : 事前チェック
	// thua toan goi den : アルゴリズム[事前チェック]を実行する (THực hiện thuật toán [Check trước ] )
	public void preCheck(DataBeforeReflectCommand input) {

		// [A222_12 退職日] と[A222_14 公開日]をチェックする ( Check [A222_14 Release date] và
		// [A222_12 Retirement date]
		
		GeneralDate retirementDate = GeneralDate.fromString(input.retirementDate, "yyyy/MM/dd"); // A222_12
		GeneralDate releaseDate = GeneralDate.fromString(input.releaseDate, "yyyy/MM/dd"); // A222_14
		
		// 社員が選択されているかのチェック(check xem employee có đang được chọn hay không)
		if (StringUtil.isNullOrEmpty(input.getSId(), false)) {
			throw new BusinessException("MsgJ_JCM007_12");
		}

		BundledBusinessException bundleExeption = BundledBusinessException.newInstance();

		int index = 0;

		if (retirementDate.before(releaseDate)) {
			index++;
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_2"));
		}
		/**
		 * { value: 1, text: '退職' }, { value: 2, text: '転籍' }, { value: 3, text:
		 * '解雇' }, { value: 4, text: '定年' }
		 */
		int retirementType = input.retirementType;
		if (retirementType == 3) {
			GeneralDate dismissalNoticeDate = GeneralDate.fromString(input.dismissalNoticeDate, "yyyy/MM/dd"); // A222_35
			if (retirementDate.before(dismissalNoticeDate)) {
				index++;
				bundleExeption.addMessage(new BusinessException("MsgJ_JCM007_3"));
			}
		}

		if (index > 0) {
			throw bundleExeption;
		}
	}

}
