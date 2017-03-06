module qmm034.a.service {
    var paths = {
        getAllEras: "ctx/basic/era/finderas",
        getEraDetail: "ctx/basic/era/find/{0}",
        deleteEra: "ctx/basic/era/deleteData",
        updateEra: "ctx/basic/era/updateData",
        addEra: "ctx/basic/era/addData"
    }
    /**
     * get list era
     */
    export function getAllEras(): JQueryPromise<Array<model.EraDto>> {
        let dfd = $.Deferred<Array<model.EraDto>>();
        nts.uk.request.ajax(paths.getAllEras).done(function(res: Array<model.EraDto>) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();

    }
    /**
     * get a company 
     */
    export function getEraDetail(startDate: Date): JQueryPromise<model.EraDto> {
        let dfd = $.Deferred<model.EraDto>();
        let self = this;
        let _path = nts.uk.text.format(paths.getEraDetail, startDate);

        nts.uk.request.ajax(_path).done(function(res: model.EraDto) {
            dfd.resolve(res);
        }).fail(function(res) {
            dfd.reject(res);
        })
        return dfd.promise();

    }
    /**
     * add Data
     */
    //     export function addData(layoutMaster: model.EraDto) {
    //        var dfd = $.Deferred<Array<any>>();
    //        nts.uk.request.ajax(paths.addEra, layoutMaster).done(function(res: Array<any>) {
    //         dfd.resolve(res);
    //        }).fail(function(res) {
    //            dfd.reject(res);
    //        })
    //        return dfd.promise();
    //    }
    /**
         * delete Data      */
    //    export function deleteData(layoutMaster: model.EraDto){
    //        var dfd = $.Deferred<Array<any>>();  
    //        nts.uk.request.ajax(paths.addCompany, layoutMaster).done(function(res: Array<any>){
    //            dfd.resolve(res);    
    //        }).fail(function(res){
    //            dfd.reject(res);
    //        })
    //        return dfd.promise(); 
    //        }
    /**
     * update era 
     */
    //    export function updateData(): JQueryPromise<Array<model.EraDto>> {
    //        let dfd = $.Deferred<Array<any>>();
    //        nts.uk.request.ajax(paths.updateEra)
    //            .done(function(res: Array<any>) {
    //                dfd.resolve(res);
    //            })
    //            .fail(function(res) {
    //                dfd.reject);
    //            })
    //        return dfd.promise();
    //   }

    export function addData(isCreated, command): JQueryPromise<Array<EraModel>> {
        let dfd = $.Deferred<Array<EraModel>>();
        let path = isCreated ? paths.addEra : paths.updateEra;
        nts.uk.request.ajax(path, command)
            .done(function(res: Array<EraModel>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function deleteData(command: model.EraDtoDelete): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        var dateObject = ko.mapping.toJS(command);
        nts.uk.request.ajax(paths.deleteEra, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export module model {
        export class EraDto {
            eraName: string;
            eraMark: string;
            startDate: Date;
            endDate: string;
            fixAttribute: number;

            constructor(eraName: string, eraMark: string, startDate: Date, endDate: string, fixAttribute: number) {
                this.eraName = eraName;
                this.eraMark = eraMark;
                this.startDate = startDate;
                this.endDate = endDate;
                this.fixAttribute = fixAttribute;
            }
        }
    }

    export module model {
        export class EraDtoDelete {
            startDate: Date;

            constructor(startDate: Date) {
                this.startDate = startDate;

            }
        }
    }

}