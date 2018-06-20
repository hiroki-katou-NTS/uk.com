package nts.uk.ctx.at.function.infra.repository.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidtgByMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidtgByMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidthOfDisplayItem;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KrcmtMonGridColWidth;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaMonGridColWidthRepository extends JpaRepository implements ColumnWidtgByMonthlyRepository{

	private static final String Get_COL_WIDTH_BY_CID = "SELECT c FROM KrcmtMonGridColWidth c "
			+ " WHERE c.monGridColWidthPk.cid = :companyID";
	
	private static final String Get_COL_WIDTH_BY_CID_AND_ATTDID = "SELECT c FROM KrcmtMonGridColWidth c "
			+ " WHERE c.monGridColWidthPk.cid = :companyID "
			+ " AND c.monGridColWidthPk.attendanceItemId IN :attendanceItemIds"; 
	@Override
	public Optional<ColumnWidtgByMonthly> getColumnWidtgByMonthly(String companyID) {
		List<KrcmtMonGridColWidth> lstData = this.queryProxy().query(Get_COL_WIDTH_BY_CID,KrcmtMonGridColWidth.class)
												.setParameter("companyID", companyID)
												.getList();
		
		if(!lstData.isEmpty()){
			List<ColumnWidthOfDisplayItem> listColumnWidthOfDisplayItem = lstData.stream()
					.map(row -> new ColumnWidthOfDisplayItem(row.monGridColWidthPk.attendanceItemId, row.columnWidth))
					.collect(Collectors.toList());
					
			return Optional.of(new ColumnWidtgByMonthly(companyID, listColumnWidthOfDisplayItem));
			
		}
		return Optional.empty();
	}

	@Override
	public void addColumnWidtgByMonthly(ColumnWidtgByMonthly columnWidtgByMonthly) {
	}

	@Override
	public void updateColumnWidtgByMonthly(Map<Integer, Integer> lstHeader) {
		List<KrcmtMonGridColWidth> entity = this.queryProxy().query(Get_COL_WIDTH_BY_CID_AND_ATTDID, KrcmtMonGridColWidth.class)
				.setParameter("companyID", AppContexts.user().companyId()).setParameter("attendanceItemIds", lstHeader.keySet()).getList();
		for(KrcmtMonGridColWidth item :entity){
			item.columnWidth = lstHeader.get(item.monGridColWidthPk.attendanceItemId);
		}
		this.commandProxy().updateAll(entity);
	}

	@Override
	public void deleteColumnWidtgByMonthly(String companyID) {
		// TODO Auto-generated method stub
		
	}

}
