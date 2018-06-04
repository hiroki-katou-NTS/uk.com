package nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim.InterimRecMng;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsRecInterimOutputPara {
	/**
	 * 暫定振休管理データ
	 */
	private List<InterimAbsMng> interimAbsMngInfor;
	/**
	 * 暫定振出振休紐付け管理
	 */
	private List<InterimRecAbsMng> interimRecAbsMngInfor;
	/**
	 * 暫定振出管理データ
	 */
	private List<InterimRecMng> interimRecMngInfor;
}
