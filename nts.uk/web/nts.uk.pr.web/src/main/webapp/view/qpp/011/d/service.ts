module qpp011.d.service {
    var paths = {
        find: "pr/core/rule/law/tax/residential/output/find",
        add: "pr/core/rule/law/tax/residential/output/add",
        update: "pr/core/rule/law/tax/residential/output/update",
        findallresidential: "pr/core/residential/findallresidential"
    }

    /**
     * Get list payment date processing.
     */
    export function findresidentialTax(resimentTaxCode: string, yearMonth: string): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.find + "/" + resimentTaxCode + "/" + yearMonth)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function findAllResidential(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.findallresidential)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function add(residentialTax: model.residentialTax): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.add, residentialTax)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function update(residentialTax: model.residentialTax): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.update, residentialTax)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export module model {
        export class residentialTax {
            resimentTaxCode: string;
            yearMonth: number;
            taxPayRollMoney: string;
            taxBonusMoney: number;
            taxOverDueMoney: number;
            taxDemandChargeMoyney: number;
            address: string;
            dueDate: Date;
            headcount: number;
            retirementBonusAmout: number;
            cityTaxMoney: number;
            prefectureTaxMoney: number;
            constructor(resimentTaxCode: string, taxPayRollMoney: string, yearMonth: number, taxBonusMoney: number, taxOverDueMoney: number, taxDemandChargeMoyney: number, address: string, dueDate: Date, headcount: number, retirementBonusAmout: number, cityTaxMoney: number, prefectureTaxMoney: number) {
                this.resimentTaxCode = resimentTaxCode;
                this.taxPayRollMoney = taxPayRollMoney;
                this.yearMonth = yearMonth;
                this.taxBonusMoney = taxBonusMoney;
                this.taxOverDueMoney = taxOverDueMoney;
                this.taxDemandChargeMoyney = taxDemandChargeMoyney;
                this.address = address;
                this.dueDate = dueDate;
                this.headcount = headcount;
                this.retirementBonusAmout = retirementBonusAmout;
                this.cityTaxMoney = cityTaxMoney;
                this.prefectureTaxMoney = prefectureTaxMoney;
            }
        }
    }


}