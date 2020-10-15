package nts.uk.ctx.at.function.infra.repository.alarm.appapproval;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedCheckItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItem;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractItemRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.ErrorAlarmMessage;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.ErrorAlarmAtr;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.appapproval.KrqmtAppApprovalFixedExtractItem;

@Stateless
public class JpaAppApprovalFixedExtractItemRepository extends JpaRepository implements AppApprovalFixedExtractItemRepository {

	@Override
	public List<AppApprovalFixedExtractItem> findAll() {
		String query = "SELECT a FROM KrqmtAppApprovalFixedExtractItem a";
		return this.queryProxy().query(query, KrqmtAppApprovalFixedExtractItem.class).getList()
				.stream().map(a -> new AppApprovalFixedExtractItem(
						a.getNo(), new ErrorAlarmMessage(a.getInitMessage())
						, ErrorAlarmAtr.values()[a.getElAlAtr()], AppApprovalFixedCheckItem.valueOf(a.getName())))
				.collect(Collectors.toList());
	}
}
