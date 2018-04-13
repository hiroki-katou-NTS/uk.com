package nts.uk.ctx.at.function.infra.repository.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyActualResults;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeRepository;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthly;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KfnmtDisplayTimeItemRC;
import nts.uk.ctx.at.function.infra.entity.monthlycorrection.fixedformatmonthly.KfnmtMonthlyActualResultRC;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;

@Stateless
public class JpaMonthlyRecordWorkTypeRepo  extends JpaRepository implements MonthlyRecordWorkTypeRepository {

	private static final  String GET_MON_BY_CODE  = "SELECT a FROM KfnmtMonthlyActualResultRC a "
			+ " WHERE a.companyID = :companyId "
			+ " AND a.businessTypeCode = :businessTypeCode";
	
	
	@Override
	public Optional<MonthlyRecordWorkType> getMonthlyRecordWorkTypeByCode(String companyID, String businessTypeCode) {
		List<KfnmtMonthlyActualResultRC> data =
				this.queryProxy().query(GET_MON_BY_CODE,KfnmtMonthlyActualResultRC.class)
				.setParameter("companyID", companyID)
				.setParameter("businessTypeCode", businessTypeCode)
				.getList();
		if(data.isEmpty()){
			return Optional.empty();
		}
		List<SheetCorrectedMonthly> listSheetCorrectedMonthly = data.stream().map(c->c.toDomain()).collect(Collectors.toList());
		
		MonthlyActualResults monthlyActualResults = new MonthlyActualResults(
				listSheetCorrectedMonthly.get(0).getMonthlyActualID(),
				listSheetCorrectedMonthly
				);
		MonthlyRecordWorkType monthlyRecordWorkType = new MonthlyRecordWorkType(
				companyID,
				new BusinessTypeCode(businessTypeCode),
				monthlyActualResults
				);
		return Optional.of(monthlyRecordWorkType);
	}

	@Override
	public void addMonthlyRecordWorkType(MonthlyRecordWorkType monthlyRecordWorkType) {
		List<KfnmtMonthlyActualResultRC> newEntity = monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly()
				.stream().map(c->KfnmtMonthlyActualResultRC.toEntity(
						monthlyRecordWorkType.getDisplayItem().getMonthlyActualID(),
						monthlyRecordWorkType.getCompanyID(),monthlyRecordWorkType.getBusinessTypeCode().v()
						, c)).collect(Collectors.toList());
		this.commandProxy().insertAll(newEntity);
	}

//	@Override
//	public void deleteMonthlyRecordWorkType(String companyID, String businessTypeCode) {
//		List<KfnmtMonthlyActualResultRC> data =
//				this.queryProxy().query(GET_MON_BY_CODE,KfnmtMonthlyActualResultRC.class)
//				.setParameter("companyID", companyID)
//				.setParameter("businessTypeCode", businessTypeCode)
//				.getList();
//		this.commandProxy().removeAll(data);
//		
//	}

	@Override
	public void updateMonthlyRecordWorkType(MonthlyRecordWorkType monthlyRecordWorkType) {
		List<KfnmtMonthlyActualResultRC> newEntity = monthlyRecordWorkType.getDisplayItem().getListSheetCorrectedMonthly()
				.stream().map(c->KfnmtMonthlyActualResultRC.toEntity(
						monthlyRecordWorkType.getDisplayItem().getMonthlyActualID(),
						monthlyRecordWorkType.getCompanyID(),monthlyRecordWorkType.getBusinessTypeCode().v()
						, c)).collect(Collectors.toList());
		//List<KfnmtDisplayTimeItemRC> listKfnmtDisplayTimeItemRC = newEntity.ge
		List<KfnmtMonthlyActualResultRC> updateEntity =
				this.queryProxy().query(GET_MON_BY_CODE,KfnmtMonthlyActualResultRC.class)
				.setParameter("companyID", monthlyRecordWorkType.getCompanyID())
				.setParameter("businessTypeCode", monthlyRecordWorkType.getBusinessTypeCode().v())
				.getList();
		//loop list from ui -> server
		for(KfnmtMonthlyActualResultRC newMonthlyRC : newEntity) {
			//loop list from db
			for(KfnmtMonthlyActualResultRC updateMonthlyRC : updateEntity) {
				//if monthlyActualID from ui = monthlyActualID from db
				if(newMonthlyRC.kfnmtMonthlyActualResultRCPK.monthlyActualID.equals(updateMonthlyRC.kfnmtMonthlyActualResultRCPK.monthlyActualID)) {
					//get list DisplayTimeItemRC from newMonthlyRC
					List<KfnmtDisplayTimeItemRC> newDisplayRC = newMonthlyRC.listKfnmtDisplayTimeItemRC;
					//get list DisplayTimeItemRC from updateMonthlyRC
					List<KfnmtDisplayTimeItemRC> updateDisplayRC = updateMonthlyRC.listKfnmtDisplayTimeItemRC;
					//loop list newDisplayRC
					for(KfnmtDisplayTimeItemRC displayNew : newDisplayRC) {
						boolean checkExist = false;
						for(KfnmtDisplayTimeItemRC displayUpdate : updateDisplayRC) {
							if(displayNew.kfnmtDisplayTimeItemRCPK.itemDisplay == displayUpdate.kfnmtDisplayTimeItemRCPK.itemDisplay) {
								checkExist = true;
								break;
							}
						}
						if(!checkExist) {
							this.commandProxy().insert(displayNew);
						}
						
					}
					
					//loop list updateDisplayRC, 
					for(KfnmtDisplayTimeItemRC displayUpdate : updateDisplayRC) {
						boolean checkExist = false;
						for(KfnmtDisplayTimeItemRC displayNew : newDisplayRC) {
							if(displayNew.kfnmtDisplayTimeItemRCPK.itemDisplay == displayUpdate.kfnmtDisplayTimeItemRCPK.itemDisplay) {
								checkExist = true;
								break;
							}
						}
						if(!checkExist) {
							this.commandProxy().remove(displayUpdate);
						}
						
					}
					
				}//end if
			}//end for 2
		}//end for 1
	}

}
