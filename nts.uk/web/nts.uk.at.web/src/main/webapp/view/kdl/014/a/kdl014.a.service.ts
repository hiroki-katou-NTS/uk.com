module kdl014.a.service {
    var paths = {
        getStampByEmployeeCode: "at/record/stamp/getstampbyempcode"
    }
    /**
     * get list External Budget
     */
    export function getStampByCode(cardNumber, startDate, endDate): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getStampByEmployeeCode + "/" + cardNumber + "/" + startDate + "/" + endDate);
    }


    //    export module model {
    //        export class ExternalBudgetDto {
    //            externalBudgetCode: string;
    //            externalBudgetName: string;
    //            budgetAtr: number;
    //            unitAtr: number;
    //        }
    //    }
}