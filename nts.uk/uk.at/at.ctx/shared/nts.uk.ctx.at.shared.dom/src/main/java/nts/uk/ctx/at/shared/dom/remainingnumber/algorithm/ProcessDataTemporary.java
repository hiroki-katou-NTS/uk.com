package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.InterimMngParamCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;

public class ProcessDataTemporary {

	public static <U extends InterimMngParamCommon, T extends InterimMngCommon> void processOverride(U param,
			List<T> lstMngParam, List<InterimRemain> lstRemainResult, List<T> lstMngResult) {

		// 20181003 DuDT fix bug 101491 ↓
		// 対象期間のドメインモデル「暫定休出管理データ」を上書き用の暫定管理データに置き換える
		if (param.isReplaceChk() && !param.getInterimMng().isEmpty() && !param.isMode()) {
			Set<GeneralDate> lstDateInParam = param.getInterimMng().stream().map(x -> x.getYmd())
					.collect(Collectors.toSet());
			lstRemainResult.removeIf(x -> {
				if (lstDateInParam.contains(x.getYmd())) {
					lstMngResult.removeIf(y -> y.getId().equals(x.getRemainManaID()));
					return true;
				} else {
					return false;
				}
			});
		}
		// 20181003 DuDT fix bug 101491 ↑
		if (param.isReplaceChk() && param.getCreatorAtr().isPresent() && param.getProcessDate().isPresent()) {
			lstRemainResult.removeIf(x -> {
				if (x.getCreatorAtr() == param.getCreatorAtr().get()
						&& x.getYmd().afterOrEquals(param.getProcessDate().get().start())
						&& x.getYmd().beforeOrEquals(param.getProcessDate().get().end())) {
					lstMngResult.removeIf(y -> y.getId().equals(x.getRemainManaID()));
					return true;
				}
				return false;
			});
		}

		if (param.isReplaceChk() && !param.getInterimMng().isEmpty() && !lstMngParam.isEmpty()) {
			Map<String, T> mapMngParam = lstMngParam.stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
			param.getInterimMng().stream().filter(x -> mapMngParam.containsKey(x.getRemainManaID())).forEach(x -> {
				lstRemainResult.removeIf(y -> {
				if (y.getYmd().equals(x.getYmd())) {
					lstMngResult.removeIf(z -> y.getRemainManaID().equals(z.getId()));
					return true;
				}
				return false;
			});
			lstRemainResult.add(x);
			lstMngResult.add(mapMngParam.get(x.getRemainManaID()));
			});
			
		}

	}

}
