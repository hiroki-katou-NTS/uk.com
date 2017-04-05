module qmm003.a.service {
    var paths = {
        getResidentialTaxList: "pr/core/residential/findallresidential",
        getRegionPrefecture: "pr/core/residential/getlistLocation",
        getResidentialDetail: "pr/core/residential/findResidentialTax/{0}",
        addResidential: "pr/core/residential/addresidential",
        updateResidential: "pr/core/residential/updateresidential",
        deleteResidential: "pr/core/residential/deleteresidential"
    }

    /**
     * Get list residential date processing.
     */
    export function getResidentialTax(): JQueryPromise<Array<model.ResidentialTax>> {
        var dfd = $.Deferred<Array<qmm003.a.service.model.ResidentialTax>>();
        nts.uk.request.ajax(paths.getResidentialTaxList)
            .done(function(res: Array<qmm003.a.service.model.ResidentialTax>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function getRegionPrefecture(): JQueryPromise<Array<model.RegionObject>> {
        var dfd = $.Deferred<Array<model.RegionObject>>();
        nts.uk.request.ajax(paths.getRegionPrefecture)
            .done(function(res: Array<model.RegionObject>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function addResidential(residential: model.ResidentialTax) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.addResidential, residential).done(function(res: Array<any>) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function updateData(residential: model.ResidentialTax) {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.updateResidential, residential)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function deleteResidential(param: Array<string>) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.deleteResidential, { resiTaxCodes: param }).done(function(res: Array<any>) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function getResidentialTaxDetail(resiTaxCode: string): JQueryPromise<model.ResidentialTax> {
        var dfd = $.Deferred<qmm003.d.service.model.ResidentialTax>();
        var objectLayout = { resiTaxCode: resiTaxCode};
        var _path = nts.uk.text.format(paths.getResidentialDetail, resiTaxCode);
        nts.uk.request.ajax(_path)
            .done(function(res: model.ResidentialTax) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export module model {
        export class ResidentialTax {
            companyCode: string;
            resiTaxCode: string;
            resiTaxAutonomy: string;
            prefectureCode: string;
            resiTaxReportCode: string;
            registeredName: string;
            companyAccountNo: string;
            companySpecifiedNo: string;
            cordinatePostalCode: string;
            cordinatePostOffice: string;
            memo: string;
            contructor(companyCode: string, resiTaxCode: string, resiTaxAutonomy: string,
                prefectureCode: string, resiTaxReportCode: string,
                registeredName: string, companyAccountNo: string, companySpecifiedNo: string,
                cordinatePostalCode: string, cordinatePostOffice: string, memo: string) {
                this.companyCode = companyCode;
                this.resiTaxCode = resiTaxCode;
                this.resiTaxAutonomy = resiTaxAutonomy;
                this.prefectureCode = prefectureCode;
                this.resiTaxReportCode = resiTaxReportCode;
                this.registeredName = registeredName;
                this.companyAccountNo = companyAccountNo;
                this.companySpecifiedNo = companySpecifiedNo;
                this.cordinatePostalCode = cordinatePostalCode;
                this.cordinatePostOffice = cordinatePostOffice;
                this.memo = memo;
            }
        }
        export class RegionObject {
            regionCode: string;
            regionName: string;
            prefectures: Array<PrefectureObject>;
            contructor(regionCode: string, regionName: string, prefectures: Array<PrefectureObject>) {
                this.regionCode = regionCode;
                this.regionName = regionName;
                this.prefectures = prefectures;
            }
        }

        export class PrefectureObject {
            prefectureCode: string;
            prefectureName: string;
            contructor(prefectureCode: string, prefectureName: string) {
                this.prefectureCode = prefectureCode;
                this.prefectureName = prefectureName;
            }

        }

    }

}