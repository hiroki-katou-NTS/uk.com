package nts.uk.ctx.at.function.infra.repository.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidtgByMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidtgByMonthlyRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.ColumnWidthOfDisplayItem;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KrcmtMonGridColWidth;
@Stateless
public class JpaMonGridColWidthRepository extends JpaRepository implements ColumnWidtgByMonthlyRepository{

	private static final String Get_COL_WIDTH_BY_CID = "SELECT c FROM KrcmtMonGridColWidth c "
			+ " WHERE c.monGridColWidthPk.cid = :companyID";
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateColumnWidtgByMonthly(ColumnWidtgByMonthly columnWidtgByMonthly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteColumnWidtgByMonthly(String companyID) {
		// TODO Auto-generated method stub
		
	}

}
