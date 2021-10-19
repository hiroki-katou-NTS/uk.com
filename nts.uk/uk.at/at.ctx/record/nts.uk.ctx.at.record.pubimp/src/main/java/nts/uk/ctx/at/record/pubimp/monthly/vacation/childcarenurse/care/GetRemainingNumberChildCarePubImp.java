package nts.uk.ctx.at.record.pubimp.monthly.vacation.childcarenurse.care;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.care.GetRemainingNumberChildCare;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodDaysInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseAggrPeriodInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseErrorsExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNursePeriodExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseRemainingNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateDaysInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseStartdateInfoExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.ChildCareNurseUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCarePub;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;

@Stateless
public class GetRemainingNumberChildCarePubImp implements GetRemainingNumberChildCarePub {
    
    @Inject
    private  GetRemainingNumberChildCare getRemainingNumberChildCare;

    @Override
    public ChildCareNursePeriodExport getRemainingNumberChildCare(String cId, String sId, GeneralDate date) {
        
        AggrResultOfChildCareNurse result = getRemainingNumberChildCare.getRemainingNumberChildCare(cId, sId, date);
        
        return mapToPub(result);
    }

 // Exportから変換
    private ChildCareNursePeriodExport mapToPub(AggrResultOfChildCareNurse c) {

        return new ChildCareNursePeriodExport(

                createError(c.getChildCareNurseErrors()),

                ChildCareNurseUsedNumberExport.of(
                        c.getAsOfPeriodEnd().getUsedDay().v(),
                        c.getAsOfPeriodEnd().getUsedTimes().map(ny -> ny.v())),

//              ChildCareNurseStartdateDaysInfo.of(
//                      mapToPub(c.getStartdateDays().getThisYear()),
//                      c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),
                ChildCareNurseStartdateDaysInfoExport.of(
                        mapToPub(c.getStartdateDays().getThisYear()),
                        c.getStartdateDays().getNextYear().map(ny -> mapToPub(ny))),

                c.isStartDateAtr(),

                ChildCareNurseAggrPeriodDaysInfoExport.of(
                        mapToPubAggrPeriodInfo(c.getAggrperiodinfo().getThisYear()),
                        c.getAggrperiodinfo().getNextYear().map(ny -> mapToPubAggrPeriodInfo(ny)))

                );
    }

    //  起算日からの休暇情報
    private ChildCareNurseStartdateInfoExport mapToPub(nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo domain) {

        return ChildCareNurseStartdateInfoExport.of(
                ChildCareNurseUsedNumberExport.of(
                        domain.getUsedDays().getUsedDay().v(),
                        domain.getUsedDays().getUsedTimes().map(t -> t.v())),
                    ChildCareNurseRemainingNumberExport.of(
                            domain.getRemainingNumber().getRemainDay().v(),
                            domain.getRemainingNumber().getRemainTimes().map(t -> t.v())),
                    domain.getLimitDays().v());
    }

    // 集計期間の休暇情報
    private ChildCareNurseAggrPeriodInfoExport mapToPubAggrPeriodInfo(ChildCareNurseUsedInfo domain) {
        return ChildCareNurseAggrPeriodInfoExport.of(
                            domain.getUsedTimes().v(),
                            domain.getUsedDays().v(),
                            ChildCareNurseUsedNumberExport.of(
                                    domain.getUsedNumber().getUsedDay().v(),
                                    domain.getUsedNumber().getUsedTimes().map(t -> t.v())));
    }

    // 介護休暇エラー情報
    private List<ChildCareNurseErrorsExport> createError(List<nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors> childCareNurseErrors) {

//      return childCareNurseErrors.stream().map(c ->
//                                                              ChildCareNurseErrors.of(
//                                                                      ChildCareNurseUsedNumber.of(
//                                                                              c.getUsedNumber().getUsedDay(),
//                                                                              c.getUsedNumber().getUsedTimes()),
//                                                                      c.getLimitDays().v(),
//                                                                      c.getYmd()))
//                                                      .collect(Collectors.toList());

        return childCareNurseErrors.stream().map(c ->
                                                            ChildCareNurseErrorsExport.of(
                                                                    ChildCareNurseUsedNumberExport.of(
                                                                            c.getUsedNumber().getUsedDay().v(),
                                                                            c.getUsedNumber().getUsedTimes().map(u -> u.v())),
                                                                    c.getLimitDays().v(),
                                                                    c.getYmd()))
                                                        .collect(Collectors.toList());
    }
}
