module qmm034.a.service {
    var paths = {
        getAllEras: "ctx/basic/era/finderas",
        getEraDetail: "ctx/basic/era/find/{startDate}",
        deleteEra: "ctx/basic/era/deleteData",
        updateEra: "ctx/basic/era/updateData",
        addEra: "ctx/basic/era/addData"
    }
    /**
     * get list era
     */
    export function getAllEras(): JQueryPromise<Array<model.EraDto>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getAllEras).done(function(res: Array<any>) {
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
    export function getEraDetail(): JQueryPromise<Array<model.EraDto>> {
        let dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getEraDetail)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
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
     * delete Data
     */    
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
//                dfd.reject(res);
//            })
//        return dfd.promise();
//    }
    
    export function addData(isCreated, command): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        let path = isCreated ? paths.addEra : paths.updateEra;
        nts.uk.request.ajax(path, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function deleteData(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
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
            startDate: string;
            endDate: string;
            fixAttribute: number;

            constructor(eraName: string, eraMark: string, startDate: string, endDate: string, fixAttribute: number) {
                this.eraName = eraName;
                this.eraMark = eraMark;
                this.startDate = startDate;
                this.endDate = endDate;
                this.fixAttribute = fixAttribute;
            }
        }
    }
}