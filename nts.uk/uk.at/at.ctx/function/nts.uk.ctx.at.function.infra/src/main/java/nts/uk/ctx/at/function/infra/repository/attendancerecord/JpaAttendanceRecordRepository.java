package nts.uk.ctx.at.function.infra.repository.attendancerecord;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframe;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.KfnmtRptWkAtdOutframePK;
import nts.uk.ctx.at.function.infra.entity.attendancerecord.item.KfnmtRptWkAtdOutatd;

/**
 * @author tuannt-nws
 *
 */
public abstract class JpaAttendanceRecordRepository extends JpaRepository {
	
	private static final String SELECT_ATD_BY_OUT_FRAME = "SELECT atd FROM KfnmtRptWkAtdOutatd atd"
			+ "	WHERE atd.layoutId = :layoutId"
			+ "		AND atd.columnIndex = :columnIndex"
			+ "		AND atd.outputAtr = :outputAtr"
			+ "		AND atd.position = :position ";
	
	private static final String SELECT_ATD_BY_LAYOUT_ID = "SELECT outatd FROM KfnmtRptWkAtdOutatd outatd"
			+ "	WHERE outatd.layoutId = :layoutId";
	
	private static final String SELECT_ATD_FRAME_BY_LAYOUT_ID = "SELECT frame FROM KfnmtRptWkAtdOutframe frame"
			+ " WHERE frame.id.layoutId = :layoutId";
	
	/**
	 * Find attendance record items.
	 *
	 * @param kfnstAttndRecPK the kfnst attnd rec PK
	 * @return the list
	 */
	public List<KfnmtRptWkAtdOutatd> findAttendanceRecordItems(KfnmtRptWkAtdOutframePK kfnstAttndRecPK) {
		// query data
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems =  this.queryProxy()
				.query(SELECT_ATD_BY_OUT_FRAME, KfnmtRptWkAtdOutatd.class)
				.setParameter("layoutId", kfnstAttndRecPK.getLayoutId())
				.setParameter("columnIndex", kfnstAttndRecPK.getColumnIndex())
				.setParameter("outputAtr", kfnstAttndRecPK.getOutputAtr())
				.setParameter("position", kfnstAttndRecPK.getPosition())
				.getList();
		return kfnstAttndRecItems;
	}

	/**
	 * Find all attendance record item.
	 *
	 * @param layoutId the layout id
	 * @return the list
	 */
	List<KfnmtRptWkAtdOutatd> findAllAttendanceRecordItem(String layoutId) {
		List<KfnmtRptWkAtdOutatd> kfnstAttndRecItems =  this.queryProxy()
				.query(SELECT_ATD_BY_LAYOUT_ID, KfnmtRptWkAtdOutatd.class)
				.setParameter("layoutId", layoutId)
				.getList();
		return kfnstAttndRecItems;
	}

	/**
	 * Removes the all attnd rec item.
	 *
	 * @param listKfnstAttndRecItem the list kfnst attnd rec item
	 */
	public void removeAllAttndRecItem(List<KfnmtRptWkAtdOutatd> listKfnstAttndRecItem) {
		if (!listKfnstAttndRecItem.isEmpty()) {
			this.commandProxy().removeAll(listKfnstAttndRecItem);
			this.getEntityManager().flush();
		}
	}
	
	/**
	 * Get all KfnmtRptWkAtdOutframe by layoutId
	 * @param layoutId
	 * @return List KfnmtRptWkAtdOutframe
	 */
	List<KfnmtRptWkAtdOutframe> findAllAttendanceRecords(String layoutId) {
		List<KfnmtRptWkAtdOutframe> kfnstAttndRecs = this.queryProxy()
				.query(SELECT_ATD_FRAME_BY_LAYOUT_ID, KfnmtRptWkAtdOutframe.class)
				.setParameter("layoutId", layoutId)
				.getList();
		return kfnstAttndRecs;
	}

}
