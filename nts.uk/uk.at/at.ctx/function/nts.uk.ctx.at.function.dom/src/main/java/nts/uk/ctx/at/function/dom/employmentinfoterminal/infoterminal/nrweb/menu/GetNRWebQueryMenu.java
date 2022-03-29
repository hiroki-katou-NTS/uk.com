package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryArg;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu.dto.NRWebQueryMenuClosureDate;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu.dto.NRWebQueryMenuClosureDate.NRWebQueryMenuDetail;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;

/**
 * @author thanh_nx
 *
 *         NRWeb照会メニュー一覧を取得
 */
public class GetNRWebQueryMenu {

	private GetNRWebQueryMenu() {

	}

	public static NRWebQueryMenuClosureDate process(Require require, NRWebQuerySidDateParameter param) {


			// $締め = require.社員に対応する処理締めを取得する($打刻カード.社員ID, システム日付)
		Integer processingYm = require
					.getClosureDataByEmployee(param.getCid(), param.getSid(), GeneralDate.today())
					.getClosureMonth().getProcessingYm().v();


		List<NRWebQueryMenuDetail> detail = Arrays.asList(NRWebQueryMenuName.values()).stream().filter(x -> x != NRWebQueryMenuName.MENU)
				.map(x -> new NRWebQueryMenuDetail(x.value, x.link, createArg(x), Optional.of(Integer.parseInt(x.no))))
				.collect(Collectors.toList());

		return new NRWebQueryMenuClosureDate(Optional.ofNullable(processingYm), detail);
	}

	// [pvt-1] 引数を作る
	private static String createArg(NRWebQueryMenuName menu) {
		switch (menu) {

		case MENU:
			return NRWebQueryArg.TYPE.value + "," + NRWebQueryArg.CNO.value + "," + NRWebQueryArg.VER.value;

		case SCHEDULE:
			return NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + "," + NRWebQueryArg.VER.value;

		case DAILY:
			return NRWebQueryArg.TYPE.value + "," + NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + ","
					+ NRWebQueryArg.VER.value;

		case MONTHLY:
			return NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + "," + NRWebQueryArg.VER.value;

		case APPLICATION:
			return NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + "," + NRWebQueryArg.KBN.value + ","
					+ NRWebQueryArg.JIKBN.value + "," + NRWebQueryArg.NDATE.value + "," + NRWebQueryArg.VER.value;

		case MONTH_WAGE:
			return NRWebQueryArg.TYPE.value + "," + NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + ","
					+ NRWebQueryArg.VER.value;

		case ANNUAL_WAGE:
			return NRWebQueryArg.TYPE.value + "," + NRWebQueryArg.CNO.value + "," + NRWebQueryArg.DATE.value + ","
					+ NRWebQueryArg.VER.value;

		default:
			return null;
		}
	}

	public static interface Require {

		// ClosureService.getClosureDataByEmployee
		// [R-1] 社員に対応する処理締めを取得する
		public Closure getClosureDataByEmployee(String companyId, String employeeId, GeneralDate baseDate);

	}
}
