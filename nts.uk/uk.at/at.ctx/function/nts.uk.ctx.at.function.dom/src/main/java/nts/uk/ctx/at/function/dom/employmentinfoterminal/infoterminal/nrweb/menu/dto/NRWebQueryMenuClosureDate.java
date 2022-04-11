package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.menu.dto;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRWeb照会メニュー一覧と締め年月
 */
@Getter
public class NRWebQueryMenuClosureDate {

	// 締め年月
	private Optional<Integer> closureYm;

	// NRWebメニュー一覧
	private List<NRWebQueryMenuDetail> menuDetails;

	public NRWebQueryMenuClosureDate(Optional<Integer> closureYm, List<NRWebQueryMenuDetail> menuDetails) {
		this.closureYm = closureYm;
		this.menuDetails = menuDetails;
	}

	// NRWebメニュー一覧
	@Getter
	public static class NRWebQueryMenuDetail {

		// メニュー名
		private String menuName;

		// URL
		private String url;

		// 引数
		private String argument;

		// メニュー番号
		private Optional<Integer> menuNo;

		public NRWebQueryMenuDetail(String menuName, String url, String argument, Optional<Integer> menuNo) {
			this.menuName = menuName;
			this.url = url;
			this.argument = argument;
			this.menuNo = menuNo;
		}

	}

}
