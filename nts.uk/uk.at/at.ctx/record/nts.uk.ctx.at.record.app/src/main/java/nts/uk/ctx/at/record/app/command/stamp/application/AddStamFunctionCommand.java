package nts.uk.ctx.at.record.app.command.stamp.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplay;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author phongtq
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class AddStamFunctionCommand {

	
	/** 使用区分 */
	private int usrAtr;
	
	/** 表示項目一覧 */
	private List<StampAttenDisplayCommand> lstDisplayItemId;
	
	public StampResultDisplay toDomain(){
		String companyId = AppContexts.user().companyId();
		return new StampResultDisplay(companyId, EnumAdaptor.valueOf(this.usrAtr, NotUseAtr.class), lstDisplayItemId.stream().map(mapper->mapper.toDomain(companyId)).collect(Collectors.toList()));
	}
}
