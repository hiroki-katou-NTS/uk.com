package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaveInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.TmpRsvLeaveMngImport;
@Getter
@AllArgsConstructor
public class RsvLeaManagerImportDto {
	/** 積立年休残数 */
	private RsvLeaveInfoImport reserveLeaveInfo;
	/** 積立年休付与残数データ */
	//積立年休付与情報
	private List<RsvLeaGrantRemainingImportDto> grantRemainingList;
	/** 暫定積立年休管理データ */
	//積立年休管理情報
	private List<TmpRsvLeaveMngImport> tmpManageList;
}
