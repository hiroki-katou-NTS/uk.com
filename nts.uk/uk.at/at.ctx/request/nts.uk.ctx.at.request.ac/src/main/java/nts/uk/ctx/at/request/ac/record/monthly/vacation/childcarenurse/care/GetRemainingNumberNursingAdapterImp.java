package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.care;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNursePeriodExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.GetRemainingNumberNursingPub;
import nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.ImportedClassConverter;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.ChildCareNursePeriodImport;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care.GetRemainingNumberNursingAdapter;

/**
 * @author anhnm
 *
 */
@Stateless
public class GetRemainingNumberNursingAdapterImp implements GetRemainingNumberNursingAdapter {
    
    @Inject
    private GetRemainingNumberNursingPub getRemainingNumberNursingPub;

    @Override
    public ChildCareNursePeriodImport getRemainingNumberNursing(String cId, String sId, GeneralDate date) {
        
        ChildCareNursePeriodExport result = getRemainingNumberNursingPub.getRemainingNumberNursing(cId, sId, date);
        
        return ImportedClassConverter.exportToImport(result);
    }

}
