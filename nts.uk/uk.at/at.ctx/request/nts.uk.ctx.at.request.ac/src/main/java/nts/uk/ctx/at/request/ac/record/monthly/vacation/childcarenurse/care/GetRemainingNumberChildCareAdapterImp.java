package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.care;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNursePeriodExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCarePub;
import nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.ImportedClassConverter;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.ChildCareNursePeriodImport;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care.GetRemainingNumberChildCareAdapter;

/**
 * @author anhnm
 *
 */
@Stateless
public class GetRemainingNumberChildCareAdapterImp implements GetRemainingNumberChildCareAdapter {
    
    @Inject
    private GetRemainingNumberChildCarePub getRemainingNumberChildCarePub;

    @Override
    public ChildCareNursePeriodImport getRemainingNumberChildCare(String cId, String sId, GeneralDate date) {
        
        ChildCareNursePeriodExport result = getRemainingNumberChildCarePub.getRemainingNumberChildCare(cId, sId, date);
        
        return ImportedClassConverter.exportToImport(result);
    }

}
