package nts.uk.ctx.pr.core.dom.laborinsurance;

/**
* 労災保険料率
*/
public interface OccAccIsPrRateRepository
{

    OccAccIsPrRate getOccAccIsPrRateByHisId(String hisId);
    
    void remove(int occAccInsurBusNo, String hisId);
    
    void remove(String hisId);

}
